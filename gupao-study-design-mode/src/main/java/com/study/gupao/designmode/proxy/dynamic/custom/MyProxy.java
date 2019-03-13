package com.study.gupao.designmode.proxy.dynamic.custom;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URISyntaxException;

public class MyProxy {
    private static String ln = "\r\n";

    private static String semicolon=";";

    public static Object newProxyInstance(MyClassLoader classLoader,Class<?>[] interfaces,MyInvocationHandler h){
        Object object = null;
        try {
            String filestr = generateCode(interfaces);

            String filePath =  MyProxy.class.getResource("").toURI().getPath();;

            System.out.println(filePath);
            File file = new File(filePath + File.separator + "$Proxy0.java");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(filestr.getBytes());
            fos.close();
            // 编译.class文件
            JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager standardFileManager = javaCompiler.getStandardFileManager(null, null,null);
            Iterable<? extends JavaFileObject> javaFileObjects = standardFileManager.getJavaFileObjects(file);
            JavaCompiler.CompilationTask task = javaCompiler.getTask(null, standardFileManager, null, null, null, javaFileObjects);
            task.call();
            standardFileManager.close();

            Class<?> clazz = classLoader.findClass(file.getName().substring(0, file.getName().indexOf(".")));
            System.out.println(clazz.getName());
            Constructor<?> constructor = clazz.getConstructor(MyInvocationHandler.class);
            file.delete();
            object = constructor.newInstance(h);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return object;
    }

    private static String generateCode(Class<?>[] interfaces) throws IllegalAccessException {
        if(interfaces == null || interfaces.length == 0){
            throw new IllegalAccessException("接口类不能为空");
        }
        StringBuffer sb = new StringBuffer("");
        sb.append("package com.study.gupao.designmode.proxy.dynamic.custom"+semicolon+ln);
        sb.append("import com.study.gupao.designmode.proxy.dynamic.custom.MyInvocationHandler"+semicolon+ln);
        sb.append("import java.lang.reflect.Method"+semicolon+ln);
        sb.append("public class $Proxy0 implements "+interfaces[0].getName() +"{"+ln);

        sb.append("    private MyInvocationHandler h"+semicolon+ln);

        sb.append("    public $Proxy0(MyInvocationHandler h){"+ln);
        sb.append("        this.h = h"+semicolon+ln);
        sb.append("    }"+ln);
        for(Method method : interfaces[0].getMethods()){
            Type genericReturnType = method.getGenericReturnType();
            String typeName = genericReturnType.getTypeName();
            sb.append("    public "+typeName+" "+method.getName()+"(){"+ln);
            sb.append("        try{"+ln);
            sb.append("            Method method = "+interfaces[0].getName()+".class.getMethod(\""+method.getName()+"\",new Class[]{})"+semicolon+ln);
            sb.append("            this.h.invoke(this,method,null)"+semicolon+ln);
            sb.append("        }catch(Throwable e){"+ln);
            sb.append("             e.printStackTrace()"+semicolon+ln);
            sb.append("        }"+ln);
            sb.append("    }"+ln);

        }

        sb.append("}");
        return sb.toString();
    }



}

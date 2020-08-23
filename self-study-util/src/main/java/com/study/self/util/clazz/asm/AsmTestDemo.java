package com.study.self.util.clazz.asm;

import javassist.bytecode.Opcode;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ASM写字节码文件
 */
public class AsmTestDemo {

    public static void main(String[] args) throws IOException {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classWriter.visit(Opcodes.V1_5,Opcodes.ACC_PUBLIC,"com/study/self/HelloAsm",null,"java/lang/Object",null);
        // 构造函数
        MethodVisitor constructionMethod = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        constructionMethod.visitVarInsn(Opcodes.ALOAD,0);
        constructionMethod.visitMethodInsn(Opcodes.INVOKESPECIAL,"java/lang/Object","<init>","()V");
        constructionMethod.visitInsn(Opcode.RETURN);
        constructionMethod.visitMaxs(1, 1);
        constructionMethod.visitEnd();
        // main 函数
        MethodVisitor methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC,"java/lang/System","out", "Ljava/io/PrintStream;");
        methodVisitor.visitLdcInsn("hello world");
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/io/PrintStream","println","(Ljava/lang/String;)V");
        methodVisitor.visitInsn(Opcodes.RETURN);
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();
        classWriter.visitEnd();
        byte[] bytes = classWriter.toByteArray();
        File filePath = new File("D:\\com\\study\\self");
        if(!filePath.exists()){
            filePath.mkdirs();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath.getAbsolutePath() + File.separator + "HelloAsm.class"));
        fileOutputStream.write(bytes);
        fileOutputStream.close();
    }
}

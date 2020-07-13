## 一、CGLIB 与JDK动态代理的区别

### 1、实现方式

* JDK动态代理：利用拦截器（拦截器必须实现`java.lang.reflect.InvocationHandler`）加上反射机制生成一个实现代理接口的匿名类，
  在调用具体方法前调用`InvocationHandler#invoke`来处理。

* CGLIB 动态代理：通过`net.sf.cglib.proxy.Enhancer`类创建代理类，设定回调类，回调类必须实现`net.sf.cglib.proxy.MethodInterceptor#intercept`方法。利用ASM开源包，对代理对象类的class文件加载进来，通过修改其字节码生成子类来处理。

### 2、何时使用JDK还是CGLiB

* 如果目标对象实现了接口，默认情况下会采用JDK的动态代理实现AOP。

* 如果目标对象实现了接口，可以强制使用CGLIB实现AOP。

* 如果目标对象没有实现了接口，必须采用CGLIB库，Spring会自动在JDK动态代理和CGLIB之间转换。

### 3、JDK动态代理和CGLIB字节码生成的区别？

* JDK动态代理只能对实现了接口的类生成代理，而不能针对类。

* CGLIB是针对类实现代理，主要是对指定的类生成一个子类，覆盖其中的方法，并覆盖其中方法实现增强，但是因为采用的是继承，所以该类或方法最好不要声明成final， 对于final类或方法，是无法继承的。

### 4、性能对比

* 使用CGLib实现动态代理，CGLib底层采用ASM字节码生成框架，使用字节码技术生成代理类，在jdk6之前比使用Java反射效率要高。唯一需要注意的是，CGLib不能对声明为final的方法进行代理，因为CGLib原理是动态生成被代理类的子类。

* 在jdk6、jdk7、jdk8逐步对JDK动态代理优化之后，在调用次数较少的情况下，JDK代理效率高于CGLIB代理效率，只有当进行大量调用的时候，jdk6和jdk7比CGLIB代理效率低一点，但是到jdk8的时候，jdk代理效率高于CGLIB代理，总之，每一次jdk[版本升级](https://www.baidu.com/s?wd=版本升级&tn=24004469_oem_dg&rsv_dl=gh_pl_sl_csd)，jdk代理效率都得到提升，相比之下使用JDK动态代理可能会获得更高的性能。
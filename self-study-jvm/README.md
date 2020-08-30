# JVM篇

## 引

计算机三大核心功能：方法调用，取指，运算。JVM是在计算机操作系统层面实现的虚拟机，因此他必须实现这三大核心功能。

注：

​		如果学习了汇编语言，会觉得，应用技术其实都一样，一直奉行的是CPU指令操作的基本理念。

## 方法调用

​		方法是程序执行的基本单元，又被成为函数，计算机要支持方法调用的原子指令。在Java中的原子指令是字节码，Java 方法是对字节码的封装，因此JVM必须支持对方法的调用。

### 汇编指令

***寄存器有待学习***

常用的汇编指令：

* movl 1, %eax  将自然数1传送到寄存器
* pop %eax  将栈顶数据从寄存器取出
* 运算指令
  * add 1，%eax    将自然数3与寄存器中的数累加，并将结果存储在eax寄存器中
  * inc %ebx 对寄存器中的数据加1

* 逻辑运算 （与，或，非，左移，右移，无符号右移）

  * shl %eax,1  将%eax左移一个二进位

  * and al,00111011B  与运算

    eg:  mov al,01100011B

    ​		and al,00111011B

    ​		结果：al=00100011B

  * or al,01100011B  或运算

* 串指令 
  * movs dst,src   串传送
  * cmps    串比较

* 无条件转移指令（JMP），条件转移指令，循环控制指令，中断指令（INT，INTO，IRET）
* 处理器控制指令 （基础决定上层建筑，Java线程的调度）
  * hlt 处理器暂停，直到出现中断或复位信号才继续
  * wait 当芯片引线test为高电平时使cpu进入等待状态
  * esc 转换到外处理器
  * lock 封锁线程
  * nop 空操作
  * stc , clc ....

### JVM指令

1. 数据交换指令
2. 函数调用指令
3. 运算指令
4. 控制转移指令
5. 对象创建与类型转换指令

## 取指

​		即取出指令，计算机在进入方法领域后，最终需要逐条取出指令并逐条执行。Java方法也是如此。因此JVM在执行Java方法时，也需要模拟CPU，能够对Java方法中的字节码指令逐条取出。

## 运算

计算机取出指令后，就要根据指令进行相应的逻辑运算，实现指令的功能。JVM作为虚拟机，也需要支持对Java字节码的运算能力



## JDK tools

JDK自带的命令，可以查看Java程序的进程信息，线程运行信息，GC信息，系统启动的配置信息等等

### JPS

打印正在运行的Java进程PID

option :

 [-q]   查看所有进程ID   [-mlvv] 查看执行Java执行程序的路径以及启动参数信息 等价于ps -ef|grep java(程序信息)

```
[root@localhost ~]# jps -mlvv
4642 org.apache.zookeeper.server.quorum.QuorumPeerMain /home/zookeeper-3.6.1/bin/../conf/zoo.cfg -Dzookeeper.log.dir=/home/zookeeper-3.6.1/bin/../logs -Dzookeeper.log.file=zookeeper--server-localhost.localdomain.log -Dzookeeper.root.logger=INFO,CONSOLE -XX:+HeapDumpOnOutOfMemoryError -XX:OnOutOfMemoryError=kill -9 %p -Xmx1000m -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.local.only=false
10418 sun.tools.jps.Jps -mlvv -Denv.class.path=.:/usr/java/jdk1.8.0_144/lib/dt.jar:/usr/java/jdk1.8.0_144/lib/tools.jar -Dapplication.home=/usr/java/jdk1.8.0_144 -Xms8m
10346 DeadLockDemo
```

# Jstack

打印进程中线程的状态，可排查死锁，线程等待等问题 

Options:
    -F  to force a thread dump. Use when jstack <pid> does not respond (process is hung)
    -m  to print both java and native frames (mixed mode)
    -l  long listing. Prints additional information about locks
    -h or -help to print this help message

eg::`com.study.selfs.jvm.DeadLockDemo`

```
[root@localhost deadlock]# jps
4642 QuorumPeerMain
11428 DeadLockDemo
11463 Jps
##打印进程的线程堆栈信息
[root@localhost deadlock]# jstack 11428
2020-08-30 21:42:47
Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.144-b01 mixed mode):

"Attach Listener" #19 daemon prio=9 os_prio=0 tid=0x00007f8cd4001000 nid=0x2d01 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"DestroyJavaVM" #18 prio=5 os_prio=0 tid=0x00007f8d20009000 nid=0x2ca5 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Thread-3" #16 prio=5 os_prio=0 tid=0x00007f8d2026b000 nid=0x2cbd waiting on condition [0x00007f8cf1b37000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at java.lang.Thread.sleep(Thread.java:340)
        at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
        at DeadLockDemo.lambda$checkDeadLock$0(DeadLockDemo.java:42)
        at DeadLockDemo$$Lambda$7/186276003.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:748)

"Thread-2" #15 prio=5 os_prio=0 tid=0x00007f8d20269000 nid=0x2cbc waiting for monitor entry [0x00007f8cf1c38000]
   java.lang.Thread.State: BLOCKED (on object monitor)
        at DeadLockDemo.lambda$deadLock$2(DeadLockDemo.java:79)
        - waiting to lock <0x00000000f6bc6b08> (a java.lang.Object)
        - locked <0x00000000f6bc6b18> (a java.lang.Object)
        at DeadLockDemo$$Lambda$6/1673605040.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:748)

"Thread-1" #14 prio=5 os_prio=0 tid=0x00007f8d20267800 nid=0x2cbb waiting for monitor entry [0x00007f8cf1d39000]
   java.lang.Thread.State: BLOCKED (on object monitor)
        at DeadLockDemo.lambda$deadLock$1(DeadLockDemo.java:66)
        - waiting to lock <0x00000000f6bc6b18> (a java.lang.Object)
        - locked <0x00000000f6bc6b08> (a java.lang.Object)
        at DeadLockDemo$$Lambda$5/1232367853.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:748)

"RMI TCP Accept-0" #13 daemon prio=5 os_prio=0 tid=0x00007f8d20260000 nid=0x2cb9 runnable [0x00007f8cf1f3b000]
   java.lang.Thread.State: RUNNABLE
        at java.net.PlainSocketImpl.socketAccept(Native Method)
        at java.net.AbstractPlainSocketImpl.accept(AbstractPlainSocketImpl.java:409)
        at java.net.ServerSocket.implAccept(ServerSocket.java:545)
        at java.net.ServerSocket.accept(ServerSocket.java:513)
        at sun.management.jmxremote.LocalRMIServerSocketFactory$1.accept(LocalRMIServerSocketFactory.java:52)
        at sun.rmi.transport.tcp.TCPTransport$AcceptLoop.executeAcceptLoop(TCPTransport.java:400)
        at sun.rmi.transport.tcp.TCPTransport$AcceptLoop.run(TCPTransport.java:372)
        at java.lang.Thread.run(Thread.java:748)

"RMI TCP Accept-1090" #12 daemon prio=5 os_prio=0 tid=0x00007f8d2024f800 nid=0x2cb8 runnable [0x00007f8cf203c000]
   java.lang.Thread.State: RUNNABLE
        at java.net.PlainSocketImpl.socketAccept(Native Method)
        at java.net.AbstractPlainSocketImpl.accept(AbstractPlainSocketImpl.java:409)
        at java.net.ServerSocket.implAccept(ServerSocket.java:545)
        at java.net.ServerSocket.accept(ServerSocket.java:513)
        at sun.rmi.transport.tcp.TCPTransport$AcceptLoop.executeAcceptLoop(TCPTransport.java:400)
        at sun.rmi.transport.tcp.TCPTransport$AcceptLoop.run(TCPTransport.java:372)
        at java.lang.Thread.run(Thread.java:748)

"RMI TCP Accept-0" #11 daemon prio=5 os_prio=0 tid=0x00007f8d2023a800 nid=0x2cb7 runnable [0x00007f8cf213d000]
   java.lang.Thread.State: RUNNABLE
        at java.net.PlainSocketImpl.socketAccept(Native Method)
        at java.net.AbstractPlainSocketImpl.accept(AbstractPlainSocketImpl.java:409)
        at java.net.ServerSocket.implAccept(ServerSocket.java:545)
        at java.net.ServerSocket.accept(ServerSocket.java:513)
        at sun.rmi.transport.tcp.TCPTransport$AcceptLoop.executeAcceptLoop(TCPTransport.java:400)
        at sun.rmi.transport.tcp.TCPTransport$AcceptLoop.run(TCPTransport.java:372)
        at java.lang.Thread.run(Thread.java:748)

"Service Thread" #9 daemon prio=9 os_prio=0 tid=0x00007f8d200c9000 nid=0x2cb6 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C1 CompilerThread3" #8 daemon prio=9 os_prio=0 tid=0x00007f8d200c6800 nid=0x2cb5 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread2" #7 daemon prio=9 os_prio=0 tid=0x00007f8d200c4000 nid=0x2cb4 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread1" #6 daemon prio=9 os_prio=0 tid=0x00007f8d200c2800 nid=0x2cb3 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread0" #5 daemon prio=9 os_prio=0 tid=0x00007f8d200bf800 nid=0x2cb2 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Signal Dispatcher" #4 daemon prio=9 os_prio=0 tid=0x00007f8d200be000 nid=0x2cb1 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Finalizer" #3 daemon prio=8 os_prio=0 tid=0x00007f8d2008b000 nid=0x2cb0 in Object.wait() [0x00007f8cf31f0000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x00000000f6808ec8> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:143)
        - locked <0x00000000f6808ec8> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:164)
        at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:209)

"Reference Handler" #2 daemon prio=10 os_prio=0 tid=0x00007f8d20086800 nid=0x2caf in Object.wait() [0x00007f8cf32f1000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x00000000f6806b68> (a java.lang.ref.Reference$Lock)
        at java.lang.Object.wait(Object.java:502)
        at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
        - locked <0x00000000f6806b68> (a java.lang.ref.Reference$Lock)
        at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)

"VM Thread" os_prio=0 tid=0x00007f8d2007f000 nid=0x2cae runnable 

"GC task thread#0 (ParallelGC)" os_prio=0 tid=0x00007f8d2001e000 nid=0x2ca6 runnable 

"GC task thread#1 (ParallelGC)" os_prio=0 tid=0x00007f8d20020000 nid=0x2ca7 runnable 

"GC task thread#2 (ParallelGC)" os_prio=0 tid=0x00007f8d20021800 nid=0x2ca8 runnable 

"GC task thread#3 (ParallelGC)" os_prio=0 tid=0x00007f8d20023800 nid=0x2ca9 runnable 

"GC task thread#4 (ParallelGC)" os_prio=0 tid=0x00007f8d20025000 nid=0x2caa runnable 

"GC task thread#5 (ParallelGC)" os_prio=0 tid=0x00007f8d20027000 nid=0x2cab runnable 

"GC task thread#6 (ParallelGC)" os_prio=0 tid=0x00007f8d20028800 nid=0x2cac runnable 

"GC task thread#7 (ParallelGC)" os_prio=0 tid=0x00007f8d2002a800 nid=0x2cad runnable 

"VM Periodic Task Thread" os_prio=0 tid=0x00007f8d20262000 nid=0x2cba waiting on condition 

JNI global references: 340


Found one Java-level deadlock:
=============================
"Thread-2":
  waiting to lock monitor 0x00007f8ce0006008 (object 0x00000000f6bc6b08, a java.lang.Object),
  which is held by "Thread-1"
"Thread-1":
  waiting to lock monitor 0x00007f8ce00062c8 (object 0x00000000f6bc6b18, a java.lang.Object),
  which is held by "Thread-2"

Java stack information for the threads listed above:
===================================================
"Thread-2":
        at DeadLockDemo.lambda$deadLock$2(DeadLockDemo.java:79)
        - waiting to lock <0x00000000f6bc6b08> (a java.lang.Object)
        - locked <0x00000000f6bc6b18> (a java.lang.Object)
        at DeadLockDemo$$Lambda$6/1673605040.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:748)
"Thread-1":
        at DeadLockDemo.lambda$deadLock$1(DeadLockDemo.java:66)
        - waiting to lock <0x00000000f6bc6b18> (a java.lang.Object)
        - locked <0x00000000f6bc6b08> (a java.lang.Object)
        at DeadLockDemo$$Lambda$5/1232367853.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:748)

Found 1 deadlock.
```



### Jmap

### 

### Jstat



### Jconsole



### jvisualvm










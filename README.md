# 引

当下互联网技术发展如此之快，以致无法跟上前进的步伐。作为对技术的爱好者，本项目库做为本人学习之用会持续更新，内容丰富，不仅包含概念和理论，还有对其技术实现的深刻认识，更多的是对技术的自我实现。

***每个模块下面有具体的 README 可供参考***

# JDK 

Java API Demo ，包含了Java中常用API Demo --`com.study`

## JDK1.5之前

* jdk5.beans   `java bean的定义`
* jdk5.io  ` IO`
* jdk5.lang  `class,comparable,integer,thread ...`
* jdk5.net `网络编程`
* jdk5.nio `NIO`
* jdk5.util `数据结构，工具类`
* jdk5.util.concurrent  `JUC`

## JDK1.6

* jdk6.util.concurrent  `JUC(LockSupport ...)`

## JDK1.7

* jdk6.util.concurrent  `JUC(ForkJoinPool, RecursiveAction ...)`

## JDK1.8

* jdk8.lang.functionalinterface `函数式编程（接口方法定义）`
* jdk8.util.function `函数式接口`
* jdk8.util.stream 流

# design-patterns  

常用的设计模式

* 适配器模式
* 装饰器模式
* 委派模式
* 策略模式
* 观察者模式（监听者）
* 代理模式
* 单例模式
* 模板方法模式
* 工厂模式和工厂方法模式



# ORM 框架
## Mybatis
ORM框架mybatis的案例，以及自我封装

# Dubbo
* Dubbo SPI
* Dubbo consumer and Dubbo provider
# SpringBoot
MVC 、REST、Reactive 
# 任务调度
## Quartz
任务调度框架Quartz
## xxx-job

# 缓存中间件（或NoSQL）
## Redis  
Redis 是完全开源的，遵守 BSD 协议，是一个高性能的 key-value 数据库。

Redis 与其他 key - value 缓存产品有以下三个特点：

* Redis支持数据的持久化，可以将内存中的数据保存在磁盘中，重启的时候可以再次加载进行使用。
* Redis不仅仅支持简单的key-value类型的数据，同时还提供list，set，zset，hash等数据结构的存储。
* Redis支持数据的备份，即master-slave模式的数据备份。

redis的学习项目，jedis api的使用，以及redis实现分布式锁的案例以及 BloomFilter案例  
## MongoDB
MongoDB是一个新的和普遍使用的数据库。 它是一个基于文档的非关系数据库提供程序。虽然它比传统的数据库快100倍，但早期说它将广泛地取代传统的RDBMS。 
但是，不可否认的是：在性能和可扩展性方面 MongoDB 有着明显的优势。 关系数据库具有典型的架构设计，可以显示表的数量以及这些表之间的关系，而在MongoDB中则没有关系的概念。

重点：WiredTiger存储引擎（MVCC, 快照隔离）， Journal(Write Ahead Log 简称WAL) 和CheckPoint机制
数据库 > 文档 > 集合 \
[参考文档](https://github.com/FanYingBo/spring-boot-bucket/tree/master/study-spring-boot/spring-boot-data/spring-data-mongodb)

# 配置中心和服务发现  
## Apache Zookeeper  
zookeeper的ZClient API 和Curator API 的使用案例   
## Apache Nacos

# 消息中间件
ActiveMQ , Kafka, RocketMQ 对比

| Messaging Product  | 	Client SDK	                   | Protocol and Specification   | 	Ordered Message	            | Scheduled Message              | 	Batched Message              | 	BroadCast Message                                | 	Message Filter                                        | 	Server Triggered Redelivery                                 | 	Message Storage                           | 	Message Retroactive                                   | 	Message Priority	                     |High Availability and Failover|	Message Track| 	Configuration |	Management and Operation Tools|
|--------------------|--------------------------------|------------------------------|------------------------------|--------------------------------|-------------------------------|---------------------------------------------------|--------------------------------------------------------|--------------------------------------------------------------|--------------------------------------------|--------------------------------------------------------|----------------------------------------|-----------------------------|-----------------|----------------|---------------------------------|
| ActiveMQ           | 	Java, .NET, C++ etc.          | 	Push model, support OpenWire, STOMP, AMQP, MQTT, JMS | 	Exclusive Consumer or Exclusive <br/>Queues can ensure ordering	 | Supported	                     | Not Supported	                | Supported	| Supported| 	Not Supported| 	Supports very fast persistence<br/> using JDBC along with a high performance journal，<br/>such as levelDB, kahaDB| 	Supported	| Supported	| Supported, depending on storage,<br/>if using levelDB it requires a ZooKeeper server | 	Not Supported	   | The default configuration is low level, <br/>user need to optimize the configuration<br/> parameters	 | Supported         |
| Kafka	| Java, Scala etc.	| Pull model, support TCP| 	Ensure ordering of messages within a partition	 | Not Supported	                                                                   | Supported, with async producer | 	Not Supported                                    | 	Supported, you can use Kafka Streams to filter messages	 | Not Supported                                   | 	High performance file storage             | 	Supported offset indicate	                     | Not Supported	                                              | Supported, requires a ZooKeeper server	Not                                                | Supported	                                                                      | Kafka uses key-value pairs format for configuration. These values can be supplied either from a file or programmatically.                                                                                                           | 	Supported, use terminal command to expose core metrics                                                                                                                                                                    |
|  RocketMQ	         | Java, C++, Go                  | 	Pull model, support TCP, JMS, OpenMessaging	 | Ensure strict ordering of messages,<br/>and can scale out gracefully | 	Supported	                    | Supported, with sync mode to avoid message loss | 	Supported	                                       | Supported,  property filter expressions based on SQL92	 | Supported	                                                                                                  | High performance and low latency file storage | 	Supported timestamp and offset two indicates          | 	Not Supported	                        | Supported, Master-Slave model, without another kit                                                                                                                                                           | 	Supported	                                                                                                               | Work out of box,user only need to pay attention to a few configurations                                                   | 	Supported, rich web and terminal command to expose core metrics                                                          |
## Kafka
Kafka是最初由Linkedin公司开发，是一个分布式、支持分区的（partition）、多副本的（replica），基于zookeeper协调的分布式消息系统，它的最大的特性就是可以实时的处理大量数据以满足各种需求场景：比如基于hadoop的批处理系统、低延迟的实时系统、storm/Spark流式处理引擎，web/nginx日志、访问日志，消息服务等等，用scala语言编写，Linkedin于2010年贡献给了Apache基金会并成为顶级开源 项目
Example:
kafka作为消息中间件的producer/consumer的客户端Demo
## ActiveMQ    
ActiveMQ 是Apache出品，最流行的，能力强劲的开源消息总线。ActiveMQ 是一个完全支持JMS1.1和J2EE 1.4规范的 JMS Provider实现，尽管JMS规范出台已经是很久的事情了，但是JMS在当今的J2EE应用中间仍然扮演着特殊的地位
## RocketMQ
消息队列RocketMQ版是阿里云基于Apache RocketMQ构建的低延迟、高并发、高可用、高可靠的分布式“消息、事件、流”统一处理平台，面向互联网分布式应用场景提供微服务异步解耦、流式数据处理、事件驱动处理等核心能力
## RabbitMQ

# 文件解析
## Xml-parser    
自定义实现基于spring的xml schema

# Guava
Google Guava 测试demo（RateLimter,BloomFilter Joiner...）

# Network-Program
## HTTP
## RMI
## Netty 

* Netty + ProtoBuf 实现服务端与客户端
* mainCmd subCmd 命令模式设计消息格式
* Netty 实现HTTP服务端
* TCP相关参数分析
# ShardingSphere
## 简介
ShardingSphere是一套开源的分布式数据库中间件解决方案组成的生态圈，
它由Sharding-JDBC、Sharding-Proxy和Sharding-Sidecar（计划中）
这3款相互独立的产品组成。 他们均提供标准化的数据分片、分布式事务和
数据库治理功能 \
[官方文档](https://shardingsphere.apache.org/document/legacy/3.x/document/cn/features/sharding/concept/sharding/)

## 概念&功能
### 数据分片
1. SQL
   * 逻辑表 \
   水平拆分的数据库表的相同逻辑和数据结构的总称
   * 真实表 \
   真实存储在数据库中的表
   * 数据节点 \
   数据分片的最小单元。由数据源名称和数据表组成
   * 绑定表 \
   指分片规则一致的主表和子表, 不会出现笛卡尔积
   * 广播表 \
   指所有的分片数据源中都存在的表，表结构和表中的数据在每个数据库中均完全一致。
   适用于数据量不大且需要与海量数据的表进行关联查询的场景
   * 逻辑索引 \
   逻辑索引用于同一个库不允许出现相同索引名称的分表场景，
   需要将同库不同表的索引名称改写为索引名 + 表名，改写之前的索引名称成为逻辑索引
2. 分片
   * 分片键 \
   用于分片的数据库字段，是将数据库(表)水平拆分的关键字段。例：将订单表中的订单主键的
   尾数取模分片，则订单主键为分片字段。 SQL中如果无分片字段，将执行全路由，性能较差。 
   除了对单分片字段的支持，ShardingSphere也支持根据多个字段进行分片
   * 分片算法 \
   通过分片算法将数据分片，支持通过=、BETWEEN和IN分片。分片算法需要应用方开发者自行实现，可实现的灵活度非常高。
   目前提供4种分片算法。由于分片算法和业务实现紧密相关，因此并未提供内置分片算法，而是通过分片策略将各种场景提
   炼出来，提供更高层级的抽象，并提供接口让应用开发者自行实现分片算法 \
        * 精确分片算法  PreciseShardingAlgorithm \
        * 范围分片算法  RangeShardingAlgorithm \
        * 复合分片算法  ComplexKeysShardingAlgorithm \
        * Hint分片算法  HintShardingAlgorithm  
   * 分片策略 \
        * 标准分片策略 \
        对应StandardShardingStrategy。提供对SQL语句中的=, IN和BETWEEN AND的分片操作支持。
        StandardShardingStrategy只支持单分片键，提供PreciseShardingAlgorithm和RangeShardingAlgorithm
        两个分片算法
        * 复合分片策略 \
        提供对SQL语句中的=, IN和BETWEEN AND的分片操作支持
        * 行表达式分片策略 \
        对应InlineShardingStrategy。使用Groovy的表达式，提供对SQL语句中的=和IN的分片操作支持，
        只支持单分片键。对于简单的分片算法，可以通过简单的配置使用，从而避免繁琐的Java代码开发，
        如: t_user_$->{u_id % 8} 表示t_user表根据u_id模8，而分成8张表，表名称为t_user_0到t_user_7
        * Hint分片策略 
   * 内核引擎 
        * 解析引擎 
        * 路由引擎 \
          a. 分片路由 \
          b. 广播路由 \
        * 改写引擎
        改写查询语句，将查询路由到正确的分片表。
        * 执行引擎
        * 归并引擎
### 读写分离
### 数据治理
### 分布式事务
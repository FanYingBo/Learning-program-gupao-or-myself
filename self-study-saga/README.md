# 简介

1987年普林斯顿大学的Hector Garcia-Molina和Kenneth Salem发表了一篇Paper Sagas，讲述的是如何处理long lived transaction（长活事务）。Saga是一个长活事务可被分解成可以交错运行的子事务集合。其中每个子事务都是一个保持数据库一致性的真实事务。
 论文地址：[sagas](https://www.cs.cornell.edu/andru/cs711/2002fa/reading/sagas.pdf)


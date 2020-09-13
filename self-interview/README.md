# 大文件查找问题

通过`com.study.self.interview.difficulties.filequery.GenerateFile` 生成文件

案例：`com.study.self.interview.difficulties.filequery.TestQueryLink` 查找

| 文件大小 | 内存大小 | 线程个数 | 耗时（ms） |
| -------- | -------- | -------- | ---------- |
| 125M     | 10M      | 8        | 3090       |
| 800M     | 10M      | 8        | 13777      |
| 800M     | 10M      | 4        | 11230      |

这里也证实一个问题，线程不是越多越好，线程上下文的切换会带来系统额外的性能开销










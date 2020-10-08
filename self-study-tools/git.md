# 创建GIT仓库

创建GIT用户：

```
$ groupadd git
$ useradd git -g git
```

创建证书登录：

```
$ cd /home/git/
$ mkdir .ssh
$ chmod 755 .ssh
$ touch .ssh/authorized_keys
$ chmod 644 .ssh/authorized_keys
```

初始化GIT仓库：

```
$ cd /home
$ mkdir gitrepo
$ chown git:git gitrepo/
$ cd gitrepo

$ git init --bare runoob.git	
Initialized empty Git repository in /home/gitrepo/runoob.git/
```

服务器上的Git仓库通常都以.git结尾，把仓库所属用户改为git

```shel
$ chown -R git:git runoob.git
```

克隆仓库到本地：

```shell
git clone git@192.168.8.156:/home/gitrepo/runoob.git	
```

# GIT常用命令

## Git 分支管理

创建分支命令：

```
git branch (branchname)
```

切换分支命令:

```
git checkout (branchname)
```

删除分支：

```
git branch -d (branchname)
```

合并分支：

* merge

```
git merge
```

* rebase

```
git rebase
```



分支冲突：

​		合并并不仅仅是简单的文件添加、移除的操作，Git 也会合并修改。

​		分支冲突需要手动处理冲突，保留想要的分支内容 `git add` 和`git commit` 提交修改后的内容到当前分支
# linux 添加用户、权限
#useradd –d /usr/sam -m sam
此命令创建了一个用户sam，其中-d和-m选项用来为登录名sam产生一个主目录/usr/sam（/usr为默认的用户主目录所在的父目录）。
假设当前用户是sam，则下面的命令修改该用户自己的口令：
#passwd
Old password:******
New password:*******
Re-enter new password:*******
如果是超级用户，可以用下列形式指定任何用户的口令：
#passwd sam
New password:*******
Re-enter new password:*******
>>参考1====================================
1、添加用户
首先用adduser命令添加一个普通用户，命令如下：
#adduser tommy  //添加一个名为tommy的用户
#passwd tommy   //修改密码
Changing password for user tommy.
New UNIX password:     //在这里输入新密码
Retype new UNIX password:  //再次输入新密码
passwd: all authentication tokens updated successfully.

# yum 命令

yum install 在线安装数据源

yum -y install yum-utils 下载 yum-utils

yumdownloader 下载数据源到本地

yum-complete-transaction 更新失败时，对进行的事务擦除

yum update 升级更新所有软件包
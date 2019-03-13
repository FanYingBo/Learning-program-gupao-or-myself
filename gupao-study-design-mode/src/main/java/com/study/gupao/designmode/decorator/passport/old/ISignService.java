package com.study.gupao.designmode.decorator.passport.old;

import com.study.gupao.designmode.decorator.passport.old.ResultMsg;

public interface ISignService {

    /**
     * 注册
     * @param username
     * @param passwd
     * @return
     */
    public ResultMsg resgist(String username,String passwd);
    /**
     * 登录
     * @param username
     * @param passwd
     * @return
     */
    public ResultMsg login(String username,String passwd);

}

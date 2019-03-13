package com.study.gupao.designmode.adapter.passport;

import com.study.gupao.designmode.adapter.ResultMsg;

public class SignService {

    /**
     * 注册
     * @param username
     * @param passwd
     * @return
     */
    public ResultMsg resgist(String username,String passwd){
        return new ResultMsg(200,"注册成功！");
    }

    /**
     * 登录
     * @param username
     * @param passwd
     * @return
     */
    public ResultMsg login(String username,String passwd){
        return new ResultMsg(200,"登陆成功！");
    }

}

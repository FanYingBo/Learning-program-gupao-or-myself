package com.study.gupao.designmode.decorator.passport.upgrade;

import com.study.gupao.designmode.decorator.passport.old.ResultMsg;
import com.study.gupao.designmode.decorator.passport.old.ISignService;

public interface ISignServiceUpgrade extends ISignService {

    /**
     * qq登录
     * @return
     */
    public ResultMsg loginByQQ(String openId);

    /**
     * 微信登录
     * @return
     */
    public ResultMsg loginByWeChat(String openId);

    /**
     * 手机登录
     * @return
     */
    public ResultMsg loginByTel(String tel,String code);

    /**
     * 注册后登陆
     * @param username
     * @param password
     * @return
     */
    public ResultMsg loginForRegist(String username,String password,String info);

}

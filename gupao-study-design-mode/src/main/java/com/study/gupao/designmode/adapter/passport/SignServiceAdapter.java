package com.study.gupao.designmode.adapter.passport;

import com.study.gupao.designmode.adapter.ResultMsg;

/**
 * 适配器 登录
 * 不改变代码
 */
public class SignServiceAdapter extends SignService {

    /**
     * qq登录
     * @return
     */
    public ResultMsg loginByQQ(String openId){
        // openId 为全局唯一 可以当做用户名
        // password 默认为null
        // 注册（在原有系统里面创建一个用户）
        // 调用原来的方法
        ResultMsg resgist = super.resgist(openId, null);
        ResultMsg login = super.login(openId, null);
        login.setInfo("QQ登录");
        return login;
    }

    /**
     * 微信登录
     * @return
     */
    public ResultMsg loginByWeChat(String openId){
        ResultMsg resgist = super.resgist(openId, null);
        ResultMsg login = super.login(openId, null);
        login.setInfo("微信登录");
        return login;
    }

    /**
     * 手机登录
     * @return
     */
    public ResultMsg loginByTel(String tel,String code){
        ResultMsg resgist = super.resgist(tel, null);
        ResultMsg login = super.login(tel, null);
        login.setInfo("手机登录");
        return login;
    }

    /**
     * 注册后登陆
     * @param username
     * @param password
     * @return
     */
    public ResultMsg loginForRegist(String username,String password){
        ResultMsg resgist = super.resgist(username, password);
        ResultMsg login = super.login(username, password);
        return login;
    }

}

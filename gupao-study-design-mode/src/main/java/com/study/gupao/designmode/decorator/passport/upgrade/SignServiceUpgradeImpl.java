package com.study.gupao.designmode.decorator.passport.upgrade;

import com.study.gupao.designmode.decorator.passport.old.ISignService;
import com.study.gupao.designmode.decorator.passport.old.ResultMsg;

public class SignServiceUpgradeImpl implements ISignServiceUpgrade{

    private ISignService signService;

    public SignServiceUpgradeImpl(){

    }

    public SignServiceUpgradeImpl (ISignService signService){
        this.signService = signService;
    }




    @Override
    public ResultMsg loginByQQ(String openId) {
       return loginForRegist(openId,null,"QQ登录");
    }

    @Override
    public ResultMsg loginByWeChat(String openId) {
        return loginForRegist(openId,null,"微信登录");
    }

    @Override
    public ResultMsg loginByTel(String tel, String code) {
        return loginForRegist(tel,null,"手机号登录");
    }

    @Override
    public ResultMsg loginForRegist(String username, String password,String info) {
        ResultMsg resgist = signService.resgist(username, password);
        resgist.setInfo("新的登录方式注册");
        System.out.println("正在注册。。。");
        System.out.println("注册成功。。。");
        System.out.println("正在登陆。。。");
        System.out.println(resgist);
        ResultMsg login = signService.login(username, password);
        login.setInfo(info);
        System.out.println(login);
        return login;
    }

    @Override
    public ResultMsg resgist(String username, String passwd) {
        ResultMsg resgist = signService.resgist(username, passwd);
        resgist.setInfo("旧的注册方式");
        System.out.println(resgist);
        return resgist;
    }

    @Override
    public ResultMsg login(String username, String passwd) {
        ResultMsg login = signService.login(username, passwd);
        login.setInfo("旧的登录方式");
        System.out.println(login);
        return login;
    }
}

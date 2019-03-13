package com.study.gupao.designmode.decorator.passport.old;

import com.study.gupao.designmode.decorator.passport.old.ResultMsg;

public class SignServiceImpl implements ISignService {
    @Override
    public ResultMsg resgist(String username, String passwd) {
        return new ResultMsg(200,"注册成功！");
    }

    @Override
    public ResultMsg login(String username, String passwd) {
        return new ResultMsg(200,"登陆成功！");
    }
}

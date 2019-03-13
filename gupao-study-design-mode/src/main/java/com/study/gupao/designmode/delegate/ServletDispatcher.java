package com.study.gupao.designmode.delegate;

import com.study.gupao.designmode.delegate.controllers.MemberAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ServletDispatcher {
    private List<Handler>  handles = new ArrayList<Handler>();

    public ServletDispatcher() {
        Class<?> clazz_1 = MemberAction.class;
        Class<?> clazz_2 = MemberAction.class;
        Class<?> clazz_3 = MemberAction.class;
        try {
            Object controller_1 = clazz_1.newInstance();
            Method method_1 = clazz_1.getMethod("getMemberById", new Class[]{int.class});
            Handler handler_1 = new Handler();
            handler_1.setController(controller_1);
            handler_1.setMethod(method_1);
            handler_1.setUrl("/webaction/membercontroller");
            Object controller_2 = clazz_2.newInstance();
            Method method_2 = clazz_2.getMethod("getMemberById", new Class[]{int.class});
            Handler handler_2 = new Handler();
            handler_2.setController(controller_2);
            handler_2.setMethod(method_2);
            handler_2.setUrl("/webaction/membercontroller");
            Object controller_3 = clazz_3.newInstance();
            Method method_3 = clazz_3.getMethod("getMemberById", new Class[]{int.class});
            Handler handler_3 = new Handler();
            handler_3.setController(controller_3);
            handler_3.setMethod(method_3);
            handler_3.setUrl("/webaction/membercontroller");

            handles.add(handler_1);
            handles.add(handler_2);
            handles.add(handler_3);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void doService(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        doDispatch(httpServletRequest,httpServletResponse);
    }

    private void doDispatch(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        // 通过请求获取url
        String requestURI = httpServletRequest.getRequestURI();

        // 通过url 获取对应的handle
        Method method = null;
        Object controller = null;
        for(Handler handler : this.handles){
            if(handler.getUrl().equals(requestURI)){
                method = handler.getMethod();
                controller = handler.getController();
                break;
            }
        }
        // 反射执行method response 写入执行结果
        try {
            if(method != null && controller != null){
                Object invoke = method.invoke(controller, new Object[]{11});
            }
        }catch (Exception e){

        }


    }


    class Handler{

        private Object controller;

        private Method method;

        private String url;

        public Object getController() {
            return controller;
        }

        public void setController(Object controller) {
            this.controller = controller;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}

package com.study.selfs.http.server.tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

public class EmbebTomcatServerDemo {

    private static final int DEFAULT_PORT = 8080;
    private static final String CONTEXT_PATH = "/tomcat";

    public static void main(String[] args) {
        Tomcat tomcat = new Tomcat();

        tomcat.setPort(DEFAULT_PORT);
        StandardContext  context = new StandardContext();

        context.addLifecycleListener(new Tomcat.FixContextListener());
        context.setPath(CONTEXT_PATH);

        Tomcat.addServlet(context,"index",IndexServlet.class.getName());
        context.addServletMappingDecoded("/index","index");
        tomcat.getHost().addChild(context);
        try {
            tomcat.init();
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}

package com.study.selfs.xml;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class CustomHandles extends NamespaceHandlerSupport {


    @Override
    public void init() {
        registerBeanDefinitionParser("genPath",new CustomBeanParserDefintions());
    }
}

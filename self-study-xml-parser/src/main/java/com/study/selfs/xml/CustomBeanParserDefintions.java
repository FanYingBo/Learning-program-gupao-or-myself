package com.study.selfs.xml;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.lang.Nullable;
import org.w3c.dom.Element;

public class CustomBeanParserDefintions implements BeanDefinitionParser {

    @Nullable
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition def = new RootBeanDefinition();

        // 设置Bean Class
        def.setBeanClass(CustomConfig.class);

        // 注册ID属性
        String tableName = element.getAttribute("tableName");
        BeanDefinitionHolder idHolder = new BeanDefinitionHolder(def, tableName);
        BeanDefinitionReaderUtils.registerBeanDefinition(idHolder,
                parserContext.getRegistry());

        // 注册属性
        String entityPath = element.getAttribute("entityPath");
        String mapperPath = element.getAttribute("mapperPath");

        BeanDefinitionHolder entityHolder = new BeanDefinitionHolder(def,
                entityPath);
        BeanDefinitionHolder mapperHolder = new BeanDefinitionHolder(def,
                mapperPath);

        BeanDefinitionReaderUtils.registerBeanDefinition(entityHolder,
                parserContext.getRegistry());
        BeanDefinitionReaderUtils.registerBeanDefinition(mapperHolder,
                parserContext.getRegistry());

        def.getPropertyValues().addPropertyValue("entityPath", entityPath);
        def.getPropertyValues().addPropertyValue("mapperPath", mapperPath);

        return def;
    }
}

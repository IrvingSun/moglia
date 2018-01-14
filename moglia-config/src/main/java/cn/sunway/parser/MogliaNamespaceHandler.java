package cn.sunway.parser;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by SUNWEI on 2018/1/11.
 */
public class MogliaNamespaceHandler extends NamespaceHandlerSupport {

    public void init() {
        registerBeanDefinitionParser("service",new ServiceBeanParser());
        registerBeanDefinitionParser("application",new ApplicationBeanParser());
    }
}

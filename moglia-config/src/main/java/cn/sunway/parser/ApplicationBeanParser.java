package cn.sunway.parser;

import cn.sunway.api.ApplicationBean;
import cn.sunway.api.ServiceBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * Created by SUNWEI on 2018/1/11.
 */
public class ApplicationBeanParser extends AbstractSingleBeanDefinitionParser {
    protected Class getBeanClass(Element element) {
        return ApplicationBean.class;
    }
    protected void doParse(Element element, BeanDefinitionBuilder bean) {
        String id = element.getAttribute("id");
        String port = element.getAttribute("port");

        if(!StringUtils.isEmpty(id)){
            bean.addPropertyValue("id",id);
        }
        if(!StringUtils.isEmpty(port)){
            bean.addPropertyValue("port",port);
        }
    }
}

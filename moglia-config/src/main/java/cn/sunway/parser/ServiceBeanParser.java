package cn.sunway.parser;

import cn.sunway.api.ServiceBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * Created by SUNWEI on 2018/1/11.
 */
public class ServiceBeanParser extends AbstractSingleBeanDefinitionParser {
    protected Class getBeanClass(Element element) {
        return ServiceBean.class;
    }
    protected void doParse(Element element, BeanDefinitionBuilder bean) {
        String id = element.getAttribute("id");
        String ref = element.getAttribute("ref");

        if(!StringUtils.isEmpty(id)){
            bean.addPropertyValue("id",id);
        }
        if(!StringUtils.isEmpty(ref)){
            bean.addPropertyReference("ref",ref);
        }
    }
}

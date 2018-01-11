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
        String anInterface = element.getAttribute("interface");
        String ref = element.getAttribute("ref");
        String port = element.getAttribute("port");

        if(!StringUtils.isEmpty(anInterface)){
            bean.addPropertyValue("interfaceName",anInterface);
        }
        if(!StringUtils.isEmpty(ref)){
            bean.addPropertyValue("ref",ref);
        }
        if(!StringUtils.isEmpty(port)){
            bean.addPropertyValue("port",port);
        }
    }
}

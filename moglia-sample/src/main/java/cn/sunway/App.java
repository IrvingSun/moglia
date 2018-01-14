package cn.sunway;

import cn.sunway.api.ServiceBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"provider-spring-config.xml"});
        context.start();
//        System.out.println(context.getBean(ServiceBean.class) );
    }
}

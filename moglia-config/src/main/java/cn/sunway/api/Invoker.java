package cn.sunway.api;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by SUNWEI on 2018/1/14.
 */
public class Invoker implements InvocationHandler {
    private Object object;

    public Invoker(Object obj){
        object = obj;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
//        System.out.println("Dynamic Proxy start -----");
        System.out.println(result = method.invoke(object, args));
//        System.out.println("Dynamic Proxy end -----");
        return result;
    }

    public Object getObject() {
        return object;
    }
}

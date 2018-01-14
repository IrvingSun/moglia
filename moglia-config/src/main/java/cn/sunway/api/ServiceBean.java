package cn.sunway.api;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by SUNWEI on 2018/1/11.
 */
public class ServiceBean {

    private String id;

    private Object ref;
    private Class<?> interfaceClass;

    private boolean exported;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getRef() {
        return ref;
    }

    public void setRef(Object ref) {
        this.ref = ref;
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    @Override
    public String toString() {
        return "[Moglia ServiceBean]: {"+
                "; serviceImpl:"+getRef().toString()
                +"}";

    }

}

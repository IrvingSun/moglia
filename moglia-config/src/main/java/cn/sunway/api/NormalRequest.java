package cn.sunway.api;

import java.io.Serializable;

/**
 * Created by SUNWEI on 2018/1/14.
 * moglia://interfaceName#refName#serviceImplClass#methodName#{param1:param1value@param2:param2value}
 */
public class NormalRequest implements Serializable{
    private String protocol;
    private String interfaceName;
    private String refName;
    private String serviceImplClass;
    private String methodName;
    private MethodParamPair[] methodParamPairs;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    public String getServiceImplClass() {
        return serviceImplClass;
    }

    public void setServiceImplClass(String serviceImplClass) {
        this.serviceImplClass = serviceImplClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public MethodParamPair[] getMethodParamPairs() {
        return methodParamPairs;
    }

    public void setMethodParamPairs(MethodParamPair[] methodParamPairs) {
        this.methodParamPairs = methodParamPairs;
    }
}

class MethodParamPair{
    private String param;
    private Object value;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

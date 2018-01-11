package cn.sunway.api;

/**
 * Created by SUNWEI on 2018/1/11.
 */
public class ServiceBean {
    private String port; //暴露服务的端口
    private String interfaceName;//接口名
    private String serviceImplName;//服务类名


    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getServiceImplName() {
        return serviceImplName;
    }

    public void setServiceImplName(String serviceImplName) {
        this.serviceImplName = serviceImplName;
    }
}

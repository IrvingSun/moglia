package cn.sunway.api;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SUNWEI on 2018/1/12.
 */
@Service
public class ApplicationBean implements InitializingBean,ApplicationListener,ApplicationContextAware {
    private String id;
    private String port;

    private ApplicationContext mogliaApplicationContext;
    private Map<String,Invoker> exportedService = new HashMap<String, Invoker>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void afterPropertiesSet() throws Exception {
        export();
    }

    public void onApplicationEvent(ApplicationEvent applicationEvent) {

    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.mogliaApplicationContext = applicationContext;
    }

    void export() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            String[] serviceBeans = mogliaApplicationContext.getBeanNamesForType(ServiceBean.class);

                for(String serviceBean: serviceBeans){

                    System.out.println("[Moglia]服务["+serviceBean+"]已经发布");
                    ServiceBean tmp = (ServiceBean)mogliaApplicationContext.getBean(serviceBean);
                    exportedService.put("serviceBean@"+tmp.getRef().getClass().getName(),new Invoker(mogliaApplicationContext.getBean(tmp.getId())));
            }
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new MogliaHandler(exportedService));
                        }
                    });

            //绑定端口、同步等待
            ChannelFuture futrue = serverBootstrap.bind(Integer.parseInt(port)).sync();
            //等待服务监听端口关闭
            futrue.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            //退出，释放线程等相关资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}

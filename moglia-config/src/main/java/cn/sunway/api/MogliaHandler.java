package cn.sunway.api;

import cn.sunway.exception.MogliaException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SUNWEI on 2018/1/14.
 */
public class MogliaHandler extends ChannelInboundHandlerAdapter {
    private Map<String,Invoker> exportedService = new HashMap<String, Invoker>();

    public MogliaHandler(Map<String, Invoker> exportedService) {
        this.exportedService = exportedService;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        byte [] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String requestUrl = new String(req,"UTF-8");
        /**
         * 解析参数
         *  eg:  moglia://refName#serviceImplClass#methodName#{param1:param1value@param2:param2value}
         */
        System.out.println("[Moglia]请求["+requestUrl+"]");

        NormalRequest normalRequest = new NormalRequest();
        String[] urls = requestUrl.split("://");
        normalRequest.setProtocol(urls[0]);

        String[] methodItems = urls[1].split("#");
        normalRequest.setRefName(methodItems[0]);
        normalRequest.setServiceImplClass(methodItems[1]);
        normalRequest.setMethodName(methodItems[2]);

        String[] methodParams = methodItems[3].substring(methodItems[3].indexOf("{")+1,methodItems[3].lastIndexOf("}")).split("@");

        if(methodParams != null && methodItems.length > 0){
            List<MethodParamPair> list = new ArrayList();

            for(String pair : methodParams){
                MethodParamPair methodParamPair = new MethodParamPair();
                String[] nameValue = pair.split(":");
                methodParamPair.setParam(nameValue[0]);
                //暂时只处理字符串类型参数
                methodParamPair.setValue(nameValue[1]);
                list.add(methodParamPair);
            }
            normalRequest.setMethodParamPairs(list.toArray(new MethodParamPair[list.size()]));
        }
        //触发方法
        Invoker invoker = exportedService.get("serviceBean@"+normalRequest.getServiceImplClass());
        if(invoker == null){
            throw  new MogliaException("服务提供者："+normalRequest.getServiceImplClass()+"不存在");
        }

        Class[] parameterTypes = new Class[normalRequest.getMethodParamPairs().length];
        Object[] params = new Object[normalRequest.getMethodParamPairs().length];

        for(int i = 0; i < normalRequest.getMethodParamPairs().length; i++){
            params[i] = normalRequest.getMethodParamPairs()[i].getValue();
            parameterTypes[i] = String.class;
        }
        //暂时只接收String类型的参数
        Method method = invoker.getObject().getClass().
                getDeclaredMethod(normalRequest.getMethodName(),parameterTypes);

        try {
            Object result = invoker.invoke(null,method,params);
            System.out.println("[Moglia]调用结果["+requestUrl+"] " +result);
            if(result == null){
                result = "null";
            }

            ByteBuf clientMessage = Unpooled.buffer(result.toString().getBytes().length);
            clientMessage.writeBytes(result.toString().getBytes());

            ctx.writeAndFlush(clientMessage);
            
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }
}

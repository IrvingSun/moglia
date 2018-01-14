package cn.sunway.api;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

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
         *  eg:  moglia://interfaceName#refName#serviceImplClass#methodName#{param1:param1value@param2:param2value}
         */
        System.out.println("[Moglia]请求["+requestUrl+"]");

        NormalRequest normalRequest = new NormalRequest();
        String[] urls = requestUrl.split("://");
        normalRequest.setProtocol(urls[0]);

        String[] methodItems = urls[1].split("#");
        normalRequest.setInterfaceName(methodItems[0]);
        normalRequest.setRefName(methodItems[1]);
        normalRequest.setServiceImplClass(methodItems[2]);
        normalRequest.setMethodName(methodItems[3]);

        String[] methodParams = methodItems[4].split("@");

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

    }


}

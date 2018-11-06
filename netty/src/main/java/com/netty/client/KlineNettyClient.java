package com.netty.client;

import android.text.TextUtils;

import com.netty.flatbuffers.FbBizMsg;
import com.netty.flatbuffers.FbKLineDataList;

import java.nio.ByteBuffer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * Created by Administrator on 2018/11/6.
 */

public class KlineNettyClient {

    private String ip;

    private int port;

    Bootstrap bootstrap;

    ChannelFuture future;
    private KlineListen klineListen;

    private EventExecutorGroup bizGroup = null;
    private static KlineNettyClient mNettyClient;

    public static KlineNettyClient getInstance() {
        if (mNettyClient == null) {
            mNettyClient = new KlineNettyClient();
        }
        return mNettyClient;
    }

    public KlineNettyClient() {
        bizGroup = new DefaultEventExecutorGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
//					ch.pipeline().addLast(new IdleStateHandler(60, 20, 0, TimeUnit.SECONDS));
//					ch.pipeline().addLast(new NettyHeartBeatDuplexHandler());
                    ch.pipeline().addLast(new NettyMessageEncoder());
                    ch.pipeline().addLast(new NettyMessageDecoder());
                    ch.pipeline().addLast(new NettyClientDuplexHandler(new NettyClientDuplexHandler.NettyClientDataListen() {
                        @Override
                        public void getData(NettyMessage message) {
                            FbBizMsg bizMsg = FbBizMsg.getRootAsFbBizMsg(message.getBizMsg());
                            String code = bizMsg.code();
                            String msg = bizMsg.msg();
                            ByteBuffer body = bizMsg.msgBodyAsByteBuffer();
                            switch (bizMsg.msgType()) {
                                case MsgConstants.KLINE:
                                    if(TextUtils.equals(code,MsgConstants.SUCCESS)){
                                        FbKLineDataList fbKLineDataList = FbKLineDataList.getRootAsFbKLineDataList(body);
                                        klineListen.success(fbKLineDataList);
                                    }else {
                                        klineListen.fail(msg);
                                    }
                                    break;
                            }

                        }
                    }));
                }
            });

            // future = b.connect(ip, port);
            bootstrap = b;
            // logger.info("成功连接到 {}:{}", ip, port);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    /**
     * 连接远程主机
     */
    public void connect() {
        mNettyClient.setIp("128.1.132.11");
        mNettyClient.setPort(8601);
        try {
            future = bootstrap.connect(ip, port).sync();
            System.out.println("成功连接到 {" + ip + "}:{" + port + "}");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void disConnect() {
        if (future != null) {
            future.channel().close();
            future = null;
        }
    }

    /**
     * 断开跟远程主机的连接，并关闭相应的线程等资源
     */
    public void close() {
        // 关闭业务线程池
        if (bizGroup != null) {
            bizGroup.shutdownGracefully();
        }
        if (future != null) {
            future.channel().close();
        }
        if (bootstrap != null) {
            if (bootstrap.config().group() != null) {
                bootstrap.config().group().shutdownGracefully();
            }
        }

    }

    public void sendQ(byte[] bts) {
        System.out.println("发送数据");
        mNettyClient.future.channel().writeAndFlush(bts);
    }

    public void getKline(KlineListen listen){
        this.klineListen = listen;
    }

    public interface KlineListen{
        void success(FbKLineDataList resp);
        void fail (String msg);
    }

}

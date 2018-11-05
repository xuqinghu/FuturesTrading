package com.netty.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * <p>
 * 负责业务处理的Handler，运行在业务线程组<code>DefaultEventExecutorGroup</code>中，以免任务太耗时而导致NIO线程阻塞；
 * 
 */
public class NettyClientDuplexHandler extends ChannelDuplexHandler {
	private static Logger logger = LoggerFactory.getLogger(NettyClientDuplexHandler.class);

	private AppBusinessProcessor bizProcessor = null;

	private NettyClientDataListen listen;

	public NettyClientDuplexHandler(NettyClientDataListen listen) {
		super();
		this.listen = listen;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage bizMsg = (NettyMessage) msg; // 拆分好的消息

		if (bizMsg.getHeader().trickCode() == MsgConstants.MA_HEARBEAT) {
			logger.info("收到心跳  -- {}", bizMsg.toString());
		} else {
			// 处理业务消息
			logger.info("收到消息  -- {}", bizMsg.toString());
			listen.getData(bizMsg);
		}
	}

	public interface NettyClientDataListen {
		void getData(NettyMessage bizMsg);
	}
}

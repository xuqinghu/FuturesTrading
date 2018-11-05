package com.netty.client;

import com.netty.flatbuffers.FbHeader;

import java.nio.ByteBuffer;

/**
 * <p>
 * 消息对象，根据协议定义消息格式。消息格式为：magicNumber+length+messageType+logId+flag+消息体byte[]，
 * 前16个字节分别对应4个整数字段，按大端存取数字,第17个字节通过0|1表示请求|响应；
 * 
 */
public class NettyMessage {

	private FbHeader header;
	private ByteBuffer bizMsg;

	public FbHeader getHeader() {
		return header;
	}

	public void setHeader(FbHeader header) {
		this.header = header;
	}

	public ByteBuffer getBizMsg() {
		return bizMsg;
	}

	public void setBizMsg(ByteBuffer bizMsg) {
		this.bizMsg = bizMsg;
	}

}

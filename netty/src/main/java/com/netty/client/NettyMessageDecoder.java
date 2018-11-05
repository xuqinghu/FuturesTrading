package com.netty.client;

import com.netty.flatbuffers.FbHeader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * <p>
 * 解码，将字节数组转换为NettyMessage对象
 * 
 */
public class NettyMessageDecoder extends ByteToMessageDecoder {

	private static Logger logger = LoggerFactory.getLogger(NettyMessageDecoder.class);
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// 不够报文头长度,返回
		if (in.readableBytes() < Constants.HEAD_LEN) {
			return;
		}
		
		in.markReaderIndex();
	    in.markWriterIndex();
	    try {
		    byte[] hdrbytes = new byte[16];
	        in.readBytes(hdrbytes);
	        ByteBuffer bb = ByteBuffer.wrap(hdrbytes);
	        bb.order(ByteOrder.LITTLE_ENDIAN);
	        FbHeader header = new FbHeader();
	        header.__init(0, bb);
	        
	        int length = header.msglen();
			
	        logger.info(Arrays.toString(hdrbytes));
			// 如果magicNumber对不上或者length为负数，那有可能是通过telnet随意输入的内容，直接丢弃处理，不需要重置readerIndex
			if ((header.trickCode() != MsgConstants.MA_BIZ && header.trickCode() != MsgConstants.MA_HEARBEAT) || length < 0) {
				logger.warn("非法输入,丢弃");
				return;
			}
			
			// 长度超过消息头长度,但是剩下的不够一个完整的报文,那么就重置readerIndex，返回等读取更多的数据再处理
			if (in.readableBytes() < length) {
				in.resetReaderIndex();
				return;
			}
			
			NettyMessage message = new NettyMessage();
			message.setHeader(header);
			if(length > 0) {
				byte[] msgArray = new byte[length];
				in.readBytes(msgArray);
				message.setBizMsg(ByteBuffer.wrap(msgArray));
			}
			
			out.add(message);
	    }catch (final Exception ex) {
    		logger.debug("Failed to parse ! postpone ...");
            in.resetReaderIndex();
            in.resetWriterIndex();
        }
	}
}

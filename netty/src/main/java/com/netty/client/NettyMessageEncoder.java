package com.netty.client;

import com.netty.flatbuffers.FbHeader;
import com.netty.flatbuffers.FlatBufferBuilder;

import java.util.List;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class NettyMessageEncoder extends MessageToMessageEncoder<byte[]> {

  @Override
  protected void encode(final ChannelHandlerContext ctx,
    final byte[] response,
    final List<Object> out) {

    final FlatBufferBuilder builder = new FlatBufferBuilder(20);
    int headerPosition = FbHeader.createFbHeader(builder, MsgConstants.MA_BIZ,
    		response.length, 0, 0);
    builder.finish(headerPosition);
    byte[] bytes = builder.sizedByteArray();

    byte[] dest = new byte[16];

    System.arraycopy(bytes, 4, dest, 0, 16);

    final ByteBuf byteBuf =
      ctx.alloc().heapBuffer().writeBytes(dest).writeBytes(response);

    out.add(byteBuf);
  }
  
}

// automatically generated by the FlatBuffers compiler, do not modify

package com.netty.flatbuffers;

import java.nio.*;
import java.lang.*;
import java.util.*;

/**
 *
 */
@SuppressWarnings("unused")
public final class FbMsgCaptchaResp extends Table {
  public static FbMsgCaptchaResp getRootAsFbMsgCaptchaResp(ByteBuffer _bb) { return getRootAsFbMsgCaptchaResp(_bb, new FbMsgCaptchaResp()); }
  public static FbMsgCaptchaResp getRootAsFbMsgCaptchaResp(ByteBuffer _bb, FbMsgCaptchaResp obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; }
  public FbMsgCaptchaResp __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  /**
   *
   */
  public String verifyKey() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer verifyKeyAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer verifyKeyInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }
  /**
   *
   */
  public String verifyCode() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer verifyCodeAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public ByteBuffer verifyCodeInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 6, 1); }

  public static int createFbMsgCaptchaResp(FlatBufferBuilder builder,
      int verifyKeyOffset,
      int verifyCodeOffset) {
    builder.startObject(2);
    FbMsgCaptchaResp.addVerifyCode(builder, verifyCodeOffset);
    FbMsgCaptchaResp.addVerifyKey(builder, verifyKeyOffset);
    return FbMsgCaptchaResp.endFbMsgCaptchaResp(builder);
  }

  public static void startFbMsgCaptchaResp(FlatBufferBuilder builder) { builder.startObject(2); }
  public static void addVerifyKey(FlatBufferBuilder builder, int verifyKeyOffset) { builder.addOffset(0, verifyKeyOffset, 0); }
  public static void addVerifyCode(FlatBufferBuilder builder, int verifyCodeOffset) { builder.addOffset(1, verifyCodeOffset, 0); }
  public static int endFbMsgCaptchaResp(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
  public static void finishFbMsgCaptchaRespBuffer(FlatBufferBuilder builder, int offset) { builder.finish(offset); }
  public static void finishSizePrefixedFbMsgCaptchaRespBuffer(FlatBufferBuilder builder, int offset) { builder.finishSizePrefixed(offset); }
}

// automatically generated by the FlatBuffers compiler, do not modify

package com.netty.flatbuffers;

import java.nio.*;
import java.lang.*;
import java.util.*;

@SuppressWarnings("unused")
public final class FbMsgLoginRsp extends Table {
  public static FbMsgLoginRsp getRootAsFbMsgLoginRsp(ByteBuffer _bb) { return getRootAsFbMsgLoginRsp(_bb, new FbMsgLoginRsp()); }
  public static FbMsgLoginRsp getRootAsFbMsgLoginRsp(ByteBuffer _bb, FbMsgLoginRsp obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; }
  public FbMsgLoginRsp __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  /**
   *token
   */
  public String token() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer tokenAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer tokenInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }
  public String taId() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer taIdAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public ByteBuffer taIdInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 6, 1); }
  public String followOrderNo() { int o = __offset(8); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer followOrderNoAsByteBuffer() { return __vector_as_bytebuffer(8, 1); }
  public ByteBuffer followOrderNoInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 8, 1); }
  public int lockScreenTime() { int o = __offset(10); return o != 0 ? bb.getInt(o + bb_pos) : 0; }

  public static int createFbMsgLoginRsp(FlatBufferBuilder builder,
      int tokenOffset,
      int taIdOffset,
      int followOrderNoOffset,
      int lockScreenTime) {
    builder.startObject(4);
    FbMsgLoginRsp.addLockScreenTime(builder, lockScreenTime);
    FbMsgLoginRsp.addFollowOrderNo(builder, followOrderNoOffset);
    FbMsgLoginRsp.addTaId(builder, taIdOffset);
    FbMsgLoginRsp.addToken(builder, tokenOffset);
    return FbMsgLoginRsp.endFbMsgLoginRsp(builder);
  }

  public static void startFbMsgLoginRsp(FlatBufferBuilder builder) { builder.startObject(4); }
  public static void addToken(FlatBufferBuilder builder, int tokenOffset) { builder.addOffset(0, tokenOffset, 0); }
  public static void addTaId(FlatBufferBuilder builder, int taIdOffset) { builder.addOffset(1, taIdOffset, 0); }
  public static void addFollowOrderNo(FlatBufferBuilder builder, int followOrderNoOffset) { builder.addOffset(2, followOrderNoOffset, 0); }
  public static void addLockScreenTime(FlatBufferBuilder builder, int lockScreenTime) { builder.addInt(3, lockScreenTime, 0); }
  public static int endFbMsgLoginRsp(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
  public static void finishFbMsgLoginRspBuffer(FlatBufferBuilder builder, int offset) { builder.finish(offset); }
  public static void finishSizePrefixedFbMsgLoginRspBuffer(FlatBufferBuilder builder, int offset) { builder.finishSizePrefixed(offset); }
}


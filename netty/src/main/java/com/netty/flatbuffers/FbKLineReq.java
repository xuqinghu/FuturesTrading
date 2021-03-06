// automatically generated by the FlatBuffers compiler, do not modify

package com.netty.flatbuffers;

import java.nio.*;
import java.lang.*;

@SuppressWarnings("unused")
public final class FbKLineReq extends Table {
  public static FbKLineReq getRootAsFbKLineReq(ByteBuffer _bb) { return getRootAsFbKLineReq(_bb, new FbKLineReq()); }
  public static FbKLineReq getRootAsFbKLineReq(ByteBuffer _bb, FbKLineReq obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; }
  public FbKLineReq __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String ContractNo() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer ContractNoAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer ContractNoInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }
  public String LineType() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer LineTypeAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public ByteBuffer LineTypeInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 6, 1); }
  public String RangeType() { int o = __offset(8); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer RangeTypeAsByteBuffer() { return __vector_as_bytebuffer(8, 1); }
  public ByteBuffer RangeTypeInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 8, 1); }

  public static int createFbKLineReq(FlatBufferBuilder builder,
      int ContractNoOffset,
      int LineTypeOffset,
      int RangeTypeOffset) {
    builder.startObject(3);
    FbKLineReq.addRangeType(builder, RangeTypeOffset);
    FbKLineReq.addLineType(builder, LineTypeOffset);
    FbKLineReq.addContractNo(builder, ContractNoOffset);
    return FbKLineReq.endFbKLineReq(builder);
  }

  public static void startFbKLineReq(FlatBufferBuilder builder) { builder.startObject(3); }
  public static void addContractNo(FlatBufferBuilder builder, int ContractNoOffset) { builder.addOffset(0, ContractNoOffset, 0); }
  public static void addLineType(FlatBufferBuilder builder, int LineTypeOffset) { builder.addOffset(1, LineTypeOffset, 0); }
  public static void addRangeType(FlatBufferBuilder builder, int RangeTypeOffset) { builder.addOffset(2, RangeTypeOffset, 0); }
  public static int endFbKLineReq(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
}


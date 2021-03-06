// automatically generated by the FlatBuffers compiler, do not modify

package com.netty.flatbuffers;

import java.nio.*;
import java.lang.*;
import java.util.*;

@SuppressWarnings("unused")
public final class FbMsgOrderReq extends Table {
  public static FbMsgOrderReq getRootAsFbMsgOrderReq(ByteBuffer _bb) { return getRootAsFbMsgOrderReq(_bb, new FbMsgOrderReq()); }
  public static FbMsgOrderReq getRootAsFbMsgOrderReq(ByteBuffer _bb, FbMsgOrderReq obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; }
  public FbMsgOrderReq __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String localOrderId() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer localOrderIdAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer localOrderIdInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }
  public String good() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer goodAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public ByteBuffer goodInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 6, 1); }
  public String contract() { int o = __offset(8); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer contractAsByteBuffer() { return __vector_as_bytebuffer(8, 1); }
  public ByteBuffer contractInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 8, 1); }
  public byte openClose() { int o = __offset(10); return o != 0 ? bb.get(o + bb_pos) : 0; }
  public byte orderType() { int o = __offset(12); return o != 0 ? bb.get(o + bb_pos) : 0; }
  public byte side() { int o = __offset(14); return o != 0 ? bb.get(o + bb_pos) : 0; }
  public double price() { int o = __offset(16); return o != 0 ? bb.getDouble(o + bb_pos) : 0.0; }
  public int quantity() { int o = __offset(18); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public byte source() { int o = __offset(20); return o != 0 ? bb.get(o + bb_pos) : 0; }
  public long holdingDetailId() { int o = __offset(22); return o != 0 ? bb.getLong(o + bb_pos) : 0L; }
  public long gdWeituoId() { int o = __offset(24); return o != 0 ? bb.getLong(o + bb_pos) : 0L; }
  public long gdTaId() { int o = __offset(26); return o != 0 ? bb.getLong(o + bb_pos) : 0L; }
  public double priceZy() { int o = __offset(28); return o != 0 ? bb.getDouble(o + bb_pos) : 0.0; }
  public double priceZs() { int o = __offset(30); return o != 0 ? bb.getDouble(o + bb_pos) : 0.0; }

  public static int createFbMsgOrderReq(FlatBufferBuilder builder,
      int localOrderIdOffset,
      int goodOffset,
      int contractOffset,
      byte openClose,
      byte orderType,
      byte side,
      double price,
      int quantity,
      byte source,
      long holdingDetailId,
      long gdWeituoId,
      long gdTaId,
      double priceZy,
      double priceZs) {
    builder.startObject(14);
    FbMsgOrderReq.addPriceZs(builder, priceZs);
    FbMsgOrderReq.addPriceZy(builder, priceZy);
    FbMsgOrderReq.addGdTaId(builder, gdTaId);
    FbMsgOrderReq.addGdWeituoId(builder, gdWeituoId);
    FbMsgOrderReq.addHoldingDetailId(builder, holdingDetailId);
    FbMsgOrderReq.addPrice(builder, price);
    FbMsgOrderReq.addQuantity(builder, quantity);
    FbMsgOrderReq.addContract(builder, contractOffset);
    FbMsgOrderReq.addGood(builder, goodOffset);
    FbMsgOrderReq.addLocalOrderId(builder, localOrderIdOffset);
    FbMsgOrderReq.addSource(builder, source);
    FbMsgOrderReq.addSide(builder, side);
    FbMsgOrderReq.addOrderType(builder, orderType);
    FbMsgOrderReq.addOpenClose(builder, openClose);
    return FbMsgOrderReq.endFbMsgOrderReq(builder);
  }

  public static void startFbMsgOrderReq(FlatBufferBuilder builder) { builder.startObject(14); }
  public static void addLocalOrderId(FlatBufferBuilder builder, int localOrderIdOffset) { builder.addOffset(0, localOrderIdOffset, 0); }
  public static void addGood(FlatBufferBuilder builder, int goodOffset) { builder.addOffset(1, goodOffset, 0); }
  public static void addContract(FlatBufferBuilder builder, int contractOffset) { builder.addOffset(2, contractOffset, 0); }
  public static void addOpenClose(FlatBufferBuilder builder, byte openClose) { builder.addByte(3, openClose, 0); }
  public static void addOrderType(FlatBufferBuilder builder, byte orderType) { builder.addByte(4, orderType, 0); }
  public static void addSide(FlatBufferBuilder builder, byte side) { builder.addByte(5, side, 0); }
  public static void addPrice(FlatBufferBuilder builder, double price) { builder.addDouble(6, price, 0.0); }
  public static void addQuantity(FlatBufferBuilder builder, int quantity) { builder.addInt(7, quantity, 0); }
  public static void addSource(FlatBufferBuilder builder, byte source) { builder.addByte(8, source, 0); }
  public static void addHoldingDetailId(FlatBufferBuilder builder, long holdingDetailId) { builder.addLong(9, holdingDetailId, 0L); }
  public static void addGdWeituoId(FlatBufferBuilder builder, long gdWeituoId) { builder.addLong(10, gdWeituoId, 0L); }
  public static void addGdTaId(FlatBufferBuilder builder, long gdTaId) { builder.addLong(11, gdTaId, 0L); }
  public static void addPriceZy(FlatBufferBuilder builder, double priceZy) { builder.addDouble(12, priceZy, 0.0); }
  public static void addPriceZs(FlatBufferBuilder builder, double priceZs) { builder.addDouble(13, priceZs, 0.0); }
  public static int endFbMsgOrderReq(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
  public static void finishFbMsgOrderReqBuffer(FlatBufferBuilder builder, int offset) { builder.finish(offset); }
  public static void finishSizePrefixedFbMsgOrderReqBuffer(FlatBufferBuilder builder, int offset) { builder.finishSizePrefixed(offset); }
}


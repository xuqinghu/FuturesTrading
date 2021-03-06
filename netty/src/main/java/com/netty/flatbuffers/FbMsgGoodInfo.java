// automatically generated by the FlatBuffers compiler, do not modify

package com.netty.flatbuffers;

import java.nio.*;
import java.lang.*;

@SuppressWarnings("unused")
public final class FbMsgGoodInfo extends Table {
  public static FbMsgGoodInfo getRootAsFbMsgGoodInfo(ByteBuffer _bb) { return getRootAsFbMsgGoodInfo(_bb, new FbMsgGoodInfo()); }
  public static FbMsgGoodInfo getRootAsFbMsgGoodInfo(ByteBuffer _bb, FbMsgGoodInfo obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; }
  public FbMsgGoodInfo __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String goodsCode() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer goodsCodeAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer goodsCodeInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }
  public String goodsName() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer goodsNameAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public ByteBuffer goodsNameInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 6, 1); }
  public String marketCode() { int o = __offset(8); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer marketCodeAsByteBuffer() { return __vector_as_bytebuffer(8, 1); }
  public ByteBuffer marketCodeInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 8, 1); }
  public String marketName() { int o = __offset(10); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer marketNameAsByteBuffer() { return __vector_as_bytebuffer(10, 1); }
  public ByteBuffer marketNameInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 10, 1); }
  public String currency() { int o = __offset(12); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer currencyAsByteBuffer() { return __vector_as_bytebuffer(12, 1); }
  public ByteBuffer currencyInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 12, 1); }
  public double contractMultiplier() { int o = __offset(14); return o != 0 ? bb.getDouble(o + bb_pos) : 0.0; }
  public double minChange() { int o = __offset(16); return o != 0 ? bb.getDouble(o + bb_pos) : 0.0; }
  public double priceUnit() { int o = __offset(18); return o != 0 ? bb.getDouble(o + bb_pos) : 0.0; }
  public double priceUnitBase() { int o = __offset(20); return o != 0 ? bb.getDouble(o + bb_pos) : 0.0; }
  public String tradeTime() { int o = __offset(22); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer tradeTimeAsByteBuffer() { return __vector_as_bytebuffer(22, 1); }
  public ByteBuffer tradeTimeInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 22, 1); }
  public double bzj() { int o = __offset(24); return o != 0 ? bb.getDouble(o + bb_pos) : 0.0; }
  public double gyBzj() { int o = __offset(26); return o != 0 ? bb.getDouble(o + bb_pos) : 0.0; }

  public static int createFbMsgGoodInfo(FlatBufferBuilder builder,
      int goodsCodeOffset,
      int goodsNameOffset,
      int marketCodeOffset,
      int marketNameOffset,
      int currencyOffset,
      double contractMultiplier,
      double minChange,
      double priceUnit,
      double priceUnitBase,
      int tradeTimeOffset,
      double bzj,
      double gyBzj) {
    builder.startObject(12);
    FbMsgGoodInfo.addGyBzj(builder, gyBzj);
    FbMsgGoodInfo.addBzj(builder, bzj);
    FbMsgGoodInfo.addPriceUnitBase(builder, priceUnitBase);
    FbMsgGoodInfo.addPriceUnit(builder, priceUnit);
    FbMsgGoodInfo.addMinChange(builder, minChange);
    FbMsgGoodInfo.addContractMultiplier(builder, contractMultiplier);
    FbMsgGoodInfo.addTradeTime(builder, tradeTimeOffset);
    FbMsgGoodInfo.addCurrency(builder, currencyOffset);
    FbMsgGoodInfo.addMarketName(builder, marketNameOffset);
    FbMsgGoodInfo.addMarketCode(builder, marketCodeOffset);
    FbMsgGoodInfo.addGoodsName(builder, goodsNameOffset);
    FbMsgGoodInfo.addGoodsCode(builder, goodsCodeOffset);
    return FbMsgGoodInfo.endFbMsgGoodInfo(builder);
  }

  public static void startFbMsgGoodInfo(FlatBufferBuilder builder) { builder.startObject(12); }
  public static void addGoodsCode(FlatBufferBuilder builder, int goodsCodeOffset) { builder.addOffset(0, goodsCodeOffset, 0); }
  public static void addGoodsName(FlatBufferBuilder builder, int goodsNameOffset) { builder.addOffset(1, goodsNameOffset, 0); }
  public static void addMarketCode(FlatBufferBuilder builder, int marketCodeOffset) { builder.addOffset(2, marketCodeOffset, 0); }
  public static void addMarketName(FlatBufferBuilder builder, int marketNameOffset) { builder.addOffset(3, marketNameOffset, 0); }
  public static void addCurrency(FlatBufferBuilder builder, int currencyOffset) { builder.addOffset(4, currencyOffset, 0); }
  public static void addContractMultiplier(FlatBufferBuilder builder, double contractMultiplier) { builder.addDouble(5, contractMultiplier, 0.0); }
  public static void addMinChange(FlatBufferBuilder builder, double minChange) { builder.addDouble(6, minChange, 0.0); }
  public static void addPriceUnit(FlatBufferBuilder builder, double priceUnit) { builder.addDouble(7, priceUnit, 0.0); }
  public static void addPriceUnitBase(FlatBufferBuilder builder, double priceUnitBase) { builder.addDouble(8, priceUnitBase, 0.0); }
  public static void addTradeTime(FlatBufferBuilder builder, int tradeTimeOffset) { builder.addOffset(9, tradeTimeOffset, 0); }
  public static void addBzj(FlatBufferBuilder builder, double bzj) { builder.addDouble(10, bzj, 0.0); }
  public static void addGyBzj(FlatBufferBuilder builder, double gyBzj) { builder.addDouble(11, gyBzj, 0.0); }
  public static int endFbMsgGoodInfo(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
}


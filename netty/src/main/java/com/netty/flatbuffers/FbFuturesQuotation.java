// automatically generated by the FlatBuffers compiler, do not modify

package com.netty.flatbuffers;

import java.nio.*;
import java.lang.*;
import java.util.*;

@SuppressWarnings("unused")
public final class FbFuturesQuotation extends Table {
  public static FbFuturesQuotation getRootAsFbFuturesQuotation(ByteBuffer _bb) { return getRootAsFbFuturesQuotation(_bb, new FbFuturesQuotation()); }
  public static FbFuturesQuotation getRootAsFbFuturesQuotation(ByteBuffer _bb, FbFuturesQuotation obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; }
  public FbFuturesQuotation __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String DateTime() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer DateTimeAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer DateTimeInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }
  public String GoodsNo() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer GoodsNoAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public ByteBuffer GoodsNoInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 6, 1); }
  public String ContractNo() { int o = __offset(8); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer ContractNoAsByteBuffer() { return __vector_as_bytebuffer(8, 1); }
  public ByteBuffer ContractNoInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 8, 1); }
  public float LastPrice() { int o = __offset(10); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public float ChangeValue() { int o = __offset(12); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public float ChangeRate() { int o = __offset(14); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public float PreSettlementPrice() { int o = __offset(16); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public float PreClosePrice() { int o = __offset(18); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public float OpenPrice() { int o = __offset(20); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public float HighestPrice() { int o = __offset(22); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public float LowestPrice() { int o = __offset(24); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public long Volume() { int o = __offset(26); return o != 0 ? bb.getLong(o + bb_pos) : 0L; }
  public long OpenInterest() { int o = __offset(28); return o != 0 ? bb.getLong(o + bb_pos) : 0L; }
  public float ClosePrice() { int o = __offset(30); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public float SettlementPrice() { int o = __offset(32); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public float BidPrice1() { int o = __offset(34); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public long BidVolume1() { int o = __offset(36); return o != 0 ? bb.getLong(o + bb_pos) : 0L; }
  public float BidPrice2() { int o = __offset(38); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public long BidVolume2() { int o = __offset(40); return o != 0 ? bb.getLong(o + bb_pos) : 0L; }
  public float BidPrice3() { int o = __offset(42); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public long BidVolume3() { int o = __offset(44); return o != 0 ? bb.getLong(o + bb_pos) : 0L; }
  public float BidPrice4() { int o = __offset(46); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public long BidVolume4() { int o = __offset(48); return o != 0 ? bb.getLong(o + bb_pos) : 0L; }
  public float BidPrice5() { int o = __offset(50); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public long BidVolume5() { int o = __offset(52); return o != 0 ? bb.getLong(o + bb_pos) : 0L; }
  public float AskPrice1() { int o = __offset(54); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public long AskVolume1() { int o = __offset(56); return o != 0 ? bb.getLong(o + bb_pos) : 0L; }
  public float AskPrice2() { int o = __offset(58); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public long AskVolume2() { int o = __offset(60); return o != 0 ? bb.getLong(o + bb_pos) : 0L; }
  public float AskPrice3() { int o = __offset(62); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public long AskVolume3() { int o = __offset(64); return o != 0 ? bb.getLong(o + bb_pos) : 0L; }
  public float AskPrice4() { int o = __offset(66); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public long AskVolume4() { int o = __offset(68); return o != 0 ? bb.getLong(o + bb_pos) : 0L; }
  public float AskPrice5() { int o = __offset(70); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public long AskVolume5() { int o = __offset(72); return o != 0 ? bb.getLong(o + bb_pos) : 0L; }
  public float AveragePrice() { int o = __offset(74); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }

  public static int createFbFuturesQuotation(FlatBufferBuilder builder,
      int DateTimeOffset,
      int GoodsNoOffset,
      int ContractNoOffset,
      float LastPrice,
      float ChangeValue,
      float ChangeRate,
      float PreSettlementPrice,
      float PreClosePrice,
      float OpenPrice,
      float HighestPrice,
      float LowestPrice,
      long Volume,
      long OpenInterest,
      float ClosePrice,
      float SettlementPrice,
      float BidPrice1,
      long BidVolume1,
      float BidPrice2,
      long BidVolume2,
      float BidPrice3,
      long BidVolume3,
      float BidPrice4,
      long BidVolume4,
      float BidPrice5,
      long BidVolume5,
      float AskPrice1,
      long AskVolume1,
      float AskPrice2,
      long AskVolume2,
      float AskPrice3,
      long AskVolume3,
      float AskPrice4,
      long AskVolume4,
      float AskPrice5,
      long AskVolume5,
      float AveragePrice) {
    builder.startObject(36);
    FbFuturesQuotation.addAskVolume5(builder, AskVolume5);
    FbFuturesQuotation.addAskVolume4(builder, AskVolume4);
    FbFuturesQuotation.addAskVolume3(builder, AskVolume3);
    FbFuturesQuotation.addAskVolume2(builder, AskVolume2);
    FbFuturesQuotation.addAskVolume1(builder, AskVolume1);
    FbFuturesQuotation.addBidVolume5(builder, BidVolume5);
    FbFuturesQuotation.addBidVolume4(builder, BidVolume4);
    FbFuturesQuotation.addBidVolume3(builder, BidVolume3);
    FbFuturesQuotation.addBidVolume2(builder, BidVolume2);
    FbFuturesQuotation.addBidVolume1(builder, BidVolume1);
    FbFuturesQuotation.addOpenInterest(builder, OpenInterest);
    FbFuturesQuotation.addVolume(builder, Volume);
    FbFuturesQuotation.addAveragePrice(builder, AveragePrice);
    FbFuturesQuotation.addAskPrice5(builder, AskPrice5);
    FbFuturesQuotation.addAskPrice4(builder, AskPrice4);
    FbFuturesQuotation.addAskPrice3(builder, AskPrice3);
    FbFuturesQuotation.addAskPrice2(builder, AskPrice2);
    FbFuturesQuotation.addAskPrice1(builder, AskPrice1);
    FbFuturesQuotation.addBidPrice5(builder, BidPrice5);
    FbFuturesQuotation.addBidPrice4(builder, BidPrice4);
    FbFuturesQuotation.addBidPrice3(builder, BidPrice3);
    FbFuturesQuotation.addBidPrice2(builder, BidPrice2);
    FbFuturesQuotation.addBidPrice1(builder, BidPrice1);
    FbFuturesQuotation.addSettlementPrice(builder, SettlementPrice);
    FbFuturesQuotation.addClosePrice(builder, ClosePrice);
    FbFuturesQuotation.addLowestPrice(builder, LowestPrice);
    FbFuturesQuotation.addHighestPrice(builder, HighestPrice);
    FbFuturesQuotation.addOpenPrice(builder, OpenPrice);
    FbFuturesQuotation.addPreClosePrice(builder, PreClosePrice);
    FbFuturesQuotation.addPreSettlementPrice(builder, PreSettlementPrice);
    FbFuturesQuotation.addChangeRate(builder, ChangeRate);
    FbFuturesQuotation.addChangeValue(builder, ChangeValue);
    FbFuturesQuotation.addLastPrice(builder, LastPrice);
    FbFuturesQuotation.addContractNo(builder, ContractNoOffset);
    FbFuturesQuotation.addGoodsNo(builder, GoodsNoOffset);
    FbFuturesQuotation.addDateTime(builder, DateTimeOffset);
    return FbFuturesQuotation.endFbFuturesQuotation(builder);
  }

  public static void startFbFuturesQuotation(FlatBufferBuilder builder) { builder.startObject(36); }
  public static void addDateTime(FlatBufferBuilder builder, int DateTimeOffset) { builder.addOffset(0, DateTimeOffset, 0); }
  public static void addGoodsNo(FlatBufferBuilder builder, int GoodsNoOffset) { builder.addOffset(1, GoodsNoOffset, 0); }
  public static void addContractNo(FlatBufferBuilder builder, int ContractNoOffset) { builder.addOffset(2, ContractNoOffset, 0); }
  public static void addLastPrice(FlatBufferBuilder builder, float LastPrice) { builder.addFloat(3, LastPrice, 0.0f); }
  public static void addChangeValue(FlatBufferBuilder builder, float ChangeValue) { builder.addFloat(4, ChangeValue, 0.0f); }
  public static void addChangeRate(FlatBufferBuilder builder, float ChangeRate) { builder.addFloat(5, ChangeRate, 0.0f); }
  public static void addPreSettlementPrice(FlatBufferBuilder builder, float PreSettlementPrice) { builder.addFloat(6, PreSettlementPrice, 0.0f); }
  public static void addPreClosePrice(FlatBufferBuilder builder, float PreClosePrice) { builder.addFloat(7, PreClosePrice, 0.0f); }
  public static void addOpenPrice(FlatBufferBuilder builder, float OpenPrice) { builder.addFloat(8, OpenPrice, 0.0f); }
  public static void addHighestPrice(FlatBufferBuilder builder, float HighestPrice) { builder.addFloat(9, HighestPrice, 0.0f); }
  public static void addLowestPrice(FlatBufferBuilder builder, float LowestPrice) { builder.addFloat(10, LowestPrice, 0.0f); }
  public static void addVolume(FlatBufferBuilder builder, long Volume) { builder.addLong(11, Volume, 0L); }
  public static void addOpenInterest(FlatBufferBuilder builder, long OpenInterest) { builder.addLong(12, OpenInterest, 0L); }
  public static void addClosePrice(FlatBufferBuilder builder, float ClosePrice) { builder.addFloat(13, ClosePrice, 0.0f); }
  public static void addSettlementPrice(FlatBufferBuilder builder, float SettlementPrice) { builder.addFloat(14, SettlementPrice, 0.0f); }
  public static void addBidPrice1(FlatBufferBuilder builder, float BidPrice1) { builder.addFloat(15, BidPrice1, 0.0f); }
  public static void addBidVolume1(FlatBufferBuilder builder, long BidVolume1) { builder.addLong(16, BidVolume1, 0L); }
  public static void addBidPrice2(FlatBufferBuilder builder, float BidPrice2) { builder.addFloat(17, BidPrice2, 0.0f); }
  public static void addBidVolume2(FlatBufferBuilder builder, long BidVolume2) { builder.addLong(18, BidVolume2, 0L); }
  public static void addBidPrice3(FlatBufferBuilder builder, float BidPrice3) { builder.addFloat(19, BidPrice3, 0.0f); }
  public static void addBidVolume3(FlatBufferBuilder builder, long BidVolume3) { builder.addLong(20, BidVolume3, 0L); }
  public static void addBidPrice4(FlatBufferBuilder builder, float BidPrice4) { builder.addFloat(21, BidPrice4, 0.0f); }
  public static void addBidVolume4(FlatBufferBuilder builder, long BidVolume4) { builder.addLong(22, BidVolume4, 0L); }
  public static void addBidPrice5(FlatBufferBuilder builder, float BidPrice5) { builder.addFloat(23, BidPrice5, 0.0f); }
  public static void addBidVolume5(FlatBufferBuilder builder, long BidVolume5) { builder.addLong(24, BidVolume5, 0L); }
  public static void addAskPrice1(FlatBufferBuilder builder, float AskPrice1) { builder.addFloat(25, AskPrice1, 0.0f); }
  public static void addAskVolume1(FlatBufferBuilder builder, long AskVolume1) { builder.addLong(26, AskVolume1, 0L); }
  public static void addAskPrice2(FlatBufferBuilder builder, float AskPrice2) { builder.addFloat(27, AskPrice2, 0.0f); }
  public static void addAskVolume2(FlatBufferBuilder builder, long AskVolume2) { builder.addLong(28, AskVolume2, 0L); }
  public static void addAskPrice3(FlatBufferBuilder builder, float AskPrice3) { builder.addFloat(29, AskPrice3, 0.0f); }
  public static void addAskVolume3(FlatBufferBuilder builder, long AskVolume3) { builder.addLong(30, AskVolume3, 0L); }
  public static void addAskPrice4(FlatBufferBuilder builder, float AskPrice4) { builder.addFloat(31, AskPrice4, 0.0f); }
  public static void addAskVolume4(FlatBufferBuilder builder, long AskVolume4) { builder.addLong(32, AskVolume4, 0L); }
  public static void addAskPrice5(FlatBufferBuilder builder, float AskPrice5) { builder.addFloat(33, AskPrice5, 0.0f); }
  public static void addAskVolume5(FlatBufferBuilder builder, long AskVolume5) { builder.addLong(34, AskVolume5, 0L); }
  public static void addAveragePrice(FlatBufferBuilder builder, float AveragePrice) { builder.addFloat(35, AveragePrice, 0.0f); }
  public static int endFbFuturesQuotation(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
}


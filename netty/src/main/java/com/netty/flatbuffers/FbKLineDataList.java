// automatically generated by the FlatBuffers compiler, do not modify

package com.netty.flatbuffers;

import java.nio.*;
import java.lang.*;
import java.util.*;

@SuppressWarnings("unused")
public final class FbKLineDataList extends Table {
  public static FbKLineDataList getRootAsFbKLineDataList(ByteBuffer _bb) { return getRootAsFbKLineDataList(_bb, new FbKLineDataList()); }
  public static FbKLineDataList getRootAsFbKLineDataList(ByteBuffer _bb, FbKLineDataList obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; }
  public FbKLineDataList __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public FbKLineData DataList(int j) { return DataList(new FbKLineData(), j); }
  public FbKLineData DataList(FbKLineData obj, int j) { int o = __offset(4); return o != 0 ? obj.__assign(__indirect(__vector(o) + j * 4), bb) : null; }
  public int DataListLength() { int o = __offset(4); return o != 0 ? __vector_len(o) : 0; }

  public static int createFbKLineDataList(FlatBufferBuilder builder,
      int DataListOffset) {
    builder.startObject(1);
    FbKLineDataList.addDataList(builder, DataListOffset);
    return FbKLineDataList.endFbKLineDataList(builder);
  }

  public static void startFbKLineDataList(FlatBufferBuilder builder) { builder.startObject(1); }
  public static void addDataList(FlatBufferBuilder builder, int DataListOffset) { builder.addOffset(0, DataListOffset, 0); }
  public static int createDataListVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startDataListVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endFbKLineDataList(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
}


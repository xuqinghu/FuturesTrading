// automatically generated by the FlatBuffers compiler, do not modify

package com.netty.flatbuffers;

import java.nio.*;
import java.lang.*;

@SuppressWarnings("unused")
public final class FbHeader extends Struct {
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; }
  public FbHeader __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public int trickCode() { return bb.getInt(bb_pos + 0); }
  public int msglen() { return bb.getInt(bb_pos + 4); }
  public int ip() { return bb.getInt(bb_pos + 8); }
  public int rsv2() { return bb.getInt(bb_pos + 12); }

  public static int createFbHeader(FlatBufferBuilder builder, int trickCode, int msglen, int ip, int rsv2) {
    builder.prep(4, 16);
    builder.putInt(rsv2);
    builder.putInt(ip);
    builder.putInt(msglen);
    builder.putInt(trickCode);
    return builder.offset();
  }
}


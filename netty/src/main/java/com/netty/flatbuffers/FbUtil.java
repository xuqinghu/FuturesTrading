package com.netty.flatbuffers;

import com.netty.client.ByteTransUtil;
import com.netty.client.MsgConstants;
import com.netty.client.Constants;

public class FbUtil {
	
	public static byte[] createHearBeatMsg() {
		byte[] head = new byte[Constants.HEAD_LEN];
		System.arraycopy(ByteTransUtil.intToByteArray(MsgConstants.MA_HEARBEAT, false), 0, head, 0, 4);
		System.arraycopy(ByteTransUtil.intToByteArray(0, false), 0, head, 4, 4);
		return head;
	}
	
	public static int createRspHead(FlatBufferBuilder builder, byte platform, String msgId, String msgType, String userId, 
			String code, String msg, byte[] msgBody) {
		int msgIdInt = msgId == null ? 0 : builder.createString(msgId);
		int userIdInt = userId == null ? 0 : builder.createString(userId);
		int msgTypeInt = msgType == null ? 0 : builder.createString(msgType);
		return FbBizMsg.createFbBizMsg(builder, platform, msgIdInt, 0, userIdInt, 
				msgTypeInt, 
				code == null ? 0 : builder.createString(code), 
				msg == null ? 0 : builder.createString(msg), 
				builder.createString("20181026113000"),
				msgBody == null ? 0 : builder.createByteVector(msgBody));
	
	}
}
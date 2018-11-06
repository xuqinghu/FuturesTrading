package com.netty.client;


import com.netty.flatbuffers.FbUtil;

public class MsgConstants {
	
	/** 设备类型 */
	public final static byte PLAT_ALL = (byte)0;
	
	public final static byte PLAT_PC = (byte)1;
	
	public final static byte PLAT_ANDROID = (byte)2;
	
	public final static byte PLAT_IOS = (byte)3;
	
	/** 识别码 */
	public final static int MA_HEARBEAT = 878787;
	
	public final static int MA_BIZ = 787878;
	
	
	/** 报文类型标号 */
 	
	
	/** 发送行情申请 */
	public final static String REGISTER_QUOTATION = "Q0001";
	/** 推送行情 */
	public final static String PUSH_QUOTATION = "Q0002";
    //验证码
	public final static String YZM = "T1000";
    //登录
	public final static String LOGIN = "T1001";
    //商品列表
	public final static String GOODS = "C1001";
    //K线数据
	public final static String KLINE = "Q1001";
	
	public final static String SUCCESS = "0";
	/** 默认心跳报文 */
	public static final byte[] HEATBEAT_MSG = FbUtil.createHearBeatMsg();
	
	
}

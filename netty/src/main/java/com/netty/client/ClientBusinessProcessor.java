package com.netty.client;

import android.util.Log;
import com.netty.flatbuffers.FbBizMsg;
import com.netty.flatbuffers.FbFuturesQuotation;
import com.netty.flatbuffers.FbFuturesQuotationList;
import com.netty.flatbuffers.FbMsgCaptchaResp;

import java.nio.ByteBuffer;
import io.netty.channel.ChannelHandlerContext;

/**
 * <p>
 * 客户服务端业务逻辑处理
 * 
 * @author myumen
 * @date 2017.09.27
 *
 */
public class ClientBusinessProcessor extends AppBusinessProcessor {
	@Override
	public void process(NettyMessage message, ChannelHandlerContext ctx) {
		FbBizMsg bizMsg = FbBizMsg.getRootAsFbBizMsg(message.getBizMsg());
		ByteBuffer body = null;
		try {
			switch(bizMsg.msgType()){
				//user
				case MsgConstants.REGISTER_QUOTATION: 
					body = bizMsg.msgBodyAsByteBuffer();
					FbFuturesQuotationList list = FbFuturesQuotationList.getRootAsFbFuturesQuotationList(body);
					FbFuturesQuotation fq = null;
					for (int i = 0; i < list.FbFuturesQuotationListLength(); i++) {
						fq = list.FbFuturesQuotationList(i);
						Log.d("Netty",fq.ContractNo() + "--" + fq.LastPrice() + "--" + fq.DateTime());
//						System.out.println(fq.ContractNo() + "--" + fq.LastPrice() + "--" + fq.DateTime());
					}
				case MsgConstants.PUSH_QUOTATION: 
					body = bizMsg.msgBodyAsByteBuffer();
//					System.out.println(Arrays.toString(body.array()));
					FbFuturesQuotation fq1 = FbFuturesQuotation.getRootAsFbFuturesQuotation(body);
					System.out.println(fq1.ContractNo() + "--" + fq1.LastPrice() + "--" + fq1.DateTime() + "--" + fq1.ChangeRate());
					Log.d("Netty",fq1.ContractNo() + "--" + fq1.LastPrice() + "--" + fq1.DateTime() + "--" + fq1.ChangeRate());
				case MsgConstants.YZM:
					body = bizMsg.msgBodyAsByteBuffer();
					FbMsgCaptchaResp fbMsgCaptchaResp = FbMsgCaptchaResp.getRootAsFbMsgCaptchaResp(body);
					Log.d("Netty",""+fbMsgCaptchaResp.verifyCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
//			writeRsp(resp, ctx);
		}
	}
	
}

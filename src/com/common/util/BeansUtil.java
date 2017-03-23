//package com.muchinfo.mtp.common.util;
//
//import com.muchinfo.mtp.bankservice.constants.FunCodeConstants;
//import com.muchinfo.mtp.bankservice.model.SendMsg;
//import com.muchinfo.mtp.proto.ExchangeCenter;
//import com.muchinfo.mtp.proto.ExchangeCenter.Msg10704;
//import com.muchinfo.mtp.proto.ExchangeCenter.Msg10804;
//import com.muchinfo.mtp.proto.ExchangeCenter.Msg13204;
//
//public class BeansUtil {
//	/**
//	 * 往包实体类 getSendMsg:(这里用一句话描述这个方法的作用). <br/>
//	 * 
//	 * @author zhou.yao
//	 * @param sendMsgId
//	 * @param recvMsgId
//	 * @param recverType
//	 * @param recver
//	 * @param msgCode
//	 * @param tranSeq
//	 * @param sendMsg
//	 * @return
//	 */
//	public static SendMsg getSendMsg(Long sendMsgId, Long recvMsgId, Integer recverType, String recver, String msgCode,
//			Long tranSeq, String sendMsg) {
//		SendMsg bean = new SendMsg();
//		bean.setSendMsgId(sendMsgId);// 往包id
//		bean.setRecvMsgId(recvMsgId);// 来包id
//		bean.setRecverType(recverType);// 接收方类型
//		bean.setRecver(recver);// 业务接收方 交易所编号、银行业务编号
//		bean.setMsgCode(msgCode);// 报文类型编号
//		bean.setTranSeq(tranSeq);// 业务流水号
//		bean.setSendMsg(sendMsg); // 报文内容
//		// sendMsg.setSysTime(new Date());//系统时间
//		// sendMsg.setRetCode(Integer.parseInt(FinalConstants.RetCode.SUCCESS));
//		// sendMsg.setRetDesc("发送成功");
//		return bean;
//	}
//	
//    /**
//     * 交易所 发起的客户信息返回报文
//     * MSG10704Builder:(这里用一句话描述这个方法的作用). <br/>
//     * @author zhou.yao
//     * @param exchNo
//     * @param exchDate
//     * @param exchSeq
//     * @param centerSeq
//     * @param acct
//     * @param acctName
//     * @param retCode
//     * @param retDesc
//     * @return
//     */
//	public static Msg10704.Builder MSG10704Builder(String exchNo, String exchDate, String exchSeq, String centerSeq,
//			String acct,String acctName,String retCode, String retDesc) {
//
//		ExchangeCenter.MessageHead.Builder value = ExchangeCenter.MessageHead.newBuilder();
//		value.setFunCode(FunCodeConstants.MSG10704);// 功能号
//		Msg10704.Builder msg10704 = Msg10704.newBuilder();
//		msg10704.setHeader(value);
//		msg10704.setExchNo(exchNo);// 交易所编号
//		msg10704.setExchDate(exchDate);// 交易所日期
//		msg10704.setExchSeq(exchSeq);// 交易所流水号
//		msg10704.setCenterSeq(centerSeq);// 中心流水号
//		//msg10704.setAcct(acct);
//		//msg10704.setAcctName(acctName);
//		msg10704.setRetCode(retCode);// 返回码
//		msg10704.setRetDesc(retDesc);// 返回结果说明
//		return msg10704;
//	}
//	
//	
//	
//	
//	/**
//	 * 交易所 客户信息注销报文
//	 * MSG10804Builder:(这里用一句话描述这个方法的作用). <br/>
//	 * @author zhou.yao
//	 * @param exchNo
//	 * @param exchDate
//	 * @param exchSeq
//	 * @param centerSeq
//	 * @param retCode
//	 * @param retDesc
//	 * @return
//	 */
//	public static Msg10804.Builder MSG10804Builder(String exchNo, String exchDate, String exchSeq, String centerSeq,
//			String retCode, String retDesc) {
//
//		ExchangeCenter.MessageHead.Builder value = ExchangeCenter.MessageHead.newBuilder();
//		value.setFunCode(FunCodeConstants.MSG10804);// 功能号
//		Msg10804.Builder msg10804 = Msg10804.newBuilder();
//		msg10804.setHeader(value);
//		msg10804.setExchNo(exchNo);// 交易所编号
//		msg10804.setExchDate(exchDate);// 交易所日期
//		msg10804.setExchSeq(exchSeq);// 交易所流水号
//		msg10804.setCenterSeq(centerSeq);// 中心流水号
//		msg10804.setRetCode(retCode);// 返回码
//		msg10804.setRetDesc(retDesc);// 返回结果说明
//		return msg10804;
//	}
//	
//	/**
//	 * 交易所付款确认返回报文 
//	 * @author zhou.yao
//	 * @return
//	 */
//	public static Msg13204.Builder MSG13204Builder(String exchNo, String exchDate, String exchSeq, String centerSeq,
//			String retCode, String retDesc) {
//
//		ExchangeCenter.MessageHead.Builder value = ExchangeCenter.MessageHead.newBuilder();
//		value.setFunCode(FunCodeConstants.MSG13104);// 功能号
//		// value.setRequestID(1);// 客户端的流水ID
//		// value.setAccountId(11); // 账号ID
//		// value.setAccessId(1);// //二级分配给客户端的接入ID
//		// value.setClientTime(new Date().getTime());// 消息发起时间
//		Msg13204.Builder msg13204 = Msg13204.newBuilder();
//		msg13204.setHeader(value);
//		msg13204.setExchNo(exchNo);// 交易所编号
//		msg13204.setExchDate(exchDate);// 交易所日期
//		msg13204.setExchSeq(exchSeq);// 交易所流水号
//		msg13204.setCenterSeq(centerSeq);// 中心流水号
//		msg13204.setRetCode(retCode);// 返回码
//		msg13204.setRetDesc(retDesc);// 返回结果说明
//		return msg13204;
//	}
//
//	
//}

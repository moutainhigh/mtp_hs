package com.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccps.exchgate.api.t2.service.client.ExchCallClearT2Service;
import com.common.constants.FinalConstants;
import com.common.constants.FunCodeConstants;
import com.common.util.CenterToExchUtil;
import com.common.util.DateHelp;
import com.common.util.TASProtoHelper;
import com.dao.Impl.ProductDao;
import com.dao.Impl.SendMsgDao;
import com.model.Product;
import com.model.SendMsg;
import com.muchinfo.common.util.PropertiesUtil;
import com.proto.CenterBank.Msg17102;
import com.proto.CenterBank.Msg17103;
import com.proto.CenterBank.Msg17202;
import com.proto.CenterBank.Msg17302;
import com.proto.CenterBank.Msg17402;
import com.proto.CenterBank.Msg17502;
import com.proto.CenterBank.Msg17602;
import com.proto.CenterBank.Msg17702;
import com.proto.CenterBank.Msg17802;
import com.proto.ExchangeCenter;
import com.service.SendBillMsg;
import com.service.SendMsgService;

import net.sf.json.JSONObject;

@Service
public class SendBillMsgImpl implements SendBillMsg {
	private static final Logger LOGGER = Logger.getLogger(SendBillMsgImpl.class);
	public String hsExchNo=PropertiesUtil.getProperty("HSexchNO");
	@Autowired
	private ExchCallClearT2Service exchCallClearT2Service;
	@Autowired
	private SendMsgDao sendMsgDao;
	@Autowired
	private ProductDao productDao;
	@Autowired 
	private SendMsgService sendMsgService;
	
	//商品新增
	@Override
	public void doMsg17102(Msg17102 msg17102, Long recMsgId) throws Exception {
		/*LOGGER.info("*商品新增*");*/
		try{
			JSONObject json = new JSONObject();
			json.put("exchangeId", hsExchNo);
			json.put("productCategoryMaxCode", msg17102.getProductCategoryCode());
			
			JSONObject productAttrJson = new JSONObject();
	//		此属性信息必须包含（基本）属性(其中类别ID和类别名称不用传)；
	//		productAttrJson.put("productCategoryMaxName", msg17102.getProductCategoryName());
	//		productAttrJson.put("productCategoryInId", msg17102.getProductCategoryId());
			productAttrJson.put("productCode", msg17102.getProductCode());
			productAttrJson.put("productName", msg17102.getProductName());
			productAttrJson.put("exchangeId", hsExchNo);
			productAttrJson.put("productCategoryMaxCode", msg17102.getProductCategoryCode());
			productAttrJson.put("presentUnit", msg17102.getPresentUnit());
			productAttrJson.put("exchangeMarketType", msg17102.getExchMarketType());
			productAttrJson.put("bizType", msg17102.getBizType());
			
			json.put("productAttrJson", productAttrJson);
			json.put("productCode", msg17102.getProductCode());
			json.put("productName", msg17102.getProductName());
			json.put("requestId", msg17102.getCenterSeq());
			json.put("productStatus", msg17102.getProductStatus());
			
			int dealType = msg17102.getDataType();
			String result = null;
			switch(dealType){        
			case 1:
				//新增
				LOGGER.info("*商品新增*");
				Product product=new Product();
				product.setProduct_id(productDao.generateId());
				product.setTran_no(msg17102.getTranNo());
				product.setBank_date(msg17102.getBankDate());
				product.setCenter_seq(msg17102.getCenterSeq());
				product.setData_type(msg17102.getDataType());
				product.setExch_no(msg17102.getExchNo());
				product.setProduct_status(msg17102.getProductStatus());
				productDao.getProductMapper().insert(product);
				result = exchCallClearT2Service.dealMSG378001(json.toString());  break;
			case 2:
				//修改
				LOGGER.info("*商品修改*");
				result = exchCallClearT2Service.dealMSG378002(json.toString());  break;
			case 3:
				//撤牌(状态变更)
				LOGGER.info("*报送商品合约状态变更*");
				result = exchCallClearT2Service.dealMSG378003(json.toString());  break;
			}
			
			
			JSONObject resultJson = JSONObject.fromObject(result);
			int errorNo = resultJson.getInt("errorNo");
			String errorInfo = resultJson.getString("errorInfo");
			LOGGER.info(errorNo+":"+errorInfo);
			
			
			LOGGER.info("*组发送至中心的商品信息返回报文Msg17103报文*");
			ExchangeCenter.MessageHead.Builder value = ExchangeCenter.MessageHead.newBuilder();
			value.setFunCode(17103); // 功能号
			Msg17103.Builder msg17103 = Msg17103.newBuilder();
			msg17103.setTranNo(msg17102.getTranNo()); 
			msg17103.setBankDate(msg17102.getBankDate());
			msg17103.setCenterSeq(msg17102.getCenterSeq());
			
			if (errorNo == 0){
				msg17103.setRetCode(FinalConstants.RetCode.SUCCESS);
				msg17103.setRetDesc(errorInfo);
			}
			else if (errorNo == 99) {
				msg17103.setRetCode(errorNo+"");
				msg17103.setRetDesc(errorInfo);
			} else {
				msg17103.setRetCode(FinalConstants.RetCode.FAIL);
				msg17103.setRetDesc(errorInfo);
			}
			LOGGER.info("*记录往包*");
			SendMsg sendMsg = new SendMsg();
			Long sendMsgID = sendMsgDao.generateId();
			sendMsg.setSendMsgId(sendMsgID); // 往包ID
			sendMsg.setRecvMsgId(recMsgId); // 来包id
			sendMsg.setRecverType(FinalConstants.SenderType.CENTER); // 接收方类型
			sendMsg.setRecver("000000"); // 业务接收方
			sendMsg.setMsgCode("17103"); // 报文类型编号
			sendMsg.setSendDate(DateHelp.getCurrentDateOfString());
			/*sendMsg.setTranSeq(); // 业务流水号*/
			sendMsg.setSendMsg(msg17103.build().toString()); // 报文内容
			sendMsg.setSysTime(new Date()); // 系统时间
			sendMsgDao.getSendMsgMapper().insert(sendMsg);
	
			byte[] bytes = TASProtoHelper.getNTAS(msg17103.build().toByteArray(), FunCodeConstants.MSG17103, 0);
			sendMsgService.send(sendMsg, bytes);
			LOGGER.info("*商品信息s应答报文*");
			
			LOGGER.info("*更新往包表状态*");
			sendMsg.setRetCode(Integer.parseInt(FinalConstants.RetCode.SUCCESS));
			sendMsg.setRetDesc("发送成功");
			sendMsgDao.getSendMsgMapper().updateByPrimaryKeySelective(sendMsg);
		
		}catch (Exception e){
			throw e;
		}
		
	}

	//报送委托
	@Override
	public void doMsg17202(Msg17202 msg17202, Long recMsgId) throws Exception {
		LOGGER.info("*报送委托*");
		JSONObject json = new JSONObject();
		json.put("initDate", msg17202.getTranDate());
		json.put("exchangeId", hsExchNo);		
		json.put("entrustNo", msg17202.getEntrustNo());
		json.put("productCategoryId", msg17202.getProductCategoryId());
		json.put("productCode", msg17202.getProductCode());
		json.put("memCode", msg17202.getMemCode());
		json.put("tradeDir", msg17202.getTradeDir());
		json.put("orderType", msg17202.getOrderType());
		json.put("orderWay", msg17202.getOrderWay());
		json.put("orderSerialNo", msg17202.getOrderSerialNo());
		json.put("depositWay", msg17202.getDepositWay());
		json.put("orderPrice", msg17202.getOrderPrice());
		json.put("orderQuantity", msg17202.getOrderNum());
		json.put("leftQuantity", msg17202.getLeftNum()); 
		json.put("depositRate", msg17202.getDepositRate());
		json.put("depositRatioType", msg17202.getDepositRatioType());
		json.put("depositType", msg17202.getDepositGetWay());
		json.put("poundageRate", msg17202.getPoundageRate());
		json.put("poundageRatioType", msg17202.getPoundageRatioType());
		json.put("tradePoundage", msg17202.getTradePoundage());
		json.put("paperBalance", msg17202.getPaperBalance());
		json.put("orderFrozen", msg17202.getOrderFrozen());
		json.put("taxRate", msg17202.getTaxRate());
		json.put("validDate", msg17202.getValidDate());
		json.put("trader", msg17202.getTrader());
		json.put("orderTime", msg17202.getOrderTime());
		json.put("orderIp", msg17202.getOrderIp());
		json.put("orderStatus", msg17202.getOrderStatus());
		json.put("remark", msg17202.getRemark());
		json.put("busiDatetime", msg17202.getBusiDatetime());
		
		String result = exchCallClearT2Service.dealMSG369002(json.toString());
		
		JSONObject resultJson = JSONObject.fromObject(result);
		int errorNo = resultJson.getInt("errorNo");
		String errorInfo = resultJson.getString("errorInfo");
		LOGGER.info(errorNo+":"+errorInfo);
	}

	//成交数据
	@Override
	public void doMsg17302(Msg17302 msg17302, Long recMsgId) throws Exception {
		LOGGER.info("*成交数据*");
		JSONObject json = new JSONObject();
		json.put("initDate", msg17302.getTranDate());
		json.put("exchangeId", hsExchNo);
		json.put("exchangeMarketType", msg17302.getExchMarketType());
		json.put("bizType", msg17302.getBizType());
		json.put("dealId", msg17302.getDealId());
		json.put("openMemCode", msg17302.getOpenMemCode());
		json.put("openFundAccount", msg17302.getOpenFundAccount());
		json.put("openTradeAccount", msg17302.getOpenTradeAccount());
		json.put("oppMemCode", msg17302.getOppMemCode());
		json.put("oppFundAccount", msg17302.getOppFundAccount());
		json.put("oppTradeAccount", msg17302.getOppTradeAccount());
		json.put("productCategoryId", msg17302.getProductCategoryId());
		json.put("productCode", msg17302.getProductCode());
		json.put("tradeDir", msg17302.getTradeDir());
		json.put("dealType", msg17302.getDealType());
		json.put("oppDealType", msg17302.getOppDealType());
		json.put("tradeType", msg17302.getTradeType());
		json.put("dealPrice", msg17302.getDealPrice());
		json.put("holdPrice", msg17302.getHoldPrice());
		json.put("dealQuantity", msg17302.getDealQuantity());
		json.put("dealTotalPrice", msg17302.getDealTotalPrice());
		json.put("depositRate", msg17302.getDepositRate());
		json.put("depositRatioType", msg17302.getDepositRatioType());
		json.put("depositType", msg17302.getDepositType());
		json.put("depositBalance", msg17302.getDepositBalance());
		json.put("oppDepositRatioType", msg17302.getDepositRatioType());
		json.put("oppDepositRate", msg17302.getOppDepositRate());
		json.put("oppDepositBalance", msg17302.getOppDepositBalance());
		json.put("dealTime", msg17302.getDealTime());
		json.put("depotOrderNo", msg17302.getDepotOrderNo());
		json.put("oppDepotOrderNo", msg17302.getOppDepotOrderNo());
		json.put("orderId", msg17302.getOrderId());
		json.put("oppOrderId", msg17302.getOppOrderId());
		json.put("settlementDate", msg17302.getSettlementDate());
		
		String result = exchCallClearT2Service.dealMSG369001(json.toString());
		JSONObject jsonObject = JSONObject.fromObject(result);
		int errorNo = jsonObject.getInt("errorNo");
		String errorInfo = jsonObject.getString("errorInfo");
		LOGGER.info(errorNo+":"+errorInfo);
	} 
	
	//报送交割单
	@Override
	public void doMsg17402(Msg17402 msg17402, Long recMsgId) throws Exception {
		LOGGER.info("*向清算所报送交割单*");
		
		JSONObject json = new JSONObject();
		json.put("initDate", msg17402.getTranDate());
		json.put("exchangeId", hsExchNo);
		json.put("memCode", msg17402.getMemCode());
		json.put("productCategoryId", msg17402.getProductCategoryId());
		json.put("productCategoryName", msg17402.getProductCategoryName());
		json.put("productCode", msg17402.getProductCode());
		json.put("productName", msg17402.getProductName());
		json.put("deliverOrderNo", msg17402.getDeliverOrderNo());
		json.put("depotOrderNo", msg17402.getDepotOrderNo());
		json.put("deliverApplyTime", msg17402.getDeliverApplyTime());
		json.put("deliverEffectTime", msg17402.getDeliverEffectTime());
		json.put("deliverAddr", msg17402.getDeliverAddr());
		json.put("deliverDepot", msg17402.getDeliverDepot());
		json.put("deliverType", msg17402.getDeliverType());
		json.put("deliverDirection", msg17402.getDeliverDirection());
		json.put("deliverPrice", msg17402.getDeliverPrice());
		json.put("deliverQuantity", msg17402.getDeliverQuantity());
		json.put("deliverPayment", msg17402.getDeliverPayment());
		json.put("deliverFees", msg17402.getDeliverFees());
		json.put("holdPrice", msg17402.getHoldPrice());
		json.put("busiDatetime", msg17402.getBusiDatetime());
		
		String result = exchCallClearT2Service.dealMSG369003(json.toString());
		
		JSONObject resultJson = JSONObject.fromObject(result);
		int errorNo = resultJson.getInt("errorNo");
		String errorInfo = resultJson.getString("errorInfo");
		LOGGER.info(errorNo+":"+errorInfo);
		
	}

	
	@Override
	public void doMsg17502(Msg17502 msg17502, Long recMsgId) throws Exception {

	}

	//报送收费单
	@Override
	public void doMsg17602(Msg17602 msg17602, Long recMsgId) throws Exception {
		LOGGER.info("*报送收费单*");
		
		JSONObject json = new JSONObject();
		json.put("initDate", msg17602.getTranDate());
		json.put("serialNo", msg17602.getSerialNo());
		json.put("exchangeId", hsExchNo);
		json.put("exchangeMarketType", msg17602.getExchMarketType());
		json.put("bizType", msg17602.getBizType());
		json.put("exchangeFeesType", msg17602.getExchangeFeesType());
		json.put("feesBalance", msg17602.getFeesBalance());
		json.put("payerMemCode", msg17602.getPayerMemCode());
		json.put("payerFundAccount", msg17602.getPayerFundAccount());
		json.put("payeeMemCode", msg17602.getPayeeMemCode());
		json.put("payeeFundAccount", msg17602.getPayeeFundAccount());
		json.put("relatedBillType", msg17602.getRelatedBillType());
		json.put("relatedBillNo", msg17602.getRelatedBillNo());
		json.put("remark", msg17602.getRemark());
		json.put("busiDatetime", msg17602.getBusiDatetime());
		
		String result = exchCallClearT2Service.dealMSG369004(json.toString());
		
		JSONObject resultJson  = JSONObject.fromObject(result);
		int errorNo = resultJson.getInt("errorNo");
		String errorInfo = resultJson.getString("errorInfo");
		LOGGER.info(errorNo+":"+errorInfo);
	}

	@Override
	public void doMsg17702(Msg17702 msg171702, Long recMsgId) throws Exception {

	}
	
	//报送行情数据
	@Override
	public void doMsg17802(Msg17802 msg17802, Long recMsgId) throws Exception {
		LOGGER.info("*报送行情数据*");
		
		JSONObject json = new JSONObject();
		json.put("initDate", msg17802.getTranDate());
		json.put("exchangeId", hsExchNo);
		json.put("priceType", msg17802.getPriceType());
		json.put("exchangeMarketType", msg17802.getExchMarketType());
		json.put("productCategoryId", msg17802.getProductCategoryId());
		json.put("productCode", msg17802.getProductCode());
		json.put("moneyType", CenterToExchUtil.HS_CURRENCY.get(msg17802.getMoneyType()));
		json.put("bitNum", msg17802.getBitNum());
		json.put("openQuotationPrice", msg17802.getOpenQuotationPrice());
		json.put("preclosePrice", msg17802.getPreclosePrice());
		json.put("askPrice", msg17802.getAskPrice());
		json.put("bidPrice", msg17802.getBidPrice());
		json.put("lastPrice", msg17802.getLastPrice());
		json.put("highPrice", msg17802.getHighPrice());
		json.put("lowPrice", msg17802.getLowPrice());
		json.put("timestamp", msg17802.getDataTime());
		
		String result = exchCallClearT2Service.dealMSG369005(json.toString());
		
		JSONObject resultJson = JSONObject.fromObject(result);
		int errorNo = resultJson.getInt("errorNo");
		String errorInfo = resultJson.getString("errorInfo");
		LOGGER.info(errorNo+":"+errorInfo);
	}

}

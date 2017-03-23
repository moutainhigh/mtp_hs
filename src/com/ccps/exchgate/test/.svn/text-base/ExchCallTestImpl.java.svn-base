package com.ccps.exchgate.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ccps.exchgate.api.pojo.BaseResp;
import com.ccps.exchgate.api.t2.param.BankResponse;
import com.ccps.exchgate.api.t2.param.BindAccount;
import com.ccps.exchgate.api.t2.param.CategoryResp;
import com.ccps.exchgate.api.t2.param.CustomerBankInfo;
import com.ccps.exchgate.api.t2.param.DealDataParamReq;
import com.ccps.exchgate.api.t2.param.DeliverOrderParamReq;
import com.ccps.exchgate.api.t2.param.EntrustOrderParamReq;
import com.ccps.exchgate.api.t2.param.ExchCashExchangeDetail;
import com.ccps.exchgate.api.t2.param.ExchCashExchangeDetailQuery;
import com.ccps.exchgate.api.t2.param.FeesOrderParamReq;
import com.ccps.exchgate.api.t2.param.FundCashResp;
import com.ccps.exchgate.api.t2.param.FundCommonReq;
import com.ccps.exchgate.api.t2.param.InGoldenReq;
import com.ccps.exchgate.api.t2.param.MemUserCancelReq;
import com.ccps.exchgate.api.t2.param.MemUsersSyncReq;
import com.ccps.exchgate.api.t2.param.MemUsersSyncResp;
import com.ccps.exchgate.api.t2.param.OpenOrCloseReq;
import com.ccps.exchgate.api.t2.param.OutGoldenReq;
import com.ccps.exchgate.api.t2.param.PayWayQuery;
import com.ccps.exchgate.api.t2.param.ProductEffectStatusReq;
import com.ccps.exchgate.api.t2.param.ProductReq;
import com.ccps.exchgate.api.t2.param.SettleFileServiceReq;
import com.ccps.exchgate.api.t2.param.SignInExchangeReq;
import com.ccps.exchgate.api.t2.param.SurrFrozenThrawReq;
import com.ccps.exchgate.api.t2.param.SurrManulAdjustReq;
import com.ccps.exchgate.api.t2.param.SurrNoTradeTransferReq;
import com.ccps.exchgate.api.t2.service.client.ExchCallClearT2Service;

/**
 * 交易所发起
 * @author jiangzz
 *
 */

@Service
public class ExchCallTestImpl /*implements IExchCallTest*/{/*
	
	@Autowired
	private ExchCallClearT2Service exchCallClearT2Service;
	
	@Override
	public MemUsersSyncResp reportMemUserRegister(MemUsersSyncReq req) {
		String jsonStr = JSON.toJSONString(req);
		return exchCallClearT2Service.insertMemUserRegister(jsonStr);
	}
	
	@Override
	public MemUsersSyncResp reportMemUserModify(MemUsersSyncReq req) {
		String jsonStr = JSON.toJSONString(req);
		return exchCallClearT2Service.syncMemUserModify(jsonStr);
	}
	
	@Override
	public BaseResp reportCancelAccount(MemUserCancelReq req) {
		String jsonStr = JSON.toJSONString(req);
		return exchCallClearT2Service.syncCancelAccount(jsonStr);
	}
	
	//客户入金
	@Override
	public FundCashResp cusInCash(InGoldenReq req) {
		String jsonStr = JSON.toJSONString(req);
		return exchCallClearT2Service.extInGolden(jsonStr);
	}
	
	//客户出金
	@Override
	public FundCashResp cusOutCash(OutGoldenReq req) {
		String jsonStr = JSON.toJSONString(req);
		return exchCallClearT2Service.extOutGolden(jsonStr);
	}
	
	//查询清算中心出入金流水
	@Override
	public ExchCashExchangeDetailQuery cashSerialQuery(ExchCashExchangeDetail req) {
		String jsonStr = JSON.toJSONString(req);
		return exchCallClearT2Service.exchQueryCashExchangeDetail(jsonStr);
	}
	
	//查询可签约银行列表
	@Override
	public PayWayQuery conBankQuery(FundCommonReq fundCommonReq) {
		String jsonStr = JSON.toJSONString(fundCommonReq);
		return exchCallClearT2Service.queryPayWays(jsonStr);
	}
	
	
	//查询资金余额
	@Override
	public BankResponse fundBalQuery(FundCommonReq req) {
		String jsonStr = JSON.toJSONString(req);
		return exchCallClearT2Service.queryAccountBalanceByExchangeFundAccount(jsonStr);
	}
	
	
	
	//客户主副卡变更
	@Override
	public BaseResp mainSecCardChange(FundCommonReq req) {
		String jsonStr = JSON.toJSONString(req);
		return exchCallClearT2Service.updateMainFundAccount(jsonStr);
	}
	
	//客户与银行解约（中国银行）
	@Override
	public BaseResp cusBankTermination(BindAccount req) {
		String jsonStr = JSON.toJSONString(req);
		return exchCallClearT2Service.clientSignBindAccount(jsonStr);
	}
	
	
	//银行签到签退
	@Override
	public BaseResp signAndOut(SignInExchangeReq req) {
		String jsonStr = JSON.toJSONString(req);
		return exchCallClearT2Service.openOrCloseExchange(jsonStr);
	}
	
	//客户银行信息变更
	@Override
	public BaseResp bankInfoChange(CustomerBankInfo req) {
		String jsonStr = JSON.toJSONString(req);
		return exchCallClearT2Service.customerBankInfoModify(jsonStr);
	}
	
	@Override
	public BaseResp fileUploadNotify(SettleFileServiceReq req) {
		String jsonStr = JSON.toJSONString(req);
		 return exchCallClearT2Service.fileUploadNotify(jsonStr);
	}

	@Override
	public BaseResp dealInfoReport(DealDataParamReq req) {
		String jsonStr = JSON.toJSONString(req);
		return exchCallClearT2Service.dealInfoReport(jsonStr);
	}

	@Override
	public BaseResp tradeReport(EntrustOrderParamReq req) throws Exception {
		String jsonStr = JSON.toJSONString(req);
		return exchCallClearT2Service.tradeReport(jsonStr);
	}

	@Override
	public BaseResp subDO(DeliverOrderParamReq req) throws Exception {
		String jsonStr = JSON.toJSONString(req);
		return exchCallClearT2Service.deliverReport(jsonStr);
	}

	@Override
	public BaseResp subChargeBills(FeesOrderParamReq req) {
		String jsonStr = JSON.toJSONString(req);
		return exchCallClearT2Service.feesReport(jsonStr);
	}

	@Override
	public BaseResp subOpenOrClose(OpenOrCloseReq req) { 
		String jsonStr = JSON.toJSONString(req);
		return exchCallClearT2Service.updateThirdPartyCalendarInfo(jsonStr);
	}


	@Override
	public BaseResp subGoodAdd(ProductReq req) {
		Map<String,String> map=new HashMap<String,String>();
    	req.setMap(map);
		String reqStr = JSON.toJSONString(req);
		return exchCallClearT2Service.taInsertProduct(reqStr);
	}
	
	@Override
	public BaseResp subGoodUpdate(ProductReq req) {
		Map<String,String> map=new HashMap<String,String>();
    	map.put("updateOperId",req.getOperatorNo());   	
    	req.setMap(map);
		String reqStr = JSON.toJSONString(req);
		return exchCallClearT2Service.taModifyProduct(reqStr);
	}

	@Override 
	public BaseResp subContractUpdate(ProductEffectStatusReq req) {
		String jsonStr = JSON.toJSONString(req);
		return exchCallClearT2Service.taModifyProductStatus(jsonStr);
	}
	
	@Override
	public CategoryResp searchCategoryList() {
		return exchCallClearT2Service.taSearchListCategory();
	}

	@Override
	public BaseResp subHoldFrozen(SurrFrozenThrawReq req) {	
		String jsonStr = JSON.toJSONString(req);
		return exchCallClearT2Service.taHoldUnOrFrozen(jsonStr);
	}

	@Override
	public BaseResp subHoldManulAdjust(SurrManulAdjustReq req) {
		String jsonStr = JSON.toJSONString(req);
		return exchCallClearT2Service.taManualAdjust(jsonStr);
	}

	@Override
	public BaseResp subHoldNoTrade(SurrNoTradeTransferReq req) {
		String jsonStr = JSON.toJSONString(req);
		return exchCallClearT2Service.taNoTradeTransfer(jsonStr);
	}

*/}

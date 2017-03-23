package com.ccps.exchgate.test;


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
import com.hundsun.jresplus.remoting.impl.annotation.ServiceModule;


/**
 * 交易所发起
 * @author jiangzz
 *
 */
@ServiceModule
public interface IExchCallTest {
	
	MemUsersSyncResp reportMemUserRegister(MemUsersSyncReq req);
	
	MemUsersSyncResp reportMemUserModify(MemUsersSyncReq req);
	
	BaseResp reportCancelAccount(MemUserCancelReq req);
	
	FundCashResp cusInCash(InGoldenReq req);
	
	FundCashResp cusOutCash(OutGoldenReq req);
	
	ExchCashExchangeDetailQuery cashSerialQuery( ExchCashExchangeDetail req);
	
	BankResponse fundBalQuery( FundCommonReq req);
	
	BaseResp bankInfoChange( CustomerBankInfo req);
	
	BaseResp mainSecCardChange( FundCommonReq req);
	
	PayWayQuery conBankQuery(FundCommonReq fundCommonReq);
	
	BaseResp cusBankTermination(BindAccount req);
	
	BaseResp signAndOut( SignInExchangeReq req);
	
	/**
	 * @param SettleFileServiceReq
	 * @return BaseResp
	 */
	public BaseResp fileUploadNotify(SettleFileServiceReq req);
	/**
	 * @param DealDataParam
	 * @return
	 */
	public BaseResp dealInfoReport(DealDataParamReq req);

	/**
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public BaseResp tradeReport(EntrustOrderParamReq req) throws Exception;
	
	/**
	 * @param page
	 * @return
	 * @throws Exception 
	 */
	public BaseResp subDO(DeliverOrderParamReq req) throws Exception;

	/**
	 * @param page
	 * @return
	 */
	public BaseResp subChargeBills(FeesOrderParamReq req);
	
	public BaseResp subOpenOrClose(OpenOrCloseReq req);
	
	
	public BaseResp subGoodAdd(ProductReq page);
	
	public BaseResp subGoodUpdate(ProductReq page);
	
	public BaseResp subContractUpdate(ProductEffectStatusReq rq);
	
	public CategoryResp searchCategoryList();	
	
	public BaseResp subHoldFrozen(SurrFrozenThrawReq req);	
	
	public BaseResp subHoldManulAdjust(SurrManulAdjustReq req);	
	
	public BaseResp subHoldNoTrade(SurrNoTradeTransferReq req);	
	
}

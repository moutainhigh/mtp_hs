package com.common.constants;


public interface FunCodeConstants {

	//中心接收码(交易所到中心)
	/**
	 * 客户出金报文
	 */
	int MSG10101 = 10101;
	/**
	 * 客户入金报文
	 */
	int MSG10201 = 10201;
	/**
	 * 签约信息报文
	 */
	int MSG10301 = 10301;
	/**
	 * 解约报文
	 */
	int MSG10401 = 10401;
	/**
	 * 交易所签到报文
	 */
	int MSG10501 = 10501;
	/**
	 * 交易所签退报文
	 */
	int MSG10601 = 10601;
	/**
	 * 客户信息报文
	 */
	int MSG10701 = 10701;
	
	//资金划转
	int MSG15101 = 15101;
	
	//费用查询
	int MSG16501 = 16501;
	/**
	 * 客户信息注销报文
	 */
	int MSG10801 = 10801;
	/**
	 * 交易所向中心文件报送通知报文
	 */
	int MSG10901 = 10901;
	//查询商户信息
	int MSG16201 = 16201;
	/**
	 * 银行端入金返回报文 --接收交易所返回
	 */
	int MSG20103 = 20103;
	/**
	 * 银行端出金返回报文 --接收交易所返回
	 */
	int MSG20203 = 20203;
	/**
	 * 银行端签约信息返回报文 --接收交易所返回
	 */
	int MSG20303 = 20303;
	/**
	 * 解约返回报文 --接收交易所返回
	 */
	int MSG20403 = 20403;
	/**
	 * 中心向交易所报送文件返回报文 --接收交易所返回
	 */
	int MSG30902 = 30902;
	
	/**
	 * 客户信息查询报文
	 */
	int MSG28201 = 28201;

	
	//中心发送码(中心到交易所)
	int MSG10104 = 10104;
	int MSG10204 = 10204;
	int MSG10304 = 10304;
	int MSG10404 = 10404;
	int MSG10502 = 10502;
	int MSG10602 = 10602;
	int MSG10702 = 10702;
	int MSG10802 = 10802;
	int MSG10902 = 10902;
	int MSG20102 = 20102;
	int MSG20202 = 20202;
	int MSG20302 = 20302;
	int MSG30901 = 30901;
	int MSG13104 = 13104;
	int MSG12304 = 12304;
	
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	//中心接收码(银行到中心)
	/**
	 * 客户出金返回报文 --银行平台返回
	 */
	int MSG10103 = 10103;
	/**
	 * 客户入金返回报文 --银行平台返回
	 */
	int MSG10203 = 10203;
	/**
	 * 签约信息返回报文 --银行平台返回
	 */
	int MSG10303 = 10303;
	/**
	 * 解约返回报文 --银行平台返回
	 */
	int MSG10403 = 10403;
	/**
	 * 银行端入金报文
	 */
	int MSG20101 = 20101;
	/**
	 * 银行端出金报文
	 */
	int MSG20201 = 20201;
	/**
	 * 银行端签约信息报文
	 */
	int MSG20301 = 20301;
	/**
	 * 银行端解约报文
	 */
	int MSG20401 = 20401;
	
	int MSG20402 = 20402;
	/**
	 * 银行向中心报送文件通知报文
	 */
	int MSG21001 = 21001;
	/**
	 * 交易所签退返回报文 --银行平台返回
	 */
	int MSG30502 = 30502;
	/**
	 * 交易所签退返回报文 --银行平台返回
	 */
	int MSG30602 = 30602;
	/**
	 * 中心向银行报送文件通知返回报文
	 */
	int MSG31002 = 31002;
		
	/**
	 * 银行端客户出金申请报文
	 */
	int MSG21201 = 21201;
	
	/**
	 * 银行端审核结果通知报文
	 */
	int MSG21301 = 21301;
	
	/**
	 * 银行端出金申请返回报文
	 */
	int MSG21203 = 21203;
	
	/**
	 * 链接地址信息报文
	 */
	int MSG28101 = 28101;
	
	
	//中心发送码(中心到银行)
	int MSG10102 = 10102;
	int MSG10202 = 10202;
	int MSG10302 = 10302;
	int MSG10402 = 10402;
	int MSG20104 = 20104;
	int MSG20204 = 20204;
	int MSG20304 = 20304;
	int MSG20404 = 20404;
	int MSG21002 = 21002;
	int MSG30501 = 30501;
	int MSG30601 = 30601;
	int MSG31001 = 31001;
	int MSG28202 = 28202;
	
	int MSG21202 = 21202;
	int MSG21204 = 21204;
	int MSG28102 = 28102;
	
	int MSG13102 = 13102;
	int MSG13202 = 13202;
	int MSG13204 = 13204;
	
	int MSG10704 = 10704;
	int MSG10804 = 10804;
	int MSG10703 = 10703;
	int MSG10803 = 10803;
	int MSG13101 = 13101;
	int MSG13103 = 13103;
	int MSG13201 = 13201;
	int MSG13203 = 13203;
	int MSG17103 = 17103;
	
	//签到应答
	int SignInRsp = 655826;
	
	//签约检查
	int SignCheckReq = 655632;
	
	//签约应答
	int BankSignRsp = 655594;
	
	//解约检查
	int BankCancelSignCheckReq = 655636;
	
	//解约应答
	int BankCancelSignRsp = 655596;
	
	//签约信息更改
	int SignUpdateRsp = 655740;
	
	//入金检查
	int BankDepositCheckReq = 655642;
	
	//增加资金
	int BankDepositAmountReq = 655640;
	
	//入金应答
	int BankDepositRsp = 655600;
	
	//解结资金
	int BankWithDrawDefrostReq = 655646;
	
	//冻结资金
	int BankWithDrawFrozenReq = 655644;
	
	//查询保证金余额
	int QueryBankAccountInfoReq = 852809;
	
	//签约结果通知
	int BankSignInformReq = 655634;
	
	//签到检查
	int SignInCheckReq = 459215;
	
	//解约结果通知
	int BankCancelSignInformReq = 655638;
	
	//解冻扣钱
	int BankWithDrawDeductReq = 655648;
	
	//出金应答
	int BankWithDrawRsp = 655598;
	
	//获取签约信息
	int GetBankSignInfoRsp = 655726;
	
	//获取解约信息
	int GetBankCancelSignInfoRsp = 655728;
	
	//簽退應答
	int SignOutRsp = 655828;
	
	//出入金结果查询应答
	int BankOutInMoneyQueryRsp = 656347;
	
	//获取签约信息
	int GetBankSignInfoReq = 655725;
	
	//获取解约信息
	int GetBankCancelSignInfoReq = 655727;
	
	//强制解约应答
	int BankForceCancelSignRsp = 655724;
	
	//查询余额
	int MSG16101 = 16101;
	
	//保证金余额查询应答
	int QueryBankAccountInfoRsp = 656112;
	
	//虚拟账户余额查询应答
	int QueryBankVirtualAccountBalanceRsp = 656485;
	
	//跨行调拨申请应答
	int BankExchChargeOutAppRsp = 655768;
	
	//资金划转审核应答
	int BankExchChargeOutComfirmRsp = 655770;
	
	//費用查詢應答
	int QueryExchangeBankCommisionMoneyRsp = 353042616;
	
	//强制签约应答
	int BankForceSignRsp = 655722;
	
	//资金服务内部资金调整
	int InsideAdjustTaAmountReq = 1003;
	
	//单边账应答
	int UnilateralAccountRsp = 655735;
	
	//秘钥申请应答
	int ApplySecretKeyRsp = 655964;
	
}

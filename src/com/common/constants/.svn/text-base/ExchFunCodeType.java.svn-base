package com.common.constants;

/**
 * @ClassName: ExchFunCodeType
 * @Description: TODO 交易所FunCode
 * @author: zhang.wei1
 * @date: 2016年8月31日 下午1:38:21
 */
public enum ExchFunCodeType {

	Heat(0),						//心跳
	
	SignInReq(655825), 				// 签到
	SignInRsp(655826),

	SignInCheckReq(459215), 		// 签到检查
	SignInCheckRsp(459216),

	SignOutReq(655827), 			// 签退
	SignOutRsp(655828),

	BankSignReq(655593), 			// 签约
	BankSignRsp(655594),

	BankSignCheckReq(655632),		// 签约检查
	BankSignCheckRsp(655633),

	BankCancelSignReq(655595), 		// 解约
	BankCancelSignRsp(655596),

	BankCancelSignCheckReq(655636), // 解约检查
	BankCancelSignCheckRsp(655637),

	GetBankSignInfoReq(655725), 	// 获取出入金签约信息
	GetBankSignInfoRsp(655726),

	GetBankCancelSignInfoReq(655727), // 预解约
	GetBankCancelSignInfoRsp(655728),

	SignUpdateReq(655739), 			// 签约信息变更
	SignUpdateRsp(655740),

	BankDepositReq(655599), 		// 入金
	BankDepositRsp(655600),

	BankDepositCheckReq(655642), 	// 入金检查
	BankDepositCheckRsp(655643),

	BankDepositAmountReq(655640), 	// 入金增加资金
	BankDepositAmountRsp(655641),

	BankWithDrawReq(655597), 		// 出金
	BankWithDrawRsp(655598),

	BankWithDrawFrozenReq(655644), // 出金冻结
	BankWithDrawFrozenRsp(655645),

	BankWithDrawDefrostReq(655646), // 出金解冻
	BankWithDrawDefrostRsp(655647),

	BankWithDrawDeductReq(655648), // 出金扣钱
	BankWithDrawDeductRsp(655649),

	GetUsableMoneyReq(852809), 		// 获取可出金额
	GetUsableMoneyRsp(852810),

	BankClearReq(655652), 			// 银行清算（管理端发起）
	BankClearRsp(655653),

	BankCheckAcoountReq(655729), 	// 银行对账（管理端发起）
	BankCheckAcoountRsp(655730),

	ExchCheckinfoReq(656197), 			// 交易所对账（资金服务发起）
	ExchCheckinfoRsp(656198),

	UnilateralAccountReq(655734), 	// 单边帐调整(管理端发起)
	UnilateralAccountRsp(655735),

	InsideAdjustTaAmountReq(1003), 	// 内部调整金额(出入金服务-->资金服务)
	InsideAdjustTaAmountRsp(1004),

	ManageBankParamConfigReq(655737), // 托管银行参数管理
	ManageBankParamConfigRsp(655738),

	BankOutInMoneyQueryReq(656346), // 出入金结果查询
	BankOutInMoneyQueryRsp(656347),

	ApplySecretKeyReq(655963), 		// 银行平台秘钥申请
	ApplySecretKeyRsp(655964),

	QueryBankAccountInfoReq(656111), // 账户保证金余额查询
	QueryBankAccountInfoRsp(656112),

	BankExchChargeOutAppReq(655767),	//交易所手续费结转（出金）申请
	BankExchChargeOutAppRsp(655768),

	BankExchChargeOutComfirmReq(655769),	//交易所手续费结转（出金）审核确认
	BankExchChargeOutComfirmRsp(655770),

	QueryBankVirtualAccountBalanceReq(656484),	//银行虚拟账户余额查询
	QueryBankVirtualAccountBalanceRsp(656485),

	QueryExchExpensesAccountsReq(353042800),	//交易所费用账号查询
	QueryExchExpensesAccountsRsp(353042801),

	QueryexchangecommisionlogReq(353042647),	//交易所手续费结转流水查询
	QueryexchangecommisionlogRsp(353042648),
	
	BankGetIdentifyCodeReq(656221), 	// 获取手机验证码
	BankGetIdentifyCodeRsp(656222);

	private Integer type;

	public Integer getType() {
		return type;
	}

	private ExchFunCodeType(Integer type) {
		this.type = type;
	}
}

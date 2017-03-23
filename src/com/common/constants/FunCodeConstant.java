package com.common.constants;
/**
 * 功能号常量
 * 
 * @author yang.xinwen
 * 
 */
public interface FunCodeConstant {

    /**
     * 通用查询请求
     */
    int                     QueryCommonReq                     = 17104898;
    /**
     * 通用查询应答
     */
    int                     QueryCommonRsp                     = 17104901;

    /**
     * 查询账户和商品信息
     */
    int                     QueryAccountAndGoodsReq            = 17104907;
    /**
     * 交易账户商品参数请求
     */
    int                     FID_QueryAccountGoodsTypeReq       = 17104912;
    /**
     * 交易账户商品参数应答
     */
    int                     FID_QueryAccountGoodsTypeRsp       = 17104913;
    /**
     * 查询账户和商品信息回应-账户、交易所、市场、商品信息
     */
    int                     QueryAccountAndGoodsRsp            = 17104908;
    /**
     * 修改账户密码请求
     */
    int                     ModifyAccountPwdReq                = 393217;

    int                     FID_MarketStatusRsp                = 393223;
    /**
     * 修改账户密码应答
     */
    int                     ModifyAccountPwdRsp                = 393218;
    /** 用户登录请求 */
    int                     FID_LoginReq                       = 131293;
    /** 用户登录应答 */
    int                     FID_LoginRsp                       = 131294;
    /** 用户登出请求 */
    int                     FID_LogoutReq                      = 131295;
    /** 用户登出应答 */
    int                     FID_LogoutRsp                      = 131296;
    /** 查询出入金设置 */
    int FID_BANK_QListBankParamReq         = 336265514;

    /**
     * 资金调整请求
     */
    int                     AdjustTaAmountReq                  = 852262;
    /**
     * 资金调整应答
     */
    int                     AdjustTaAmountRsp                  = 852263;

    /**
     * 资金调整请求
     */
    int                     FundModifyReq                      = 80001;
    /**
     * 查询账户菜单权限请求
     */
    int                     QUERY_MENUAUTH_REQ                 = 17104909;
    /**
     * 查询账户菜单权限应答
     */
    int                     QUERY_MENUAUTH_RSP                 = 17104910;
    /**
     * 操作变更通知
     */
    int                     OPERATE_NOTIFY                     = 393219;
    /**
     * 开休市通知
     */
    int                     MARKET_CHANGE                      = 393220;

    /**
     * 出入金强制解约请求
     */
    int                     FID_BankForceCancelSignReq         = 655723;

    /**
     * 出入金强制解约应答
     */
    int                     FID_BankForceCancelSignRsp         = 655724;

    /**
     * 清算对账状态查询请求
     */
    int                     FID_BANK_QuerySettleCheckStatusReq = 353042559;

    /**
     * 清算对账状态查询返回
     */
    int                     FID_BANK_QuerySettleCheckStatusRsp = 353042560;

    /**
     * 单边账处理请求
     */
    int                     FID_UnilateralAccountReq           = 655734;

    /**
     * 单边账处理应答
     */
    int                     FID_UnilateralAccountRsp           = 655735;

    /**
     * 强制签约请求
     */
    int                     FID_BankForceSignReq               = 655721;

    /**
     * 强制签约应答
     */
    int                     FID_BankForceSignRsp               = 655722;

    /**
     * 风控通知协议
     */
    int                     FID_RiskControlMsg                 = 262572;

    /**
     * 投资者公告协议
     */
    int                     FID_RiskCtrlBltRsp                 = 263090;

    /**
     * 风控通知协议
     */
    int                     FID_RiskControlMember              = 262571;

    /**
     * 投资者公告协议
     */
    int                     FID_RiskControlInvestor            = 263091;

    /// <summary>
    /// 发售委托单请求(0, 70, 213)
    /// </summary>
    // [Description("发售委托单请求")]
    int                     FID_IMOrderReq                     = 4587733;
    /// <summary>
    /// 发售委托单应答(0, 70, 214)
    /// </summary>
    // [Description("发售委托单应答")]
    int                     FID_IMOrderRsp                     = 4587734;
    /// <summary>
    /// 发售委托审核请求(0, 70, 216)
    /// </summary>
    // [Description("发售委托审核请求")]
    int                     FID_IssueAuditReq                  = 4587736;
    /// <summary>
    /// 发售委托审核应答(0, 70, 217)
    /// </summary>
    // [Description("发售委托审核应答")]
    int                     FID_IssueAuditRsp                  = 4587737;
    /// <summary>
    /// 认购方指定账户交易结构(0, 70, 218)
    /// </summary>
    // [Description("认购方指定账户交易结构")]
    int                     FID_ArrayIAppointedDeal            = 4587738;
    /// <summary>
    /// 认购方指定账户交易请求(0, 70, 219)
    /// </summary>
    // [Description("认购方指定账户交易请求")]
    int                     FID_IAppointedDealReq              = 4587739;
    /// <summary>
    /// 认购方指定成交应答(0, 70, 220)
    /// </summary>
    // [Description("认购方指定成交应答")]
    int                     FID_IAppointedDealRsp              = 4587740;
    /// <summary>
    /// 发行市场摇号结果确认请求(0, 70, 221)
    /// </summary>
    // [Description("发行市场摇号结果确认请求")]
    int                     FID_ILotteryConfirmReq             = 4587741;
    /// <summary>
    /// 发行市场摇号结果确认应答(0, 70, 222)
    /// </summary>
    // [Description("发行市场摇号结果确认应答")]
    int                     FID_ILotteryConfirmRsp             = 4587742;
    /// <summary>
    /// 发行市场配号请求(0, 70, 223)
    /// </summary>
    // [Description("发行市场配号请求")]
    int                     FID_IDistributionReq               = 4587743;
    /// <summary>
    /// 发行市场配号应答(0, 70, 224)
    /// </summary>
    // [Description("发行市场配号应答")]
    int                     FID_IDistributionRsp               = 4587744;
    /// <summary>
    /// 发行市场摇号请求(0, 70, 225)
    /// </summary>
    // [Description("发行市场摇号请求")]
    int                     FID_ILotteryReq                    = 4587745;
    /// <summary>
    /// 发行市场摇号应答(0, 70, 226)
    /// </summary>
    // [Description("发行市场摇号应答")]
    int                     FID_ILotteryRsp                    = 4587746;
    /// <summary>
    /// 发售转二级市场请求(0, 70, 227)
    /// </summary>
    // [Description("发售转二级市场请求")]
    int                     FID_ITransToBidReq                 = 4587747;
    /// <summary>
    /// 发售转二级市场应答(0, 70, 228)
    /// </summary>
    // [Description("发售转二级市场应答")]
    int                     FID_ITransToBidRsp                 = 4587748;
    /// <summary>
    /// 认购方指定账户交易审核请求(0, 70, 230)
    /// </summary>
    // [Description("认购方指定账户交易审核请求")]
    int                     FID_IAppointedDealAuditReq         = 4587750;
    /// <summary>
    /// 认购方指定账户交易审核应答(0, 70, 231)
    /// </summary>
    // [Description("认购方指定账户交易审核应答")]
    int                     FID_IAppointedDealAuditRsp         = 4587751;
    /// <summary>
    /// 发售方指定账户交易结构(0, 70, 232)
    /// </summary>
    // [Description("发售方指定账户交易结构")]
    int                     FID_ArrayIAppointedDealSeller      = 4587752;
    /// <summary>
    /// 发售指定账户交易请求(0, 70, 233)
    /// </summary>
    // [Description("发售指定账户交易请求")]
    int                     FID_ISellerAppointedDealReq        = 4587753;
    /// <summary>
    /// 发售指定成交应答(0, 70, 234)
    /// </summary>
    // [Description("发售指定成交应答")]
    int                     FID_ISellerAppointedDealRsp        = 4587754;
    /// <summary>
    /// 承销会员账户交易结构(0, 70, 235)
    /// </summary>
    // [Description("承销会员账户交易结构")]
    int                     FID_ArrayIConsignmentDeal          = 4587755;
    /// <summary>
    /// 承销会员账户交易请求(0, 70, 236)
    /// </summary>
    // [Description("承销会员账户交易请求")]
    int                     FID_IConsignmentDealReq            = 4587756;
    /// <summary>
    /// 承销指定成交应答(0, 70, 237)
    /// </summary>
    // [Description("承销指定成交应答")]
    int                     FID_IConsignmentDealRsp            = 4587757;
    /// <summary>
    /// 发售方指定账户交易审核请求(0, 70, 238)
    /// </summary>
    // [Description("发售方指定账户交易审核请求")]
    int                     FID_ISellerAppointedDealAuditReq   = 4587758;
    /// <summary>
    /// 发售方指定账户交易审核应答(0, 70, 239)
    /// </summary>
    // [Description("发售方指定账户交易审核应答")]
    int                     FID_ISellerAppointedDealAuditRsp   = 4587759;
    /// <summary>
    /// 承销会员账户交易审核请求(0, 70, 240)
    /// </summary>
    // [Description("承销会员账户交易审核请求")]
    int                     FID_IConsignmentDealAuditReq       = 4587760;
    /// <summary>
    /// 承销会员账户交易审核响应(0, 70, 241)
    /// </summary>
    // [Description("承销会员账户交易审核响应")]
    int                     FID_IConsignmentDealAuditRsp       = 4587761;
    /// <summary>
    /// 确认分配结束请求(0, 70, 242)
    /// </summary>
    // [Description("确认分配结束请求")]
    int               FID_IConfirmDistributeFinishReq    = 4587762;
    /// <summary>
    /// 确认分配结束应答(0, 70, 243)
    /// </summary>
    // [Description("确认分配结束应答")]
    int               FID_IConfirmDistributeFinishRsp    = 4587763;
    /// <summary>
    /// 终止发行请求(0, 70, 244)
    /// </summary>
    // [Description("终止发行请求")]
    int               FID_ITerminateIssueReq             = 4587764;
    /// <summary>
    /// 终止发行应答(0, 70, 245)
    /// </summary>
    // [Description("终止发行应答")]
    int               FID_ITerminateIssueRsp             = 4587765;
}

package com.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: FinalConstants
 * @Description: TODO	常量配置
 */
public class FinalConstants {

	public final static String EXCH_NO = "exch_no";
	
	/*真：1；假：0*/
	public final static class Is {
		public final static Integer YES = 1;
		public final static Integer NO = 0;
	}

	public final static class FileType {
		public static final String CHECKACCOUNT_DATA_FILE = "EF01";// 对账数据文件
		public static final String CLEAR_DATA_FILE = "EF02";// 清算数据文件
		public static final String CLEAR_DATA_FILE_RESULT = "EF03";// 清算数据结果返回文件
		public static final String SETTLE_DATA_FILE = "EF04";//结算数据文件
		public static final String CLIENT_FILE_INFO = "EF05";	// 客户信息
		public static final String PRODUCT_DATA_FILE = "BF11";//商品信息文件
	}

	public final static class CheckError {
		public static final int SUCCESS = 0;	// 成功
		public static final int NOT_SUC = 1;	// 银行成功，平台缺少
		public static final int FAIL_SUC = 2;	// 银行成功，平台失败
		public static final int DO_SUC = 3;		// 银行成功，平台处理中
		public static final int SUC_FAIL = 4;	// 银行失败，平台成功
		public static final int DO_FAIL = 5;	// 银行失败，平台处理中 
	}

	public final static class Topic {
		public final static String CENTER_REQ = "center_req";

		public final static String CASH_REQ = "cash_req";
		public final static String CASH_RSP = "cash_rsp";
		public final static String MONEY_REQ = "money_req";
		public final static String MONEY_RSP = "money_rsp";
		public final static String SETTLE_REQ = "settle_req";
		public final static String SETTLE_RSP = "settle_rsp";

		public final static String BANK_QUERY_REQ = "bank_query_req";
		public final static String BANK_QUERY_RSP = "bank_query_rsp";
	}
	
	// 发送消息配置路由器key
	public final static class routingKey {
		public static final String EXCH_BING_KEY = "";

		public static final String BANK_BING_KEY = "";

	}

	// 日志类型[INFO:1-信息; WARN:2-警告; ERROR:3-错误]
	public final static class LogType {

		public static final Integer INFO = 1;// 信息

		public static final Integer WARN = 2;// 警告

		public static final Integer ERROR = 3;// 错误

	}

	/** 
	 * @ClassName: SenderType 
	 * @Description: TODO	修改
	 * @author: zhang.wei1
	 * @date: 2016年8月26日 下午5:43:38  
	 */
	// 发送方(接收方)	Y
	public final static class SenderType {

		public final static int CENTER = 000000;// 清算中心

		public final static int EXCH = 1;// 交易所

		public final static int BANK = 2;// 银行
		
		public final static int CLIENT = 11; // 客户端

		public final static int FUND = 12; // 资金服务

		public final static int MANAGER = 13; // 管理端
		
		public final static int JOB = 14; // 定时服务
		
		public final static int BANKSERVICE = 15; // 银行服务
		
		public final static int SETTLE = 16; // 结算服务
		
		public final static int TAS_MANAGER = 20; // TAS管理端
		
		public final static Map<Integer, String> ROUTINGKEY = new HashMap<Integer, String>();
		static{
			ROUTINGKEY.put(0,  "rabbitmq.bankservice.center.bingingkey");
			ROUTINGKEY.put(1,  "rabbitmq.bankservice.exch.bingingkey");
			ROUTINGKEY.put(2,  "rabbitmq.bankservice.bank.bingingkey");
			ROUTINGKEY.put(11, "rabbitmq.bankservice.exch.client.bingingkey");
			ROUTINGKEY.put(12, "rabbitmq.bankservice.exch.manager.bingingkey");
			ROUTINGKEY.put(13, "rabbitmq.bankservice.exch.fund.bingingkey");
			ROUTINGKEY.put(14, "rabbitmq.bankservice.exch.job.bingingkey");
			ROUTINGKEY.put(15, "rabbitmq.bankservice.exch.bankservice.bingingkey");
			ROUTINGKEY.put(16, "rabbitmq.bankservice.exch.settle.bingingkey");
			ROUTINGKEY.put(20, "rabbitmq.bankservice.tas.manager.bingingkey");
		}
	}

	// 客户信息状态
	public final static class ClientStatus {

		public static final Integer OK = 0;// 正常

		public static final Integer CANCEL = 1;// 注销
	}
	
	// 银行卡状态
		public final static class CardStatus {

			public static final Integer NO_BIND = 0;// 未绑定

			public static final Integer BIND = 1;// 绑定
			
			public static final Integer BIND_OFF = 2;// 已解绑
		}
		
	// 是否强制
		public final static class IsForce {

			public static final Integer NO = 0;// 不强制

			public static final Integer YES = 1;// 强制
		}

	// 客户信息变跟类别
	public final static class ChangeType {
		public static final Integer ADD = 1;// 增加
		public static final Integer ALTER = 2;// 修改
		public static final Integer CANCEL = 3;// 注销
		public static final Integer BIND = 4;// 绑卡
		public static final Integer RELIEVE = 5;// 解绑
		public static final Integer CHANGE = 6;// 换卡

		public static final Integer OPEN = 1;// 正常
		public static final Integer SIGN = 1;// 签约
		public static final Integer UPDATE = 2;// 修改

	}

	// 处理状态
	public final static class DealStatus {
		public static final Integer SUCCESS = 0;// 成功
		public static final Integer FAIL = 1;// 失败
		public static final Integer SEND = 2;// 已发送
		public static final Integer CHECK = 3;// 已发送检查
		public static final Integer SEND_DEAL = 4;// 已发送处理
//		public static final Integer SEND_FROZEN = 5;// 已发送冻结
		public static final Integer UNKNOW = 9;// 未明确
		
		public static final Integer UNREGISTEROUT = 21;// 没签退
		public static final Integer VALID = 22;// 已校验
		public static final Integer VALIDFAIL = 23;// 校验失败
		public static final Integer BANKFAIL = 24;// 银行返回清算失败
	}

	// 签到状态
	public final static class RegisterStatus {

		public static final Integer ON = 0;// 在线
		public static final Integer OFF = 1;// 离线
		public static final Integer NO_NEED = 3;//不需要签到
	}

	// 业务状态
	public final static class TranStatus {

		public static final Integer OK = 0;// 正常
		public static final Integer STOP = 1;// 暂停
	}

	// 签约状态
	public final static class SignStatus {

		public static final Integer NO_SIGN = 0;// 未签约

		public static final Integer SIGN = 1;// 已签约

		public static final Integer SIGN_OFF = 2;// 已解约
	}

	// 签到标志[ON:1-签到; OFF2-签退]
	public final static class RegisterFlag {

		public static final Integer ON = 1;// 签到

		public static final Integer OFF = 2;// 签退
	}

	public final static class MapperSapce {

	}

	public final static class Diction {// 系统字典表D_QUERY_CODE常量定义类

	}

	public final static class RetCode {// 返回码

		public static final String SUCCESS = "0";// 成功
		public static final String FAIL = "1";// 失败
		public static final String REFUSE = "8";// 已拒绝
		public static final String UNKNOWN = "9";// 未明确

	}

	public final static class RecvSendType {

		public final static Integer RECV = 1; // 接收

		public final static Integer SEND = 2;// 发送
		
		public final static Integer LOCAL = 99; //本地生成

	}

	/**
	 * 业务类型
	 */
	public final static class TranType {

		public final static Integer OUT = 0; // 出金

		public final static Integer IN = 1;// 入金

		public final static Integer CLEAR = 2;// 清算

		public final static Integer PAY = 4;// 付款

		public final static Integer PAY_CONFIRM = 5;// 收款
		
		public final static Integer OUT_APPLY = 6;// 出金申请
		public final static Integer IN_APPLY = 7;// 入金申请
	}

	/**
	 * 是否自动签到签退
	 */
	public final static class IsAutoRegister {

		public final static Integer YES = 0; // 是

		public final static Integer NO = 1;// 否

	}

	/**
	 * 出入金标志
	 */
	public final static class OutInFlag {

		public final static Integer OUT = 0; // 出金

		public final static Integer IN = 1;// 入金
		
		public final static Integer OUT_APPLY = 2; // 出金申请

		public final static Integer IN_APPLY = 3;// 入金申请

	}

	public final static class RecvMsgFlag {

		public static final int EXCH = 1;// 交易所

		public static final int BANK = 2;// 银行

	}

	/**
	 * 签约标志
	 */
	public final static class SignFlag {

		public static final Integer SIGN_ON = 1;// 签约

		public static final Integer SIGN_OFF = 2;// 解约

	}

	/**
	 * 
	 * 系统参数配置
	 *
	 */
	public final static class SysParam {

		public static final String CENTER_NO = "000000"; // 中心统一编号

		public static final String PARAM_NAME = "clear_model"; // 参数名称

		// 清算模式
		public static final String WAY = "1"; // 1-通道式

		public static final String UNIFY = "2"; // 2-统一清算式

	}

	public final static class SerNo {// 报文的业务编号

		public static final String EXCH_AMT_IN = "101"; // 交易所入金

		public static final String EXCH_AMT_OUT = "102"; // 交易所出金

		public static final String EXCH_SIGN = "103"; // 交易所签约

		public static final String EXCH_SIGN_OFF = "104"; // 交易所解约

	}

	public final static class PayStatus {// 付款状态

		public static final Integer PAID = 0; // 已付

		public static final Integer FROZEN = 1; // 冻结

	}
	//?????已经存在
	public final static class Status {// 客户信息状态

		public static final Integer OK = 0; // 正常

		public static final Integer FALSE = 1; // 注销

	}

	public final static class IsStatus {// 是否

		public static final String NO = "0"; // 否

		public static final String YES = "1"; // 是

	}

	public final static class isNeedRegister {// 是否需要签到

		public static final Integer NO = 0; // 否

		public static final Integer YES = 1; // 是
	}
	
	public final static class QueryType {// 账户信息查询类别
		
//		[1-查询所有信息; 2-查询余额; 3-查询银行卡]
		public static final String ALLMSG = "1"; // 查询所有信息

		public static final String AVAILABLE_AMT = "2"; // 查询余额
		
		public static final String QUERY_CARD = "3"; // 查询银行卡
	}
	
	public final static class TransferType {// 商户账户查询类别
//		[FUND:1-资金调拨; FEE:2-费用划转; ACCURAL:3-利息划转]

		public static final Integer FUND = 1; // 否

		public static final Integer FEE = 2; // 是
		
		public static final Integer ACCURAL = 3; // 是
	}
	
	//TAS返回码定义
	public final static class TASRetCode {

		public final static Integer RECV = 1; // 接收

		public final static Integer SEND = 2;// 发送
		
		public final static Integer LOCAL = 99; //本地生成

	}
	
	//查询交易类型
	//TAS返回码定义
	public final static class QueryTranType {

		public final static Integer ACCT = 1; // 接收

		public final static Integer MER_ACCT = 2;// 发送
			
		public final static Integer TRAN = 3; //本地生成

	}
	
	//对账结果
	public final static class CHECK_RESULT {

		public final static Integer NOT = 0; // 未平

		public final static Integer EQUAL = 1;// 已平
	}
	
	//对账结果
		public final static class CHECK_STATUS {

			public final static Integer NON = 0; // 未对

			public final static Integer CHECKED = 1;// 已对
		}
		
	// 对账结果
	public final static class CHECK_DEAL_STATUS {

		public final static Integer NON = 0; // 未处理

		public final static Integer DEAL = 1;// 已处理
	}

	// 对账结果
	public final static class CHECK_DEAL_TYPE {

		public final static Integer NON = 0; // 不調整

		public final static Integer CHG = 1;// 調整
	}
	
	//客户端返回状态
	public final static class CLIENT_STATUS {

		public final static Integer BankDealStatus__Succeed = 0; // 成功

		public final static Integer BankDealStatus__Pend = 1;// 待审核
		
		public final static Integer BankDealStatus__BankWebComfirm = 2;// 待银行网页确认
		
		public final static Integer BankDealStatus__BankProcessing= 3;// 银行处理中
	}

}

package com.muchinfo.common.constants;

/**
 * 静态常量类 ClassName: FinalConstants <br/>
 * date: 2016年6月15日 下午1:52:49 <br/>
 * 
 * @author zhou.yao
 * @version
 */
public class FinalConstants {
	
	/**
	 * functionId 方法编号
	 */
	public final static class FunctionId {
		/*---------------------------交易所发起的业务----------------------------------*/
		public static final String MSG311029 = "311029";// 银行签到/签退

		public static final String MSG399000 = "399000";// 发送开闭市通知

		public static final String MSG309000 = "309000";// 报送客户/会员开户信息

		public static final String MSG309001 = "309001";// 根据客户/会员开户信息内容修改

		public static final String MSG309002 = "309002";// 报送客户销户信息

		public static final String MSG312009 = "312009";// 查询资金余额

		public static final String MSG315001 = "315001";// 客户入金

		public static final String MSG315002 = "315002";// 客户出金

		public static final String MSG369000 = "369000";// 交易所报送文件通知

		public static final String MSG99900 = "?";// 客户信息变更文件
													// yyyymmdd_xxx(交易所代码)_clientInfoMod.txt

		public static final String MSG999001 = "??";// 成交清算文件
													// yyyymmdd_xxx(交易所代码)_dealInfo.txt

		public static final String MSG999002 = "???";// 资金余额文件
														// yyyymmdd_xxx(交易所代码)_memberFund.txt

		//新增接口
		public static final String MSG309999 = "309999";  //查询交易所会员信息
		
		public static final String MSG369001 = "369001";  //交易所向清算平台报送成交数据
		
		public static final String MSG369002 = "369002"; //报送交易单
		
		public static final String MSG369003 = "369003"; //向清算所报送交割单                                        
		
		public static final String MSG369004 = "369004"; //向清算所报送收费单     
		
		public static final String MSG369005 = "369005"; //向清算所报送行情数据
		
		public static final String MSG315003 = "315003"; //查询清算中心出入金流水
		
		public static final String MSG319005 = "319005";//客户银行信息变更
		
		public static final String MSG312023 = "312023";//客户主/副卡变更        
		
		public static final String MSG313010 = "313010";//查询可签约银行列表
		
		public static final String MSG312022 = "312022";//客户与银行解约(仅限中国银行)
		
		public static final String MSG378001 = "378001"; //报送商品新增  
		
		public static final String MSG378002 = "378002";  //报送商品修改                 
		
		public static final String MSG378003 = "378003";    // 报送商品合约状态变更                                             
		
		public static final String MSG378004 = "378004"; //查询清算中心产品类别
		
		public static final String MSG378011 = "378011"; //持仓冻结/解冻同步
		
		public static final String MSG378012 = "378012"; //持仓手工调账份额变更同步
		
		public static final String MSG378013 = "378013";  // 持仓非交易过户同步
		
		
		/*---------------------------广清所发起的业务----------------------------------*/
		public static final String MSG280000 = "280000";// 客户与银行签约/解约通知
		
		public static final String MSG280001 = "280001";//客户银行信息变更

		public static final String MSG280002 = "280002";// 客户银行卡变更

		public static final String MSG280003 = "280003";// 客户入金

		public static final String MSG280004 = "280004";// 客户出金

		public static final String MSG280005 = "280005";// 清算中心向交易所推送文件通知
		
		public static final String MSG280006 = "280006";// 清算中心通知交易所密钥交互结果
		
		public static final String MSG280007 = "280007";// 清算中心向交易所推送审核结果

		public static final String MSG119010 = "119010";// 客户与银行签约/解约

		public static final String MSG00000 = "?";// 银行清算反馈文件
													// yyyymmdd_xxx(交易所代码)_yyy(银行产品代码)_bankSettle.txt

		public static final String MSG00001 = "??";// 银行余额文件
													// yyyymmdd_xxx(交易所代码)_yyy(银行产品代码)_bankBalance.txt

		public static final String MSG00002 = "???";// 账户类对帐明细文件
													// yyyymmdd_xxx(交易所代码)_memberAccount.txt

		public static final String MSG00003 = "????";// 银行出入金对账文件
														// yyyymmdd_xxx(交易所代码)_yyy(银行产品代码)_bankCheck.txt

	}
    /**
     * Des加密key
     * ClassName: DesKey <br/>
     * Function: TODO ADD FUNCTION. <br/>
     * date: 2016年8月15日 下午5:12:51 <br/>
     * @author zhou.yao
     * @version FinalConstants
     */
	public final static class DesKey {
		public static final String KEY1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		public static final String KEY2 = "8C7ZORS1B9DN6A0EPIK23T5UH4WXYMLFJVGQ";
		public static final String KEY3 = "Y3XFSIK29NQ7RJHT6OGW50D1ZPVELB8MUA4C";

	}
	
	/**
	 * 发送给服务器端的IP地址 、端口号配置
	 * ClassName: SocketConfig <br/>
	 * date: 2016年8月15日 下午5:25:10 <br/>
	 * @author zhou.yao
	 * @version FinalConstants
	 */
	public final static class SocketConfig{
		//"192.168.31.94 8989" "192.168.30.72", 3800
		public static final String HOST = "192.168.31.94"; //tas
		
		public static final int  PORT = 8989; 
	}
	

}

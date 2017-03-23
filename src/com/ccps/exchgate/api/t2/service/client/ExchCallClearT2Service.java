package com.ccps.exchgate.api.t2.service.client;



import com.hundsun.jresplus.remoting.impl.annotation.Service;
import com.hundsun.jresplus.remoting.impl.annotation.ServiceModule;
import com.hundsun.jresplus.remoting.impl.annotation.ServiceParam;
import com.muchinfo.common.constants.FinalConstants;

/**
 * 交易所发起向清算中心的服务调用
 * @author jiangzz
 *
 */
@ServiceModule
public interface ExchCallClearT2Service {
	//--------------------------------- 交易所发起向清算中心(广清所)的服务调用-------------------------------------------//
	@Service(functionId=FinalConstants.FunctionId.MSG311029,desc="银行签到/签退")
	public String dealMSG311029(@ServiceParam(value="reqStr") String reqStr);

	@Service(functionId=FinalConstants.FunctionId.MSG399000,desc="发送开闭市通知")
	public String dealMSG399000(@ServiceParam(value="reqStr") String reqStr);
	
	@Service(functionId=FinalConstants.FunctionId.MSG309000,desc="报送客户/会员开户信息")
	public String dealMSG309000(@ServiceParam(value="reqStr") String reqStr);
	
	@Service(functionId=FinalConstants.FunctionId.MSG309001,desc="根据客户/会员开户信息内容修改")
	public String dealMSG309001(@ServiceParam(value="reqStr") String reqStr);
	
	@Service(functionId=FinalConstants.FunctionId.MSG309002,desc="报送客户销户信息")
	public String dealMSG309002(@ServiceParam(value="reqStr") String reqStr);
	
	@Service(functionId=FinalConstants.FunctionId.MSG312009,desc="查询资金余额")
	public String dealMSG312009(@ServiceParam(value="reqStr") String reqStr);
	
	@Service(functionId=FinalConstants.FunctionId.MSG315001,desc="客户入金")
	public String dealMSG315001(@ServiceParam(value="reqStr") String reqStr);
	
	@Service(functionId=FinalConstants.FunctionId.MSG315002,desc="客户出金")
	public String dealMSG315002(@ServiceParam(value="reqStr") String reqStr);
	
	@Service(functionId=FinalConstants.FunctionId.MSG369000,desc="交易所报送文件通知")
	public String dealMSG369000(@ServiceParam(value="reqStr") String reqStr);
	
	@Service(functionId=FinalConstants.FunctionId.MSG369001,desc="报送成交数据")
	public String dealMSG369001(@ServiceParam(value="reqStr") String reqStr);
	
	@Service(functionId=FinalConstants.FunctionId.MSG369002,desc="报送委托单")
	public String dealMSG369002(@ServiceParam(value="reqStr") String reqStr);
	
	@Service(functionId = FinalConstants.FunctionId.MSG369003, desc = "向清算所报送交割单 ")
	public String dealMSG369003(@ServiceParam(value = "reqStr") String reqStr);
	
	@Service(functionId = FinalConstants.FunctionId.MSG369004, desc = "向清算所报送收费单")
	public String dealMSG369004(@ServiceParam(value = "reqStr") String reqStr);
	
	@Service(functionId = FinalConstants.FunctionId.MSG369005, desc = "向清算所报送行情数据")
	public String dealMSG369005(@ServiceParam(value = "reqStr") String reqStr);
	
	@Service(functionId = FinalConstants.FunctionId.MSG378001, desc = "报送商品新增 ")
	public String dealMSG378001(@ServiceParam(value = "reqStr") String reqStr);
	
	@Service(functionId = FinalConstants.FunctionId.MSG378002, desc = "报送商品修改")
	public String dealMSG378002(@ServiceParam(value = "reqStr") String reqStr);
	
	@Service(functionId = FinalConstants.FunctionId.MSG378003, desc = "报送商品合约状态变更")
	public String dealMSG378003(@ServiceParam(value = "reqStr") String reqStr);
	
	
	/**
	 * 
	 * 新增T2接口
	 * @author zhou.yao
	 * @param reqStr
	 * @return
	 */
	@Service(functionId = "309999", desc = "查询交易所会员信息")
	public String dealMSG309999(@ServiceParam(value = "reqStr") String reqStr);

	
	
	
	
	@Service(functionId = "315003", desc = "查询清算中心出入金流水")
	public String dealMSG315003(@ServiceParam(value = "reqStr") String reqStr);
	
	@Service(functionId = "319005", desc = "客户银行信息变更")
	public String dealMSG319005(@ServiceParam(value = "reqStr") String reqStr);
	
	@Service(functionId = "319006", desc = "客户与银行签/解约")
	public String dealMSG319006(@ServiceParam(value = "reqStr") String reqStr);
	
	@Service(functionId = "312023", desc = "客户主/副卡变更")
	public String dealMSG312023(@ServiceParam(value = "reqStr") String reqStr);
	
	@Service(functionId = "313010", desc = "查询可签约银行列表")
	public String dealMSG313010(@ServiceParam(value = "reqStr") String reqStr);
	
	@Service(functionId = "312022", desc = "客户与银行解约(仅限中国银行)")
	public String dealMSG312022(@ServiceParam(value = "reqStr") String reqStr);
	
	@Service(functionId = "378004", desc = "查询清算中心产品类别")
	public String dealMSG378004(@ServiceParam(value = "reqStr") String reqStr);
	
	@Service(functionId = "378011", desc = "持仓冻结/解冻同步")
	public String dealMSG378011(@ServiceParam(value = "reqStr") String reqStr);
	
	@Service(functionId = "378012", desc = "持仓手工调账份额变更同步")
	public String dealMSG378012(@ServiceParam(value = "reqStr") String reqStr);
	
	@Service(functionId = "378013", desc = "持仓非交易过户同步")
	public String dealMSG378013(@ServiceParam(value = "reqStr") String reqStr);
	
	
}

package com.ccps.exchgate.api.t2.service.server;

import com.hundsun.jresplus.remoting.impl.annotation.Service;
import com.hundsun.jresplus.remoting.impl.annotation.ServiceModule;
import com.hundsun.jresplus.remoting.impl.annotation.ServiceParam;
import com.muchinfo.common.constants.FinalConstants;

/**
 * 清算发起
 * @author jiangzz
 *
 */
@ServiceModule
public interface ExchOwnT2Service {
  //---------------------------------清算中心(广清所)向交易所发起的服务调用-------------------------------------------//
	@Service(functionId=FinalConstants.FunctionId.MSG280000,desc="客户与银行签约/解约通知")
	public String  bankSignOrOut(@ServiceParam(value="reqStr") String reqStr);
	
	@Service(functionId=FinalConstants.FunctionId.MSG280001,desc="客户银行信息变更")
	public String bankInfoChange(@ServiceParam(value="reqStr") String reqStr);
	
	@Service(functionId=FinalConstants.FunctionId.MSG280002,desc="客户银行卡变更")
	public String  bankNoChange(@ServiceParam(value="reqStr") String reqStr);
	
	@Service(functionId=FinalConstants.FunctionId.MSG280003,desc="客户入金")
	public String  fundIn(@ServiceParam(value="reqStr") String reqStr);
	
	@Service(functionId=FinalConstants.FunctionId.MSG280004,desc="客户出金")
	public String  fundOut(@ServiceParam(value="reqStr") String reqStr);
	
	@Service(functionId=FinalConstants.FunctionId.MSG280005,desc="清算中心向交易所推送文件通知")
	public String  fileNotify(@ServiceParam(value="reqStr") String reqStr);
	
	@Service(functionId=FinalConstants.FunctionId.MSG280006,desc="清算中心通知交易所密钥交互结果")
	public String  secretkeyNotify(@ServiceParam(value="reqStr") String reqStr);
	
	@Service(functionId=FinalConstants.FunctionId.MSG280007,desc="清算中心向交易所推送审核结果")
	public String  verifyNotify(@ServiceParam(value="reqStr") String reqStr);
	
	
			
}




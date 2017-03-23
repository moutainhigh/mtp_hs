package com.muchinfo.tcp.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.ccps.exchgate.api.t2.service.client.ExchCallClearT2Service;
import com.ccps.exchgate.api.t2.service.server.ExchOwnT2Service;
import com.des.desUtils.DesUtils;
import com.muchinfo.common.constants.FinalConstants;
import com.muchinfo.common.util.PropertiesUtil;
import com.muchinfo.tcp.service.MsgDealtService;
import com.muchinfo.tcp.socket.ClientSocket;

import net.sf.json.JSONObject;

/**
 * 
 * @ClassName: MsgDealtService
 * @Description: 处理t2服务消息
 * @author zhou.yao
 * @date 2016年7月6日 上午10:38:14
 *
 */
@Service
public class MsgDealtServiceImpl implements MsgDealtService {
	private static final Logger LOGGER = Logger.getLogger(MsgDealtServiceImpl.class);
	@Autowired
	private ExchCallClearT2Service exchCallClearT2Service;// 交易所发起向清算中心(广清所)的服务调用
	@Autowired
	private ExchOwnT2Service exchOwnT2Service;// 广清所向交易所发起的服务调用

	/**
	 * 发送至清算所 sendMsgReq:(处理交易所的请求消息). <br/>
	 * 
	 * @author zhou.yao
	 * @param functionId
	 *            方法id
	 * @param jsonReq
	 *            消息内容
	 * @return
	 */
	public String sendMsgReq(String functionId, String jsonReq) throws Exception {
		String respMsg = "{\"sysError\":false,\"success\":false,\"errorInfo\":\"功能码错误\"}";
		try {
			//Boolean bool = true; // 默认不加密(OTC的是自己加解密，TAS是需要你那边加解密)
			Boolean bool = PropertiesUtil.getProperty("is.encrypt").equals("1") ? true:false;
			try {
				if (bool) {
					if (!StringUtils.isEmpty(jsonReq)) {
						if (jsonReq.indexOf("bankPassword") > -1) {
							JSONObject jsonObj = JSONObject.fromObject(jsonReq);
							String bankPassword = jsonObj.getString("bankPassword");
							if (!StringUtils.isEmpty(bankPassword)) {
								bankPassword = DesUtils.enCodePwd(bankPassword, FinalConstants.DesKey.KEY1);// 交易所发的业务银行密码加密
								jsonObj.put("bankPassword", bankPassword);
							}
							jsonReq = jsonObj.toString();
						}
					}
				}
			} catch (Exception e) {
				LOGGER.error("MsgDealtServiceImpl.sendMsgReq:json格式转化错误" + e.getMessage(), e);
				throw e;
			}

			switch (functionId) {
			case FinalConstants.FunctionId.MSG311029:
				// 银行签到/签退
				respMsg = exchCallClearT2Service.dealMSG311029(jsonReq);
				break;
			case FinalConstants.FunctionId.MSG399000:
				// 发送开闭市通知
				respMsg = exchCallClearT2Service.dealMSG399000(jsonReq);
				break;
			case FinalConstants.FunctionId.MSG309000:
				// 报送客户/会员开户信息
				respMsg = exchCallClearT2Service.dealMSG309000(jsonReq);
				break;
			case FinalConstants.FunctionId.MSG309001:
				// 根据客户/会员开户信息内容修改
				respMsg = exchCallClearT2Service.dealMSG309001(jsonReq);
				break;
			case FinalConstants.FunctionId.MSG309002:
				// 报送客户销户信息
				respMsg = exchCallClearT2Service.dealMSG309002(jsonReq);
				break;
			case FinalConstants.FunctionId.MSG312009:
				// 查询资金余额
				respMsg = exchCallClearT2Service.dealMSG312009(jsonReq);
				break;
			case FinalConstants.FunctionId.MSG315001:
				// 客户入金
				respMsg = exchCallClearT2Service.dealMSG315001(jsonReq);
				break;
			case FinalConstants.FunctionId.MSG315002:
				// 客户出金
				respMsg = exchCallClearT2Service.dealMSG315002(jsonReq);
				break;
			case FinalConstants.FunctionId.MSG369000:
				// 交易所报送文件通知
				respMsg = exchCallClearT2Service.dealMSG369000(jsonReq);
				break;
//			case FinalConstants.FunctionId.MSG99900:
//				// 客户信息变更文件 yyyymmdd_xxx(交易所代码)_clientInfoMod.txt
//				respMsg = exchCallClearT2Service.clientInfoMod(jsonReq);
//				break;
//			case FinalConstants.FunctionId.MSG999001:
//				// 成交清算文件 yyyymmdd_xxx(交易所代码)_dealInfo.txt
//				respMsg = exchCallClearT2Service.dealInfo(jsonReq);
//				break;
//			case FinalConstants.FunctionId.MSG999002:
//				// 资金余额文件 yyyymmdd_xxx(交易所代码)_memberFund.txt
//				respMsg = exchCallClearT2Service.memberFund(jsonReq);
//				break;

			/**
			 * 
			 * 新增T2接口
			 * 
			 * @author zhou.yao
			 * @param reqStr
			 * @return
			 */
			case FinalConstants.FunctionId.MSG309999:
				// 查询交易所会员信息
				respMsg = exchCallClearT2Service.dealMSG309999(jsonReq);
				break;
			case FinalConstants.FunctionId.MSG369001:
				// 查询交易所会员信息
				respMsg = exchCallClearT2Service.dealMSG369001(jsonReq);
				break;
			case FinalConstants.FunctionId.MSG369002:
				// 报送交易单
				respMsg = exchCallClearT2Service.dealMSG369002(jsonReq);
				break;
			case FinalConstants.FunctionId.MSG369003:
				// 向清算所报送交割单
				respMsg = exchCallClearT2Service.dealMSG369003(jsonReq);
				break;
			case FinalConstants.FunctionId.MSG369004:
				// 向清算所报送收费单
				respMsg = exchCallClearT2Service.dealMSG369004(jsonReq);
				break;
			case FinalConstants.FunctionId.MSG315003:
				// 查询清算中心出入金流水
				respMsg = exchCallClearT2Service.dealMSG315003(jsonReq);
				break;
			case FinalConstants.FunctionId.MSG319005:
				// 客户银行信息变更
				respMsg = exchCallClearT2Service.dealMSG319005(jsonReq);
				break;
			case FinalConstants.FunctionId.MSG312023:
				// 客户主/副卡变更
				respMsg = exchCallClearT2Service.dealMSG312023(jsonReq);
				break;
			case FinalConstants.FunctionId.MSG313010:
				// 查询可签约银行列表
				respMsg = exchCallClearT2Service.dealMSG313010(jsonReq);
				break;

			case FinalConstants.FunctionId.MSG312022:
				// 客户与银行解约(仅限中国银行)
				respMsg = exchCallClearT2Service.dealMSG312022(jsonReq);
				break;

			case FinalConstants.FunctionId.MSG378001:
				// 报送商品新增
				respMsg = exchCallClearT2Service.dealMSG378001(jsonReq);
				break;

			case FinalConstants.FunctionId.MSG378002:
				// 报送商品修改
				respMsg = exchCallClearT2Service.dealMSG378002(jsonReq);
				break;

			case FinalConstants.FunctionId.MSG378003:
				// 报送商品合约状态变更
				respMsg = exchCallClearT2Service.dealMSG378003(jsonReq);
				break;

			case FinalConstants.FunctionId.MSG378004:
				// 查询清算中心产品类别
				respMsg = exchCallClearT2Service.dealMSG378004(jsonReq);
				break;

			case FinalConstants.FunctionId.MSG378011:
				// 持仓冻结/解冻同步
				respMsg = exchCallClearT2Service.dealMSG378011(jsonReq);
				break;

			case FinalConstants.FunctionId.MSG378012:
				// 持仓手工调账份额变更同步
				respMsg = exchCallClearT2Service.dealMSG378012(jsonReq);
				break;

			case FinalConstants.FunctionId.MSG378013:
				// 持仓非交易过户同步
				respMsg = exchCallClearT2Service.dealMSG378013(jsonReq);
				break;

			default:
				break;
			}

		} catch (Exception e) {

			LOGGER.error("MsgDealtServiceImpl.sendMsgReq:" + e.getMessage(), e);
			throw e;
		}
		return respMsg;
	}

	/**
	 * 发送交易所的返回信息 sendMsgResp:(处理清算所发起的业务). <br/>
	 * 
	 * @author zhou.yao
	 * @param functionId
	 *            方法id
	 * @param jsonReq
	 *            消息内容
	 * @return
	 */
	public String sendMsgResp(String jsonReq) {
		String respMsg = "";
		try {
			String host = "192.168.30.72"; // 服务器ip地址
			int port = 3800;// 服务器端端口号
			respMsg = ClientSocket.sendMsg(host, port, jsonReq);

			/*
			 * switch (functionId) { case FinalConstants.FunctionId.MSG280000:
			 * // 客户与银行签约/解约通知
			 * 
			 * break; case FinalConstants.FunctionId.MSG280002: // 客户银行卡变更
			 * 
			 * break; case FinalConstants.FunctionId.MSG280003: // 客户入金
			 * 
			 * break; case FinalConstants.FunctionId.MSG280004: // 客户出金
			 * 
			 * break; case FinalConstants.FunctionId.MSG280005: //
			 * 清算中心向交易所推送文件通知
			 * 
			 * break; case FinalConstants.FunctionId.MSG119010: // 客户与银行签约/解约
			 * 
			 * break; case FinalConstants.FunctionId.MSG00000: // 银行清算反馈文件
			 * yyyymmdd_xxx(交易所代码)_yyy(银行产品代码)_bankSettle.txt
			 * 
			 * break; case FinalConstants.FunctionId.MSG00001: //银行余额文件
			 * yyyymmdd_xxx(交易所代码)_yyy(银行产品代码)_bankBalance.txt break; case
			 * FinalConstants.FunctionId.MSG00002: //账户类对帐明细文件
			 * yyyymmdd_xxx(交易所代码)_memberAccount.txt
			 * 
			 * break;
			 * 
			 * case FinalConstants.FunctionId.MSG00003: //银行出入金对账文件
			 * yyyymmdd_xxx(交易所代码)_yyy(银行产品代码)_bankCheck.txt
			 * 
			 * break; default: break; }
			 */

		} catch (Exception e) {
			LOGGER.error("MsgDealtServiceImpl.sendMsgResp:" + e.getMessage(), e);
			throw e;
		}
		return respMsg;
	}

}

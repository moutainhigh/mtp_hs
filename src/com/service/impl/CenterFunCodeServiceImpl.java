package com.service.impl;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.common.constants.FinalConstants;
import com.common.util.DateHelp;
import com.common.util.StringUtil;
import com.dao.Impl.RecvMsgDao;
import com.dao.Impl.SendMsgDao;
import com.model.RecvMsg;
import com.proto.CenterBank.Msg10102;
import com.proto.CenterBank.Msg10202;
import com.proto.CenterBank.Msg10302;
import com.proto.CenterBank.Msg10402;
import com.proto.CenterBank.Msg10702;
import com.proto.CenterBank.Msg10802;
import com.proto.CenterBank.Msg17102;
import com.proto.CenterBank.Msg17202;
import com.proto.CenterBank.Msg17302;
import com.proto.CenterBank.Msg17402;
import com.proto.CenterBank.Msg17502;
import com.proto.CenterBank.Msg17602;
import com.proto.CenterBank.Msg17702;
import com.proto.CenterBank.Msg17802;
import com.proto.CenterBank.Msg20104;
import com.proto.CenterBank.Msg20204;
import com.proto.CenterBank.Msg20304;
import com.proto.CenterBank.Msg20404;
import com.proto.CenterBank.Msg21304;
import com.proto.CenterBank.Msg30501;
import com.proto.CenterBank.Msg30601;
import com.proto.CenterBank.Msg31001;
import com.service.BankAmtInService;
import com.service.BankAmtOutService;
import com.service.BankSignOutService;
import com.service.BankSignService;
import com.service.BankVerifyNotifyService;
import com.service.ClientMsgService;
import com.service.DestroyTheAccountService;
import com.service.ExchAmtInService;
import com.service.ExchAmtOutService;
import com.service.ExchFileNoService;
import com.service.ExchRegisterOutService;
import com.service.ExchRegisterService;
import com.service.ExchSignOutService;
import com.service.ExchSignService;
import com.service.FunCodeService;
import com.service.SendBillMsg;
import com.service.SendMsgService;

@Service
public class CenterFunCodeServiceImpl implements FunCodeService {
	protected static final Logger LOGGER = Logger.getLogger(CenterFunCodeServiceImpl.class);

	@Resource
	private SendMsgService sendMsgServiceImpl;
	@Resource
	private SendMsgDao sendMsgDao;

	@Resource
	private RecvMsgDao recvMsgDao;
	
	@Resource
	private ClientMsgService clientMsgServiceImpl;
	@Resource
	private ExchAmtInService exchAmtInServiceImpl;
	@Resource
	private DestroyTheAccountService destroyTheAccountServiceImpl;
	@Resource
	private ExchAmtOutService exchAmtOutServiceImpl;
	@Resource
	private ExchRegisterService exchRegisterServiceImpl;
	@Resource
	private ExchRegisterOutService exchRegisterOutServiceImpl;
	@Resource
	private ExchSignService exchSignServiceImpl;
	@Resource
	private ExchSignOutService exchSignOutServiceImpl;
	@Resource
	private BankSignService bankSignServiceImpl;
	@Resource
	private BankSignOutService bankSignOutServiceImpl;
	@Resource
	private BankAmtInService bankAmtInServiceImpl;
	@Resource
	private BankAmtOutService bankAmtOutServiceImpl;
	@Resource
	private ExchFileNoService exchFileNoServiceImpl;
	@Resource
	private BankVerifyNotifyService bankVerifyNotifyServiceImpl;
	@Resource
	private SendBillMsg sendBillMsgImpl;
	
	
	
	//中心开户信息
	public void MSG10702(int funCode, byte[] body) throws Exception {
		LOGGER.info("*中心客户信息报文*");

		Msg10702.Builder msg10702 = Msg10702.newBuilder().mergeFrom(body);

		String centerSeq = msg10702.getCenterSeq();
		if (StringUtils.isEmpty(centerSeq)) {
			centerSeq = StringUtil.getSn();
			msg10702.setCenterSeq(centerSeq);
		}
		LOGGER.info("保存来包");
		Long recvMsgId = this.saveRecvMsg(funCode, FinalConstants.SenderType.CENTER,
				Long.valueOf(msg10702.getCenterSeq()), msg10702.toString(), DateHelp.getCurrentDateOfString());
		clientMsgServiceImpl.doAcctReq(msg10702.build(), recvMsgId);
	}

	// 客户信息注销
	public void MSG10802(int funCode, byte[] body) throws Exception {
		LOGGER.info("*客户信息注销*");

		Msg10802.Builder msg10802 = Msg10802.newBuilder().mergeFrom(body);

		String centerSeq = msg10802.getCenterSeq();
		if (StringUtils.isEmpty(centerSeq)) {
			centerSeq = StringUtil.getSn();
			msg10802.setCenterSeq(centerSeq);
		}
		LOGGER.info("保存来包");
		Long recvMsgId = this.saveRecvMsg(funCode, FinalConstants.SenderType.CENTER,
				Long.valueOf(msg10802.getCenterSeq()), msg10802.toString(), DateHelp.getCurrentDateOfString());
		destroyTheAccountServiceImpl.doDesTheAccReq(msg10802.build(), recvMsgId);
	}

	// 入金
	public void MSG10102(int funCode, byte[] body) throws Exception {
		LOGGER.info("*入金*");

		Msg10102.Builder msg10102 = Msg10102.newBuilder().mergeFrom(body);

		String centerSeq = msg10102.getCenterSeq();
		if (StringUtils.isEmpty(centerSeq)) {
			centerSeq = StringUtil.getSn();
			msg10102.setCenterSeq(centerSeq);
		}
		LOGGER.info("保存来包");
		Long recvMsgId = this.saveRecvMsg(funCode, FinalConstants.SenderType.CENTER,
				Long.valueOf(msg10102.getCenterSeq()), msg10102.toString(), DateHelp.getCurrentDateOfString());
		exchAmtInServiceImpl.doAmtInReq(msg10102.build(), recvMsgId);
	}

	// 出金
	public void MSG10202(int funCode, byte[] body) throws Exception {
		LOGGER.info("*出金*");

		Msg10202.Builder msg10202 = Msg10202.newBuilder().mergeFrom(body);

		String centerSeq = msg10202.getCenterSeq();
		if (StringUtils.isEmpty(centerSeq)) {
			centerSeq = StringUtil.getSn();
			msg10202.setCenterSeq(centerSeq);
		}
		LOGGER.info("保存来包");
		Long recvMsgId = this.saveRecvMsg(funCode, FinalConstants.SenderType.CENTER,
				Long.valueOf(msg10202.getCenterSeq()), msg10202.toString(), DateHelp.getCurrentDateOfString());
		exchAmtOutServiceImpl.doAmtOutReq(msg10202.build(), recvMsgId);
	}

	// 签到
	public void MSG30501(int funCode, byte[] body) throws Exception {
		LOGGER.info("*签到*");

		Msg30501.Builder msg30501 = Msg30501.newBuilder().mergeFrom(body);

		String centerSeq = msg30501.getCenterSeq();
		if (StringUtils.isEmpty(centerSeq)) {
			centerSeq = StringUtil.getSn();
			msg30501.setCenterSeq(centerSeq);
		}
		LOGGER.info("保存来包");
		Long recvMsgId = this.saveRecvMsg(funCode, FinalConstants.SenderType.CENTER,
				Long.valueOf(msg30501.getCenterSeq()), msg30501.toString(), DateHelp.getCurrentDateOfString());
		exchRegisterServiceImpl.doRegisterReq(msg30501.build(), recvMsgId);
	}

	// 签退
	public void MSG30601(int funCode, byte[] body) throws Exception {
		LOGGER.info("*签退*");

		Msg30601.Builder msg30601 = Msg30601.newBuilder().mergeFrom(body);

		String centerSeq = msg30601.getCenterSeq();
		if (StringUtils.isEmpty(centerSeq)) {
			centerSeq = StringUtil.getSn();
			msg30601.setCenterSeq(centerSeq);
		}
		LOGGER.info("保存来包");
		Long recvMsgId = this.saveRecvMsg(funCode, FinalConstants.SenderType.CENTER,
				Long.valueOf(msg30601.getCenterSeq()), msg30601.toString(), DateHelp.getCurrentDateOfString());
		exchRegisterOutServiceImpl.doRegisterOutReq(msg30601.build(), recvMsgId);
	}

	// 中心文件报送
	public void MSG31001(int funCode, byte[] body) throws Exception {
		LOGGER.info("*中心文件报送*");

		Msg31001.Builder msg31001 = Msg31001.newBuilder().mergeFrom(body);

		String centerSeq = msg31001.getCenterSeq();
		if (StringUtils.isEmpty(centerSeq)) {
			centerSeq = StringUtil.getSn();
			msg31001.setCenterSeq(centerSeq);
		}
		LOGGER.info("保存来包");
		Long recvMsgId = this.saveRecvMsg(funCode, FinalConstants.SenderType.CENTER,
				Long.valueOf(msg31001.getCenterSeq()), msg31001.toString(), DateHelp.getCurrentDateOfString());
		exchFileNoServiceImpl.doExchFileNoReq(msg31001.build(), recvMsgId);
	}
	
	// 签约
	public void MSG10302(int funCode, byte[] body) throws Exception {
		LOGGER.info("*签约*");

		Msg10302.Builder msg10302 = Msg10302.newBuilder().mergeFrom(body);

		String centerSeq = msg10302.getCenterSeq();
		if (StringUtils.isEmpty(centerSeq)) {
			centerSeq = StringUtil.getSn();
			msg10302.setCenterSeq(centerSeq);
		}
		LOGGER.info("保存来包");
		Long recvMsgId = this.saveRecvMsg(funCode, FinalConstants.SenderType.CENTER,
				Long.valueOf(msg10302.getCenterSeq()), msg10302.toString(), DateHelp.getCurrentDateOfString());
		exchSignServiceImpl.doSignReq(msg10302.build(), recvMsgId);
	}

	// 解约
	public void MSG10402(int funCode, byte[] body) throws Exception {
		LOGGER.info("*解约*");

		Msg10402.Builder msg10402 = Msg10402.newBuilder().mergeFrom(body);

		String centerSeq = msg10402.getCenterSeq();
		if (StringUtils.isEmpty(centerSeq)) {
			centerSeq = StringUtil.getSn();
			msg10402.setCenterSeq(centerSeq);
		}
		LOGGER.info("保存来包");
		Long recvMsgId = this.saveRecvMsg(funCode, FinalConstants.SenderType.CENTER,
				Long.valueOf(msg10402.getCenterSeq()), msg10402.toString(), DateHelp.getCurrentDateOfString());
		exchSignOutServiceImpl.doSignOutReq(msg10402.build(), recvMsgId);
	}

	// 银行签约（银行信息变更，银行卡信息变更）
	public void MSG20304(int funCode, byte[] body) throws Exception {
		LOGGER.info("*银行签约*");

		Msg20304.Builder msg20304 = Msg20304.newBuilder().mergeFrom(body);

		String centerSeq = msg20304.getCenterSeq();
		if (StringUtils.isEmpty(centerSeq)) {
			centerSeq = StringUtil.getSn();
			msg20304.setCenterSeq(centerSeq);
		}
		LOGGER.info("保存来包");
		Long recvMsgId = this.saveRecvMsg(funCode, FinalConstants.SenderType.CENTER,
				Long.valueOf(msg20304.getCenterSeq()), msg20304.toString(), DateHelp.getCurrentDateOfString());
		bankSignServiceImpl.doBankSignReq(msg20304.build(), recvMsgId);
	}
		
	// 银行解约
	public void MSG20404(int funCode, byte[] body) throws Exception {
		LOGGER.info("*银行解约*");

		Msg20404.Builder msg20404 = Msg20404.newBuilder().mergeFrom(body);

		String centerSeq = msg20404.getCenterSeq();
		if (StringUtils.isEmpty(centerSeq)) {
			centerSeq = StringUtil.getSn();
			msg20404.setCenterSeq(centerSeq);
		}
		LOGGER.info("保存来包");
		Long recvMsgId = this.saveRecvMsg(funCode, FinalConstants.SenderType.CENTER,
				Long.valueOf(msg20404.getCenterSeq()), msg20404.toString(), DateHelp.getCurrentDateOfString());
		bankSignOutServiceImpl.doSignOutReq(msg20404.build(), recvMsgId);
	}

	// 银行入金
	public void MSG20104(int funCode, byte[] body) throws Exception {
		LOGGER.info("*银行入金*");

		Msg20104.Builder msg20104 = Msg20104.newBuilder().mergeFrom(body);

		String centerSeq = msg20104.getCenterSeq();
		if (StringUtils.isEmpty(centerSeq)) {
			centerSeq = StringUtil.getSn();
			msg20104.setCenterSeq(centerSeq);
		}
		LOGGER.info("保存来包");
		Long recvMsgId = this.saveRecvMsg(funCode, FinalConstants.SenderType.CENTER,
				Long.valueOf(msg20104.getCenterSeq()), msg20104.toString(), DateHelp.getCurrentDateOfString());
		bankAmtInServiceImpl.doBankAmtIn(msg20104.build(), recvMsgId);
	}
	
	public void MSG20204(int funCode, byte[] body) throws Exception {
		LOGGER.info("*银行出金*");

		Msg20204.Builder msg20204 = Msg20204.newBuilder().mergeFrom(body);

		String centerSeq = msg20204.getCenterSeq();
		if (StringUtils.isEmpty(centerSeq)) {
			centerSeq = StringUtil.getSn();
			msg20204.setCenterSeq(centerSeq);
		}
		LOGGER.info("保存来包");
		Long recvMsgId = this.saveRecvMsg(funCode, FinalConstants.SenderType.CENTER,
				Long.valueOf(msg20204.getCenterSeq()), msg20204.toString(), DateHelp.getCurrentDateOfString());
		bankAmtOutServiceImpl.doAmtOutReq(msg20204.build(), recvMsgId);
		
	}
	
	public void MSG21304(int funCode, byte[] body) throws Exception {
		LOGGER.info("*清算中心向交易所推送审核结果返回*");

		Msg21304.Builder msg21304 = Msg21304.newBuilder().mergeFrom(body);

		String centerSeq = msg21304.getCenterSeq();
		if (StringUtils.isEmpty(centerSeq)) {
			centerSeq = StringUtil.getSn();
			msg21304.setCenterSeq(centerSeq);
		}
		LOGGER.info("保存来包");
		Long recvMsgId = this.saveRecvMsg(funCode, FinalConstants.SenderType.CENTER,
				Long.valueOf(msg21304.getCenterSeq()), msg21304.toString(), DateHelp.getCurrentDateOfString());
		bankVerifyNotifyServiceImpl.doBankVerifyNotifyService(msg21304.build(), recvMsgId);
		
	}

	// 商品信息
	public void MSG17102(int funCode, byte[] body) throws Exception {
		LOGGER.info("*商品信息*");

		Msg17102.Builder msg17102 = Msg17102.newBuilder().mergeFrom(body);

		LOGGER.info("保存来包");
		Long recvMsgId = this.saveRecvMsg(funCode, FinalConstants.SenderType.CENTER,
				0L, msg17102.toString(), DateHelp.getCurrentDateOfString());
		sendBillMsgImpl.doMsg17102(msg17102.build(), recvMsgId);
	}
	
	// 委托单
	public void MSG17202(int funCode, byte[] body) throws Exception {
		LOGGER.info("*委托单*");

		Msg17202.Builder msg17202 = Msg17202.newBuilder().mergeFrom(body);

		LOGGER.info("保存来包");
		Long recvMsgId = this.saveRecvMsg(funCode, FinalConstants.SenderType.CENTER,
				0L, msg17202.toString(), DateHelp.getCurrentDateOfString());
		sendBillMsgImpl.doMsg17202(msg17202.build(), recvMsgId);
	}
		
	// 成交单
	public void MSG17302(int funCode, byte[] body) throws Exception {
		LOGGER.info("*成交单*");

		Msg17302.Builder msg17302 = Msg17302.newBuilder().mergeFrom(body);

		LOGGER.info("保存来包");
		Long recvMsgId = this.saveRecvMsg(funCode, FinalConstants.SenderType.CENTER,
				0L, msg17302.toString(), DateHelp.getCurrentDateOfString());
		sendBillMsgImpl.doMsg17302(msg17302.build(), recvMsgId);
	}

	// 交割单
	public void MSG17402(int funCode, byte[] body) throws Exception {
		LOGGER.info("*交割单*");

		Msg17402.Builder msg17402 = Msg17402.newBuilder().mergeFrom(body);

		LOGGER.info("保存来包");
		Long recvMsgId = this.saveRecvMsg(funCode, FinalConstants.SenderType.CENTER, 0L, msg17402.toString(),
				DateHelp.getCurrentDateOfString());
		sendBillMsgImpl.doMsg17402(msg17402.build(), recvMsgId);
	}

	// 持仓单
	public void MSG17502(int funCode, byte[] body) throws Exception {
		LOGGER.info("*持仓单*");

		Msg17502.Builder msg17502 = Msg17502.newBuilder().mergeFrom(body);

		LOGGER.info("保存来包");
		Long recvMsgId = this.saveRecvMsg(funCode, FinalConstants.SenderType.CENTER, 0L, msg17502.toString(),
				DateHelp.getCurrentDateOfString());
		sendBillMsgImpl.doMsg17502(msg17502.build(), recvMsgId);
	}

	// 费用数据
	public void MSG17602(int funCode, byte[] body) throws Exception {
		LOGGER.info("*费用数据*");

		Msg17602.Builder msg17602 = Msg17602.newBuilder().mergeFrom(body);

		LOGGER.info("保存来包");
		Long recvMsgId = this.saveRecvMsg(funCode, FinalConstants.SenderType.CENTER, 0L, msg17602.toString(),
				DateHelp.getCurrentDateOfString());
		sendBillMsgImpl.doMsg17602(msg17602.build(), recvMsgId);
	}

	// 结算价
	public void MSG17702(int funCode, byte[] body) throws Exception {
		LOGGER.info("*结算价*");

		Msg17702.Builder msg17702 = Msg17702.newBuilder().mergeFrom(body);

		LOGGER.info("保存来包");
		Long recvMsgId = this.saveRecvMsg(funCode, FinalConstants.SenderType.CENTER, 0L, msg17702.toString(),
				DateHelp.getCurrentDateOfString());
		sendBillMsgImpl.doMsg17702(msg17702.build(), recvMsgId);
	}

	// 行情数据
	public void MSG17802(int funCode, byte[] body) throws Exception {
		LOGGER.info("*行情数据*");

		Msg17802.Builder msg17802 = Msg17802.newBuilder().mergeFrom(body);

		LOGGER.info("保存来包");
		Long recvMsgId = this.saveRecvMsg(funCode, FinalConstants.SenderType.CENTER,
				0L, msg17802.toString(), DateHelp.getCurrentDateOfString());
		sendBillMsgImpl.doMsg17802(msg17802.build(), recvMsgId);
	}
		
	//保存来包
	private Long saveRecvMsg(int funCode, int sendType, Long seq, String bodyReq, String sendDate) throws Exception {
		Long id = recvMsgDao.generateId();

		RecvMsg recvMsg = new RecvMsg();
		recvMsg.setRecvMsgId(id);
		recvMsg.setMsgCode(String.valueOf(funCode));
		recvMsg.setSenderType(sendType);
		recvMsg.setSender(String.valueOf(sendType));
		recvMsg.setSenderDate(sendDate);
		recvMsg.setSenderSeq(String.valueOf(seq));
		recvMsg.setRecvMsg(bodyReq.length() >= 1000 ? "" : bodyReq);

		recvMsgDao.getRecvMsgMapper().insertSelective(recvMsg);

		return id;
	}


	@Override
	public void execute(int funCode, byte[] body) throws Exception {
		this.getClass().getMethod("MSG" + funCode, new Class[] { int.class, byte[].class }).invoke(this,
				new Object[] { funCode, body });
	}
	
	
}

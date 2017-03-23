package com.service.impl;
import java.io.File;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccps.exchgate.api.t2.service.client.ExchCallClearT2Service;
import com.common.constants.FinalConstants;
import com.common.constants.FunCodeConstants;
import com.common.util.DateHelp;
import com.common.util.TASProtoHelper;
import com.core.util.MD5Util;
import com.core.util.PropertiesUtil;
import com.dao.Impl.CenterFileRecDao;
import com.dao.Impl.SendMsgDao;
import com.model.CenterFileRec;
import com.model.SendMsg;
import com.proto.CenterBank.Msg31001;
import com.proto.CenterBank.Msg31002;
import com.proto.ExchangeCenter;
import com.service.ExchFileNoService;
import com.service.MakeClearFileService;
import com.service.MakeClearPriceFileService;
import com.service.MakeClientMsgFile;
import com.service.MakeDealInfoFileService;
import com.service.MakeDeliverFileService;
import com.service.MakeEntrustFileService;
import com.service.MakeMemberFeeFileService;
import com.service.MakeMemberFundFileService;
import com.service.MakeMemberPositionFile;
import com.service.MakeQuotationFileService;
import com.service.SendMsgService;

import net.sf.json.JSONObject;

/**
 *  
 * ClassName: ExchFileNoServiceImpl.java
 * date: 2016年12月15日上午9:23:13
 * @author yu.jian
 * @version
 */
@Service
@Transactional
public class ExchFileNoServiceImpl implements ExchFileNoService {
	private static final Logger LOGGER = Logger.getLogger(ExchFileNoServiceImpl.class);
	/**
	 *  
	 * ClassName: ExchFileNoServiceImpl.java
	 * date: 2016年12月15日上午9:23:14
	 * @author yu.jian
	 * @version
	 */
	@Autowired
	private MakeClearFileService makeClearFileServiceImpl;
	@Autowired
	private CenterFileRecDao fileRecDao;
	@Autowired
	private SendMsgDao sendMsgDao;
	@Autowired
	private SendMsgService sendMsgService;
	@Autowired
	private MakeMemberFundFileService makeMemberFundFileServiceImpl;
	@Autowired
	private MakeMemberPositionFile makeMemberPositionFileImpl;
	@Autowired
	private MakeMemberFeeFileService makeMemberFeeFileServiceImpl;
	@Autowired
	private MakeDealInfoFileService makeDealInfoFileServiceImpl;
	@Autowired
	private MakeClearPriceFileService makeClearPriceFileServiceImpl;
	@Autowired
	private MakeEntrustFileService makeEntrustFileServiceImpl;
	@Autowired
	private MakeQuotationFileService makeQuotationFileServiceImpl;
	@Autowired
	private MakeDeliverFileService makeDeliverFileServiceImpl;
	@Autowired
	private MakeClientMsgFile makeClientMsgFileImpl;
	@Autowired
	private ExchCallClearT2Service exchCallClearT2ServiceImpl;
	
	
	@Override
	public void doExchFileNoReq(Msg31001 msg31001, long recMsgId) throws Exception {
		try{
			LOGGER.info("*交易所文件报送通知*");
			String fileType = msg31001.getFileType();
			String hs_fileType = null;
			String hs_fileName = null;
			long recId = saveFileRec(msg31001, recMsgId);
			switch(fileType){
			case "BF02":
				LOGGER.info("*清算数据文件*");
				hs_fileName = makeClearFileServiceImpl.doMakeClearFile(msg31001, recId);
				hs_fileType="4";
				break;
			case "BF04":
				LOGGER.info("*结算余额文件文件*");
				hs_fileName = makeMemberFundFileServiceImpl.doMakeFundFile(msg31001, recId);
				hs_fileType="3";
				break;
			case "BF05":
				LOGGER.info("*客户信息文件*");
				hs_fileName = makeClientMsgFileImpl.doMakeClientMsgFile(msg31001, recId);
				hs_fileType="1";
				break;
			case "BF11":
				LOGGER.info("*商品信息文件*");
				
				break;
			case "BF12":
				LOGGER.info("*委托单文件*");
				hs_fileName = makeEntrustFileServiceImpl.doMakeEntrustFile(msg31001, recId);
				hs_fileType="D";
				break;
			case "BF13":
				LOGGER.info("*成交单文件*");
				hs_fileName = makeDealInfoFileServiceImpl.doMakeDealInfoFile(msg31001, recId);
				hs_fileType="2";
				break;
			case "BF14":
				LOGGER.info("*交割单文件*");
				hs_fileName = makeDeliverFileServiceImpl.doMakeDeliverFile(msg31001, recId);
				hs_fileType="C";
				break;
			case "BF15":
				LOGGER.info("*持仓单文件*");
				hs_fileName = makeMemberPositionFileImpl.doMakeMemberPositionFile(msg31001, recId);
				hs_fileType="6";
				break;
			case "BF16":
				LOGGER.info("*费用数据文件*");
				hs_fileName = makeMemberFeeFileServiceImpl.doMakeMemberFeeFile(msg31001, recId);
				hs_fileType="9";
				break;
			case "BF17":
				LOGGER.info("*结算价文件*");
				hs_fileName = makeClearPriceFileServiceImpl.doMakeClearPriceFile(msg31001, recId);
				hs_fileType="B";
				break;
			case "BF18":
				LOGGER.info("*行情数据文件*");
				hs_fileName = makeQuotationFileServiceImpl.doMakeQuotationFile(msg31001, recId);
				hs_fileType="A";
				break;
			default:
				throw new Exception("*文件类型错误*");
			}
			
			LOGGER.info("*向广清所报送文件*");
//			交易日期(YYYYMMDD)	initDate
//			交易所代码	exchangeId
//			文件类型	fileType
//			文件路径	filePath
//			文件名称	fileName
//			是否重发(0:否；1：是)	reissueFlag
//			文件MD5码	fileMd5
//			业务时间(yyyyMMddHHmmss)	busiDatetime
			//生成MD5码
			String MD5 = createMD5(hs_fileName,msg31001.getExchNo());
			JSONObject json = new JSONObject();
			json.put("initDate", msg31001.getBankDate());
			json.put("exchangeId", "4110014");
			json.put("fileType", hs_fileType);
			json.put("fileName", hs_fileName);
			json.put("filePath", msg31001.getBankDate());
			json.put("reissueFlag", msg31001.getIsResend());
			json.put("fileMd5", MD5);
			json.put("busiDatetime", DateHelp.getCurrentDateTimeOfStringHS());
			
			String result = exchCallClearT2ServiceImpl.dealMSG369000(json.toString());
			
			JSONObject jsonObj = JSONObject.fromObject(result);
			int errorNo = jsonObj.getInt("errorNo");
			String errorInfo = jsonObj.getString("errorInfo");
			int retCode=0;
			if(errorNo==0){
				LOGGER.info(errorNo+":"+errorInfo);
				retCode=0;
			}
			else if(errorNo==99){
				LOGGER.info(errorNo+":"+errorInfo);
				retCode=9;
			}
			else{
				LOGGER.info(errorNo+":"+errorInfo);
				retCode=1;
			}
			
			LOGGER.info("*文件请求应答Msg31002报文*");
			ExchangeCenter.MessageHead.Builder value = ExchangeCenter.MessageHead.newBuilder();
			value.setFunCode(31002); // 功能号
			Msg31002.Builder msg31002 = Msg31002.newBuilder();
			msg31002.setTranNo(msg31001.getTranNo());
			msg31002.setBankDate(msg31001.getBankDate());
			Long sendMsgID = sendMsgDao.generateId();
			msg31002.setBankSeq(sendMsgID+"");
			msg31002.setCenterSeq(msg31001.getCenterSeq());
			msg31002.setRetCode(retCode+"");
			msg31002.setRetDesc(errorInfo);
			
			LOGGER.info("*记录往包*");
			SendMsg sendMsg = new SendMsg();
			sendMsg.setSendMsgId(sendMsgID); // 往包ID
			sendMsg.setRecvMsgId(recMsgId); // 来包id
			sendMsg.setRecverType(FinalConstants.SenderType.CENTER); // 接收方类型
			sendMsg.setRecver("000000"); // 业务接收方
			sendMsg.setMsgCode("31002"); // 报文类型编号
			sendMsg.setSendDate(DateHelp.getCurrentDateOfString());
			sendMsg.setTranSeq(recId); // 业务流水号
			sendMsg.setSendMsg(msg31002.build().toString()); // 报文内容
			sendMsg.setSysTime(new Date()); // 系统时间
			sendMsgDao.getSendMsgMapper().insert(sendMsg);

			byte[] bytes = TASProtoHelper.getNTAS(msg31002.build().toByteArray(), FunCodeConstants.MSG31002, 0);
			sendMsgService.send(sendMsg, bytes);
			LOGGER.info("*成功发送文件请求应答报文*");

			LOGGER.info("*更新往包表状态*");
			sendMsg.setRetCode(Integer.parseInt(FinalConstants.RetCode.SUCCESS));
			sendMsg.setRetDesc("发送成功");
			sendMsgDao.getSendMsgMapper().updateByPrimaryKeySelective(sendMsg);
			
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
	}
	
	
	//文件记录
	public long saveFileRec(Msg31001 msg31001, long recMsgId){
		CenterFileRec fileRec = new CenterFileRec();
		Long recId = fileRecDao.generateId();
		fileRec.setCenterFileRecId(recId);
		fileRec.setTranDate(msg31001.getBankDate());
		fileRec.setCenterSeq(msg31001.getCenterSeq());
		fileRec.setRecvSendType(1);         //[RECV:1-接收; SEND:2-发
		fileRec.setFileType(msg31001.getFileType());
		fileRec.setFilePath(msg31001.getFilePath());
		fileRec.setFileName(msg31001.getFileName());
		fileRec.setIsResend(msg31001.getIsResend());
		fileRec.setRecvMsgId(recMsgId);
		fileRec.setSysTime(new Date());
		fileRecDao.getCenterFileRecMapper().insert(fileRec);
		
		return recId;
	}
	
	public String createMD5(String fileName,String exchNo){
		String sysPath = System.getProperty("user.dir");
		String path = sysPath + File.separator + PropertiesUtil.getProperty("send_path");
		String MD5 = MD5Util.getMD5Checksum(path+ File.separator + fileName);
		return MD5;
	}
}

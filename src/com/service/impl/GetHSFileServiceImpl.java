package com.service.impl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.constants.FinalConstants;
import com.common.constants.FunCodeConstants;
import com.common.util.DateHelp;
import com.common.util.TASProtoHelper;
import com.core.util.FileUtil;
import com.core.util.FtpUtil;
import com.core.util.PropertiesUtil;
import com.dao.TranMapper;
import com.dao.Impl.CenterFileRecDao;
import com.dao.Impl.SendMsgDao;
import com.github.pagehelper.StringUtil;
import com.model.BankCheck;
import com.model.CenterFileRec;
import com.model.ClearResult;
import com.model.SendMsg;
import com.model.Tran;
import com.proto.CenterBank.Msg21001;
import com.service.GetHSFileService;
import com.service.SendMsgService;

import net.sf.json.JSONObject;

/**
 *  
 * ClassName: GetHSFileServiceImpl.java
 * date: 2016年12月19日上午9:43:04
 * @author yu.jian
 * @version
 */
@Service
@Transactional
public class GetHSFileServiceImpl implements GetHSFileService {
	private static final Logger LOGGER = Logger.getLogger(GetHSFileServiceImpl.class);
	/**
	 *  
	 * ClassName: GetHSFileServiceImpl.java
	 * date: 2016年12月19日上午9:43:04
	 * @author yu.jian
	 * @version
	 */
	@Autowired
	private CenterFileRecDao fileRecDao;
	@Autowired
	private TranMapper tranMapper;
	@Autowired
	private SendMsgDao sendMsgDao;
	@Autowired
	private SendMsgService sendMsgService;
	@Override
	public void doGetHSFileService(String reqStr) throws Exception {
		try{
			LOGGER.info("*收到广清所报送文件通知*");
			JSONObject json = JSONObject.fromObject(reqStr);
			
			String initDate = json.getString("initDate");
			String exchangeId = json.getString("exchangeId");
			String bankFileType = json.getString("bankFileType");
			String bankProCode = json.getString("bankProCode");
			String fileName = json.getString("fileName");
//			String fileMd5 = json.getString("fileMd5");		//暂不作校验
			
			Tran tran = tranMapper.selectByBankPro(bankProCode); 
			String tranNo = tran.getTranNo();
			
			CenterFileRec fileRec = new CenterFileRec();
			Long recId = fileRecDao.generateId();
			fileRec.setCenterFileRecId(recId);
			fileRec.setTranDate(initDate);
			fileRec.setRecvSendType(1);         //[RECV:1-接收; SEND:2-发
			if(Integer.parseInt(bankFileType)==1)
				fileRec.setFileType("BF01");
			else if(Integer.parseInt(bankFileType)==3)
				fileRec.setFileType("BF03");
			/*else
				throw new Exception("*文件类型错误*");*/
			
//			String filePath = PropertiesUtil.getProperty("hs_ftp_path");
			fileRec.setFilePath(initDate);
			fileRec.setFileName(fileName);
			fileRec.setIsResend(0);
			fileRec.setSysTime(new Date());
			Long sendMsgId = sendMsgDao.generateId(); 
			fileRec.setSendMsgId(sendMsgId);
			fileRecDao.getCenterFileRecMapper().insert(fileRec);
			
			LOGGER.info("*取文件*");
			//取ftp信息
			String hs_url = PropertiesUtil.getProperty("hs_ftp_url");
			int hs_port = Integer.valueOf(PropertiesUtil.getProperty("hs_ftp_port"));
			String hs_username = PropertiesUtil.getProperty("hs_ftp_user_name");
			String hs_password = PropertiesUtil.getProperty("hs_ftp_pw");
			
			//交易所ftp信息
			String url = PropertiesUtil.getProperty("210001.exch_ftp_url");     //ftp地址
			int port = Integer.valueOf(PropertiesUtil.getProperty("210001.exch_ftp_port"));   //端口
			String username = PropertiesUtil.getProperty("210001.exch_ftp_user_name");        
			String password = PropertiesUtil.getProperty("210001.exch_ftp_pw");
			
			//下载出入金对账文件到本地路径
			String sysPath = System.getProperty("user.dir");
			String localPath = sysPath + File.separator + PropertiesUtil.getProperty("recv_exch_path");
			
			LOGGER.info("下载文件开始 URL:" + hs_url + "|PORT:" + hs_port + "|filePath:" + initDate + "+|localPath:"
					+ localPath );
			boolean isSuccess = FtpUtil.downFile(hs_url, hs_port, hs_username, hs_password, initDate, fileName, localPath);
			
			if (!isSuccess) {
				fileRec.setDealStatus(1);
				fileRec.setDealDesc("失败");
				fileRecDao.getCenterFileRecMapper().updateByPrimaryKeySelective(fileRec);
				throw new Exception("*文件下载失败*");
			}
			
			LOGGER.info("*文件下载成功*");
			
			if(Integer.parseInt(bankFileType)==1){
				LOGGER.info("*出入金对账文件*");
				Map<String, Object> dataMap = getBankCheckData(localPath+ File.separator + fileName);
				//取数据内容
				List<BankCheck> dataList = (List<BankCheck>) dataMap.get("beanList"); 
				
				LOGGER.info("*开始组装出入金对账文件*");
				String upBankCheckFileName = makeBankCheckFile(dataList, tranNo);
				
				//上传文件
				LOGGER.info("【出入金对账文件】  出入金对账价文件文件上传  localPath:" + localPath + "+|fileName：" + upBankCheckFileName);
				
				
				String send_localPath = sysPath + File.separator + PropertiesUtil.getProperty("send_path");
				String srcFilename = send_localPath + File.separator + upBankCheckFileName;
				
				isSuccess = FtpUtil.uploadFile(url, port, username, password, File.separator + "E"+exchangeId, upBankCheckFileName,srcFilename);
				if (!isSuccess) {
					throw new Exception("出入金对账价文件上传失败");
				}
			}
			else if(Integer.parseInt(bankFileType)==3){
				LOGGER.info("*清算结果返回文件*");
				Map<String, Object> dataMap = getClearResultData(localPath+ File.separator + fileName);
				//取数据内容
				List<ClearResult> dataList = (List<ClearResult>) dataMap.get("beanList"); 
				
				LOGGER.info("*开始组装清算结果返回文件*");
				String upClearResultFileName = makeClearResultFile(dataList, tranNo);
				
				//上传文件
				LOGGER.info("【清算结果返回文件】  清算结果返回文件文件上传  localPath:" + localPath + "+|fileName：" + upClearResultFileName);
				
				
				String send_localPath = sysPath + File.separator + PropertiesUtil.getProperty("send_path");
				String srcFilename = send_localPath + File.separator + upClearResultFileName;
				
				isSuccess = FtpUtil.uploadFile(url, port, username, password, initDate, upClearResultFileName,srcFilename);
				if (!isSuccess) {
					throw new Exception("清算结果返回文件上传失败");
				}
			}
			
			//组发往center的文件通知报文
			Msg21001.Builder msg21001 = Msg21001.newBuilder();
			msg21001.setTranNo(tranNo);
			msg21001.setBankDate(initDate);
			msg21001.setBankSeq(sendMsgId+"");
			msg21001.setExchNo("210001");
			msg21001.setFileType(fileRec.getFileType());
			msg21001.setFilePath(initDate);
			msg21001.setFileName(fileRec.getFileName());
			msg21001.setIsResend(0);
			msg21001.setFileMd5("");
			
			LOGGER.info("*记录往包*");
			SendMsg sendMsg = new SendMsg();
			sendMsg.setSendMsgId(sendMsgId); // 往包ID
			sendMsg.setRecvMsgId(0L); // 来包id
			sendMsg.setRecverType(FinalConstants.SenderType.CENTER); // 接收方类型
			sendMsg.setRecver("000000"); // 业务接收方
			sendMsg.setMsgCode("21001"); // 报文类型编号 
			sendMsg.setSendDate(DateHelp.getCurrentDateOfString());
			sendMsg.setTranSeq(fileRec.getCenterFileRecId()); // 业务流水号
			sendMsg.setSendMsg(msg21001.build().toString()); // 报文内容
			sendMsg.setSysTime(new Date()); // 系统时间
			sendMsgDao.getSendMsgMapper().insert(sendMsg);
			
			byte[] bytes = TASProtoHelper.getNTAS(msg21001.build().toByteArray(), FunCodeConstants.MSG21001, 0);
			sendMsgService.send(sendMsg, bytes);
			LOGGER.info("*成功发送入金应答报文*");

			LOGGER.info("*更新往包表状态*");
			sendMsg.setRetCode(Integer.parseInt(FinalConstants.RetCode.SUCCESS));
			sendMsg.setRetDesc("发送成功");
			sendMsgDao.getSendMsgMapper().updateByPrimaryKeySelective(sendMsg);
			
		}catch(Exception e){
			LOGGER.error(e);
			throw e;
		}
	}
	
	/**
	 * @param dataList
	 * @param bankProCode
	 * @return
	 * @throws Exception 
	 */
	private String makeClearResultFile(List<ClearResult> dataList, String tranNo) throws Exception {
		String upClearResultData = "";

		//文件中的第一行是：银行业务编号|银行业务日期|总记录数
		String date = DateHelp.getCurrentDateOfString();
		int size = dataList.size();
		upClearResultData = tranNo+"&"+date+"&"+size+"\r\n";
		
		for (int i = 0; i < dataList.size(); i++) {
			ClearResult clearResult = dataList.get(i);
			String tran_no = tranMapper.selectByBankPro(clearResult.getBankProCode()).getTranNo();//	银行业务编号	C12	Y
			String bank_date = clearResult.getInitDate();//	银行业务日期	C8	Y
			String exch_no = "210001";//	交易所编号	C12	Y
			String trade_acct = clearResult.getFundAccount();//	交易账号	C50	Y
			String start_amt = "";//	期初余额	M	Y
			String end_amt = "";//	当前余额	M	Y
			String occur_amt = "";//	发生金额	M	Y
			String ret_code;
			if(StringUtil.isEmpty(clearResult.getErrorReason()))
				ret_code = "0";//	返回码	C10	Y
			else ret_code = "1";
			String ret_desc = clearResult.getErrorReason();//	返回结果说明	C200	N
			
			
			upClearResultData = tran_no + "&" + bank_date + "&" + exch_no + "&" + trade_acct + "&"
					+ start_amt + "&" + end_amt + "&" + occur_amt +"&"+ ret_code + "&"
					+ ret_desc +  "\r\n";
		}
		// 将所有数据一次性写入文件
		// 下载出入金对账价文件到本地路径
		String sysPath = System.getProperty("user.dir");
		String localPath = sysPath + File.separator + PropertiesUtil.getProperty("send_path");
		String fileName = "F"+date+"_"+tranNo+"_"+"BF03"+".txt";
		
		boolean isSuccess = FileUtil.writeFile(upClearResultData, localPath, fileName);
		if (!isSuccess) {
			throw new Exception(fileName + "*清算结果文件写入失败*");
		}
		return fileName;
	}

	/**
	 * @param string
	 * @return
	 * @throws Exception 
	 */
	private Map<String, Object> getClearResultData(String filePath) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ClearResult> clearResultList = new ArrayList<ClearResult>();
		try {
			int i = 0;
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
			String lineStr = br.readLine();
			while (lineStr != null) {
				ClearResult clearResult = new ClearResult();
				String[] liStr = lineStr.split("|");
				clearResult.setInitDate(liStr[0]);
				clearResult.setExchangeId(liStr[1]);
				clearResult.setFundAccount(liStr[2]);
				clearResult.setBankNo(liStr[3]);
				clearResult.setBankProCode(liStr[4]);
				clearResult.setMoneyType(liStr[5]);
				clearResult.setCustName(liStr[6]);
				clearResult.setErrorReason(liStr[7]);
				clearResult.setAccountBalance(liStr[8]);
				
				clearResultList.add(clearResult);

				lineStr = br.readLine();
			}
			map.put("beanList", clearResultList);
		} catch (FileNotFoundException e) {
			LOGGER.error("*清算结果文件读取失败*", e);
			throw new Exception("*清算结果文件读取失败*", e);
		} catch (IOException e) {
			LOGGER.error("*清算结果文件读取失败*", e);
			throw new Exception("*清算结果文件读取失败*", e);
		}
		return map;
	}

	/**
	 * @param dataList
	 * @param exchangeId
	 * @return
	 * @throws Exception 
	 */
	private String makeBankCheckFile(List<BankCheck> dataList, String tranNo) throws Exception {
		String upBankCheckData = "";

		//文件中的第一行是：银行业务编号|银行业务日期|总记录数
		String date = DateHelp.getCurrentDateOfString();
		int size = dataList.size();
		upBankCheckData = tranNo+"&"+date+"&"+size+"\r\n";
		
		for (int i = 0; i < dataList.size(); i++) {
			BankCheck bankCheck = dataList.get(i);
			String tran_no = tranMapper.selectByBankPro(bankCheck.getBankProCode()).getTranNo();//	银行业务编号
			String check_date = bankCheck.getInitDate();//	对账日期
			String bank_seq = bankCheck.getSerialNo();//	银行流水号
			String center_seq = bankCheck.getTradeSerialNo();//	中心流水号
			String exch_no = "210001";//	交易所编号
			String trade_acct = bankCheck.getFundAccount();//	交易账号
			String acct = bankCheck.getBankAccount();//	银行账号
			String acct_name = "";//	银行账户名
			String amt = bankCheck.getOccurAmount();//	金额
			String currency = bankCheck.getMoneyType().equals("CNY")?"RMB":bankCheck.getMoneyType();//币种
			String card_bank_no = bankCheck.getBankNo();//	银行卡行号
			String card_acct = bankCheck.getBankAccount();//	银行卡号
			String card_name = "";//	银行卡户名
			String tran_type = bankCheck.getInoutType();//	业务类型
			String sender_type = bankCheck.getInoutSource().equals("3")?"2":bankCheck.getInoutSource();//	发起方类型
			String tran_status = bankCheck.getDealStatus().equals("0")?"0":"1";//	业务状态

			upBankCheckData = tran_no + "&" + check_date + "&" + bank_seq + "&" + center_seq + "&"
					+ exch_no + "&" + trade_acct + "&" + acct +"&"+ acct_name + "&"
					+ amt + "&" + currency + "&" + card_bank_no+"&"+ card_acct + "&"
					+ card_name + "&" + tran_type + "&" + sender_type+ "&" +tran_status+ "\r\n";
		}
		// 将所有数据一次性写入文件
		// 下载出入金对账价文件到本地路径
		String sysPath = System.getProperty("user.dir");
		String localPath = sysPath + File.separator + PropertiesUtil.getProperty("send_path");
		String fileName = "F"+date+"_"+tranNo+"_"+"BF01"+".txt";
		
		boolean isSuccess = FileUtil.writeFile(upBankCheckData, localPath, fileName);
		if (!isSuccess) {
			throw new Exception(fileName + "*对账文件写入失败*");
		}
		return fileName;

	}

	public Map<String, Object> getBankCheckData(String filePath) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<BankCheck> bankCheckList = new ArrayList<BankCheck>();
		try {
			int i = 0;
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
			String lineStr = br.readLine();
			while (lineStr != null) {
				BankCheck bankBankCheck = new BankCheck();
				String[] liStr = lineStr.split("|");
				bankBankCheck.setSerialNo(liStr[0]);
				bankBankCheck.setTradeSerialNo(liStr[1]);
				bankBankCheck.setInitDate(liStr[2]);
				bankBankCheck.setExchangeId(liStr[3]);
				bankBankCheck.setMemCode(liStr[4]);
				bankBankCheck.setFundAccount(liStr[5]);
				bankBankCheck.setInoutWay(liStr[6]);
				bankBankCheck.setInoutType(liStr[7]);
				bankBankCheck.setInoutSource(liStr[8]);
				bankBankCheck.setOccurAmount(liStr[9]);
				bankBankCheck.setMoneyType(liStr[10]);
				bankBankCheck.setBankNo(liStr[11]);
				bankBankCheck.setBankProCode(liStr[12]);
				bankBankCheck.setBankAccount(liStr[13]);
				bankBankCheck.setDealStatus(liStr[14]);
				bankBankCheck.setRemark(liStr[15]);
				
				bankCheckList.add(bankBankCheck);

				lineStr = br.readLine();
			}
			map.put("beanList", bankCheckList);
		} catch (FileNotFoundException e) {
			LOGGER.error("*银行出入金对账文件读取失败*", e);
			throw new Exception("*银行出入金对账文件读取失败*", e);
		} catch (IOException e) {
			LOGGER.error("*银行出入金对账文件读取失败*", e);
			throw new Exception("*银行出入金对账文件读取失败*", e);
		}
		return map;
	}

}

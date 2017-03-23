package com.service.impl;
import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.util.DateHelp;
import com.core.util.FileUtil;
import com.core.util.FtpUtil;
import com.core.util.PropertiesUtil;
import com.core.util.ZipUtil;
import com.dao.CenterFileRecMapper;
import com.model.CenterFileRec;
import com.model.ClearData;
import com.proto.CenterBank.Msg31001;
import com.service.MakeClearFileService;


/**
 *  组装发往广清所的清算文件
 * ClassName: MakeClearFileServiceImpl.java
 * date: 2016年12月15日上午10:08:59
 * @author yu.jian
 * @version
 */
@Service
@Transactional
public class MakeClearFileServiceImpl implements MakeClearFileService {
	private static final Logger LOGGER = Logger.getLogger(MakeClearFileServiceImpl.class);
	/**
	 *  
	 * ClassName: MakeClearFileServiceImpl.java
	 * date: 2016年12月15日上午10:08:59
	 * @author yu.jian
	 * @version
	 */
	@Autowired
	private CenterFileRecMapper fileRecMapper;
	@Override
	public String doMakeClearFile(Msg31001 msg31001, long recId) throws Exception {
		try{
			CenterFileRec fileRec = fileRecMapper.selectByPrimaryKey(recId);
			
			String exchNo = msg31001.getExchNo();
			String filePath = msg31001.getFilePath();
			String fileName = msg31001.getFileName();
			
			//读取ftp信息
			String url = PropertiesUtil.getProperty(exchNo + ".exch_ftp_url");     //ftp地址
			int port = Integer.valueOf(PropertiesUtil.getProperty(exchNo + ".exch_ftp_port"));   //端口
			String username = PropertiesUtil.getProperty(exchNo + ".exch_ftp_user_name");        
			String password = PropertiesUtil.getProperty(exchNo + ".exch_ftp_pw");
			
			//下载清算文件到本地路径
			String sysPath = System.getProperty("user.dir");
			String localPath = sysPath + File.separator + PropertiesUtil.getProperty("recv_exch_path");
			
			LOGGER.info("【清算文件】  下载文件开始 URL:" + url + "|PORT:" + port + "|filePath:" + filePath + "|localPath:"
					+ localPath );
			boolean isSuccess = FtpUtil.downFile(url, port, username, password, filePath, fileName, localPath);
			
			if (!isSuccess) {
				fileRec.setDealStatus(1);
				fileRec.setDealDesc("失败");
				fileRecMapper.updateByPrimaryKeySelective(fileRec);
				throw new Exception("*文件下载失败*");
			}
			
			LOGGER.info("*文件下载成功*");
			
			//读取清算文件内容
			Map<String, Object> dataMap = FileUtil.readClearFile(localPath+ File.separator + fileName);
			//取数据内容
			List<ClearData> clearDataList = (List<ClearData>) dataMap.get("beanList"); 
			
			LOGGER.info("*开始组装成交清算文件*");
			String upClearFileName = makeClearFile(clearDataList, "4110014", msg31001.getBankDate());
			
			
			//上传文件
			LOGGER.info("【成交清算文件】  成交清算文件文件上传  localPath:" + localPath + "+|fileName：" + upClearFileName);
			
			String hs_url = PropertiesUtil.getProperty("hs_ftp_url");
			int hs_port = Integer.valueOf(PropertiesUtil.getProperty("hs_ftp_port"));
			String hs_username = PropertiesUtil.getProperty("hs_ftp_user_name");
			String hs_password = PropertiesUtil.getProperty("hs_ftp_pw");
			
			String send_localPath = sysPath + File.separator + PropertiesUtil.getProperty("send_path");
			String srcFilename = send_localPath + File.separator + upClearFileName;
			
//			isSuccess = FtpUtil.uploadFile(url, port, username, password, msg31001.getBankDate(), "20170207_4110014_clearResult.txt","E:\\sts-bundle\\sts-3.7.2.RELEASE\\20170206\\20170207_4110014_clearResult.txt");
			isSuccess = FtpUtil.uploadFile(hs_url, hs_port, hs_username, hs_password, msg31001.getBankDate(), upClearFileName,srcFilename);
			if (!isSuccess) {
				throw new Exception("*成交清算文件上传失败*");
			}
			
			return upClearFileName;
		}catch(Exception e){
			throw e;
		}
	}
	
	//组装成交清算文件
	public String makeClearFile(List<ClearData> clearDataList, String exchNo, String bankDate) throws Exception{
		String upClearData = "";
		
		for(int i=0; i<clearDataList.size();i++){
			ClearData clearData = clearDataList.get(i);
			String initDate = clearData.getTranDate();            //N8	业务发生日期(YYYYMMDD)	
			String exchangeId = exchNo;              //C32	交易所代码	
			String memCode = clearData.getTradeAcct();             //C20	  会员编码	
			String fundAccount = clearData.getTradeAcct();         //C20	资金账号	
			String changeBalance = clearData.getTrueClearAmt()+"";	           //L	总变动资金	
			String tradePoundage = "0"; 	           //L	交易手续费	
			String squareProfitLoss	= "0";         //L	平仓盈亏	
			String settleProfitLoss = clearData.getProfitLoss().toString();	        //L	结算盈亏	
			String delayFees = "0"; 	                 //L	滞纳金	
			String deliverPoundage	= "0";             //L	交割手续费	
			String deliverPayment = "0"; 	               //L	交割货款	
			String backBalance = "0"; 	                  //L	穿仓资金回退金额	
			String pointDiffBalance	= "0";               //L	点差	
			
			upClearData = upClearData + initDate+"|"+exchangeId+"|"+memCode+"|"+fundAccount+"|"+changeBalance
					+"|"+tradePoundage+"|"+squareProfitLoss+"|"+settleProfitLoss+"|"+delayFees+"|"+
					deliverPoundage+"|"+deliverPayment+"|"+backBalance+"|"+pointDiffBalance+"\r\n";
		}
		//将所有数据一次性写入文件
		//上传清算文件到本地路径
		String sysPath = System.getProperty("user.dir");
		String localPath = sysPath + File.separator + PropertiesUtil.getProperty("send_path");
		String fileName = bankDate+"_"+ exchNo+"_" +"clearResult.txt";//yyyymmdd_xxx(交易所代码)_clearResult.txt
		
		boolean isSuccess = FileUtil.writeFile(upClearData, localPath, fileName);
		if(!isSuccess){
			throw new Exception( fileName + "*成交清算文件写入失败*");
		}
		File file = new File(localPath + File.separator + fileName);
		String zipFileName = bankDate+"_"+ exchNo+"_" +"clearResult.zip";
		File zipFile = new File(localPath + File.separator+zipFileName);
		ZipUtil.zip(file, zipFile);
		return zipFileName;

	}
	
}

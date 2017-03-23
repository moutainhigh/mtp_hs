package com.service.impl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

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
import com.proto.CenterBank.Msg31001;
import com.service.MakeMemberPositionFile;


/**
 *  
 * ClassName: MemberPositionImpl.java
 * date: 2016年12月16日下午3:59:52
 * @author yu.jian
 * @version
 */
@Service
@Transactional
public class MakeMemberPositionFileImpl implements MakeMemberPositionFile {
	private static final Logger LOGGER = Logger.getLogger(MakeMemberPositionFileImpl.class);
	/**
	 *  
	 * ClassName: MemberPositionImpl.java
	 * date: 2016年12月16日下午3:59:52
	 * @author yu.jian
	 * @version
	 */
	@Autowired
	private CenterFileRecMapper fileRecMapper;
	@Override
	public String doMakeMemberPositionFile(Msg31001 msg31001, long recId) throws Exception {
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
			
			//下载持仓明细文件到本地路径
			String sysPath = System.getProperty("user.dir");
			String localPath = sysPath + File.separator + PropertiesUtil.getProperty("recv_exch_path");
			
			LOGGER.info("【持仓明细文件文件】  下载文件开始 URL:" + url + "|PORT:" + port + "|filePath:" + filePath + "+|localPath:"
					+ localPath );
			boolean isSuccess = FtpUtil.downFile(url, port, username, password, filePath, fileName, localPath);
			
			if (!isSuccess) {
				fileRec.setDealStatus(1);
				fileRec.setDealDesc("失败");
				fileRecMapper.updateByPrimaryKeySelective(fileRec);
				throw new Exception("*文件下载失败*");
			}
			
			LOGGER.info("*文件下载成功*");
			
			//读取并组装持仓余额文件内容
			String upMemberPositionFileName = readMemberPositionFile(localPath+ File.separator + fileName,"4110014",msg31001.getBankDate());
			
			//上传文件
			LOGGER.info("【持仓明细文件文件】  持仓明细文件文件上传  localPath:" + localPath + "+|fileName：" + upMemberPositionFileName);
			
			String hs_url = PropertiesUtil.getProperty("hs_ftp_url");
			int hs_port = Integer.valueOf(PropertiesUtil.getProperty("hs_ftp_port"));
			String hs_username = PropertiesUtil.getProperty("hs_ftp_user_name");
			String hs_password = PropertiesUtil.getProperty("hs_ftp_pw");
			
			String send_localPath = sysPath + File.separator + PropertiesUtil.getProperty("send_path");
			String srcFilename = send_localPath + File.separator + upMemberPositionFileName;
			
			isSuccess = FtpUtil.uploadFile(hs_url, hs_port, hs_username, hs_password, msg31001.getBankDate(), upMemberPositionFileName,srcFilename);
			if (!isSuccess) {
				throw new Exception("*持仓明细文件文件上传失败*");
			}
			return upMemberPositionFileName;
		}catch(Exception e){
			throw e;
		}
	}
	
	//读取持仓文件
	public String readMemberPositionFile(String filePath,String exchNo, String bankDate) throws Exception {
		try {
			int i = 0;
			String data = "";
//			String tranNo ;
//			String bankDate ;
//			String totalRecord ;
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
			String lineStr = br.readLine();
			while (lineStr != null) {
				if(i++==0){
//					String[] liStr = lineStr.split("&");
//					tranNo = liStr[0];
//					bankDate = liStr[1];
//					totalRecord = liStr[2];
				}else{
					String[] liStr = lineStr.split("&");
					String initDate = liStr[1];// N8 业务发生日期(YYYYMMDD)
					String exchangeId = exchNo;// C32 交易所代码
					String holdId = liStr[2];// C32 持仓单号
					String memCode = liStr[3];// C20 会员编码
					String tradeAccount = liStr[4];// C20 交易账号
					String productCategoryId = liStr[5];// N16 产品类别ID
					String productCode = liStr[6];// C20 产品代码
					String tradeDir = liStr[7];// C1 是否买方向(1-买2-卖)
					String depositWay = liStr[8];// C1 是否定金方式(0-仓单 1-定金)
					String openPrice = liStr[9];// L 开仓价格
					String holdPrice = liStr[10];// L 持仓价格
					String dealQuantity = liStr[11];// D 成交数量
					String leftQuantity = liStr[12];// D 剩余数量
					String presentUnit = liStr[13];// D 数量单位
					String tradePoundage = liStr[14];// L 手续费
					String delayFees = liStr[15];// L 滞纳金
					String performBalance = liStr[16];// L 履约准备金
					String depositRate = liStr[17];// D 定金率
					String squareProfitLoss = liStr[18];// L 平仓盈亏
					String settleProfitLoss = liStr[19];// L 结算盈亏
					String settlePrice = liStr[20];// L 结算价
					String depositRatioType = liStr[21];// C1 定金率是否比率(0-固定 1-比率)
					String depositType = liStr[22];// C1 定金收取方式(0-开仓价 1-持仓价)
					String todayHoldFlag = liStr[23];// C1 是否今仓(0-否 1-是)
					String busiDatetime = liStr[24];// C14 成交时间(yyyyMMddHHmmss)
					
					data = data+initDate+"|"+exchangeId+"|"+holdId+"|"+memCode+"|"+tradeAccount
							+"|"+productCategoryId+"|"+productCode+"|"+tradeDir+"|"+depositWay+"|"+openPrice
							+"|"+holdPrice+"|"+dealQuantity+"|"+leftQuantity+"|"+presentUnit+"|"+tradePoundage
							+"|"+delayFees+"|"+performBalance+"|"+depositRate+"|"+squareProfitLoss+"|"+settleProfitLoss
							+"|"+settlePrice+"|"+depositRatioType+"|"+depositType+"|"+todayHoldFlag+"|"+busiDatetime+"\r\n";
					
				}
				lineStr = br.readLine();
			}
			
			//将所有数据一次性写入文件
			//持仓单文件到本地路径
			String sysPath = System.getProperty("user.dir");
			String localPath = sysPath + File.separator + PropertiesUtil.getProperty("send_path");
			String fileName = bankDate+"_"+ exchNo+"_" +"memberPositionDetail.txt";//yyyymmdd_xxx(交易所代码)_clearResult.txt
			
			boolean isSuccess = FileUtil.writeFile(data, localPath, fileName);
			if(!isSuccess){
				throw new Exception( fileName + "*持仓单文件写入失败*");
			}
			File file = new File(localPath + File.separator + fileName);
			String zipFileName = bankDate+"_"+ exchNo+"_" +"memberPositionDetail.zip";
			File zipFile = new File(localPath + File.separator+zipFileName);
			ZipUtil.zip(file, zipFile);
			return zipFileName;
		} catch (FileNotFoundException e) {
			throw new Exception("*文件读取失败*");
		}
	}
	
}
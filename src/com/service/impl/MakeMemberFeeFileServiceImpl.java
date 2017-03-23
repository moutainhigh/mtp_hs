package com.service.impl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.util.FileUtil;
import com.core.util.FtpUtil;
import com.core.util.PropertiesUtil;
import com.core.util.ZipUtil;
import com.dao.CenterFileRecMapper;
import com.model.CenterFileRec;
import com.model.MemberFee;
import com.proto.CenterBank.Msg31001;
import com.service.MakeMemberFeeFileService;


/**
 *  费用数据文件
 * ClassName: MakeMemberFeeFileServiceImpl.java
 * date: 2016年12月16日下午5:35:00
 * @author yu.jian
 * @version
 */
@Service
@Transactional
public class MakeMemberFeeFileServiceImpl implements MakeMemberFeeFileService {
	private static final Logger LOGGER = Logger.getLogger(MakeMemberFeeFileServiceImpl.class);
	/**
	 *  
	 * ClassName: MakeMemberFeeFileServiceImpl.java
	 * date: 2016年12月16日下午5:35:00
	 * @author yu.jian
	 * @version
	 */
	@Autowired
	private CenterFileRecMapper fileRecMapper;
	@Override
	public String doMakeMemberFeeFile(Msg31001 msg31001, long recId) throws Exception {
		
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
			
			//下载费用文件到本地路径
			String sysPath = System.getProperty("user.dir");
			String localPath = sysPath + File.separator + PropertiesUtil.getProperty("recv_exch_path");
			
			LOGGER.info("【费用文件】  下载文件开始 URL:" + url + "|PORT:" + port + "|filePath:" + filePath + "+|localPath:"
					+ localPath );
			boolean isSuccess = FtpUtil.downFile(url, port, username, password, filePath, fileName, localPath);
			
			if (!isSuccess) {
				fileRec.setDealStatus(1);
				fileRec.setDealDesc("失败");
				fileRecMapper.updateByPrimaryKeySelective(fileRec);
				throw new Exception("*文件下载失败*");
			}
			
			LOGGER.info("*文件下载成功*");
			
			//读取费用文件内容
			Map<String, Object> dataMap = readBF16(localPath+ File.separator + fileName);
			//取数据内容
			List<MemberFee> dataList = (List<MemberFee>) dataMap.get("beanList"); 
			
			LOGGER.info("*开始组装费用文件*");
			String upFeedFileName = makeFeeFile(dataList, "4110014",msg31001.getBankDate());
			
			
			//上传文件
			LOGGER.info("【费用文件】  费用文件文件上传  localPath:" + localPath + "+|fileName：" + upFeedFileName);
			
			String hs_url = PropertiesUtil.getProperty("hs_ftp_url");
			int hs_port = Integer.valueOf(PropertiesUtil.getProperty("hs_ftp_port"));
			String hs_username = PropertiesUtil.getProperty("hs_ftp_user_name");
			String hs_password = PropertiesUtil.getProperty("hs_ftp_pw");
			
			String send_localPath = sysPath + File.separator + PropertiesUtil.getProperty("send_path");
			String srcFilename = send_localPath + File.separator + upFeedFileName;
			
			isSuccess = FtpUtil.uploadFile(hs_url, hs_port, hs_username, hs_password, msg31001.getBankDate(), upFeedFileName,srcFilename);
			if (!isSuccess) {
				throw new Exception("*费用文件上传失败*");
			}
			return upFeedFileName;
		}catch(Exception e){
			throw e;
		}
	}
	
	//读取费用文件
	public Map<String, Object> readBF16(String filePath) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		List<MemberFee> MemberFeeList = new ArrayList<MemberFee>();
		try {
			int i = 0;
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
			String lineStr = br.readLine();
			while (lineStr != null) {
				if (i++ == 0) {
					String[] liStr = lineStr.split("&");
					String tranNo = liStr[0];
					String bankDate = liStr[1];
					String totalRecord = liStr[2];
					map.put("tranNo", tranNo);
					map.put("exchDate", bankDate);
					map.put("totalRecord", totalRecord);
				} else {
					MemberFee memberFee = new MemberFee();
					String[] liStr = lineStr.split("&");
					memberFee.setInitDate(liStr[1]);
					memberFee.setSerialNo(liStr[2]);
					memberFee.setExchangeId(liStr[0]);
					memberFee.setExchangeMarketType(liStr[3]);
					memberFee.setBizType(liStr[4]);
					memberFee.setExchangeFeesType(liStr[5]);
					memberFee.setFeesBalance(liStr[6]);
					memberFee.setPayerMemCode(liStr[7]);
					memberFee.setPayerFundAccount(liStr[8]);
					memberFee.setPayeeMemCode(liStr[9]);
					memberFee.setPayeeFundAccount(liStr[10]);
					memberFee.setRelatedBillType(liStr[11]);
					memberFee.setRelatedBillNo(liStr[12]);
					memberFee.setRemark(liStr[13]);
					memberFee.setBusiDatetime(liStr[14]);
					
					MemberFeeList.add(memberFee);
				}

				lineStr = br.readLine();
			}
			map.put("beanList", MemberFeeList);
		} catch (FileNotFoundException e) {
			LOGGER.error("*交易所费用文件读取失败*", e);
			throw new Exception("*交易所费用文件读取失败*", e);
		} catch (IOException e) {
			LOGGER.error("*交易所费用文件读取失败*", e);
			throw new Exception("*交易所费用文件读取失败*", e);
		}
		return map;
	}
	
	//组装费用文件
	public String makeFeeFile(List<MemberFee> dataList, String exchNo,String bankDate) throws Exception{
		String upFeedData = "";
		
		for(int i=0; i<dataList.size();i++){
			MemberFee memberFee = dataList.get(i);
			String initDate = memberFee.getInitDate();//	C10	业务发生日期(YYYYMMDD)	
			String serialNo = memberFee.getSerialNo();//	N8	费用流水号	
			String exchangeId = exchNo;//	C32	交易所代码	
			String exchangeMarketType = memberFee.getExchangeMarketType();//	C1	交易所子市场类型	
			String bizType = memberFee.getBizType();//	C4	业务类型	
			String exchangeFeesType = memberFee.getExchangeFeesType();//	C2	费用类型（为"q"时代表返佣，付款方为收益账号，收款方为对应待返佣账户）	
			String feesBalance = memberFee.getFeesBalance();//	L	费用金额	
			String payerMemCode = memberFee.getPayerMemCode();//	C20	付费会员编码	
			String payerFundAccount = memberFee.getPayeeFundAccount();//	C20	付款资金账号	
			String payeeMemCode = memberFee.getPayeeMemCode();//	C20	收款会员编码	
			String payeeFundAccount = memberFee.getPayeeFundAccount();//	C20	收款资金账号	
			String relatedBillType = memberFee.getRelatedBillType();//	C1	单据类型	
			String relatedBillNo = memberFee.getRelatedBillNo();//	C32	关联单据编号（对于成交为deal_id，对于委托为entrust_no，对于持仓为hold_id,对于交割为deliver_order_no）	
			String remark = memberFee.getRemark();//	C255	备注	
			String busiDatetime = memberFee.getBusiDatetime();//	C14	操作时间(yyyyMMddHHmmss)
			
			upFeedData = upFeedData+initDate+"|"+serialNo+"|"+exchangeId+"|"+exchangeMarketType+"|"+bizType
					+"|"+exchangeFeesType+"|"+feesBalance+"|"+payerMemCode+"|"+payerFundAccount+"|"+payeeMemCode
					+"|"+payeeFundAccount+"|"+relatedBillType
					+"|"+relatedBillNo+"|"+remark+"|"+busiDatetime+"\r\n";
		}
		//将所有数据一次性写入文件
		//下载费用文件到本地路径
		String sysPath = System.getProperty("user.dir");
		String localPath = sysPath + File.separator + PropertiesUtil.getProperty("send_path");
		String fileName = bankDate+"_"+ exchNo+"_" +"memberFee.txt";//yyyymmdd_xxx(交易所代码)_clearResult.txt
		
		boolean isSuccess = FileUtil.writeFile(upFeedData, localPath, fileName);
		if(!isSuccess){
			throw new Exception( fileName + "*费用文件写入失败*");
		}
		File file = new File(localPath + File.separator + fileName);
		String zipFileName = bankDate+"_"+ exchNo+"_" +"memberFee.zip";
		File zipFile = new File(localPath + File.separator+zipFileName);
		ZipUtil.zip(file, zipFile);
		return zipFileName;

	}

}

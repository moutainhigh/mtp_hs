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

import com.common.util.CenterToExchUtil;
import com.common.util.DateHelp;
import com.core.util.FileUtil;
import com.core.util.FtpUtil;
import com.core.util.PropertiesUtil;
import com.core.util.ZipUtil;
import com.dao.CenterFileRecMapper;
import com.model.CenterFileRec;
import com.model.Quotation;
import com.proto.CenterBank.Msg31001;
import com.service.MakeQuotationFileService;


/**
 *  行情文件
 * ClassName: MakeQuotationFileServiceImpl.java
 * date: 2016年12月16日下午8:49:43
 * @author yu.jian
 * @version
 */
@Service
@Transactional
public class MakeQuotationFileServiceImpl implements MakeQuotationFileService {
	private static final Logger LOGGER = Logger.getLogger(MakeQuotationFileServiceImpl.class);
	/**
	 *  
	 * ClassName: MakeQuotationFileServiceImpl.java
	 * date: 2016年12月16日下午8:49:43
	 * @author yu.jian
	 * @version
	 */
	@Autowired
	private CenterFileRecMapper fileRecMapper;
	@Override
	public String doMakeQuotationFile(Msg31001 msg31001, long recId) throws Exception {
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
			
			//下载行情文件到本地路径
			String sysPath = System.getProperty("user.dir");
			String localPath = sysPath + File.separator + PropertiesUtil.getProperty("recv_exch_path");
			
			LOGGER.info("【行情文件】  下载文件开始 URL:" + url + "|PORT:" + port + "|filePath:" + filePath + "+|localPath:"
					+ localPath );
			boolean isSuccess = FtpUtil.downFile(url, port, username, password, filePath, fileName, localPath);
			
			if (!isSuccess) {
				fileRec.setDealStatus(1);
				fileRec.setDealDesc("失败");
				fileRecMapper.updateByPrimaryKeySelective(fileRec);
				throw new Exception("*文件下载失败*");
			}
			
			LOGGER.info("*文件下载成功*");
			
			//读取行情文件内容
			Map<String, Object> dataMap = readBF18(localPath+ File.separator + fileName);
			//取数据内容
			List<Quotation> dataList = (List<Quotation>) dataMap.get("beanList"); 
			
			LOGGER.info("*开始组装行情文件*");
			String upQuotationFileName = makeQuotationFile(dataList, "4110014", msg31001.getBankDate());
			
			
			//上传文件
			LOGGER.info("【行情文件】  行情文件文件上传  localPath:" + localPath + "+|fileName：" + upQuotationFileName);
			
			String hs_url = PropertiesUtil.getProperty("hs_ftp_url");
			int hs_port = Integer.valueOf(PropertiesUtil.getProperty("hs_ftp_port"));
			String hs_username = PropertiesUtil.getProperty("hs_ftp_user_name");
			String hs_password = PropertiesUtil.getProperty("hs_ftp_pw");
			
			String send_localPath = sysPath + File.separator + PropertiesUtil.getProperty("send_path");
			String srcFilename = send_localPath + File.separator + upQuotationFileName;
			
			isSuccess = FtpUtil.uploadFile(hs_url, hs_port, hs_username, hs_password, msg31001.getBankDate(), upQuotationFileName,srcFilename);
			if (!isSuccess) {
				throw new Exception("*行情文件上传失败*");
			}
			return upQuotationFileName;
		}catch(Exception e){
			throw e;
		}
	}
	
	//读取行情文件
	public Map<String, Object> readBF18(String filePath) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		List<Quotation> quotationList = new ArrayList<Quotation>();
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
					Quotation quotation = new Quotation();
					String[] liStr = lineStr.split("&");
					quotation.setInitDate(liStr[1]);
					quotation.setPriceType(liStr[2]);
					quotation.setExchangeId(liStr[0]);
					quotation.setExchangeMarketType(liStr[3]);
					quotation.setProductCategoryId(liStr[4]);
					quotation.setProductCode(liStr[5]);
					quotation.setMoneyType(CenterToExchUtil.HS_CURRENCY.get(liStr[6]));
					quotation.setBitNum(liStr[7]);
					quotation.setOpenQuotationPrice(liStr[8]);
					quotation.setPreclosePrice(liStr[9]);
					quotation.setAskPrice(liStr[10]);
					quotation.setBidPrice(liStr[11]);
					quotation.setLastPrice(liStr[12]);
					quotation.setHighPrice(liStr[13]);
					quotation.setLowPrice(liStr[14]);
					quotation.setTimestamph(liStr[15]);
					
					quotationList.add(quotation);
				}

				lineStr = br.readLine();
			}
			map.put("beanList", quotationList);
		} catch (FileNotFoundException e) {
			LOGGER.error("*交易所行情文件读取失败*", e);
			throw new Exception("*交易所行情文件读取失败*", e);
		} catch (IOException e) {
			LOGGER.error("*交易所行情文件读取失败*", e);
			throw new Exception("*交易所行情文件读取失败*", e);
		}
		return map;
	}
	
	//组装行情文件
	public String makeQuotationFile(List<Quotation> dataList, String exchNo, String bankDate) throws Exception{
		String upQuotationData = "";
		
		for(int i=0; i<dataList.size();i++){
			Quotation quotation = dataList.get(i);
			String initDate = quotation.getInitDate();//	N8	业务发生日期	
			String priceType = quotation.getPriceType();//	C1	行情类型；0更新实时行情1开市2休市3更新结算价	
			String exchangeId = exchNo;//	C32	交易所代码	
			String exchangeMarketType = quotation.getExchangeMarketType();//	C1	子市场代码	
			String productCategoryId = quotation.getProductCategoryId();//	N16	类别ID	
			String productCode = quotation.getProductCode();//	C20	产品代码	
			String moneyType = quotation.getMoneyType();//	C3	货币类型	
			String bitNum = quotation.getBitNum();//	I	小数位	
			String openQuotationPrice = quotation.getOpenQuotationPrice();//	D	开盘价	
			String preclosePrice = quotation.getPreclosePrice();//	D	昨收价	
			String askPrice = quotation.getAskPrice();//	D	买入价	
			String bidPrice = quotation.getBidPrice();//	D	卖出价	
			String lastPrice = quotation.getLastPrice();//	D	最新价	
			String highPrice = quotation.getHighPrice();//	D	最高价	
			String lowPrice = quotation.getLowPrice();//	D	最低价	
			String timestamp = quotation.getTimestamph();//	C32	时间戳	
			
			upQuotationData = upQuotationData+initDate+"|"+priceType+"|"+exchangeId+"|"+exchangeMarketType+"|"+productCategoryId
					+"|"+productCode+"|"+moneyType+"|"+bitNum+"|"+openQuotationPrice+"|"+preclosePrice
					+"|"+askPrice+"|"+bidPrice
					+"|"+lastPrice+"|"+highPrice+"|"+lowPrice+"|"+timestamp+"\r\n";
		}
		//将所有数据一次性写入文件
		//下载行情文件到本地路径
		String sysPath = System.getProperty("user.dir");
		String localPath = sysPath + File.separator + PropertiesUtil.getProperty("send_path");
		String fileName = bankDate+"_"+ exchNo+"_" +"quotation.txt";//yyyymmdd_xxx(交易所代码)_quotationResult.txt
		
		boolean isSuccess = FileUtil.writeFile(upQuotationData, localPath, fileName);
		if(!isSuccess){
			throw new Exception( fileName + "*行情文件写入失败*");
		}
		File file = new File(localPath + File.separator + fileName);
		String zipFileName = bankDate+"_"+ exchNo+"_" +"quotation.zip";
		File zipFile = new File(localPath + File.separator+zipFileName);
		ZipUtil.zip(file, zipFile);
		return zipFileName;

	}

}

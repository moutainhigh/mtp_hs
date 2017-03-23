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

import com.common.util.DateHelp;
import com.core.util.FileUtil;
import com.core.util.FtpUtil;
import com.core.util.PropertiesUtil;
import com.core.util.ZipUtil;
import com.dao.CenterFileRecMapper;
import com.model.CenterFileRec;
import com.model.Deliver;
import com.proto.CenterBank.Msg31001;
import com.service.MakeDeliverFileService;


/**
 *  交割单文件
 * ClassName: MakeDeliverFileServiceImpl.java
 * date: 2016年12月16日下午9:19:57
 * @author yu.jian
 * @version
 */
@Service
@Transactional
public class MakeDeliverFileServiceImpl implements MakeDeliverFileService {
	private static final Logger LOGGER = Logger.getLogger(MakeDeliverFileServiceImpl.class);
	/**
	 *  
	 * ClassName: MakeDeliverFileServiceImpl.java
	 * date: 2016年12月16日下午9:19:57
	 * @author yu.jian
	 * @version
	 */
	@Autowired
	private CenterFileRecMapper fileRecMapper;
	@Override
	public String doMakeDeliverFile(Msg31001 msg31001, long recId) throws Exception {
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
			
			//下载交割单文件到本地路径
			String sysPath = System.getProperty("user.dir");
			String localPath = sysPath + File.separator + PropertiesUtil.getProperty("recv_exch_path");
			
			LOGGER.info("【交割单文件】  下载文件开始 URL:" + url + "|PORT:" + port + "|filePath:" + filePath + "+|localPath:"
					+ localPath );
			boolean isSuccess = FtpUtil.downFile(url, port, username, password, filePath, fileName, localPath);
			
			if (!isSuccess) {
				fileRec.setDealStatus(1);
				fileRec.setDealDesc("失败");
				fileRecMapper.updateByPrimaryKeySelective(fileRec);
				throw new Exception("*文件下载失败*");
			}
			
			LOGGER.info("*文件下载成功*");
			
			//读取交割单文件内容
			Map<String, Object> dataMap = readBF14(localPath+ File.separator + fileName);
			//取数据内容
			List<Deliver> dataList = (List<Deliver>) dataMap.get("beanList"); 
			
			LOGGER.info("*开始组装交割单文件*");
			String upDeliverFileName = makeFeeFile(dataList, "4110014", msg31001.getBankDate());
			
			
			//上传文件
			LOGGER.info("【交割单文件】  交割单文件文件上传  localPath:" + localPath + "+|fileName：" + upDeliverFileName);
			
			String hs_url = PropertiesUtil.getProperty("hs_ftp_url");
			int hs_port = Integer.valueOf(PropertiesUtil.getProperty("hs_ftp_port"));
			String hs_username = PropertiesUtil.getProperty("hs_ftp_user_name");
			String hs_password = PropertiesUtil.getProperty("hs_ftp_pw");
			
			String send_localPath = sysPath + File.separator + PropertiesUtil.getProperty("send_path");
			String srcFilename = send_localPath + File.separator + upDeliverFileName;
			
			isSuccess = FtpUtil.uploadFile(hs_url, hs_port, hs_username, hs_password, msg31001.getBankDate(), upDeliverFileName,srcFilename);
			if (!isSuccess) {
				throw new Exception("*交割单文件上传失败*");
			}
			return upDeliverFileName;
		}catch(Exception e){
			throw e;
		}
	}
	
	//读取交割单文件
	public Map<String, Object> readBF14(String filePath) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		List<Deliver> deliverList = new ArrayList<Deliver>();
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
					Deliver deliver = new Deliver();
					String[] liStr = lineStr.split("&");
					deliver.setInitDate(liStr[1]);
					deliver.setExchangeId(liStr[0]);
					deliver.setMemCode(liStr[2]);
					deliver.setProductCategoryId(liStr[3]);
					deliver.setProductCategoryName(liStr[4]);
					deliver.setProductCode(liStr[5]);
					deliver.setProductName(liStr[6]);
					deliver.setDeliverOrderNo(liStr[7]);
					deliver.setDepotOrderNo(liStr[8]);
					deliver.setDeliverApplyTime(liStr[9]);
					deliver.setDeliverEffectTime(liStr[10]);
					deliver.setDeliverAddr(liStr[11]);
					deliver.setDeliverDepot(liStr[12]);
					deliver.setDeliverType(liStr[13]);
					deliver.setDeliverDirection(liStr[14]);
					deliver.setDeliverPrice(liStr[15]);
					deliver.setDeliverQuantity(liStr[16]);
					deliver.setDeliverPayment(liStr[17]);
					deliver.setDeliverFees(liStr[18]);
					deliver.setHoldPrice(liStr[19]);
					deliver.setBusiDatetime(liStr[20]);
					
					deliverList.add(deliver);
				}

				lineStr = br.readLine();
			}
			map.put("beanList", deliverList);
		} catch (FileNotFoundException e) {
			LOGGER.error("*交易所交割单文件读取失败*", e);
			throw new Exception("*交易所交割单文件读取失败*", e);
		} catch (IOException e) {
			LOGGER.error("*交易所交割单文件读取失败*", e);
			throw new Exception("*交易所交割单文件读取失败*", e);
		}
		return map;
	}
	
	//组装交割单文件
	public String makeFeeFile(List<Deliver> dataList, String exchNo, String bankDate) throws Exception{
		String upDeliverData = "";
		
		for(int i=0; i<dataList.size();i++){
			Deliver deliver = dataList.get(i);
			String initDate = deliver.getInitDate();//	N8	业务发生日期(YYYYMMDD)	
			String exchangeId = exchNo;//	C32	交易所代码	
			String memCode = deliver.getMemCode();//	C20	会员编码	
			String productCategoryId = deliver.getProductCategoryId();//	N16	产品类别ID	
			String productCategoryName = deliver.getProductCategoryName();//	C60	产品类别名称	
			String productCode = deliver.getProductCode();//	C20	商品合约编码	
			String productName = deliver.getProductName();//	C20	商品合约名称	
			String deliverOrderNo = deliver.getDeliverOrderNo();//	C20	交割单编码	
			String depotOrderNo = deliver.getDepotOrderNo();//	C20	建仓单号	
			String deliverApplyTime = deliver.getDeliverApplyTime();//	C14	交割申请日期时间	
			String deliverEffectTime = deliver.getDeliverEffectTime();//	C14	交割生效日期时间	
			String deliverAddr = deliver.getDeliverAddr();//	C120	交割地点	
			String deliverDepot = deliver.getDeliverDepot();//	C64	交割仓库	
			String deliverType = deliver.getDeliverType();//	C1	交割类型	
			String deliverDirection = deliver.getDeliverDirection();//	C1	交割方向	
			String deliverPrice = deliver.getDeliverPrice();//	L	交割价格	
			String deliverQuantity = deliver.getDeliverQuantity();//	D	交割数量	
			String deliverPayment = deliver.getDeliverPayment();//	L	交割货款（与费用文件数据一致）	
			String deliverFees = deliver.getDeliverFees();//	L	交割费（与费用文件数据一致）	
			String holdPrice = deliver.getHoldPrice();//	L	持仓价格	
			String busiDatetime = deliver.getBusiDatetime();//	C14	业务时间(yyyyMMddHHmmss)	
			
			upDeliverData = upDeliverData+initDate+"|"+exchangeId+"|"+memCode+"|"+productCategoryId+"|"+productCategoryName
					+"|"+productCode+"|"+productName+"|"+deliverOrderNo+"|"+depotOrderNo+"|"+deliverApplyTime
					+"|"+deliverEffectTime+"|"+deliverAddr
					+"|"+deliverDepot+"|"+deliverType+"|"+deliverDirection
					+"|"+deliverPrice+"|"+deliverQuantity+"|"+deliverPayment+"|"+deliverFees+"|"+holdPrice
					+"|"+busiDatetime+"\r\n";
		}
		//将所有数据一次性写入文件
		//下载交割单文件到本地路径
		String sysPath = System.getProperty("user.dir");
		String localPath = sysPath + File.separator + PropertiesUtil.getProperty("send_path");
		String fileName = bankDate+"_"+ exchNo+"_" +"deliver.txt";//yyyymmdd_xxx(交易所代码)_deliverResult.txt
		
		boolean isSuccess = FileUtil.writeFile(upDeliverData, localPath, fileName);
		if(!isSuccess){
			throw new Exception( fileName + "*交割单文件写入失败*");
		}
		File file = new File(localPath + File.separator + fileName);
		String zipFileName = bankDate+"_"+ exchNo+"_" +"deliver.zip";
		File zipFile = new File(localPath + File.separator+zipFileName);
		ZipUtil.zip(file, zipFile);
		return zipFileName;

	}

}

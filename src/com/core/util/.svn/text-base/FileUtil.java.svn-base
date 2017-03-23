package com.core.util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.core.exception.ServiceException;
import com.dao.Impl.ClearDataDao;
import com.model.ClearData;


public class FileUtil{

	private static final Logger LOGGER = Logger.getLogger(FileUtil.class);

	
	/** 
	 * @Title: readBankClearDataRet 
	 * @Description: TODO银行清算返回文件
	 * @param filePath
	 * @param fileName
	 * @return
	 * @return: Map<String,BankClearDataRet>
	 */
	public static Map<String, Object> readClearFile(String filePath) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		List<ClearData> clearDataList = new ArrayList<ClearData>();
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
					map.put("exchNo", tranNo);
					map.put("exchDate", bankDate);
					map.put("totalRecord", totalRecord);
				} else {
					String[] liStr = lineStr.split("&");
					String tranNo = liStr[0];
					String bankDate = liStr[1];
					String exchNo = liStr[2];
					String tradeAcct = liStr[3];
					String trueClearAmt = liStr[4];
					String trueProfitLoss = liStr[5];
					String trueExchGetFee = liStr[6];
					String bankStartAmt = liStr[7];
					String bankEndAmt = liStr[8];
					// String startAmt = liStr[4];
					// String endAmt = liStr[5];
					// String occurAmt = liStr[6];
					// String outInAmt = liStr[7];
					// String outAmt = liStr[8];
					// String inAmt = liStr[9];
					// String getPayAmt = liStr[10];
					// String getAmt = liStr[11];
					// String payAmt = liStr[12];
					// String clearAmt = liStr[13];
					// String profitLoss = liStr[14];
					// String exchGetFee = liStr[15];
					// String heapClearAmt = liStr[16];
					// String heapProfitLoss = liStr[17];
					// String heapExchGetFee = liStr[18];
					// String heapFrom = liStr[22];

					ClearData clearData = new ClearData();
					clearData.setClearDataId(new ClearDataDao().generateId());
					clearData.setTranNo(tranNo);
					clearData.setTranDate(bankDate);
					clearData.setExchNo(exchNo);
					clearData.setTradeAcct(tradeAcct);
					clearData.setTrueClearAmt(new BigDecimal(trueClearAmt));
					clearData.setTrueProfitLoss(new BigDecimal(trueProfitLoss));
					clearData.setTrueExchGetFee(new BigDecimal(trueExchGetFee));
					clearData.setStartAmt(new BigDecimal(bankStartAmt));
					clearData.setEndAmt(new BigDecimal(bankEndAmt));
					// clearData.setStartAmt(new BigDecimal(startAmt));
					// clearData.setEndAmt(new BigDecimal(endAmt));
					// clearData.setOccurAmt(new BigDecimal(occurAmt));
					// clearData.setOutInAmt(new BigDecimal(outInAmt));
					// clearData.setOutAmt(new BigDecimal(outAmt));
					// clearData.setInAmt(new BigDecimal(inAmt));
					// clearData.setGetPayAmt(new BigDecimal(getPayAmt));
					// clearData.setGetAmt(new BigDecimal(getAmt));
					// clearData.setPayAmt(new BigDecimal(payAmt));
					// clearData.setClearAmt(new BigDecimal(clearAmt));
					// clearData.setProfitLoss(new BigDecimal(profitLoss));
					// clearData.setExchGetFee(new BigDecimal(exchGetFee));
					// clearData.setHeapClearAmt(new BigDecimal(heapClearAmt));
					// clearData.setHeapProfitLoss(new
					// BigDecimal(heapProfitLoss));
					// clearData.setHeapExchGetFee(new
					// BigDecimal(heapExchGetFee));
					// clearData.setHeapFrom(Integer.parseInt(heapFrom));

					clearDataList.add(clearData);
				}

				lineStr = br.readLine();
			}
			map.put("beanList", clearDataList);
		} catch (FileNotFoundException e) {
			LOGGER.error("FileUtil.readClearFile: 交易所清算文件读取失败", e);
			throw new Exception("FileUtil.readClearFile: 交易所清算文件读取失败", e);
		} catch (IOException e) {
			LOGGER.error("FileUtil.readClearFile: 交易所清算文件读取失败", e);
			throw new Exception("FileUtil.readClearFile: 交易所清算文件读取失败", e);
		}
		return map;
	}
	
	

	
	public static boolean writeFile(String dataStr, String localPath, String localFileName) throws Exception{
		boolean isSuccess = true;
		BufferedWriter bufOut = null;
		//如果无此文件夹即创建
    	createFileDir(localPath);
		try {
			bufOut = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(localPath + File.separator + localFileName, false), "UTF-8"));
			// fileWriter = new FileWriter("D:/test.txt");
			bufOut.write(dataStr);
			bufOut.flush();
			bufOut.close();
		} catch (IOException e) {
			isSuccess = false;
			LOGGER.error("FileUtil.writeFile: 写入文件失败",e);
			throw new Exception("FileUtil.writeFile:写入文件失败",e);
		} finally {
			try {
				bufOut.close();
			} catch (IOException e) {
				isSuccess = false;
				LOGGER.error("FileUtil.writeFile: 写入文件失败",e);
				throw new Exception("FileUtil.writeFile: 写入文件失败",e);
			}

		}
		return isSuccess;
	}
	
	 /**
     * 创建文件夹
     * createFileDir:(这里用一句话描述这个方法的作用). <br/>
     * @author yan.zhigang
     * @param path
     * @throws Exception
     */
    private static void createFileDir(String path) throws Exception{
    	try{
    		File file = new File(path);
        	if(!file.exists()){
        		file.mkdirs();
        	}
    	}catch(Exception e){
    		LOGGER.error("FtpUtil.createFileDir:"+e.getMessage(),e);
			throw e;
    	}
    }

    
	public static void test(String filePath, String fileName, String md5) throws Exception {

		BufferedReader br =null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath + File.separator + fileName), "UTF-8"));
		} catch (Exception e1) {
			LOGGER.error("没发现该文件：" + filePath + File.separator + fileName);
			throw new ServiceException("没发现该文件：" + filePath + File.separator + fileName);
		}
		try {
			int i=0;
			String lineStr = br.readLine();
			while(lineStr != null){
				if(i++==0){
					String[] liStr = lineStr.split("&");
					System.out.println("----------"+liStr[0]);
				}else{
					String[] liStr = lineStr.split("&");
					System.out.println("================="+liStr[0]);
				}				
				lineStr = br.readLine();
			}
		} catch (Exception e) {
			LOGGER.error(String.format("文件读取后处理异常：%s文件名%s", filePath, fileName) + ExceptionUtils.getStackTrace(e));
			throw new ServiceException("文件读取后处理异常：" + filePath + File.separator + fileName + "	"+e.getMessage());
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				LOGGER.error(String.format("文件关闭失败：%s文件名%s", filePath, fileName));
			}
		}
	}
}

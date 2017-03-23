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
import com.common.util.StringUtil;
import com.core.util.FileUtil;
import com.core.util.FtpUtil;
import com.core.util.PropertiesUtil;
import com.core.util.ZipUtil;
import com.dao.AccountMapper;
import com.dao.CenterFileRecMapper;
import com.model.Account;
import com.model.CenterFileRec;
import com.model.Client;
import com.proto.CenterBank.Msg31001;
import com.service.MakeClientMsgFile;


/**
 *  客户信息文件
 * ClassName: MakeClientMsgFileImpl.java
 * date: 2016年12月17日上午11:00:28
 * @author yu.jian
 * @version
 */
@Service
@Transactional
public class MakeClientMsgFileImpl implements MakeClientMsgFile {
	private static final Logger LOGGER = Logger.getLogger(MakeClientMsgFileImpl.class);
	/**
	 *  
	 * ClassName: MakeClientMsgFileImpl.java
	 * date: 2016年12月17日上午11:00:28
	 * @author yu.jian
	 * @version
	 */
	@Autowired
	private CenterFileRecMapper fileRecMapper;
	@Autowired
	private AccountMapper accountMapper;
	@Override
	public String doMakeClientMsgFile(Msg31001 msg31001, long recId) throws Exception {
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
			
			//下载客户信息文件到本地路径
			String sysPath = System.getProperty("user.dir");
			String localPath = sysPath + File.separator + PropertiesUtil.getProperty("recv_exch_path");
			
			LOGGER.info("【客户信息文件】  下载文件开始 URL:" + url + "|PORT:" + port + "|filePath:" + filePath + "+|localPath:"
					+ localPath );
			boolean isSuccess = FtpUtil.downFile(url, port, username, password, filePath, fileName, localPath);
			
			if (!isSuccess) {
				fileRec.setDealStatus(1);
				fileRec.setDealDesc("失败");
				fileRecMapper.updateByPrimaryKeySelective(fileRec);
				throw new Exception("*文件下载失败*");
			}
			
			LOGGER.info("*文件下载成功*");
			
			//读取客户信息文件内容
			Map<String, Object> dataMap = readBF05(localPath+ File.separator + fileName);
			//取数据内容
			List<Client> dataList = (List<Client>) dataMap.get("beanList"); 
			
			LOGGER.info("*开始组装客户信息文件*");
			String upClientFileName = makeClientFile(dataList, "4110014", msg31001.getBankDate());
			
			
			//上传文件
			LOGGER.info("【客户信息文件】  客户信息文件文件上传  localPath:" + localPath + "+|fileName：" + upClientFileName);
			
			String hs_url = PropertiesUtil.getProperty("hs_ftp_url");
			int hs_port = Integer.valueOf(PropertiesUtil.getProperty("hs_ftp_port"));
			String hs_username = PropertiesUtil.getProperty("hs_ftp_user_name");
			String hs_password = PropertiesUtil.getProperty("hs_ftp_pw");
			
			String send_localPath = sysPath + File.separator + PropertiesUtil.getProperty("send_path");
			String srcFilename = send_localPath + File.separator + upClientFileName;
			
			isSuccess = FtpUtil.uploadFile(hs_url, hs_port, hs_username, hs_password, msg31001.getBankDate(), upClientFileName,srcFilename);
			if (!isSuccess) {
				throw new Exception("*客户信息文件上传失败*");
			}
			return upClientFileName;
		}catch(Exception e){
			throw e;
		}
	}
	
	//读取客户信息文件
	public Map<String, Object> readBF05(String filePath) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		List<Client> clientList = new ArrayList<Client>();
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
					Client client = new Client();
					String[] liStr = lineStr.split("&");
					
					client.setExchNo(liStr[1]);
					client.setTradeAcct(liStr[2]);
					client.setClientName(liStr[3]);
					client.setClientShortName(liStr[4]);
					client.setClientType(Integer.parseInt(liStr[5]));
					client.setClientAcctType(Integer.parseInt(liStr[6]));
					client.setCertType(liStr[7]);
					client.setCertCode(liStr[8]);
					client.setSex(Integer.parseInt(liStr[9]));
					client.setLegalPerson(liStr[10]);
					client.setOrgCode(liStr[11]);
					client.setNationality(liStr[12]);
					client.setAddress(liStr[13]);
					client.setContactName(liStr[14]);
					client.setContactPhone(liStr[15]);
					client.setContactFax(liStr[16]);
					client.setContactEmail(liStr[17]);
					client.setRelatedAcctStr(liStr[18]);
					client.setExtendInfo(liStr[19]);
					client.setClientStatus(StringUtil.isEmpty(liStr[20]) ? null :Integer.parseInt(liStr[20]));
					
					clientList.add(client);
				}

				lineStr = br.readLine();
			}
			map.put("beanList", clientList);
		} catch (FileNotFoundException e) {
			LOGGER.error("*交易所客户信息文件读取失败*", e);
			throw new Exception("*交易所客户信息文件读取失败*", e);
		} catch (IOException e) {
			LOGGER.error("*交易所客户信息文件读取失败*", e);
			throw new Exception("*交易所客户信息文件读取失败*", e);
		}
		return map;
	}
	
	//组装客户信息文件
	public String makeClientFile(List<Client> dataList, String exchNo, String bankDate) throws Exception{
		String upClientData = "";
		
		for(int i=0; i<dataList.size();i++){
			Client client = dataList.get(i);
			String initDate = DateHelp.getCurrentDateOfString();//	N8	业务发生日期		Y
			String serialNo	= "";//C20	交易所端流水号		Y
			/*String exchangeId = client.getExchNo();//C30	交易所代码		Y*/
			String exchangeId = exchNo;//C30	交易所代码		Y
			String memCode = "";//C20	会员账号		Y
			String fundAccount = client.getTradeAcct();//	C20	资金账号		Y
			String tradeAccount	= client.getTradeAcct();//C20	交易账号		Y
			String memberType = client.getClientAcctType()+"";//C1	会员类型；1-交易所，2-综合类，3-结算类，4-经纪类；5-交易类；		Y
			String memberMainType = (Integer.parseInt(client.getCertType())==1?2:1)+"";//C1	会员主体类型；1-机构；2-个人		Y
			String fullName	= client.getClientName();//C100	会员全称		Y
			String shortName = client.getClientShortName();//C50	会员简称		N
			String enFullName = "";//	C200	会员英文全称		N
			String enShortName = "";//	C50	会员英文简称		N
			String tel = client.getContactPhone();//	C30	会员联系电话		Y
			Account account  = accountMapper.selectByTradeAcct(tradeAccount);
			String exchangeMemberStatus=null;
			if(account!=null){
				if(account.getSignStatus()==1)
					exchangeMemberStatus="1";
				else 
					exchangeMemberStatus="2";
			}//C1	交易所会员状态；1-已签约；2-未签约；3-销户		Y
			String upMemCode = "";//	C20	上级会员账号（不存在不填）		Y
			String brokerCode = "";//	C20	经纪会员编号（普通类型会员不存在不填，其他类型会员不填）		N
			String legalPerson = client.getLegalPerson();//	C64	法人名称		Y
			String idKind = CenterToExchUtil.HS_CERT_TYPE.get(client.getCertType());//	C4	证件类型		Y
			String idNo	= client.getCertCode();//C32	证件号码		Y
			String gender = CenterToExchUtil.HS_SEX.get(client.getSex());//	C1	性别；1：男，0：女，2：未知		Y
			String nationality = client.getNationality();//C3	国籍地区；CHN:中国		Y
			String businessCert = "";//	C100	工商营业执照（机构类型必填,证件类型为'I10：统一社会信用代码'时可不填）		N
			String orgCode = client.getOrgCode();//C30	组织机构代码（机构类型必填,证件类型为'I10：统一社会信用代码'时可不填）		N
			String taxCert = "";//C30	税务登记证号（机构类型必填,证件类型为'I10：统一社会信用代码'时可不填）		N
			String taxCertType = "";//	C1	税务登记证类型；1：地税；2：国税，机构类型必填,证件类型为'I10：统一社会信用代码'时可不填		N
			String regAddr = "";//	C255	注册地址		N
			String comAddr = client.getAddress();//	C255	营业地址		N
			String contactName = client.getClientName();//	C32	联系人姓名		N
			String contactTel = client.getContactPhone();//	C30	联系人电话		N
			String contactFax = client.getContactFax();//	C32	联系人传真		N
			String contactEmail = client.getContactEmail();//	C32	联系人邮箱		N

			upClientData = upClientData+initDate+"|"+serialNo+"|"+exchangeId+"|"+memCode+"|"+fundAccount
					+"|"+tradeAccount+"|"+memberType+"|"+memberMainType+"|"+fullName+"|"+shortName
					+"|"+enFullName+"|"+enShortName+"|"+tel+"|"+exchangeMemberStatus+"|"+upMemCode+
					brokerCode+"|"+legalPerson+"|"+idKind+"|"+idNo+"|"+gender+"|"+nationality+"|"+businessCert
					+"|"+orgCode+"|"+taxCert+"|"+taxCertType+"|"+regAddr+"|"+comAddr+
					contactName+"|"+contactTel+"|"+contactFax+"|"+contactEmail+"\r\n";
		}
		//将所有数据一次性写入文件
		//下载客户信息文件到本地路径
		String sysPath = System.getProperty("user.dir");
		String localPath = sysPath + File.separator + PropertiesUtil.getProperty("send_path");
		String fileName = bankDate+"_"+ exchNo+"_" +"clientInfoMod.txt";//yyyymmdd_xxx(交易所代码)clientInfoMod.txt
		
		boolean isSuccess = FileUtil.writeFile(upClientData, localPath, fileName);
		if(!isSuccess){
			throw new Exception( fileName + "*客户信息文件写入失败*");
		}
		
		File file = new File(localPath + File.separator + fileName);
		String zipFileName = bankDate+"_"+ exchNo+"_" +"clientInfoMod.zip";
		File zipFile = new File(localPath + File.separator+zipFileName);
		ZipUtil.zip(file, zipFile);
		return zipFileName;

	}

}

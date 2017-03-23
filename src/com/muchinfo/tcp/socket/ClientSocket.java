package com.muchinfo.tcp.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.des.desUtils.DesUtils;
import com.muchinfo.common.constants.FinalConstants;
import com.muchinfo.common.util.LogFileUtils;
import com.muchinfo.common.util.PropertiesUtil;

import net.sf.json.JSONObject;

/**
 * 
 * ClassName: ClientSocket <br/>
 * date: 2016年7月7日 上午10:09:00 <br/>
 * 
 * @author zhou.yao
 * @version
 */
public class ClientSocket {
	private static final Logger LOGGER = Logger.getLogger(ClientSocket.class);

	/**
	 * sendMsg:(客户端给服务器端发送消息). <br/>
	 * 
	 * @author zhou.yao
	 * @param host
	 *            服务器端ip地址
	 * @param port
	 *            服务器端端口号
	 * @param msg
	 *            发送消息内容
	 */
	public static String sendMsg(String host, int port, String msg) {
		LOGGER.info("ClientSocket.sendMsg 客户端准备发送消息:" + host + ":" + port + ":" + msg);
		String respMsg = "";
		Socket socket = null;
		PrintWriter out = null;
		BufferedReader br = null;
		try {
			socket = new Socket(host, port);
			out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			out.println(msg); // TODO: 实际发送数据
			out.flush();
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			respMsg = br.readLine();
			LOGGER.info("ClientSocket.sendMsg 服务器端(TAS)回复消息:" + respMsg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				out.close();
		}
		return respMsg;
	}

	/**
	 * socket服务器转发地址消息 sendMsg:(这里用一句话描述这个方法的作用). <br/>
	 * 
	 * @author zhou.yao
	 * @param jsonReq
	 * @return
	 */
	public static String sendMsg(String jsonReq) {
		//String host = FinalConstants.SocketConfig.HOST; // 服务器ip地址
		//int port = FinalConstants.SocketConfig.PORT;// 服务器端端口号
		String host =  PropertiesUtil.getProperty("server.socket.ip");; // 服务器ip地址
		int port =  Integer.parseInt(PropertiesUtil.getProperty("server.socket.port"));// 服务器端端口号
		Boolean bool = PropertiesUtil.getProperty("is.encrypt").equals("1") ? true:false;
		//Boolean bool = true; // 默认不加密(OTC的是自己加解密，TAS是需要你那边加解密)
		try {
			if (bool) {
				if (!StringUtils.isEmpty(jsonReq)) {
					if (jsonReq.indexOf("fundPassword") > -1) {
						String functionId = jsonReq.substring(0, 6);// 解析方法id
						jsonReq = jsonReq.substring(6, jsonReq.length());// 解析json字符串消息内容
						JSONObject jsonObj = JSONObject.fromObject(jsonReq);
						String fundPassword = jsonObj.getString("fundPassword");
						if (!StringUtils.isEmpty(fundPassword)) {
							fundPassword = DesUtils.deCodePwd(fundPassword, FinalConstants.DesKey.KEY1);// 银行发起的业务资金密码解密
							jsonObj.put("fundPassword", fundPassword);
						}
						jsonReq = functionId + jsonObj.toString();

					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("MsgDealtServiceImpl.sendMsgReq:json格式转化错误" + e.getMessage(), e);
			throw e;
		}
		return sendMessage(jsonReq, host, port);
	}

	public static String sendMessage(String msg, String host, int port) {
		LogFileUtils.getInstance().witeLogFile("T2服务到TAS/OTC发送的消息：",msg,1);
		
		System.out.println("ClientSocket.sendMsg 客户端准备发送消息:" + host + ":" + port + ":" + msg);
		Long t1 = System.currentTimeMillis();
		String respMsg = "";
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			Long t3 = System.currentTimeMillis();
			System.out.println("连接TAS/OTC时间差" + (t3 - t1));
			OutputStream out = socket.getOutputStream();
			InputStream in = socket.getInputStream();
			out.write(msg.getBytes("GBK"));
			out.flush();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			char chars[] = new char[3048];
			br.read(chars);
			String message = new String(chars);
			respMsg = new String(message);
			System.out.println("TAS/OTC回复的消息:" + respMsg);
			br.close();
			in.close();
			out.close();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		if (!StringUtils.isEmpty(respMsg)) {
			respMsg = respMsg.substring(6, respMsg.length()).trim();
		}
		Long t2 = System.currentTimeMillis();
		System.out.println("TAS/OTC回复的消息时间差：" + (t2 - t1) + "\n回复的数据\n" + respMsg);
		LogFileUtils.getInstance().witeLogFile("T2服务到TAS/OTC收到的消息：",respMsg,1);
		return respMsg;

	}

	public static void main(String[] args) {
		// "192.168.31.94 8989" "192.168.30.72", 3800

		String a = "900000{\"memberMainType\":\"2\",\"fullName\":\"苏国邦\",\"idKind\":\"111\",\"idNo\":\"410521199002250553\",\"initDate\":20160824,\"serialNo\":\"16082410000102000938\",\"exchangeId\":\"0200008\",\"exchangeFundAccount\":\"000400000314\",\"bankProCode\":\"pabankcg\",\"bankAccount\":\"6230580000077442767\",\"bankAccountName\":\"苏国邦\",\"fundPassword\":\"16DB6B7FE953BA02\",\"bankPassword\":\"111\",\"moneyType\":\"CNY\",\"signType\":\"1\",\"custSign\":\"\",\"outAcctIdBankName\":\"平安银行\"}";
		// String a = "111";
		// System.out.println(a);

		// ClientSocket.sendMessage(a, "192.168.31.94", 8989);
		ClientSocket.sendMsg(a);
		// JSONObject jsonObj = new JSONObject();
		// jsonObj.put("11", "54");

		// jsonObj.put("11", "66");
		// System.out.println(jsonObj.get("11"));

		// System.out.print(jsonObj.toString());

		/*
		 * String sg = DesUtils.deCodePwd("C7873361B16AF644",
		 * FinalConstants.DesKey.KEY1);//解密 try { //String sg =
		 * DesUtils.enCodePwd("111", FinalConstants.DesKey.KEY1);//加密
		 * System.out.println(sg); } catch (Exception e) {
		 * 
		 * }
		 */
		/*
		 * try { String jsonReq = ""; JSONObject jsonObj =
		 * JSONObject.fromObject(a); String bankPassword =
		 * jsonObj.getString("bankPassword"); if
		 * (!StringUtils.isEmpty(bankPassword)) { bankPassword =
		 * DesUtils.enCodePwd(bankPassword, FinalConstants.DesKey.KEY1);//
		 * 交易所发的业务银行密码加密 jsonObj.put("bankPassword", bankPassword); } jsonReq =
		 * jsonObj.toString(); System.out.println("加密后："+jsonReq); } catch
		 * (Exception e) {
		 * 
		 * 
		 * }
		 */

	}
}
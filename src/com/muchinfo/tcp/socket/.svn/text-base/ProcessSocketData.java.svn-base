package com.muchinfo.tcp.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.muchinfo.common.util.ContextUtil;
import com.muchinfo.common.util.LogFileUtils;
import com.muchinfo.tcp.service.MsgDealtService;

/**
 * Socket服务器端消息处理 ClassName: ProcessSocketData <br/>
 * date: 2016年7月7日 上午9:47:52 <br/>
 * 
 * @author zhou.yao
 * @version
 */
@Service
public class ProcessSocketData extends Thread {
	private static final Logger LOGGER = Logger.getLogger(ProcessSocketData.class);
	private Socket socket;
	private ServletContext servletContext;

	public ProcessSocketData() {
		super();
	}

	public ProcessSocketData(Socket socket, ServletContext servletContext) {
		this.socket = socket;
		this.servletContext = servletContext;
	}

	public void run() {
		InputStream in = null;
		BufferedReader br = null;
		PrintWriter out = null;
		try {
			MsgDealtService msgDealtService = (MsgDealtService) ContextUtil.getBean("msgDealtService");
			in = socket.getInputStream();
			br = new BufferedReader(new InputStreamReader(in,"GBK"));
			if (!StringUtils.isEmpty(br)) {
				char chars[] = new char[in.available()];
			    br.read(chars) ;
				String message = new String(chars);
				LOGGER.info("服务器收到的消息:\n" + message);
				out = new PrintWriter(socket.getOutputStream());
				if (!StringUtils.isEmpty(message)) {
					
					LogFileUtils.getInstance().witeLogFile("TAS/OTC到T2服务发送的消息：",message,0);
					
					// 执行自定义的请求解析方法，生成响应response
					String functionId = message.substring(0, 6);// 解析方法id
					LOGGER.info("functionId:" + functionId);
					String jsonReq = message.substring(6, message.length());// 解析json字符串消息内容
					LOGGER.info("发送交易所向广清所发起的业务消息:\n" + jsonReq);
					String respMsg = functionId+msgDealtService.sendMsgReq(functionId,jsonReq.trim());
					LOGGER.info("服务器(广清所)回复的消息:\n" +respMsg);
					
					LogFileUtils.getInstance().witeLogFile("TAS/OTC到T2服务收到的消息：",respMsg,0);
					
					out.println(respMsg);
					out.flush(); // 刷新缓冲区
				}
			}
		} catch (Exception e) {
			LOGGER.error("ProcessSocketData.run:" + e.getMessage(), e);
		} finally {
			if (out != null) {
				out.close();
			}
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					LOGGER.error("ProcessSocketData.run:" + e.getMessage(), e);
				}
			}
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					LOGGER.error("ProcessSocketData.run:" + e.getMessage(), e);
				}
			}
		}
	}
}
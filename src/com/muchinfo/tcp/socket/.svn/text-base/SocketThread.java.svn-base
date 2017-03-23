package com.muchinfo.tcp.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.muchinfo.common.util.PropertiesUtil;

/**
 * 自定义一个Socket线程类继承自线程类，重写run()方法，用于接收客户端socket请求报文
 */
public class SocketThread extends Thread {
	private static final Logger LOGGER = Logger.getLogger(SocketThread.class);
	private ServletContext servletContext;
	private ServerSocket serverSocket;

	public SocketThread(ServerSocket serverSocket, ServletContext servletContext) {
		this.servletContext = servletContext;
		// 从web.xml中context-param节点获取socket端口
		//String port = this.servletContext.getInitParameter("socketPort");
		String port = PropertiesUtil.getProperty("localhost.socket.port");
		LOGGER.info("\n#####################服务器socket端口号#####################"+port);
		if (serverSocket == null) {
			try {
				this.serverSocket = new ServerSocket(Integer.parseInt(port));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void run() {
		while (!this.isInterrupted()) { // 线程未中断执行循环
			try {
				Socket socket = serverSocket.accept();
				if (socket != null)
					LOGGER.info(socket.getInetAddress()+":"+socket.getLocalPort()+"连接服务器Socket成功");
					new ProcessSocketData(socket, this.servletContext).start();
			} catch (IOException e) {
				   LOGGER.info("服务器Socket连接失败");
				   LOGGER.error("SocketThread.run:" + e.getMessage(), e);
			}
		}
	}

	public void closeServerSocket() {
		try {
			if (serverSocket != null && !serverSocket.isClosed())
				serverSocket.close();
		} catch (IOException e) {
			LOGGER.info("服务器Socket关闭失败");
			LOGGER.error("SocketThread.closeServerSocket:" + e.getMessage(), e);
		}
	}
}
package com.muchinfo.tcp.socket;




import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
/**
 * socket监听器
 */
public class ServerSocketListener implements ServletContextListener {
	private static final Logger LOGGER = Logger.getLogger(ServerSocketListener.class);
	private SocketThread socketThread;

	public void contextDestroyed(ServletContextEvent e) {
		if (socketThread != null && socketThread.isInterrupted()) {
			socketThread.closeServerSocket();
			socketThread.interrupt();
		}
	}

	public void contextInitialized(ServletContextEvent e) {
		ServletContext servletContext = e.getServletContext();
		if (socketThread == null) {
			LOGGER.info("ServerSocketListener.contextInitialized: spring上下文初始化时启动socket服务端线程");
			socketThread = new SocketThread(null, servletContext);
			socketThread.start(); // servlet上下文初始化时启动socket服务端线程
		}
	}
}
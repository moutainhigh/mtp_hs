package com.muchinfo.common.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	private static Properties properties = new Properties();

	/**
	 * 读取properties配置文件信息
	 */
	static {
		try {
			properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据key得到value的值
	 */
	public static String getProperty(String key) {
		String vlaue = "";
		try {
			// properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties"));
			vlaue = properties.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vlaue;
	}
}
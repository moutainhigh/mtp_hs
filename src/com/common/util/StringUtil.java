package com.common.util;

import org.apache.commons.lang3.RandomStringUtils;

public class StringUtil {

	public static String getSn() {
		String sn = String.valueOf(System.currentTimeMillis()).substring(5);
		String extend = String.format("%04d", Integer.valueOf(RandomStringUtils.randomNumeric(4)));

		return sn + extend;
	}
	
	public static boolean isEmpty(Object str){
		return (str == null || "".equals(str));
	}
}

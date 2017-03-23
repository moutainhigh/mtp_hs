package com.muchinfo.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.muchinfo.common.util.PropertiesUtil;
import com.sun.org.apache.bcel.internal.classfile.Field;



public class Test {
public static void main(String[] args) {
	
	
	
	/*
	try {
		InputStream in = PropertiesUtil.class.getResourceAsStream("/config.properties");
		byte[] bytes = new  byte[2048];
		while (in.read()!= -1) {
			in.read(bytes);
			
		}
		in.close();
		
		
		System.out.println(new String(bytes));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	*/
	
	//String ms_tran_no = PropertiesUtil.getProperty("is.encrypt");
	//System.out.println("is.encrypt:"+ms_tran_no);
	

	try {
		File f = new File("E:\\T2服务系统日志\\Log\\交易所发起的业务11\\20160902_00版本.txt");
		f.createNewFile();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
}

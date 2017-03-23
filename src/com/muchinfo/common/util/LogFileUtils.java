package com.muchinfo.common.util;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * 将数据写入到文件中
 * @author zhou.yao
 *
 */
public class LogFileUtils {
	private static  LogFileUtils logFileUtils=null;
	public static LogFileUtils getInstance(){
		if(logFileUtils==null){
			logFileUtils=new LogFileUtils();
		}
		return logFileUtils;
	}
	String logMrdPath= null;
	String logFilePath=null;
	String sendData="发送的送据为:";
	String receiveData="接收的数据为:";
	private boolean isLoggable=true;
	
	public void setLogEnable(boolean isLoggable){
		this.isLoggable=isLoggable;
	}

	/**
	 * 0 TAS/OTC发起的业务 1广清所发起的业务
	 * @param type
	 * @return
	 */
	private File isHavingFile(int type){
		String sendType = "";
		if(0 == type ){
			sendType = "交易所发起的业务";
		}else if(1 == type){
			sendType = "广清所发起的业务";
			
		}
		logMrdPath="C:"+File.separator+"T2服务系统日志"+File.separator+"Log"+File.separator+sendType;
		File mrdFile=new File(logMrdPath);
		mrdFile.mkdirs();
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String dateStr=sdf.format(date)+"_00版本";
		logFilePath=logMrdPath+File.separator+dateStr+".txt";
		File file = new File(logFilePath);
		try {
		if(!file.exists()){
			file.createNewFile();
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}
	
	
	/**
	 * 普通日志文件
	 * @param message		功能说明
	 * @param writeData		 日志内容
	 * @param type			1  发送数据    2接收数据   3自定义内容
	 * 发送方类型
	 */
	public synchronized void witeLogFile(String message,String writeData,int type){
		if(isLoggable){
		File file=isHavingFile(type);
		FileWriter fw=null;
		BufferedWriter bw=null;
		Date date=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		try {
			fw = new FileWriter(file, true);
			bw =new BufferedWriter(fw);

				bw.newLine();
				date=new Date();
				String dateStr=sdf.format(date);
				bw.write("*****************************************");
				bw.newLine();
				if(message==null){
					message="";
				}
				bw.write(dateStr+" "+message);
				bw.newLine();
				if(writeData!=null){
					bw.write(writeData);
				}
				bw.newLine();
				bw.write("*****************************************");
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(bw!=null){
					bw.flush();
					bw.close();
				}
				if(fw!=null){
					fw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		   
	}
	}
	
	
	public static void main(String[] args) {
		LogFileUtils.getInstance().witeLogFile("TAS/OTC", "2222", 2);
	}

}

package com.core.util;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
/**
 * 读取配置文件的工具类
 * ClassName: PropertiesUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2016年6月13日 下午3:49:48 <br/>
 * @author yan.zhigang
 * @version
 */
public class PropertiesUtil {
	
	private final Logger LOGGER = Logger.getLogger(PropertiesUtil.class);
	
	private String[] location ;
	private static Properties properties = new Properties();
	
	/**
	 * spring初始化该类的时候执行该方法
	 * @author yan.zhigang
	 * @date 2016年6月13日 下午3:50:20 
	 * @param location
	 */
	public PropertiesUtil(String[] location){
		this.location = location;
		//加载所有的properties文件  （获取文件名字）
		loadProperties();
	}
	
	/**
	 * 加载所有的properties文件  （获取文件名字）
	 * loadProperties:(这里用一句话描述这个方法的作用). <br/>
	 * @author yan.zhigang
	 */
	public void loadProperties(){
		for(int i=0;i<location.length;i++){
          	loanSingelProperty(location[i]);		
		}
		
	}
	
	/**
	 * 加载单个properties文件  （获取文件名字），并读取文件里面的内容
	 * loanSingelProperty:(这里用一句话描述这个方法的作用). <br/>
	 * @author yan.zhigang
	 * @param path
	 */
	public void loanSingelProperty(String path){
		InputStream in = PropertiesUtil.class.getResourceAsStream(path);
		try {
			properties.load(in);
		} catch (IOException e) {
			LOGGER.error("PropertiesUtil.loanSingelProperty >>"+e.getMessage(),e);
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				in = null;
			} 
			
		}
		
	}
	
	/**
	 * 根据传入的配置文件中的  文件属性 key ，获取相应文件属性key的value值
	 * getProperty:(这里用一句话描述这个方法的作用). <br/>
	 * @author yan.zhigang
	 * @param key
	 * @return
	 */
	public  static String getProperty(String key){
		return properties.getProperty(key);
	}
	
	/**
	 * 查询所有以type开头的属性的值
	 * getPropertiesVlue:(这里用一句话描述这个方法的作用). <br/>
	 * @author yan.zhigang
	 * @param type
	 * @return
	 */
	public static List<String> getPropertiesVlue(String type){
		List<String> keys = new ArrayList<String>();
		Set<Entry<Object,Object>> set = properties.entrySet();
		for(Entry entry : set){
			String value = (String)entry.getValue();
			if(value.startsWith(type)){
				keys.add(value);
			}
		}
		return keys;
	}
}

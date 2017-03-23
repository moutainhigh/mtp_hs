package com.common.util;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.core.util.PropertiesUtil;



public class CheckPropertyUtil {
	
	private static  final Log LOGGER = LogFactory.getLog(CheckPropertyUtil.class);
	
	public static String propertyCheck(Map<String,Object> map,String msgType){
		String returnResult = null;
		Object fieldValue = null;
		Integer errorType = null;
		List<String[]> list = getSplitList(msgType);
		for(String[] strs : list){
			String fieldType = strs[1].trim();
			String fieldName = formatCon(strs[2].trim());
			String fieldLength = strs[3].trim();
			fieldValue = map.get(fieldName.trim());
			
			if(StringUtils.isEmpty(fieldValue)){
				returnResult = "#非法格式"+fieldName+":必填项";
				break;
			}
			
			errorType = judgeType(fieldType, fieldValue);
			if(null == errorType){
				errorType = checkLegal(fieldType,fieldLength,fieldValue);
			}
			if(null != errorType){
				if(1 == errorType || 2 == errorType || 3 == errorType){
					returnResult = "#非法格式"+fieldName+"->"+fieldValue+":字段的长度超出了最大长度限制"+fieldLength;
					break;
				} else if(4 == errorType || 5 == errorType){
					returnResult = "#非法格式"+fieldName+"->"+fieldValue+":字段的类型错误";
					break;
				} 
			}
		}
		return returnResult ;
	}
	
	/**
	 * 判断fieldValue类型的合法性
	 * dudgeType:(这里用一句话描述这个方法的作用). <br/>
	 * @author yan.zhigang
	 * @param fieldType
	 * @param fieldValue
	 * @return
	 */
	private static Integer judgeType(String fieldType,Object fieldValue){
		Integer errorType = null;
		if("string".equals(fieldType)){
		} else if("double".equals(fieldType)){
			if(!convDouble(fieldValue)){
				errorType = 4;
			} 
		} else if("int32".equals(fieldType)){
			if(!convInt(fieldValue)){
				errorType = 5;
			} 
		}
		return errorType;
	}
	
	/**
	 * 分割所有的字符串并放入到List中
	 * getSplitList:(这里用一句话描述这个方法的作用). <br/>
	 * @author yan.zhigang
	 * @param msgType
	 * @return
	 */
	private static List<String[]> getSplitList(String msgType){
		List<String[]> lists = new ArrayList<String[]>();
		 List<String> keyList = PropertiesUtil.getPropertiesVlue(msgType);
		 for(String key : keyList){
			 lists.add(key.split("&"));
		 }
		 return lists;
	}
	
	/**
	 * 判断fieldValue长度的合法性
	 * checkLegal:(这里用一句话描述这个方法的作用). <br/>
	 * @author yan.zhigang
	 * @param fieldType
	 * @param fieldLength
	 * @param fieldValue
	 * @return
	 */
	private static Integer checkLegal(String fieldType,String fieldLength,Object fieldValue){
		Integer errorType = null;
		if("string".equals(fieldType)){
			if(String.valueOf(fieldValue).toString().length() > Integer.valueOf(fieldLength)){
				//长度不合理
				errorType = 1;
			}
		}else if("double".equals(fieldType)){
			if(new BigDecimal(String.valueOf(fieldValue)).toString().length() > Integer.valueOf(fieldLength)){
				//长度不合理
				errorType = 2;
			}
		}else if("int32".equals(fieldType)){
			if(String.valueOf(fieldValue).toString().length() > Integer.valueOf(fieldLength)){
				//长度不合理
				errorType = 3;
			}
		}
		return errorType;
	}
	
	/**
	 * 字符串转double
	 * convDouble:(这里用一句话描述这个方法的作用). <br/>
	 * @author yan.zhigang
	 * @param fieldValue
	 * @return
	 */
	private static boolean convDouble(Object fieldValue){
		boolean flag = true;
		try{
			fieldValue = (Double)fieldValue;
		}catch(Exception e){
			flag = false;
			LOGGER.error("CheckPropertyUtil.convDouble:"+e.getMessage(),e);
		}
		return flag;
	}
	
	/**
	 * 字符串转int
	 * convInt:(判断是否是整形数字). <br/>
	 * @author yan.zhigang
	 * @param fieldValue
	 * @return
	 */
	private static boolean convInt(Object fieldValue){
		String str = String.valueOf(fieldValue);
		boolean regex = str.matches("[0-9]+$");
		return regex;
		/*
		boolean flag = true;
		try{
			fieldValue = (Integer)fieldValue;
		}catch(Exception e){
			flag = false;
			LOGGER.error("CheckPropertyUtil.convInt:"+e.getMessage(),e);
		}
		return flag;
	*/}
		
	/**
	 * 属性名格式转换
	 * formatCon:(这里用一句话描述这个方法的作用). <br/>
	 * @author yan.zhigang
	 * @param fieldName
	 * @return
	 */
	private static String formatCon(String fieldName){
		String field = null;
		if(fieldName.contains("_")){
			String[] fields = fieldName.split("_");
			int fieldLength = fields.length;
			field = fields[0];
			for(int i = 1;i < fieldLength;i++){
				field = field + fields[i].substring(0,1).toUpperCase() + fields[i].substring(1);
			}
		}else{
			field = fieldName;
		}
		return field;
	}
	
}

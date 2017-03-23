//package com.muchinfo.mtp.common.util;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import com.muchinfo.mtp.bankservice.model.AmtRec;
//
//public class AssignmentUtil {
//
//	private static  final Log LOGGER = LogFactory.getLog(AssignmentUtil.class);
//	/**
//	 * 把propertyValue赋给javabean对象的fieldName属性
//	 * assignment:(这里用一句话描述这个方法的作用). <br/>
//	 * @author yan.zhigang
//	 * @param javabean
//	 * @param fieldName
//	 * @param propertyValue
//	 */
//	private static void assignment(Object javabean,String fieldName,String propertyValue){
//		Class clazz = javabean.getClass();
//		try {
//			//获取javabean对象中fieldName属性的类型
//			Field field = clazz.getDeclaredField(fieldName);
//			if(field.getType().getSimpleName().toString().equals("String")){
//				//给javabean对象中fieldName属性赋值
//				assignment(javabean,fieldName,field,propertyValue);
//			}
//			if(field.getType().getSimpleName().toString().equals("Long")){
//				Long value = Long.valueOf(propertyValue);
//				assignment(javabean,fieldName,field,value);			
//			}
//			if(field.getType().getSimpleName().toString().equals("Integer")){
//				Integer value = Integer.valueOf(propertyValue);
//				assignment(javabean,fieldName,field,value);
//			}
//			if(field.getType().getSimpleName().toString().equals("BigDecimal")){
//				BigDecimal value = new BigDecimal(propertyValue);
//				assignment(javabean,fieldName,field,value);
//			}
//			if(field.getType().getSimpleName().toString().equals("Date")){
//				Date value = string2Date(propertyValue);
//				assignment(javabean,fieldName,field,value);
//			}
//		} catch (Exception e) {
//			LOGGER.error("AssignmentUtil.assignment:"+e.getMessage(),e);
//		}
//	}
//	
//	/**
//	 * 字符串转日期
//	 * string2Date:(这里用一句话描述这个方法的作用). <br/>
//	 * @author yan.zhigang
//	 * @param propertyValue
//	 * @return
//	 */
//	private static Date string2Date(String propertyValue){
//		Date date = null;
//		try{
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
//			date = sdf.parse(propertyValue);
//		}catch(Exception e){
//			LOGGER.error("AssignmentUtil.string2Date:"+e.getMessage(),e);
//		}
//		return date;
//		
//	}
//	
//	/**
//	 * 给属性赋值
//	 * assignment:(这里用一句话描述这个方法的作用). <br/>
//	 * @author yan.zhigang
//	 * @param javabean
//	 * @param fieldName
//	 * @param field
//	 * @param value
//	 */
//	private static void assignment(Object javabean,String fieldName,Field field,Object value){
//		try{
//			Class clazz = javabean.getClass();
//			String setMethod = pareSetName(fieldName);
//			Method method = clazz.getMethod(setMethod, field.getType()); 
//			method.invoke(javabean, value);  
//			
//		}catch(Exception e){
//			LOGGER.error("AssignmentUtil.assignment:"+e.getMessage(),e);
//		}
//		 
//	}
//	
//	/** 
//	 * 拼接某属性set 方法  
//	 * pareSetName:(这里用一句话描述这个方法的作用). <br/>
//	 * @author yan.zhigang
//	 * @param fldname
//	 * @return
//	 */
//    public static String pareSetName(String fldname){  
//        if(null == fldname || "".equals(fldname)){  
//            return null;  
//        }  
//        String pro = "set"+fldname.substring(0,1).toUpperCase()+fldname.substring(1);  
//        return pro;  
//    } 
//    
//    public static void main(String args[]){
//    	AmtRec amtRec = new AmtRec();
//    	//1.String,Integer,bigdecimal,long
//    	
//		assignment(amtRec,"sysTime","2016-06-26");
////		System.out.println(amtRec.getAcct());
//		System.out.println(amtRec.getSysTime());
//		
//    }
//}

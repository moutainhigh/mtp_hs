//package com.muchinfo.mtp.common.util;
//
//import java.beans.PropertyDescriptor;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.util.StringUtils;
//
//import com.google.protobuf.InvalidProtocolBufferException;
//import com.muchinfo.mtp.bankservice.model.AmtRec;
//import com.muchinfo.mtp.proto.ExchangeCenter.MsgEF01;
//
//
//public class PorpertyCopyUtil {
//	
//	private static  final Log LOGGER = LogFactory.getLog(PorpertyCopyUtil.class);
//	
//	/**
//	 * 属性拷贝
//	 * PropertyCopy:(这里用一句话描述这个方法的作用). <br/>
//	 * @author yan.zhigang
//	 * @param javabean javabean
//	 * @param struct 结构体
//	 */
//	public static void propertyCopy(Object struct,Object javabean){
//		try{
//			Map<String,String> beanMap  = new ConcurrentHashMap<String,String>();
//			Map<String,String> structMap  = new ConcurrentHashMap<String,String>();
//			//获取javabean中所有的get方法
//			beanMap = getBeanMethod(javabean);
//			//获取结构体中所有的get方法
//			structMap = getStructGetMethod(struct);
//			for(String getMethod : beanMap.keySet())
//			{
//				if(structMap.containsKey(getMethod)){
//					//取结构体中的值
//					String fieldName = getMethod2FieldName(getMethod);
//					String propertyValue = getReadMethodValue(struct,fieldName);
//					if (!StringUtils.isEmpty(propertyValue)){
//						assignment(javabean,fieldName, propertyValue);
//					}
//				}
//			}
//		}catch(Exception e){
//			LOGGER.error("PorpertyCopyUtil.propertyCopy:"+e.getMessage(),e);
//		}
//		
//	}
//	
//	public static void main(String args[]){
//		AmtRec amtRec = new AmtRec();
//		amtRec.setAcct("sss");
////		assignment(amtRec,"exchDate","123");
////		string2Date("2016-03-26");
//		byte[] body = "sdf".getBytes();
//		try {
//			MsgEF01.Builder exchOutReq = MsgEF01.newBuilder();
//			exchOutReq.setAcctName("zhangsan");
//			
//			MsgEF01 exchOut = MsgEF01.newBuilder().mergeFrom(exchOutReq.build().toByteArray()).build();
//			exchOutReq.getAcctName();
////			
//			getReadMethodValue(exchOut,"acctName");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		getStructGetMethod(exchOutReq);
////		getBeanMethod(amtRec);
////		getReadMethodValue(amtRec,"acct");
////		getMethod2FieldName("getAcct");
//	}
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
//			LOGGER.error("PorpertyCopyUtil.assignment:"+e.getMessage(),e);
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
//			LOGGER.error("PorpertyCopyUtil.string2Date:"+e.getMessage(),e);
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
//			LOGGER.error("PorpertyCopyUtil.assignment:"+e.getMessage(),e);
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
//	/**
//	 * 获取javabean中所有的get方法
//	 * getBeanMethod:(这里用一句话描述这个方法的作用). <br/>
//	 * @author yan.zhigang
//	 * @param javabean
//	 * @return
//	 */
//	private static Map<String,String> getBeanMethod(Object javabean)
//	{
//		Map<String,String> beanMap  = new ConcurrentHashMap<String,String>();
//		try{
//			Class clazz = javabean.getClass();
//			Field[] fields = javabean.getClass().getDeclaredFields();//获取属性
//			for(Field field : fields)
//			{
//				PropertyDescriptor pd = new PropertyDescriptor(field.getName(),clazz);
//				Method getMethod = pd.getReadMethod();
//				String getMethodName = getMethod.getName();
//				beanMap.put(getMethodName, getMethodName);
//			}
//		}catch(Exception e){
//			LOGGER.error("PorpertyCopyUtil.getBeanMethod:"+e.getMessage(),e);
//		}
//		return beanMap;
//	}
//	
//	/**
//	 * 获取structObject中所有的get方法
//	 * getStructGetMethod:(这里用一句话描述这个方法的作用). <br/>
//	 * @author yan.zhigang
//	 * @param structObject
//	 * @return
//	 */
//	private static Map<String,String> getStructGetMethod(Object structObject)
//	{
//		Map<String,String> beanMap  = new ConcurrentHashMap<String,String>();
//		try{
//			Class clazz = structObject.getClass();
//			Field[] fields = structObject.getClass().getDeclaredFields();//获取属性
//			for(Field field : fields)
//			{
//				String filedName = field.getName();
//				int leng = filedName.length();
//				if(filedName.endsWith("_")){
//					beanMap.put("get"+filedName.substring(0,1).toUpperCase()+filedName.substring(1,leng-1),"get"+filedName.substring(0,1).toUpperCase()+filedName.substring(1,leng-1));
//				}
//			}
//		}catch(Exception e){
//			LOGGER.error("PorpertyCopyUtil.getBeanMethod:"+e.getMessage(),e);
//		}
//		return beanMap;
//	}
//	
//	/**
//	 * 获取某个属性的值
//	 * getReadMethodValue:(这里用一句话描述这个方法的作用). <br/>
//	 * @author yan.zhigang
//	 * @param struct
//	 * @param fieldName 属性名
//	 * @return
//	 */
//	private static String getReadMethodValue(Object struct,String fieldName){
//		String o = null;
//		try{
//			Class clazz = struct.getClass();
//			PropertyDescriptor pd = new PropertyDescriptor(fieldName,clazz);
//			Method getMethod = pd.getReadMethod();//获取get方法
//			if(pd != null){
//				o = (String)getMethod.invoke(struct);//执行get方法返回一个Object
//			}
//		}catch(Exception e){
//			LOGGER.error("PorpertyCopyUtil.getReadMethodValue:"+e.getMessage(),e);
//		}
//		return o;
//	}
//	
//	/**
//	 * 通过get方法名获取属性名
//	 * getMethod2FieldName:(这里用一句话描述这个方法的作用). <br/>
//	 * @author yan.zhigang
//	 * @param getMethodName
//	 */
//	private static String getMethod2FieldName(String getMethodName){
//		String fieldName = null;
//		try{
//			getMethodName = getMethodName.substring(3,getMethodName.length());
//			String firstChar = getMethodName.substring(0,1);
//			firstChar = firstChar.toLowerCase();
//			getMethodName = firstChar + getMethodName.substring(1,getMethodName.length());
//			fieldName = getMethodName;
//		}catch(Exception e){
//			LOGGER.error("PorpertyCopyUtil.getMethod2FieldName:"+e.getMessage(),e);
//		}
//		return fieldName;
//	}
//		
//}

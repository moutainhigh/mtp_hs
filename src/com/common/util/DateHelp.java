package com.common.util;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateHelp {

    public static Date getDate(String time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getDateTime(String dateTime) {
        return getDate(dateTime, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date toDate(String dateTime) {
        return getDate(dateTime, "yyyyMMdd");
    }
    
    public static String getCurrentDateTimeOfString() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
    
    public static String getCurrentDateTimeOfStringHS() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }
    
    public static String getCurrentDateTimeOfString(String pattern){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
    
    public static String getCurrentDateOfString(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }
    
    public static Integer getCurrentWeekDay(){
        Calendar cal = Calendar.getInstance();
        Integer day = cal.get(Calendar.DAY_OF_WEEK);
        return --day;
    }

    public static String toString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
    
    public static String toDateString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }
    public static String toTimeString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        return sdf.format(date);
    }
    
    public static String getCurrdayIncre(String dateStr) {
    	String day="";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date date = sdf.parse(dateStr);
			Calendar c=new GregorianCalendar();
			c.setTime(date);
			c.add(Calendar.DAY_OF_MONTH, 1);
			date=c.getTime();
			day=sdf.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return day;
    }

    public static String clientDateToString(String dateStr) throws Exception {
    	String result = "20160927";

    	if(dateStr==null || dateStr.length()==0) return result;
    	
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date = sdf.parse(dateStr);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		result = sdf1.format(date);

        return result;
    }

    public static boolean getIsWeekday(String date){
    	boolean bool=false;
    	try{
    		DateFormat format=new SimpleDateFormat("yyyyMMdd");
    		Date bdate=format.parse(date);
    		Calendar c=Calendar.getInstance();
    		c.setTime(bdate);
    		if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
    			bool=true;
    		}
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return bool;
    }
    
    public static void main(String[] args) {
//        System.out.println(getCurrentDateTimeOfString("HH:mm"));
//        System.out.println(getCurrentWeekDay());
//        System.out.println(getCurrentDateOfString());
    	String dateStr ="2016/9/20 0:00:00";
    	
    	try {
			System.out.println( DateHelp.clientDateToString(dateStr) );
			System.out.println(getIsWeekday(DateHelp.getCurrentDateOfString()));
			System.out.println(getCurrdayIncre("20170228"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}

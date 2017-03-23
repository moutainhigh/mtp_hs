package com.common.util;
import java.util.HashMap;
import java.util.Map;

public class CenterToExchUtil {

	
	//交易所转广清
	public final static Map<String, String> HS_CERT_TYPE = new HashMap<String, String>();
	static{
		HS_CERT_TYPE.put("0","111");
		HS_CERT_TYPE.put("1","513");
		HS_CERT_TYPE.put("2","I02");
		HS_CERT_TYPE.put("3","990");
		HS_CERT_TYPE.put("4","I01");
		HS_CERT_TYPE.put("5","114");
		HS_CERT_TYPE.put("6","119");
		HS_CERT_TYPE.put("7","115");
		HS_CERT_TYPE.put("8","I04");
		HS_CERT_TYPE.put("9","113");
		HS_CERT_TYPE.put("10","112");
		HS_CERT_TYPE.put("11","I06");
		HS_CERT_TYPE.put("12","414");
		HS_CERT_TYPE.put("13","119");
		HS_CERT_TYPE.put("14","I04");
		HS_CERT_TYPE.put("15","990");
		HS_CERT_TYPE.put("16","113");
		HS_CERT_TYPE.put("17","213");
		HS_CERT_TYPE.put("18","I01");
		HS_CERT_TYPE.put("19","511");
		HS_CERT_TYPE.put("20","990");
		HS_CERT_TYPE.put("21","I10");
	}
	
	//广清转交易所
	public final static Map<String, String> SH_CERT_TYPE = new HashMap<String, String>();
	static{
		SH_CERT_TYPE.put("111","0");
		SH_CERT_TYPE.put("513","1");
		SH_CERT_TYPE.put("I02","2");
		SH_CERT_TYPE.put("990","3");
		SH_CERT_TYPE.put("I01","4");
		SH_CERT_TYPE.put("114","5");
		SH_CERT_TYPE.put("119","6");
		SH_CERT_TYPE.put("115","7");
		SH_CERT_TYPE.put("I04","8");
		SH_CERT_TYPE.put("113","9");
		SH_CERT_TYPE.put("112","10");
		SH_CERT_TYPE.put("I06","11");
		SH_CERT_TYPE.put("414","12");
		SH_CERT_TYPE.put("119","13");
		SH_CERT_TYPE.put("I04","14");
		SH_CERT_TYPE.put("990","15");
		SH_CERT_TYPE.put("113","16");
		SH_CERT_TYPE.put("213","17");
		SH_CERT_TYPE.put("I01","18");
		SH_CERT_TYPE.put("511","19");
		SH_CERT_TYPE.put("990","20");
		SH_CERT_TYPE.put("I10","21");
	}
//	Male	0	男性
//	Female	1	女性
	
	public final static Map<String, String> HS_SEX = new HashMap<String, String>();
	static{
		HS_SEX.put("0","2");
		HS_SEX.put("1","1");
		HS_SEX.put("2","0");
	}
	

	
	
	public final static Map<String, String> HS_CURRENCY = new HashMap<String, String>();
	static{
		HS_CURRENCY.put("RMB","CNY");
		HS_CURRENCY.put("USD","USD");
	}
	
//	'ccbsmt'		建设银行商贸通
//	'bocnet'		中国银行银企对接
//	'cibbce'		兴业银行银商三方存管
//	'abcyst'		农业银行银商通二期
//	'citic'		中信银行大宗资管
//	'bocspm'		交通银行银现转帐
//	'spdbcg'		浦发银行存管
//	'cmbyst'		招商银行银商转帐
//	'gzcb'		广州银行
//	'icbcyz'		工商银行银证转账
//	'pabankcg'		平安银行存管
//	'cgbcg'		广发银行存管
//	'cmsbsmt'		民生银行银商
//	'cmsbsct'		民生银行市场通
//	'abcec'		农行电子商务
	
	
//	pacggw	平安公网
//	pacgzx	平安专线
//	mssct	民生市场通
//	zxzjjg	中信交易资金监管系统

	public final static Map<String, String> HS_TRAN = new HashMap<String, String>();
	static{
		HS_TRAN.put("mssct", "cmsbsct");
	}

}

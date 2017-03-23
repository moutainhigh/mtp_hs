package com.service.impl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccps.exchgate.api.t2.service.client.ExchCallClearT2Service;
import com.common.util.DateHelp;
import com.core.exception.ServiceException;
import com.dao.ControlDateMapper;
import com.dao.ExchMapper;
import com.model.ControlDate;
import com.model.Exch;
import com.muchinfo.common.util.PropertiesUtil;
import com.service.OpenAndCloseTradeService;

import net.sf.json.JSONObject;

/**
 *  
 * ClassName: OpenAndCloseTradeServiceImpl.java
 * date: 2016年12月17日下午3:17:59
 * @author yu.jian
 * @version
 */
@Service
@Transactional
public class OpenAndCloseTradeServiceImpl implements OpenAndCloseTradeService {
	private static final Logger LOGGER = Logger.getLogger(OpenAndCloseTradeServiceImpl.class);
	/**
	 *  
	 * ClassName: OpenAndCloseTradeServiceImpl.java
	 * date: 2016年12月17日下午3:17:59
	 * @author yu.jian
	 * @version
	 */
	@Autowired
	private ExchMapper exchMapper;
	@Autowired
	private ControlDateMapper controlDateMapper;
	@Autowired
	private ExchCallClearT2Service exchCallClearT2Service;
	@Override
	public void dealTrade(int exchangeFlag) throws Exception {
		try{
//			业务日期(YYYYMMDD)	initDate
//			交易所代码	exchangeId
//			交易所子市场编码	exchangeMarketType
//			交易市场业务类型	bizType
//			开闭市标志（0闭市 1开市,参见数据字典2）	exchangeFlag
//			下一工作日	nextWorkday
//			操作时间(yyyyMMddHHmmss)	busiDatetime
			String hsExchNo=PropertiesUtil.getProperty("HSexchNO");
			Exch exch = exchMapper.selectByStatus(0).get(0);
			JSONObject json = new JSONObject();
			
			String date=DateHelp.getCurrentDateOfString();//业务当天日期
			boolean isWeekday=DateHelp.getIsWeekday(date);//业务当天是否是周末
			//开市日期控制表  数据只有周末开市的   工作日不开市的     
			//开始的情况：1.是周末能在表里查到开市的   2.是工作日在表里查不到开始的
			if(isWeekday && openTrade(date) || !isWeekday && dayOpenTrade(date))
			{
				String nextDate = date;//下一个开市日期
				boolean flag = true;
				while(flag)
				{
					nextDate = DateHelp.getCurrdayIncre(nextDate);
					boolean isWeekday2=DateHelp.getIsWeekday(nextDate);
					//判断这个日期是否开市
					if(isWeekday2 && openTrade(nextDate) || !isWeekday2 && dayOpenTrade(nextDate))
					{
						//如果开市
						flag=false;
					}else{//不开市
						/*nextDate=DateHelp.getCurrdayIncre(nextDate);*/
						flag=true;
						/*nextDate = nextDate+1;*/
					}
				}
				
				//开市---------------------------------
				json.put("initDate", date);
				json.put("exchangeId", hsExchNo);
				json.put("bizType", 1);      //1、不区分 2、大宗商品 3、金融资产 4、文化产权 5、邮币卡
				json.put("exchangeFlag", exchangeFlag);    //0闭市 1开市
				json.put("nextWorkday", nextDate);     //下一个工作日？？？？？
				json.put("busiDatetime", DateHelp.getCurrentDateTimeOfStringHS());//操作时间
				String result = exchCallClearT2Service.dealMSG399000(json.toString());
				JSONObject jsonObj = JSONObject.fromObject(result);
				int errorNo = jsonObj.getInt("errorNo");
				String errorInfo = jsonObj.getString("errorInfo");
				
				if(errorNo==0){
					LOGGER.info("*开闭市通知成功*");
					exch.setOpenCloseFlag(exchangeFlag);
					exchMapper.updateByPrimaryKeySelective(exch);
				}
				else if(errorNo==99)
					LOGGER.info("*请求正在审核*");
				else
					LOGGER.info("*开闭市通知失败*"+errorInfo);
				//开市---------------------------------
				
			}else{
				//不开市，打印日志
				LOGGER.info("*不开市*");
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	//周末开市（查到的开市）
	public boolean openTrade(String date){
		ControlDate controlDate=controlDateMapper.selectByPrimaryKey(date);
		if( controlDate!=null && controlDate.getIsOpen()==1 ){
			return true ;
		}
		return false;
	}
	
	//工作日开市（查不到的开市）
	public boolean dayOpenTrade(String date){
		ControlDate controlDate=controlDateMapper.selectByPrimaryKey(date);
		if( controlDate==null){
			return true ;
		}
		return false;
	}
	
}

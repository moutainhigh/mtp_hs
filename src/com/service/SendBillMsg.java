package com.service;

import com.proto.CenterBank.Msg17102;
import com.proto.CenterBank.Msg17202;
import com.proto.CenterBank.Msg17302;
import com.proto.CenterBank.Msg17402;
import com.proto.CenterBank.Msg17502;
import com.proto.CenterBank.Msg17602;
import com.proto.CenterBank.Msg17702;
import com.proto.CenterBank.Msg17802;

public interface SendBillMsg {
	public void doMsg17102(Msg17102 msg17102, Long recMsgId) throws Exception;
	
	public void doMsg17202(Msg17202 msg17202, Long recMsgId) throws Exception;
	
	public void doMsg17302(Msg17302 msg17302, Long recMsgId) throws Exception;
	
	public void doMsg17402(Msg17402 msg17402, Long recMsgId) throws Exception;
	
	public void doMsg17502(Msg17502 msg17502, Long recMsgId) throws Exception;
	
	public void doMsg17602(Msg17602 msg17602, Long recMsgId) throws Exception;
	
	public void doMsg17702(Msg17702 msg171702, Long recMsgId) throws Exception;
	
	public void doMsg17802(Msg17802 msg17802, Long recMsgId) throws Exception;
}

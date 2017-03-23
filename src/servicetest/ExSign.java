package servicetest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.common.util.TASProtoHelper;
import com.proto.CenterBank.Msg10302;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *  
 * ClassName: ExSign.java
 * date: 2017年1月10日上午10:31:36
 * @author yu.jian
 * @version
 */
public class ExSign {

	/**
	 * @param args
	 */
	private static final String EXCHANGE_NAME = "entry";  
	
	public static void main(String[] args) throws IOException, TimeoutException {
		// 创建连接和频道
        ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost("192.168.21.28");
        factory.setPort(5610);
        factory.setVirtualHost("test");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();  
        Channel channel = connection.createChannel(); 
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        Msg10302.Builder msg10302 = Msg10302.newBuilder();
       
      //------------------开始编辑------------------------------
        msg10302.setTranNo("pacgzx");
        msg10302.setBankDate("20170106");
        msg10302.setCenterSeq("201701120002");
        msg10302.setExchNo("4110014");
        msg10302.setTradeAcct("201701110002");
        msg10302.setCurrency("RMB");
        msg10302.setCardBankNo("307584007998");
        msg10302.setCardAcct("6214837829357379");
        msg10302.setCardName("於建");
        msg10302.setAcctType("1");
        msg10302.setCertType("0");
        msg10302.setCertCode("421127199407071739");
        msg10302.setClientName("test1");
        msg10302.setChangeType(1);
        msg10302.setIsForce(0);
      //------------------编辑结束------------------------------
        byte[] bytes = TASProtoHelper.getNTAS(msg10302.build().toByteArray(), 10302, 0);
        channel.basicPublish(EXCHANGE_NAME, "queue_110", null, bytes);
        channel.close();
        connection.close();
//		System.out.println(DateHelp.getCurrentDateTimeOfStringHS());
	}

}

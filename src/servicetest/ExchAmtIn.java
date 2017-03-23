package servicetest;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.common.util.TASProtoHelper;
import com.proto.CenterBank.Msg10102;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 *  交易所入金Test
 * ClassName: ExchAmtIn.java
 * date: 2016年9月8日下午3:13:49
 * @author yu.jian
 * @version
 */
public class ExchAmtIn {
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

        Msg10102.Builder msg10102 = Msg10102.newBuilder();
       
      //------------------开始编辑------------------------------
        msg10102.setTranNo("pacgzx");
        msg10102.setBankDate("20170113");
        msg10102.setCenterSeq("201701130003");
        msg10102.setExchNo("4110014");
        msg10102.setTradeAcct("201701110002");
        msg10102.setAcct("6214837829357379");
        msg10102.setAcctName("於建");
        msg10102.setAmt(100.00);
        msg10102.setCurrency("RMB");
        msg10102.setCardAcct("6214837829357379");
        msg10102.setCardName("於建");
        
      //------------------编辑结束------------------------------
        byte[] bytes = TASProtoHelper.getNTAS(msg10102.build().toByteArray(), 10102, 0);
        channel.basicPublish(EXCHANGE_NAME, "queue_110", null, bytes);
        channel.close();
        connection.close();
	}
}

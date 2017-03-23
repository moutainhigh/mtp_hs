package servicetest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.common.util.TASProtoHelper;
import com.proto.CenterBank.Msg10202;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *  
 * ClassName: ExAmtOut.java
 * date: 2017年1月10日上午10:33:29
 * @author yu.jian
 * @version
 */
public class ExAmtOut {

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

        Msg10202.Builder msg10202 = Msg10202.newBuilder();
       
      //------------------开始编辑------------------------------
        msg10202.setTranNo("pacgzx");
        msg10202.setBankDate("20170106");
        msg10202.setCenterSeq("201701130004");
        msg10202.setExchNo("4110014");
        msg10202.setTradeAcct("201701110002");
        msg10202.setAcct("6214837829357379");
        msg10202.setAcctName("於建");
        msg10202.setAmt(100.00);
        msg10202.setCurrency("RMB");
        msg10202.setCardAcct("6214837829357379");
        msg10202.setCardName("於建");
        
      //------------------编辑结束------------------------------
        byte[] bytes = TASProtoHelper.getNTAS(msg10202.build().toByteArray(), 10202, 0);
        channel.basicPublish(EXCHANGE_NAME, "queue_110", null, bytes);
        channel.close();
        connection.close();
	}
}

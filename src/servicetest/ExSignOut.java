package servicetest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.common.util.TASProtoHelper;
import com.proto.CenterBank.Msg10402;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *  
 * ClassName: ExSignOut.java
 * date: 2017年1月10日上午10:32:19
 * @author yu.jian
 * @version
 */
public class ExSignOut {

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

        Msg10402.Builder msg10402 = Msg10402.newBuilder();
       
      //------------------开始编辑------------------------------
        msg10402.setTranNo("pacgzx");
        msg10402.setBankDate("20170106");
        msg10402.setCenterSeq("201701120002");
        msg10402.setExchNo("4110014");
        msg10402.setTradeAcct("201701110002");
        msg10402.setAcct("6214837829357379");
        msg10402.setAcctName("於建");
        msg10402.setCurrency("RMB");
      //------------------编辑结束------------------------------
        byte[] bytes = TASProtoHelper.getNTAS(msg10402.build().toByteArray(), 10402, 0);
        channel.basicPublish(EXCHANGE_NAME, "queue_110", null, bytes);
        channel.close();
        connection.close();
//		System.out.println(DateHelp.getCurrentDateTimeOfStringHS());
	}

}

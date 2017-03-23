package servicetest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.common.util.TASProtoHelper;
import com.proto.CenterBank.Msg10802;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class DestroyClient {
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

        Msg10802.Builder msg10802 = Msg10802.newBuilder();
       
      //------------------开始编辑------------------------------
        msg10802.setTranNo("pacgzx");
        msg10802.setBankDate("20170206");
        msg10802.setCenterSeq("201702060002");
        msg10802.setExchNo("4110014");
        msg10802.setTradeAcct("201701110002");
      //------------------编辑结束------------------------------
        byte[] bytes = TASProtoHelper.getNTAS(msg10802.build().toByteArray(), 10802, 0);
        channel.basicPublish(EXCHANGE_NAME, "queue_110", null, bytes);
        channel.close();
        connection.close();
	}
}

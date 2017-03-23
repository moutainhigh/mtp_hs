package servicetest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.common.util.TASProtoHelper;
import com.proto.CenterBank.Msg30601;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RegisterOut {

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

        Msg30601.Builder msg30601 = Msg30601.newBuilder();
       
      //------------------开始编辑------------------------------
        msg30601.setTranNo("pacgzx");
        msg30601.setBankDate("20170106");
        msg30601.setCenterSeq("201701160001");
        msg30601.setExchNo("4110014");
      //------------------编辑结束------------------------------
        byte[] bytes = TASProtoHelper.getNTAS(msg30601.build().toByteArray(), 30601, 0);
        channel.basicPublish(EXCHANGE_NAME, "queue_110", null, bytes);
        channel.close();
        connection.close();
	}
}

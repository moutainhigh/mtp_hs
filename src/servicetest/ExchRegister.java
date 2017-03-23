 package servicetest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.common.util.TASProtoHelper;
import com.proto.CenterBank.Msg30501;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ExchRegister {
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

        Msg30501.Builder msg30501 = Msg30501.newBuilder();
       
      //------------------开始编辑------------------------------
        msg30501.setTranNo("pacgzx");
        msg30501.setBankDate("20170106");
        msg30501.setCenterSeq("201701120001");
        msg30501.setExchNo("4110014");
      //------------------编辑结束------------------------------
        byte[] bytes = TASProtoHelper.getNTAS(msg30501.build().toByteArray(), 30501, 0);
        channel.basicPublish(EXCHANGE_NAME, "queue_110", null, bytes);
        channel.close();
        connection.close();
	}
}

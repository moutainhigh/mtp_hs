package servicetest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.common.util.TASProtoHelper;
import com.proto.CenterBank.Msg20204;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class BankAmtOut {
	
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

        Msg20204.Builder msg20204 = Msg20204.newBuilder();
       
      //------------------开始编辑------------------------------
        msg20204.setTranNo("pacgzx");
        msg20204.setBankDate("20170106");
        msg20204.setCenterSeq("201701120002");
        msg20204.setBankSeq("201701120002");
        msg20204.setRetCode("0");
        msg20204.setRetDesc("操作成功");
      //------------------编辑结束------------------------------
        byte[] bytes = TASProtoHelper.getNTAS(msg20204.build().toByteArray(), 20204, 0);
        channel.basicPublish(EXCHANGE_NAME, "queue_110", null, bytes);
        channel.close();
        connection.close();
//		System.out.println(DateHelp.getCurrentDateTimeOfStringHS());
	}
}

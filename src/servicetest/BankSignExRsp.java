package servicetest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.common.util.TASProtoHelper;
import com.proto.CenterBank.Msg20304;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class BankSignExRsp {
	
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

        Msg20304.Builder msg20304 = Msg20304.newBuilder();
       
      //------------------开始编辑------------------------------
        msg20304.setTranNo("pacgzx");
        msg20304.setBankDate("20170113");
        msg20304.setCenterSeq("201701130002");
        msg20304.setBankSeq("201701130002");
        msg20304.setRetCode("0");
        msg20304.setRetDesc("操作成功");
      //------------------编辑结束------------------------------
        byte[] bytes = TASProtoHelper.getNTAS(msg20304.build().toByteArray(), 20304, 0);
        channel.basicPublish(EXCHANGE_NAME, "queue_110", null, bytes);
        channel.close();
        connection.close();
	}
}

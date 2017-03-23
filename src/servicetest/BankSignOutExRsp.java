package servicetest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.common.util.TASProtoHelper;
import com.proto.CenterBank.Msg20404;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class BankSignOutExRsp {
	
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

        Msg20404.Builder msg20404 = Msg20404.newBuilder();
       
      //------------------开始编辑------------------------------
        msg20404.setTranNo("pacgzx");
        msg20404.setBankDate("20170113");
        msg20404.setCenterSeq("201701130002");
        msg20404.setBankSeq("201701170001");
        msg20404.setRetCode("0");
        msg20404.setRetDesc("操作成功");
      //------------------编辑结束------------------------------
        byte[] bytes = TASProtoHelper.getNTAS(msg20404.build().toByteArray(), 20404, 0);
        channel.basicPublish(EXCHANGE_NAME, "queue_110", null, bytes);
        channel.close();
        connection.close();
	}
}

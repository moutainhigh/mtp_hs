package servicetest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.common.util.TASProtoHelper;
import com.proto.CenterBank.Msg20104;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class BankAmtIn {

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

        Msg20104.Builder msg20104 = Msg20104.newBuilder();
       
      //------------------开始编辑------------------------------
        msg20104.setTranNo("pacgzx");
        msg20104.setBankDate("20170106");
        msg20104.setCenterSeq("201701120002");
        msg20104.setBankSeq("201701120002");
        msg20104.setRetCode("0");
        msg20104.setRetDesc("操作成功");
      //------------------编辑结束------------------------------
        byte[] bytes = TASProtoHelper.getNTAS(msg20104.build().toByteArray(), 20104, 0);
        channel.basicPublish(EXCHANGE_NAME, "queue_110", null, bytes);
        channel.close();
        connection.close();
//		System.out.println(DateHelp.getCurrentDateTimeOfStringHS());
	}

}

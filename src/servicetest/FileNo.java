package servicetest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.common.util.TASProtoHelper;
import com.proto.CenterBank.Msg31001;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class FileNo {
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

        Msg31001.Builder msg31001 = Msg31001.newBuilder();
       
      //------------------开始编辑------------------------------
        msg31001.setTranNo("pacgzx");
        msg31001.setBankDate("20170224");
        msg31001.setCenterSeq("201702060005");
        msg31001.setExchNo("4110014");
        msg31001.setFileType("BF02");
        msg31001.setFilePath("down");
        msg31001.setFileName("BF02.txt");
        msg31001.setFileMd5("");
        msg31001.setIsResend(0);
      //------------------编辑结束------------------------------
        byte[] bytes = TASProtoHelper.getNTAS(msg31001.build().toByteArray(), 31001, 0);
        channel.basicPublish(EXCHANGE_NAME, "queue_110", null, bytes);
        channel.close();
        connection.close();
	}
}

package servicetest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.common.util.DateHelp;
import com.common.util.TASProtoHelper;
import com.proto.CenterBank.Msg17602;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Msg17602Test {
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

        Msg17602.Builder msg17602 = Msg17602.newBuilder();
       
      //------------------开始编辑------------------------------
        msg17602.setTranDate("20170106");
		msg17602.setSerialNo("00000000");
		msg17602.setExchNo("4110014");
		msg17602.setExchMarketType(2);
		msg17602.setBizType(2);
		msg17602.setExchangeFeesType(1);
		msg17602.setFeesBalance(2.5);
		msg17602.setPayerMemCode("9999999999");
		msg17602.setPayerFundAccount("9999999999");
		msg17602.setPayeeMemCode("88888888");
		msg17602.setPayeeFundAccount("88888888");
		msg17602.setRelatedBillType(2);
		msg17602.setRelatedBillNo("465542");
		msg17602.setRemark("test");
		msg17602.setBusiDatetime(DateHelp.getCurrentDateTimeOfStringHS());
      //------------------编辑结束------------------------------
        byte[] bytes = TASProtoHelper.getNTAS(msg17602.build().toByteArray(), 17602, 0);
        channel.basicPublish(EXCHANGE_NAME, "queue_110", null, bytes);
        channel.close();
        connection.close();
	}
}

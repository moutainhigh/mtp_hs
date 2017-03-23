package servicetest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.common.util.DateHelp;
import com.common.util.TASProtoHelper;
import com.proto.CenterBank.Msg10702;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *  客户信息Test
 * ClassName: ClientMsg.java
 * date: 2017年1月10日上午10:27:58
 * @author yu.jian
 * @version
 */
public class ClientMsg {

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

        Msg10702.Builder msg10702 = Msg10702.newBuilder();
       
      //------------------开始编辑------------------------------
        msg10702.setTranNo("pf");
        msg10702.setBankDate(DateHelp.getCurrentDateOfString());
        msg10702.setCenterSeq("201701110002");
        msg10702.setExchNo("210001");
        msg10702.setTradeAcct("201701110002");
        msg10702.setChangeType("1");
        msg10702.setClientName("test1");
        msg10702.setClientShortName("test");
        msg10702.setClientType(1);
        msg10702.setCertType("0");
        msg10702.setCertCode("421127199407071739");
        msg10702.setSex("1");
        msg10702.setNationality("CHN");
        msg10702.setAddress("深圳");
        msg10702.setClientName("test1");
        msg10702.setContactPhone("13207147820");
        
        
      //------------------编辑结束------------------------------
        byte[] bytes = TASProtoHelper.getNTAS(msg10702.build().toByteArray(), 10702, 0);
        channel.basicPublish(EXCHANGE_NAME, "queue_110", null, bytes);
        channel.close();
        connection.close();
	}

}

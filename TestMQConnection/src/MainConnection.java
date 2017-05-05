import java.io.EOFException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.ibm.jms.JMSTextMessage;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.headers.MQDataException;
import com.ibm.mq.headers.MQHeaderList;
import com.ibm.mq.jms.JMSC;
import com.ibm.mq.jms.MQConnection;
import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.mq.jms.MQQueueConnection;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.mq.jms.MQQueueReceiver;
import com.ibm.mq.jms.MQQueueSender;
import com.ibm.mq.jms.MQQueueSession;
import com.kbank.mq.clients.FileUitl;
import com.kbank.mq.clients.GetMessageApplication;

public class MainConnection {

	public static void main(String[] args) throws JMSException, MQException, IOException, MQDataException, InterruptedException
	{
		System.out.println("MainConnection is processing ...");
	
		
		MQQueueConnectionFactory config = new MQQueueConnectionFactory();		
		config.setHostName("172.31.164.44");
		config.setPort(1415);
		config.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
		config.setQueueManager("QMD7");
		config.setChannel("FRAUD.SVRCONN");	
		MQQueueConnection connection = (MQQueueConnection)config.createQueueConnection();
		MQQueueSession sessionSender = (MQQueueSession) connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
	//	MQQueueSession sessionReceive = (MQQueueSession) connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		 com.ibm.mq.jms.MQQueue queue = (com.ibm.mq.jms.MQQueue)sessionSender.createQueue("CEP.FRAUD.SENDQ.VDOLOG.TEST");
	//	 com.ibm.mq.jms.MQQueue queueReceive = (com.ibm.mq.jms.MQQueue)sessionReceive.createQueue("CEP.FRAUD.SENDQ.VDOLOG.TEST");
		 MQQueueSender sender = (MQQueueSender)sessionSender.createSender((Queue)queue);
		 
		 
//		 MQQueueReceiver receive = (MQQueueReceiver)sessionReceive.createReceiver((Queue)queueReceive);
//		 receive.setMessageListener(new MessageListener() {
//			
//			@Override
//			public void onMessage(Message m) {
//				// TODO Auto-generated method stub
//				System.out.println("Got Message");
//				TextMessage message = (TextMessage)m;
//				try {
//					System.out.println("Message is : "+message.getText());
//				} catch (JMSException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		});
		 JMSTextMessage text = (JMSTextMessage)sessionSender.createTextMessage("Test from sender");
		 JMSTextMessage text2 = (JMSTextMessage)sessionSender.createTextMessage("Test from sender_2");
		 JMSTextMessage text3 = (JMSTextMessage)sessionSender.createTextMessage("Test from sender_3");
		 JMSTextMessage text4 = (JMSTextMessage)sessionSender.createTextMessage("Test from sender_4");
		 JMSTextMessage text5 = (JMSTextMessage)sessionSender.createTextMessage("Test from sender_5");
		connection.start();
		
		System.out.println("Start ....");
		System.out.println("Get ClientID: "+connection.getClientID());
		
		sender.send(text);sender.send(text2);sender.send(text3);sender.send(text4);sender.send(text5);
		System.out.println("Messaging ....");
		
	//	receive.receive(5000);
	//	System.out.println("receiving ...");
		sender.close();
	//	receive.close();
		sessionSender.close();
	//	sessionReceive.close();
		connection.close();
		
//		MQQueueManager qMgr = new MQQueueManager("QMD7");
//		
//	    MQQueue queue = qMgr.accessQueue("CEP.FRAUD.SENDQ.VDOLOG.TEST", openOptions);
//	    int countFile = 0;
//	    int maxFile = 100;
//	    MQMessage mmqmsg = null;
//	    do
//	    {
//	      mmqmsg = getMessage(queue);
//	      System.out.println("Show Length Message: "+mmqmsg.getDataLength());
//	      countFile += 1;
//	      if (mmqmsg != null)
//	      {
//	        Date now = Calendar.getInstance().getTime();
//	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HHmmssSSSSSS");
//	        String n = df.format(now);
//	        String directory = "./";
//	       boolean outputHeader = false;
//	        boolean outputText = false;
//	        boolean outputBinary = false;
//	        if (mmqmsg != null)
//	        {
//	          if (outputHeader)
//	          {
//	            MQHeaderList mqHeaderList = new MQHeaderList(mmqmsg, true);
//	            FileUitl.writeTextToFile(mqHeaderList.toString(), directory + n + ".hdr");
//	          }
//	          if (outputText) {
//	            FileUitl.writeTextToFile(getMessageString(mmqmsg), directory + n + ".txt");
//	          }
//	          if (outputBinary) {
//	            FileUitl.writeByteToFile(getMessageBytes(mmqmsg), directory + n + ".msg");
//	          }
//	        }
//	        Thread.sleep(100L);
//	      }
//	    } while ((mmqmsg != null) && ((countFile < maxFile) || (maxFile <= 0)));
//	    queue.close();
//	    qMgr.disconnect();
		
		
	}
	
	private static MQMessage getMessage(MQQueue queue)
		    throws IOException
		  {
		    MQMessage rcvMessage = new MQMessage();
		    MQGetMessageOptions gmo = new MQGetMessageOptions();
		    try
		    {
		      queue.get(rcvMessage, gmo);
		    }
		    catch (MQException e)
		    {
		      if (e.getReason() == 2033) {
		        return null;
		      }
		    }
		    return rcvMessage;
		  }
	
	private static String getMessageString(MQMessage rcvMessage)
		    throws EOFException, IOException
		  {
		    rcvMessage.seek(0);
		    return rcvMessage.readStringOfByteLength(rcvMessage.getMessageLength());
		  }
	
	  private static byte[] getMessageBytes(MQMessage rcvMessage)
			    throws IOException
			  {
			    rcvMessage.seek(0);
			    byte[] data = new byte[rcvMessage.getMessageLength()];
			    rcvMessage.readFully(data);
			    return data;
			  }
}

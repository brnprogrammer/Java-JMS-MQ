package br.com.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class TesteConsomeFila {
	
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
		Destination fila = (Destination) context.lookup("financeiro");
		MessageConsumer consumer = session.createConsumer(fila);
		
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				
				TextMessage textMessage = (TextMessage)message;
		
				
				try {
				//	message.acknowledge();
					System.out.println(textMessage.getText());
					session.rollback();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		
		
		new Scanner(System.in).nextLine();
		
		session.close();
		
		connection.close();
		context.close();
		
		// fila entrega as mensagens de forma balan�eada, topic envia as mensagens para todos clientes que estao online
		
	}

}
package br.com.jmslog;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

import org.apache.activemq.command.ProducerAck;

public class TesteProdutorFila {

	public static void main(String[] args) throws Exception{

		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("LOG");
		
		MessageProducer messageProducer =  session.createProducer(fila);

		Message message = session.createTextMessage("## INFO LOG ##");
		messageProducer.send(message, DeliveryMode.NON_PERSISTENT, 3, 50000);
		// o terceiro parametro define a prioridade da mensagem, e o quarto parametro o tempo de envio
		
		
		
		/*
		 * for (int i = 0; i < 1000; i++) { Message m1 =
		 * session.createTextMessage("<teste>" + i + "</teste>");
		 * messageProducer.send(m1); }
		 */
		
		//new Scanner(System.in).nextLine();

		session.close();

		connection.close();
		context.close();
	}
}

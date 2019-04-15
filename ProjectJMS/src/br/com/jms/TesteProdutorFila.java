package br.com.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
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
		Destination fila = (Destination) context.lookup("financeiro");
		
		MessageProducer messageProducer =  session.createProducer(fila);

		Message message = session.createTextMessage("<livro><autor>13</autor><editora>Abril</editora></livro>");
		messageProducer.send(message);
		
		
		
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

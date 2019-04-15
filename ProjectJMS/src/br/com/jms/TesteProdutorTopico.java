package br.com.jms;

import java.io.StringWriter;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

import br.com.jms.modelo.Pedido;
import br.com.jms.modelo.PedidoFactory;

public class TesteProdutorTopico {
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		
		
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination topico = (Destination) context.lookup("loja"); // nome vindo a partir do .properties

		MessageProducer messageProducer = session.createProducer(topico);
		Pedido pedido = new PedidoFactory().geraPedidoComValores();
		
		/*
		 * 
		 * writer = new StringWriter(); JAXB.marshal(pedido, writer);
		 * 
		 * String xml = writer.toString(); System.out.println(xml);
		 */
		
				
		
		Message message = session.createObjectMessage(pedido);
		//message.setBooleanProperty("ebook", true);
		messageProducer.send(message);

		Message m1 = session.createTextMessage("<teste>999</teste>");
		messageProducer.send(m1);

		session.close();

		connection.close();
		context.close();
	}

}

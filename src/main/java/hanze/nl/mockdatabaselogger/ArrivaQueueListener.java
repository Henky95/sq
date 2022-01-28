package hanze.nl.mockdatabaselogger;

import com.thoughtworks.xstream.XStream;
import hanze.nl.infobord.InfoBord;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ArrivaQueueListener implements MessageListener {
	int aantalBerichten=0;
	int aantalETAs=0;

	public ArrivaQueueListener() {
	}

	public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
	            TextMessage textMessage = (TextMessage) message;
				String text = textMessage.getText();

				XStream xstream = new XStream();
				xstream.alias("Bericht", Bericht.class);
				xstream.alias("ETA", ETA.class);
				Bericht bericht=(Bericht)xstream.fromXML(text);
				aantalBerichten++;
				aantalETAs+=bericht.ETAs.size();
			} else {
				System.out.println("Received: " + message);
			}
		}
		catch (JMSException e) {
			e.printStackTrace();
    	}
	}
}


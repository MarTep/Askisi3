package gr.upatras.Askisi3;

import java.util.List;

import gr.upatras.Askisi3.Message;
import gr.upatras.Askisi3.SimpleMQTTClient;

/**
 * @author MarTep
 *
 */
public interface IMessageService {
	
	
	/**
	 * @return all messages
	 */
	List<Message> findAll();

	/**
	 * @param p
	 * @return the @Message added
	 */
	Message addMessage(Message p);


	//	---------
	SimpleMQTTClient class2 = new SimpleMQTTClient();
	
}


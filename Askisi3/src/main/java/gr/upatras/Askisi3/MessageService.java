package gr.upatras.Askisi3;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author MarTep
 *
 */
@Service
public class MessageService implements IMessageService {
	
	
	
	// creating an object of ArrayList
	List<Message> messages = new ArrayList<Message>();
	
	int ix = 1;
	//-----SimpleMQTTClient class2 = new SimpleMQTTClient();
	
	/**
	 * adding Messages to the List
	 */
	public MessageService() {		
		
		super();
		class2.runClient();
	}

	/**
	 * returns a list of message
	 */
	@Override
	public List<Message> findAll() {
		return messages;
	}

	@Override
	public Message addMessage(Message p) {
		
		// Lucky Number Index
		p.setId(ix);
		ix = ix + 1;
		messages.add(p);
		return p;
	}
	
}

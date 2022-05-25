package gr.upatras.Askisi3;

/**
 * @author MarTep
 *
 */
public class Message {
	private int id;
	private String yourMessage;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getyourMessage() {
		return yourMessage;
	}

	public void setyourMessage(String yourMessage) {
		this.yourMessage = yourMessage;
	}
	


	
	public Message(int id, String yourMessage) {
		super();
		this.id = id;
		this.yourMessage = yourMessage;
	}
}

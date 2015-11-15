package at.fhooe.im440.workbench.services.Messenger;

public class Message {

	private String type;
	private MessageData data;
	
	public Message() {
		this.type = "DEFAULT";
	}
	
	public Message(String type) {
		this.type = type.toUpperCase();
	}
	
	public Message(String type, MessageData data) {
		this(type);
		this.data = data;
	}
	
	public String getType() {
		return this.type;
	}
	
	public MessageData getData() {
		return this.data;
	}
	
}

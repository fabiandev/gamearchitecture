package at.fhooe.im440.workbench.services.Messenger;

import at.fhooe.im440.workbench.utilities.GenericArrayList;

public class Message {

	private MessageType type;
	
	private GenericArrayList<MessageData> contents = new GenericArrayList<MessageData>();
	
	public Message(MessageType type) {
		this.type = type;
	}
	
	public MessageType getType() {
		return this.type;
	}
	
	public MessageData[] getContents() {
		return this.contents.toArray();
	}
	
	public <T extends MessageData> Message with(T messageDataObject) {
		this.contents.add(messageDataObject);
		
		return this;
	}
	
	public <T extends MessageData> boolean has(Class<T> type) {
		if (this.contents.hasOne(type)) {
			return true;
		}
		
		return false;
	}
	
	public <T extends MessageData> T get(Class<T> type) {
		return this.contents.getFirst(type);
	}
	
}

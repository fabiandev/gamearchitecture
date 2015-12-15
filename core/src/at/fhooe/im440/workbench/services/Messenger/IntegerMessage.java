package at.fhooe.im440.workbench.services.Messenger;

public class IntegerMessage extends MessageData {

	int value;
	
	public IntegerMessage(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}

}

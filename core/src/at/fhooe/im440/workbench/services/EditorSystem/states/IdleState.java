package at.fhooe.im440.workbench.services.EditorSystem.states;

import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.EditorSystem.EditorSystem;
import at.fhooe.im440.workbench.services.Messenger.Message;
import at.fhooe.im440.workbench.services.Messenger.MessageType;
import at.fhooe.im440.workbench.services.Messenger.Messenger;
import at.fhooe.im440.workbench.services.Messenger.Subscribeable;

public class IdleState implements State, Subscribeable {

	// private EditorSystem editorSystem;
	private MessageType[] listenTo = new MessageType[] { };

	public IdleState(EditorSystem editorSystem) {
		// this.editorSystem = editorSystem;
	}
	
	@Override
	public void on() {
		this.subscribe();
	}

	@Override
	public void off() {
		this.unsubscribe();
	}

	@Override
	public void message(Message message) {
		MessageType type = message.getType();

		switch (type) {
		case KEY_DOWN:
			// int keyCode = message.get(IntegerMessage.class).getValue();
			break;
		default:
			break;
		}
	}

	@Override
	public void subscribe() {
		ServiceManager.getService(Messenger.class).subscribe(this, this.listenTo);
	}

	@Override
	public void unsubscribe() {
		ServiceManager.getService(Messenger.class).unsubscribe(this, this.listenTo);
	}

}

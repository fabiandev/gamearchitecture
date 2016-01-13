package at.fhooe.im440.workbench.services.EditorSystem.states;

import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.EditorSystem.EditorSystem;
import at.fhooe.im440.workbench.services.Messenger.Message;
import at.fhooe.im440.workbench.services.Messenger.MessageType;
import at.fhooe.im440.workbench.services.Messenger.Messenger;
import at.fhooe.im440.workbench.services.Messenger.PositionMessage;
import at.fhooe.im440.workbench.services.Messenger.Subscribeable;

public class SingleSelectedState implements State, Subscribeable {

	private EditorSystem editorSystem;
	private MessageType[] listenTo = new MessageType[] { 
			MessageType.MOUSE_MOVED,
			MessageType.TOUCH_DOWN,
			MessageType.TOUCH_UP
		};

	public SingleSelectedState(EditorSystem editorSystem) {
		this.editorSystem = editorSystem;
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
		case MOUSE_MOVED:
			PositionMessage mousePosition = message.get(PositionMessage.class);
			if (mousePosition != null) {
				this.editorSystem.positionSelectedEditables(mousePosition.getVector());
			}
			
			break;
		case TOUCH_DOWN:
			if (this.editorSystem.deselectAllEditables()) {
				this.editorSystem.setState(EditorState.SINGLE_SELECTING);
			}
			
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

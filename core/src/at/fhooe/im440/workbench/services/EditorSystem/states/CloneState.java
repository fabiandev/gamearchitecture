package at.fhooe.im440.workbench.services.EditorSystem.states;

import com.badlogic.gdx.Input.Keys;

import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.EditorSystem.EditorSystem;
import at.fhooe.im440.workbench.services.Messenger.IntegerMessage;
import at.fhooe.im440.workbench.services.Messenger.Message;
import at.fhooe.im440.workbench.services.Messenger.MessageType;
import at.fhooe.im440.workbench.services.Messenger.Messenger;
import at.fhooe.im440.workbench.services.Messenger.PositionMessage;
import at.fhooe.im440.workbench.services.Messenger.Subscribeable;

public class CloneState implements State, Subscribeable {

	private EditorSystem editorSystem;
	private MessageType[] listenTo = new MessageType[] { MessageType.TOUCH_DOWN, MessageType.KEY_UP };

	public CloneState(EditorSystem editorSystem) {
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
		case TOUCH_DOWN:
			PositionMessage mousePosition = message.get(PositionMessage.class);
			this.editorSystem.cloneCollidingEditable(mousePosition.getVector());
			this.editorSystem.setState(EditorState.SINGLE_SELECTED);
			
			break;
		case KEY_UP:
			int keyCode = message.get(IntegerMessage.class).getValue();

			if (keyCode == Keys.C) {
				this.editorSystem.setState(EditorState.IDLE);
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

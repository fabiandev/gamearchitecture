package at.fhooe.im440.workbench.services.EditorSystem.states;

import com.badlogic.gdx.Gdx;

import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.EditorSystem.EditorSystem;
import at.fhooe.im440.workbench.services.Messenger.Message;
import at.fhooe.im440.workbench.services.Messenger.MessageType;
import at.fhooe.im440.workbench.services.Messenger.Messenger;
import at.fhooe.im440.workbench.services.Messenger.PositionMessage;
import at.fhooe.im440.workbench.services.Messenger.Subscribeable;

public class RotatingState implements State, Subscribeable {

	private EditorSystem editorSystem;
	private MessageType[] listenTo = new MessageType[] { 
			MessageType.TOUCH_DRAGGED,
			MessageType.TOUCH_UP
		};

	public RotatingState(EditorSystem editorSystem) {
		this.editorSystem = editorSystem;
	}
	
	@Override
	public void on() {
		this.subscribe();
		Gdx.app.log("EditorState", "ROTATING");
	}

	@Override
	public void off() {
		this.unsubscribe();
	}

	@Override
	public void message(Message message) {
		MessageType type = message.getType();

		switch (type) {
		case TOUCH_DRAGGED:
			PositionMessage mousePosition = message.get(PositionMessage.class);
			if (mousePosition != null) {
				this.editorSystem.rotateSelectedEditables(mousePosition.getVector());
			}
			
			break;
		case TOUCH_UP:
			if (this.editorSystem.deselectCollideables()) {
				this.editorSystem.setPreviousState();
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

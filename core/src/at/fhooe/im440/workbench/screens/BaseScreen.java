package at.fhooe.im440.workbench.screens;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;

import at.fhooe.im440.workbench.Workbench;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.Messenger.IntegerMessage;
import at.fhooe.im440.workbench.services.Messenger.Message;
import at.fhooe.im440.workbench.services.Messenger.MessageType;
import at.fhooe.im440.workbench.services.Messenger.Messenger;
import at.fhooe.im440.workbench.services.Messenger.Subscribeable;

public abstract class BaseScreen extends ScreenAdapter implements Screen, Subscribeable {

	private MessageType[] listenTo = new MessageType[] { MessageType.KEY_DOWN };
	
	@Override
	public void subscribe() {
		Messenger messenger = ServiceManager.getService(Messenger.class);
		messenger.subscribe(this, this.listenTo);
	}

	@Override
	public void unsubscribe() {
		Messenger messenger = ServiceManager.getService(Messenger.class);
		messenger.unsubscribe(this, this.listenTo);
	}
	
	@Override
	public void message(Message message) {
		MessageType type = message.getType();

		switch (type) {
		case KEY_DOWN:
			int keyCode = message.get(IntegerMessage.class).getValue();

			if (keyCode == Keys.ESCAPE) {
				Workbench.get().setScreen(new MenuScreen());
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public void show() {
		super.show();
		this.subscribe();
	}
	
	@Override
	public void hide() {
		super.hide();
		this.unsubscribe();
	}
	
}

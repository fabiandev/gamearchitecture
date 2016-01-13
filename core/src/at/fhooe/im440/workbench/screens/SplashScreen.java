package at.fhooe.im440.workbench.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

import at.fhooe.im440.workbench.Workbench;
import at.fhooe.im440.workbench.helpers.Picasso;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.Messenger.Message;
import at.fhooe.im440.workbench.services.Messenger.MessageType;
import at.fhooe.im440.workbench.services.Messenger.Messenger;
import at.fhooe.im440.workbench.services.Messenger.Subscribeable;

public class SplashScreen extends BaseScreen implements Subscribeable {
	private static final float FADE_TIME = 1.0f;
	private static final float DELAY_TIME = 5.0f;
	private Workbench workbench;
	private Stage stage;
	private Texture texture;
	private boolean dead;
	private MessageType[] listenTo = new MessageType[] { MessageType.KEY_UP };
	
	public SplashScreen() {
		this.workbench = Workbench.get();
		this.stage = workbench.getStage();
		texture = new Texture("splash.png");
	}

	@Override
	public void show() {
		super.show();
		this.subscribe();
		
		Image img = new Image(texture);
		img.setPosition(0, 0, Align.center);
		img.getColor().a = 0f;
		img.setName("splashImage");
		
		img.addAction(Actions.sequence(
				Actions.delay(0.5f),
				Actions.fadeIn(FADE_TIME),
				Actions.delay(DELAY_TIME),
				Actions.run(new Runnable() {
					
					@Override
					public void run() {
						fadeOut();
					}
				})
		));
		
		stage.addActor(img);
		
		stage.getViewport().getCamera().position.set(0, 0, 0);
		stage.getViewport().update((int)Workbench.VIEWPORT_WIDTH, (int)Workbench.VIEWPORT_HEIGHT);
		
		dead = false;
	}
	
	public void fadeOut() {
		if (dead) {
			return;
		}
		dead = true;
		Image img = stage.getRoot().findActor("splashImage");
		img.clearActions();
		img.addAction(Actions.sequence(
				Actions.fadeOut(FADE_TIME),
				Actions.hide(),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						endOfState();
					}
					
				})		
		));
	}
	
	public void endOfState() {
		Workbench.get().setScreen(Workbench.get().getMenuScreen());
	}

	@Override
	public void render(float delta) {
		Picasso.paintBackground(Picasso.GAME_PALEGREEN);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		stage.getViewport().getCamera().position.set(0, 0, 0);
		stage.getViewport().update(width, height);
	}
	
	@Override
	public void message(Message message) {
		switch(message.getType()) {
		case KEY_UP:
			this.fadeOut();
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

	@Override
	public void hide() {
		super.hide();
		this.unsubscribe();
	}
	
	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}

}

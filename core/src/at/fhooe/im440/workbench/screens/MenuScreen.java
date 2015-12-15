package at.fhooe.im440.workbench.screens;

import java.util.Map.Entry;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import at.fhooe.im440.workbench.Workbench;
import at.fhooe.im440.workbench.helpers.Picasso;
import at.fhooe.im440.workbench.menu.Menu;
import at.fhooe.im440.workbench.menu.MenuElement;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.Messenger.IntegerMessage;
import at.fhooe.im440.workbench.services.Messenger.Message;
import at.fhooe.im440.workbench.services.Messenger.MessageType;
import at.fhooe.im440.workbench.services.Messenger.Messenger;
import at.fhooe.im440.workbench.services.Messenger.Subscribeable;

public class MenuScreen extends ScreenAdapter implements Screen, Subscribeable {
	
	private Workbench workbench;
	private BitmapFont font;
	private Stage stage;
	private Menu menu;
	private LabelStyle defaultLabelStyle;
	private LabelStyle activeLabelStyle;
	
	private boolean dead = false;

	public MenuScreen(Workbench workbench) {
		this.workbench = workbench;
		this.font = this.workbench.getFont();
		this.stage = this.workbench.getStage();
		this.defaultLabelStyle = new LabelStyle(this.font, Picasso.GRAY);
		this.activeLabelStyle = new LabelStyle(this.font, Picasso.BLACK);
		this.createMenu();
		this.subscribe();
	}
	
	private void createMenu() {
		this.menu = new Menu();
		
		
		Label start = new Label("START", defaultLabelStyle);
		this.menu.add(new MenuElement<EditorScreen>("editor", new Label("EDITOR", defaultLabelStyle), EditorScreen.class));
		this.menu.add(new MenuElement<Screen>("settings", new Label("SETTINGS", defaultLabelStyle)));
		this.menu.add(new MenuElement<GameScreen>("start", start, GameScreen.class));
		this.menu.add(new MenuElement<Screen>("credits", new Label("CREDITS", defaultLabelStyle)));
		this.menu.add(new MenuElement<ExitScreen>("exit", new Label("EXIT", defaultLabelStyle), ExitScreen.class));
		
		this.menu.highlight("start");
		this.menu.position();
	}
	
	@SuppressWarnings("rawtypes")
	private void resetMenu() {
		for(Entry<String, MenuElement> element : this.menu.getAll().entrySet()) {
			Label label = (Label) element.getValue().getElement();
			label.setStyle(defaultLabelStyle);
		}
	}
	
	private void renderMenu() {
		this.resetMenu();
		Actor active = this.menu.getActive().getElement();
		((Label)active).setStyle(activeLabelStyle);
		
	}
	
	private void animateMenu() {
		Actor active = this.menu.getActive().getElement();
		active.clearActions();
		active.addAction(Actions.sequence(
				Actions.fadeOut(0.3f, Interpolation.sine),
				Actions.fadeIn(0.4f, Interpolation.sine),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						animateMenu();
					}
					
				})
			));
	}
	
	private void stopMenuAnimtation() {
		Actor active = this.menu.getActive().getElement();
		active.clearActions();
		active.addAction(Actions.fadeIn(0f));
	}

	@Override
	public void render(float delta) {
		Picasso.paintBackground(Picasso.GAME_PALEGREEN);
		
		this.renderMenu();
		
		this.stage.act(delta);
		this.stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		stage.getViewport().getCamera().position.set(0, 0, 0);
		stage.getViewport().update(width, height);
		//this.stage.getViewport().getCamera().position.set(0, 0, 0);
		//this.stage.getViewport().update(width, height);
	}

	@Override
	public void show() {
		super.show();
		
		stage.getViewport().getCamera().position.set(0, 0, 0);
		stage.getViewport().update((int)Workbench.VIEWPORT_WIDTH, (int)Workbench.VIEWPORT_HEIGHT);
		
		this.stage.addActor(this.menu.getGroup());
		animateMenu();
		// Gdx.input.setInputProcessor(this);
	
		this.dead = false;
	}

	@Override
	public void hide() {
		super.hide();
		this.menu.getGroup().addAction(Actions.hide());
		this.stopMenuAnimtation();
	}

	@Override
	public void dispose() {
		super.dispose();
		this.stage.dispose();
		this.font.dispose();
	}

	@Override
	public void message(Message message) {
		if (dead) {
			return;
		}
		
		this.stopMenuAnimtation();
		
		switch(message.getType()) {
		case KEY_UP:
			switch(message.get(IntegerMessage.class).getValue()) {
			case Keys.RIGHT:
				this.menu.highlightNext();
				break;
			case Keys.LEFT:
				this.menu.highlightPrev();
				break;
			case Keys.ENTER:
				Screen screen = this.menu.getActive().getScreen(this.workbench);
				if (screen != null) {
					this.dead = true;
					this.workbench.setScreen(screen);
				}
				break;
			}
			default:
				break;
		}
		
		this.animateMenu();
	}

	private MessageType[] listenTo = new MessageType[] { MessageType.KEY_UP };
	
	@Override
	public void subscribe() {
		Messenger messenger = ServiceManager.getService(Messenger.class);
		messenger.subscribe(this, this.listenTo);
	}

	@Override
	public void unsubscribe() {
		Messenger messenger = ServiceManager.getService(Messenger.class);
		messenger.subscribe(this, this.listenTo);
	}

}

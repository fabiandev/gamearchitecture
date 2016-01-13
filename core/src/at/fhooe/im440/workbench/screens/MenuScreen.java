package at.fhooe.im440.workbench.screens;

import java.util.Map.Entry;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
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

public class MenuScreen extends BaseScreen implements Subscribeable {
	
	private Workbench workbench;
	private BitmapFont font;
	private Stage stage;
	private Menu menu;
	private LabelStyle defaultLabelStyle;
	private LabelStyle activeLabelStyle;
	private MessageType[] listenTo = new MessageType[] { MessageType.KEY_UP };
	private Vector2 activeMenuElementPosition = new Vector2();
	
	public MenuScreen() {
		this.workbench = Workbench.get();
		this.font = this.workbench.getFont();
		this.stage = this.workbench.getStage();
		this.defaultLabelStyle = new LabelStyle(this.font, Picasso.SLATE);
		this.activeLabelStyle = new LabelStyle(this.font, Picasso.DARK_GRAY);
		this.createMenu();
	}
	
	private void createMenu() {
		this.menu = new Menu();
		
		this.menu.add(new MenuElement<CameraTargetScreen>("cameraTarget", new Label("CAMERA TARGET", defaultLabelStyle), CameraTargetScreen.class));
		this.menu.add(new MenuElement<PhysicsScreen>("physics", new Label("PHYSICS", defaultLabelStyle), PhysicsScreen.class));
		this.menu.add(new MenuElement<GameScreen>("start", new Label("START GAME", defaultLabelStyle), GameScreen.class));
		this.menu.add(new MenuElement<EditorScreen>("editor", new Label("EDITOR", defaultLabelStyle), EditorScreen.class));
		this.menu.add(new MenuElement<ExitScreen>("exit", new Label("EXIT", defaultLabelStyle), ExitScreen.class));
		
		this.menu.highlight("start");
		this.menu.position();

		this.menu.getGroup().addAction(Actions.hide());
		this.stage.addActor(this.menu.getGroup());
		
		this.rememberActiveMenuElementPosition();
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
		
		active.addAction(Actions.sequence(
				Actions.moveBy(0f, 20f, 0.4f, Interpolation.sineOut),
				Actions.moveTo(this.activeMenuElementPosition.x, this.activeMenuElementPosition.y, 0.3f, Interpolation.sineIn),
				//Actions.fadeOut(0.3f, Interpolation.sine),
				//Actions.fadeIn(0.4f, Interpolation.sine),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						animateMenu();
					}
					
				})
			));
	}
	
	private void stopMenuAnimtation(float duration) {
		Actor active = this.menu.getActive().getElement();
		active.clearActions();
		this.resetActiveMenuElementPosition(duration);
	}
	
//	private void stopMenuAnimtation() {
//		this.stopMenuAnimtation(0.3f);
//	}

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
	}

	@Override
	public void show() {
		super.show();
		
		stage.getRoot().getColor().a = 0;
		stage.getRoot().addAction(Actions.fadeIn(0.5f));
		
		this.subscribe();
		
		stage.getViewport().getCamera().position.set(0, 0, 0);
		stage.getViewport().update((int)Workbench.VIEWPORT_WIDTH, (int)Workbench.VIEWPORT_HEIGHT);
		
		this.menu.getGroup().addAction(Actions.show());
		this.animateMenu();
	}
	
	public void switchScreen(final Screen newScreen){
	    stage.getRoot().getColor().a = 1;
	    SequenceAction sequenceAction = new SequenceAction();
	    sequenceAction.addAction(Actions.fadeOut(0f));
	    sequenceAction.addAction(Actions.run(new Runnable() {
	        @Override
	        public void run() {
	            Workbench.get().setScreen(newScreen);
	        }
	    }));
	    stage.getRoot().addAction(sequenceAction);
	}

	@Override
	public void hide() {
		this.stopMenuAnimtation(0f);
		super.hide();
		this.unsubscribe();
		
		this.menu.getGroup().addAction(Actions.hide());
	}

	@Override
	public void dispose() {
		this.hide();
		super.dispose();
	}

	private void resetActiveMenuElementPosition(float duration) {
		Actor active = this.menu.getActive().getElement();
		active.addAction(Actions.moveTo(this.activeMenuElementPosition.x, this.activeMenuElementPosition.y, duration, Interpolation.sineIn));
	}
	
//	private void resetActiveMenuElementPosition() {
//		this.resetActiveMenuElementPosition(0.3f);
//	}
	
	private void rememberActiveMenuElementPosition() {
		Actor active = this.menu.getActive().getElement();
		this.activeMenuElementPosition.x = active.getX();
		this.activeMenuElementPosition.y = active.getY();
	}
	
	@Override
	public void message(Message message) {
		
		switch(message.getType()) {
		case KEY_UP:
			switch(message.get(IntegerMessage.class).getValue()) {
			case Keys.RIGHT:
				this.stopMenuAnimtation(0.3f);
				this.menu.highlightNext();
				this.rememberActiveMenuElementPosition();
				this.animateMenu();
				break;
			case Keys.LEFT:
				this.stopMenuAnimtation(0.3f);
				this.menu.highlightPrev();
				this.rememberActiveMenuElementPosition();
				this.animateMenu();
				break;
			case Keys.ENTER:
				Screen screen = this.menu.getActive().getScreen();
				
				if (screen != null) {
					this.switchScreen(screen);
					return;
				}
				break;
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

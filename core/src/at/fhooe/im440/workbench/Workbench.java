package at.fhooe.im440.workbench;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import at.fhooe.im440.workbench.components.StaticPose;
import at.fhooe.im440.workbench.screens.MenuScreen;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.AssetManager.AssetManager;
import at.fhooe.im440.workbench.services.CameraSystem.CameraSystem;
import at.fhooe.im440.workbench.services.CameraSystem.CameraTarget;
import at.fhooe.im440.workbench.services.ColliderSystem.ColliderSystem;
import at.fhooe.im440.workbench.services.EntityManager.EntityFactory;
import at.fhooe.im440.workbench.services.EntityManager.EntityManager;
import at.fhooe.im440.workbench.services.Messenger.IntegerMessage;
import at.fhooe.im440.workbench.services.Messenger.Message;
import at.fhooe.im440.workbench.services.Messenger.MessageType;
import at.fhooe.im440.workbench.services.Messenger.Messenger;
import at.fhooe.im440.workbench.services.Messenger.PositionMessage;
import at.fhooe.im440.workbench.services.PhysicsEngine.PhysicsEngine;
import at.fhooe.im440.workbench.services.RenderSystem.RenderSystem;
import at.fhooe.im440.workbench.services.UpdateService.UpdateService;

public class Workbench extends Game implements ApplicationListener, InputProcessor {
	
	public static final float WINDOW_WIDTH = 640; // 480 1366 640
	public static final float WINDOW_HEIGHT = 920; // 320 768 920
	
	public static final float VIEWPORT_WIDTH = 16; // 6  16
	public static final float VIEWPORT_HEIGHT = 23; // 5  23
	
	public static final String VERSION = "1.0.0";
	public static final String APP_TITLE = "Workbench";
	
	private Stage stage;
	private BitmapFont font;
	private SpriteBatch batch;
	
	private Messenger messenger;
	
	public static Workbench get() {
		return (Workbench) Gdx.app.getApplicationListener();
	}
	
	private void init() {
		this.batch = new SpriteBatch();
		this.stage = new Stage();
		this.font = new BitmapFont(Gdx.files.internal("arial_black_32.fnt"), Gdx.files.internal("arial_black_32.png"), false);
	
		this.messenger = new Messenger();
		this.messenger.activate();
		new AssetManager().activate();
		new EntityManager().activate();
		new EntityFactory().activate();
		new CameraSystem(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, new CameraTarget(new StaticPose())).activate();
		new RenderSystem(this.getBatch()).activate();
		new ColliderSystem().activate();
		new UpdateService().activate();
		new PhysicsEngine().activate();
		
		ServiceManager.activate();
	}
	
	@Override
	public void create () {
		this.init();
		this.setScreen(new MenuScreen());
		
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render() {
		super.render();	
		ServiceManager.update();
	}
	
	@Override
	public void resize (int width, int height) {
		super.resize(width, height);
		ServiceManager.getService(CameraSystem.class).update(width, height);
		ServiceManager.update();
	}
	
	@Override
	public void pause () {
		super.pause();
	}

	@Override
	public void resume () {
		super.resume();
	}

	public SpriteBatch getBatch() {
		return this.batch;
	}
	
	public Stage getStage() {
		return this.stage;
	}
	
	public BitmapFont getFont() {
		return this.font;
	}
	
	public void dispose() {
		this.batch.dispose();
		this.font.dispose();
		this.stage.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		messenger.fire(new Message(MessageType.KEY_DOWN).with(new IntegerMessage(keycode)));
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		messenger.fire(new Message(MessageType.KEY_UP).with(new IntegerMessage(keycode)));
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		messenger.fire(new Message(MessageType.TOUCH_DOWN).with(new PositionMessage(screenX, screenY)));
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		messenger.fire(new Message(MessageType.TOUCH_UP).with(new PositionMessage(screenX, screenY)));
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		messenger.fire(new Message(MessageType.TOUCH_DRAGGED).with(new PositionMessage(screenX, screenY)));
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		messenger.fire(new Message(MessageType.MOUSE_MOVED).with(new PositionMessage(screenX, screenY)));
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return true;
	}
	
}

package at.fhooe.im440.workbench;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import at.fhooe.im440.workbench.components.Position;
import at.fhooe.im440.workbench.entities.TestEntity;
import at.fhooe.im440.workbench.screens.SplashScreen;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.entityManager.EntityManager;
import at.fhooe.im440.workbench.services.messenger.Messenger;
import at.fhooe.im440.workbench.services.renderSystem.RenderSystem;

public class Workbench extends Game {
	
	private ServiceManager serviceManager;
	
	public static final float V_WIDTH = 1366;
	public static final float V_HEIGHT = 768;
	public static final String VERSION = "1.0.0";
	public static final String APP_TITLE = "Workbench";
	
	private Stage stage;
	private SpriteBatch batch;
	
	private void init() {
		this.serviceManager = new ServiceManager();
		
		serviceManager.addService(new Messenger());
		serviceManager.addService(new RenderSystem(this.batch));
		serviceManager.addService(new EntityManager());
		
		TestEntity testEntity = new TestEntity();
		testEntity.addComponent(new Position(25, 25));
		
		serviceManager.getService(EntityManager.class).addEntity(testEntity);
	}
	
	@Override
	public void create () {
		this.init();
		
		this.batch = new SpriteBatch();
		this.stage = new Stage();
		
		//this.font = new BitmapFont(Gdx.files.internal("arial_black_32.fnt"), Gdx.files.internal("arial_black_32.png"), false);
		
		setScreen(new SplashScreen(this));
	}

	@Override
	public void render () {
		// TODO: add tasks that need to be updated for all screens (aka state)
		this.serviceManager.update();
		
		// call render of base class which will call render for current screen
		super.render();
		
	}
	
	public ServiceManager getServiceManager() {
		return this.serviceManager;
	}

	public SpriteBatch getBatch() {
		return this.batch;
	}
	
	public Stage getStage() {
		return this.stage;
	}
	
	public BitmapFont getFont() {
		return new BitmapFont(Gdx.files.internal("arial_black_32.fnt"), Gdx.files.internal("arial_black_32.png"), false);
		// return this.font;
	}
	
	public void dispose() {
		// this.font.dispose();
	}
}

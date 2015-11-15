package at.fhooe.im440.workbench;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import at.fhooe.im440.workbench.components.SpriteVisual;
import at.fhooe.im440.workbench.components.StaticPose;
import at.fhooe.im440.workbench.entities.TestEntity;
import at.fhooe.im440.workbench.screens.SplashScreen;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.AssetManager.AssetManager;
import at.fhooe.im440.workbench.services.CameraSystem.CameraSystem;
import at.fhooe.im440.workbench.services.CameraSystem.CameraTarget;
import at.fhooe.im440.workbench.services.EntityManager.Entity;
import at.fhooe.im440.workbench.services.EntityManager.EntityManager;
import at.fhooe.im440.workbench.services.Messenger.Messenger;
import at.fhooe.im440.workbench.services.RenderSystem.RenderSystem;

public class Workbench2 extends Game {
	
	public static final float WINDOW_WIDTH = 800; // 1366
	public static final float WINDOW_HEIGHT = 400; // 768
	
	public static final float WORLD_WIDTH = 100; // 1366
	public static final float WORLD_HEIGHT = 50; // 768
	
	public static final String VERSION = "1.0.0";
	public static final String APP_TITLE = "Workbench";
	
	private Stage stage;
	private BitmapFont font;
	private SpriteBatch batch;
	
	private void init() {
		this.batch = new SpriteBatch();
		this.stage = new Stage();
		this.font = new BitmapFont(Gdx.files.internal("arial_black_32.fnt"), Gdx.files.internal("arial_black_32.png"), false);
		// Make an update service??
	
		ServiceManager.addService(new AssetManager());
		ServiceManager.addService(new EntityManager());
		ServiceManager.addService(new Messenger());
		ServiceManager.addService(new CameraSystem(WORLD_WIDTH, WORLD_HEIGHT, new CameraTarget(new StaticPose())));
		ServiceManager.addService(new RenderSystem(this.getBatch()));
		ServiceManager.activate();
		
		SpriteVisual spriteVisual = new SpriteVisual(ServiceManager.getService(AssetManager.class).getRegion("cog1"));
		
		Entity testEntity = new TestEntity().addComponents(new StaticPose(), spriteVisual);
		
		testEntity.activate();
		spriteVisual.activate();
		
		
		//t.addComponent(new StaticPose());
		//t.setAnimation(AssetManager.gearwheel);
	}
	
	@Override
	public void create () {
		//Gdx.graphics.setDisplayMode((int)V_WIDTH, (int)V_HEIGHT, false);
		
		this.init();
		
		//stage.setViewport(ServiceManager.getService(CameraSystem.class).getViewport());
		
		//setScreen(new SplashScreen(this));
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
		this.font.dispose();
		this.batch.dispose();
	}
	

}

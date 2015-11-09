package at.fhooe.im440.workbench.world;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

import at.fhooe.im440.workbench.Workbench;
import at.fhooe.im440.workbench.assets.AssetManager;
import at.fhooe.im440.workbench.components.Position;
import at.fhooe.im440.workbench.entities.TestEntity;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.CameraSystem.CameraSystem;
import at.fhooe.im440.workbench.services.EntityManager.EntityManager;
import at.fhooe.im440.workbench.services.Messenger.Messenger;
import at.fhooe.im440.workbench.services.RenderSystem.RenderSystem;

/*
 * NOTES: What I've changed
 * 
 * GameScreen: Use existing stage from SplashScreen or create new?
 * Messenger: fixed Type subscribe
 * World: Should work as kind of Game Engine...
 * 
 * Maybe add an Animateable interface to renderSystem stuff?
 */

public class World {

	private Workbench workbench;
	private BitmapFont font;
	
	public World(Workbench workbench) {
		this.workbench = workbench;
		this.font = this.workbench.getFont();
		
		ServiceManager.addService(new EntityManager());
		ServiceManager.addService(new Messenger());
		ServiceManager.addService(new RenderSystem(this.workbench.getBatch()));
		ServiceManager.addService(new CameraSystem());
		
	}
	
	// Idea: Provide game logic here (aka game engine), 
	// create game levels which provide informations to be processed from logic below...
	
	public void init() {
		TestEntity t = new TestEntity(this.workbench.getBatch());
		t.addComponent(new Position(20f, 20f));
		t.setAnimation(AssetManager.gearwheel);
		
		ServiceManager.getService(EntityManager.class).addEntity(t);
	}
}

package at.fhooe.im440.workbench.world;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

import at.fhooe.im440.workbench.Workbench;
import at.fhooe.im440.workbench.assets.AssetManager;
import at.fhooe.im440.workbench.components.Position;
import at.fhooe.im440.workbench.entities.TestEntity;
import at.fhooe.im440.workbench.services.ServiceManager;
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
	private ServiceManager serviceManager;
	private BitmapFont font;
	
	public World(Workbench workbench) {
		this.workbench = workbench;
		this.serviceManager = this.workbench.getServiceManager();
		this.font = this.workbench.getFont();
		
		this.serviceManager.addService(new EntityManager());
		this.serviceManager.addService(new Messenger());
		this.serviceManager.addService(new RenderSystem(this.workbench.getBatch()));
		
	}
	
	// Idea: Provide game logic here (aka game engine), 
	// create game levels which provide informations to be processed from logic below...
	
	public void init() {
		TestEntity t = new TestEntity();
		t.addComponent(new Position(20f, 20f));
		t.setRegion(AssetManager.enemy);
		
		// Here, a persisting, accessible key would be needed...
		this.serviceManager.getService(EntityManager.class).addEntity(t);
	}
	
	public void update() {
		
	}
}

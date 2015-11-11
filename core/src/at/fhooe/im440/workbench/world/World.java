package at.fhooe.im440.workbench.world;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

import at.fhooe.im440.workbench.Workbench;

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
	}
	
	// Idea: Provide game logic here (aka game engine), 
	// create game levels which provide informations to be processed from logic below...
	
	public void init() {
		
	}
}

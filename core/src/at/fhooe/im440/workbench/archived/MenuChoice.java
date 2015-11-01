package at.fhooe.im440.workbench.archived;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MenuChoice {

	private Actor[] actors;
	
	public MenuChoice(Actor[] a) {
		this.actors = a;
	}
	
	public void setAction(int entry) {
		if (this.actors[entry].getName().equals("Quit")) {
			this.safeExit();
		}
	}
	
	private void safeExit() {
		Gdx.app.exit();
	}
}
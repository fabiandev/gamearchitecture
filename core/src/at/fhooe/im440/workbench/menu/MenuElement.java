package at.fhooe.im440.workbench.menu;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MenuElement {
	
	String name;
	Actor element;
	Screen screen;
	
	public MenuElement(String name, Actor element) {
		this.name = name;
		this.element = element;
	}
	
	public MenuElement(String name, Actor element, Screen screen) {
		this(name, element);
		this.screen = screen;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Actor getElement() {
		return element;
	}

	public void setElement(Actor element) {
		this.element = element;
	}
	
	public Screen getScreen() {
		return this.screen;
	}
	
	public void setScreen(Screen screen) {
		this.screen = screen;
	}
	
}

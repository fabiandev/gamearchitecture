package at.fhooe.im440.workbench.menu;

import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;

import at.fhooe.im440.workbench.screens.BaseScreen;

public class MenuElement<T extends BaseScreen> {
	
	String name;
	Actor element;
	Class<T> screenClass;
	T screen;
	
	public MenuElement(String name, Actor element) {
		this.name = name;
		this.element = element;
	}
	
	public MenuElement(String name, Actor element, Class<T> screen) {
		this(name, element);
		this.screenClass = screen;
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
		if (screenClass == null) {
			return null;
		}
				
			try {
				T screen = this.screenClass.getConstructor().newInstance();
				
				if (this.screen != null) {
					this.screen.dispose();
				}
				
				this.screen = screen;
				this.screen.subscribe();	
			} catch (InstantiationException e) {
				return null;
			} catch (IllegalAccessException e) {
				return null;
			} catch (IllegalArgumentException e) {
				return null;
			} catch (InvocationTargetException e) {
				return null;
			} catch (NoSuchMethodException e) {
				return null;
			} catch (SecurityException e) {
				return null;
			}
		
		return this.screen;
	}
	
	public void setScreen(Class<T> screen) {
		this.screenClass = screen;
	}
	
	@SuppressWarnings("unchecked")
	public void setScreen(T screenInstance) {
		this.screenClass = (Class<T>) screenInstance.getClass();
		this.screen = screenInstance;
	}
	
}

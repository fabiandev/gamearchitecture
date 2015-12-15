package at.fhooe.im440.workbench.menu;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;

@SuppressWarnings("rawtypes")
public class Menu {

	private Map<String, MenuElement> elements = new LinkedHashMap<String, MenuElement>();
	private ArrayList<String> order = new ArrayList<String>();
	private Group group = new Group();
	
	private int active = 0;
	
	public MenuElement add(MenuElement menuElement) {
		MenuElement element = this.elements.put(menuElement.getName(), menuElement);
		this.buildReflections();
		return element;
	}
	
	public MenuElement get(String name) {
		return this.elements.get(name);
	}
	
	public MenuElement remove(String name) {
		MenuElement element = this.elements.remove(name);
		this.buildReflections();
		return element;
	}
	
	public Map<String, MenuElement> getAll() {
		return this.elements;
	}
	
	public MenuElement getActive() {
		return this.elements.get(this.order.get(this.active));
	}
	
	public String getActiveKey() {
		return this.order.get(this.active);
	}
	
	public MenuElement highlight(String name) {
		int index = this.order.indexOf(name);
		
		if (index == -1) {
			return null;
		}
		
		this.active = index;
		
		return this.elements.get(name);
	}
	
	public MenuElement highlightNext() {
		int lastIndex = this.order.size() - 1;
		int requestedIndex = this.active + 1;
		
		if (lastIndex == -1) {
			return null;
		}
		
		if (requestedIndex > lastIndex) {
			requestedIndex = 0;
		}
		
		String nextElement = this.order.get(requestedIndex);
		this.active = requestedIndex;
		
		this.moveToActive();
		
		return this.elements.get(nextElement);
	}
	
	public void moveToActive() {
		this.moveToActive(.15f);
	}
	
	public void moveToActive(float duration) {
		MenuElement menuElement = this.elements.get(this.order.get(this.active));
		Actor actor = menuElement.getElement();
		
		float offset = actor.getWidth() / 2f;
		
		this.group.clearActions();
		this.group.addAction(Actions.sequence(
				//Actions.moveToAligned((actor.getX() + offset) * -1f, 0f, Align.center, duration, Interpolation.sine)
				Actions.moveTo((actor.getX() + offset) * -1f, 0f, duration)
			));
	}
	
	public MenuElement highlightPrev() {
		int lastIndex = this.order.size() - 1;
		int requestedIndex = this.active -1;
		
		if (lastIndex == -1) {
			return null;
		}
		
		if (requestedIndex < 0) {
			requestedIndex = lastIndex;
		}
		
		String prevElement = this.order.get(requestedIndex);
		this.active = requestedIndex;
		
		this.moveToActive();
		
		return this.elements.get(prevElement);
	}
	
	public Group getGroup() {
		return this.group;
	}
	
	public void position() {
		positionCentered();
		positionHorizontally();
	}
	
	public void positionCentered() {
		this.group.setPosition(0.0f, 0.0f, Align.center);
	}
	
	public void positionHorizontally() {
		float spacing = 50f;
		float offset = 0f;
		float menuWidth = 0f;
		float maxHeight = 0f;
		for(Entry<String, MenuElement> element : this.elements.entrySet()) {
			Actor actor = element.getValue().getElement();
			float width = actor.getWidth();
			float height = actor.getHeight();
			float move = width / 2;
			if (maxHeight < height) maxHeight = height;
			menuWidth += offset + move;
			actor.setPosition(offset + move, 0.0f, Align.center);
			offset += width + spacing;
		}
		
		this.group.setHeight(maxHeight);
		this.group.setWidth(menuWidth);
		//this.group.setPosition(menuWidth / this.elements.size() * -1f, 0.0f);
		this.moveToActive(0f);
	}
	
//	public void positionVertically() {
//		int i = this.elements.size();
//		float totalHeight = 0;
//		float maxWidth = 0;
//		
//		for(Entry<String, MenuElement> element : this.elements.entrySet()) {
//			Actor actor = element.getValue().getElement();
//			float height = actor.getHeight();
//			float width = actor.getWidth();
//			totalHeight += height;
//			if (maxWidth < width) maxWidth = width;
//			actor.setPosition(0, height * i, Align.center);
//			i--;
//		}
//		
//		this.group.setHeight(totalHeight);
//		this.group.setWidth(maxWidth);
//		this.group.setPosition(0, (totalHeight / 2) *-1);
//	}
	
	private void buildReflections() {
		this.group.clear();
		
		for(Entry<String, MenuElement> element : this.elements.entrySet()) {
		    this.group.addActor(element.getValue().getElement());
		}
		
		this.order = new ArrayList<String>(this.elements.keySet());
	}
	
}

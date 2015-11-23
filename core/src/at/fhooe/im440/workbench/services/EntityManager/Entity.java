package at.fhooe.im440.workbench.services.EntityManager;

import at.fhooe.im440.workbench.components.Component;
import at.fhooe.im440.workbench.components.SpriteVisual;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.utilities.GenericArrayList;

public abstract class Entity {
	
	protected GenericArrayList<Component> components = new GenericArrayList<Component>();
	
	public abstract void update();
	
	public void activate() {
		ServiceManager.getService(EntityManager.class).addEntity(this);
	}

	public void addComponentsToManagers() {
		for (Component component : this.components) {
			component.addToManager();
		}
	}
	
	public void removeComponentsFromManagers() {
		for (Component component : this.components) {
			component.removeFromManager();
		}
	}
	
	public void deactivate() {
		ServiceManager.getService(EntityManager.class).removeEntity(this);
	}
	
	public <T extends Component> T getComponent(Class<T> type) {
		return this.components.getFirst(type);
	}
	
	public <T extends Component> boolean hasComponent(Class<T> type) {
		return this.components.hasOne(type);
	}
	
	public Entity addComponent(Component component) {
		component.setEntity(this);
		components.add(component);
		
		return this;
	}
	
	public Entity addComponents(Component... components) {
		for (Component component : components) {
			this.addComponent(component);
		}
		
		return this;
	}
	
	public <T extends Component> T removeComponent(Class<T> type) {
		return this.components.removeFirst(type);
	}
	
}

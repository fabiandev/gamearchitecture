package at.fhooe.im440.workbench.services.EntityManager;

import at.fhooe.im440.workbench.components.Component;
import at.fhooe.im440.workbench.utilities.GenericArrayList;

public abstract class Entity {
	
	protected GenericArrayList<Component> components = new GenericArrayList<Component>();
	
	public abstract void update();
	
	public <T extends Component> T getComponent(Class<T> type) {
		return this.components.getFirst(type);
	}
	
	public boolean addComponent(Component component) {
		return this.components.add(component);
	}
	
	public void addComponents(Component... components) {
		for (Component component : components) {
			this.addComponent(component);
		}
	}
	
	public <T extends Component> T removeComponent(Class<T> type) {
		return this.components.removeFirst(type);
	}
	
}

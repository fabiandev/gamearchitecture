package at.fhooe.im440.workbench.services.EntityManager;

import java.util.ArrayList;

import at.fhooe.im440.workbench.entities.Cogwheel;
import at.fhooe.im440.workbench.entities.Spring;
import at.fhooe.im440.workbench.services.Service;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.utilities.GenericArrayList;

public class EntityManager implements Service {

	private GenericArrayList<Entity> entities = new GenericArrayList<Entity>();

	public boolean addEntity(Entity entity) {
		return this.entities.add(entity);
	}
	
	public boolean removeEntity(Entity entity) {
		return this.entities.remove(entity);
	}
	
	public void deactivateAllEntities() {
		for (Entity entity : this.entities) {
			entity.deactivateComponents();
		}
		
		GenericArrayList<Entity> entities = this.entities.clone();
		
		for (Entity entity : entities) {
			entity.deactivate();
		}
	}
	
	public <T> int removeAll(Class<T> type) {
		return this.entities.removeAll(type);
	}
	
	public ArrayList<Spring> getSprings() {
		return this.entities.getAll(Spring.class);
	}
	
	public ArrayList<Cogwheel> getCogwheels() {
		return this.entities.getAll(Cogwheel.class);
	}
	
	@Override
	public void activate() {
		ServiceManager.addService(this);
	}

	@Override
	public void deactivate() {
		ServiceManager.removeService(this.getClass());
	}
	
	@Override
	public void update() {
		for (Entity entity : this.entities) {
			entity.update();
		}
	}

}

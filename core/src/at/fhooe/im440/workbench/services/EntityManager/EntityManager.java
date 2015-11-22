package at.fhooe.im440.workbench.services.EntityManager;

import at.fhooe.im440.workbench.components.Collider;
import at.fhooe.im440.workbench.components.StaticPose;
import at.fhooe.im440.workbench.services.Service;
import at.fhooe.im440.workbench.utilities.GenericArrayList;

public class EntityManager implements Service {

	private GenericArrayList<Entity> entities = new GenericArrayList<Entity>();

	public boolean addEntity(Entity entity) {
		return this.entities.add(entity);
	}
	
	public boolean removeEntity(Entity entity) {
		return this.entities.remove(entity);
	}
	
	public <T> int removeAll(Class<T> type) {
		return this.entities.removeAll(type);
	}
	
	@Override
	public void update() {
		for (Entity entity : this.entities) {
			entity.update();
		}
	}

}

package at.fhooe.im440.workbench.services.EntityManager;

import at.fhooe.im440.workbench.components.Component;
import at.fhooe.im440.workbench.services.Service;

public class EntityFactory implements Service {
	
	private Entity lastEntity;
	
	public Entity getLastEntity() {
		return this.lastEntity;
	}
	
	@SuppressWarnings("unchecked")
	public <e extends Entity> void generateEntity(Class<e> entity, Component... components) {
		// TODO: Change, if too weird.
		e temp = null;
		try {
			temp = (e) entity.newInstance().addComponents(components);
			temp.addComponentsToManagers();
			temp.activate();
			this.lastEntity = temp;
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public Entity cloneEntity(Entity entity) {
		// Clone Entity here, make sure that properties are copied by value!
		return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}

package at.fhooe.im440.workbench.components;

import at.fhooe.im440.workbench.services.EntityManager.Entity;

public abstract class BaseComponent implements Component {

	private Entity entity;
	
	public Entity getEntity() {
		return this.entity;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
}

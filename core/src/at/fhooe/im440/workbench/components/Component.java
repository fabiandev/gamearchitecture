package at.fhooe.im440.workbench.components;

import at.fhooe.im440.workbench.services.EntityManager.Entity;

public abstract class Component {
	
	private Entity entity;
	
	public abstract void activate();
	
	public abstract void deactivate();
	
	public Entity getEntity() {
		return this.entity;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
}

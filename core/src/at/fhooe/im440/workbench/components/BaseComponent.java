package at.fhooe.im440.workbench.components;

import at.fhooe.im440.workbench.services.EntityManager.Entity;

public abstract class BaseComponent implements Component {

	private Entity entity;
	
	private boolean active = true;
	
	public Component clone() {
		Component dummy = null;
		try {
			dummy = (Component) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dummy;
	}
	
	public Entity getEntity() {
		return this.entity;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	public boolean isActive() {
		return this.active;
	}
	
	public void activate() {
		this.active = true;
	}
	
	public void deactivate() {
		this.active = false;
	}
	
}

package at.fhooe.im440.workbench.components;

import at.fhooe.im440.workbench.services.EntityManager.Entity;

public interface Component {
	
	public Entity getEntity();
	
	public void setEntity(Entity entity);
	
	public void activate();
	
	public void deactivate();
	
}

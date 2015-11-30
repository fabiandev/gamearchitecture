package at.fhooe.im440.workbench.services.PhysicsEngine;

import at.fhooe.im440.workbench.entities.Spring;

public interface PhysicsObject {

	public void update(float delta);
	public float getMass();
	public void attachToSpring(Spring spring);
	public void detachFromSpring();
	
}

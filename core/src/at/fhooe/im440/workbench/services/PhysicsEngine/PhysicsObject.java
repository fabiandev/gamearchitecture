package at.fhooe.im440.workbench.services.PhysicsEngine;

import at.fhooe.im440.workbench.components.Pose;

public interface PhysicsObject {

	public void update(float delta);
	public float getMass();
	public Pose getPose();
	public float getVelocityX();
	public float getVelocityY();
	public float getAccelerationX();
	public float getAccelerationY();
	public void applyForce(float forceX, float forceY);
	public void resetForce();
	
}

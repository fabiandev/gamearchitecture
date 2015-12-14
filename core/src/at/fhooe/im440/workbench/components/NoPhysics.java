package at.fhooe.im440.workbench.components;

import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.PhysicsEngine.PhysicsEngine;
import at.fhooe.im440.workbench.services.PhysicsEngine.PhysicsObject;

public class NoPhysics extends BaseComponent implements PhysicsObject {

	private Pose pose;
	private PhysicsEngine physicsEngine;

	public NoPhysics() {
		this.physicsEngine = ServiceManager.getService(PhysicsEngine.class);
	}
	
	@Override
	public void activate() {
		this.physicsEngine.addPhysicsObject(this);
		this.pose = this.getEntity().getComponent(Pose.class);
	}

	@Override
	public void deactivate() {
		this.physicsEngine.removePhysicsObject(this);
	}
	
	@Override
	public void applyForce(float forceX, float forceY) {
		
	}
	
	@Override
	public void update(float timeStep) {
		
	}
	
	public NoPhysics setMass(float mass) {
		return this;
	}

	public float getMass() {
		return 0f;
	}

	public float getVelocityX() {
		return 0f;
	}
	
	public float getVelocityY() {
		return 0f;
	}
	
	public float getAccelerationX() {
		return 0f;
	}
	
	public float getAccelerationY() {
		return 0f;
	}

	@Override
	public Pose getPose() {
		return this.pose;
	}
	
	public void resetForce() {
		
	}

}

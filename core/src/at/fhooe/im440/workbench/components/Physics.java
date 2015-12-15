package at.fhooe.im440.workbench.components;

import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.PhysicsEngine.PhysicsEngine;
import at.fhooe.im440.workbench.services.PhysicsEngine.PhysicsObject;

public class Physics extends BaseComponent implements PhysicsObject {

	private Pose pose;
	private PhysicsEngine physicsEngine;
	
	private float mass = 2f;
	
	private float accelerationX = 0f;
	private float accelerationY = 0f;
	
	//private float decayX = 0.5f;
	private float decayY = 0.5f;
	
	private float velocityX = 0f;
	private float velocityY = 0f;
	
	private float forceX = 0f;
	private float forceY = 0f;
	private float gravity;

	public Physics() {
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
		this.forceX += forceX;
		this.forceY += forceY;
	}
	
	@Override
	public void update(float timeStep) {
		this.gravity = this.physicsEngine.getGravity();
		
		float lastAccelerationX = this.accelerationX;
		float lastAccelerationY = this.accelerationY;
		
		this.accelerationY = this.gravity/* * this.pose.getPosY()*/ - this.velocityY * this.decayY;
		
		if (this.mass != 0) {
			this.accelerationX = this.forceX / this.mass;
			this.accelerationY += this.forceY / this.mass;
		}
		
		float avgAccelerationX = (lastAccelerationX + this.accelerationX) / 2f;
		float avgAccelerationY = (lastAccelerationY + this.accelerationY) / 2f;
		
		this.velocityX += avgAccelerationX * timeStep;
		this.velocityY += avgAccelerationY * timeStep;
		
		float positionX = this.velocityX * timeStep + (0.5f * avgAccelerationX * timeStep * timeStep);
		float positionY = this.velocityY * timeStep + (0.5f * avgAccelerationY * timeStep * timeStep);
		
		this.pose.setPos(this.pose.getPosX() + positionX, this.pose.getPosY() + positionY);
		
		this.resetForce();
	}
	
	private void resetForce() {
		this.forceX = 0f;
		this.forceY = 0f;
	}
	
	public Physics setMass(float mass) {
		this.mass = mass;
		return this;
	}

	public float getMass() {
		return this.mass;
	}

	public float getVelocityX() {
		return this.velocityX;
	}
	
	public float getVelocityY() {
		return this.velocityY;
	}
	
	public float getAccelerationX() {
		return this.accelerationX;
	}
	
	public float getAccelerationY() {
		return this.accelerationY;
	}

	@Override
	public Pose getPose() {
		return this.pose;
	}
	

}

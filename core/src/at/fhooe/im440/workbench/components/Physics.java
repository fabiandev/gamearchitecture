package at.fhooe.im440.workbench.components;

import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.PhysicsEngine.PhysicsEngine;
import at.fhooe.im440.workbench.services.PhysicsEngine.PhysicsObject;

public class Physics extends Component implements PhysicsObject {

	private Pose pose;
	private PhysicsEngine physicsEngine;
	
	private float mass = 2f;
	
	private float accelerationX = 0f;
	private float accelerationY = 0f;
	
	//private float decayX = 0.5f;
	private float decayY = 0.5f;
	
	private float velocityX = 0f;
	private float velocityY = 0f;
	
	private float maxVelocityX = 1000f;
	private float maxVelocityY = 1000f;
	
	private float forceX = 0f;
	private float forceY = 0f;
	private float gravity;
	
	private boolean active = false;

	public Physics() {
		this.physicsEngine = ServiceManager.getService(PhysicsEngine.class);
	}
	
	@Override
	public void activate() {
		this.active = true;
		this.physicsEngine.addPhysicsObject(this);
		this.pose = this.getEntity().getComponent(Pose.class);
	}

	@Override
	public void deactivate() {
		this.active = false;
		this.physicsEngine.removePhysicsObject(this);
	}
	
	@Override
	public void applyForce(float forceX, float forceY) {
		if (!active) {
			return;
		}
		
		this.forceX += forceX;
		this.forceY += forceY;
	}
	
	@Override
	public void update(float timeStep) {
//		if (this.getEntity().hasComponent(Collider.class)) {
//			if (this.getEntity().getComponent(Collider.class).isColliding()) {
//				return;
//			}
//		}
		
		this.gravity = this.physicsEngine.getGravity();
		
		float lastAccelerationX = this.accelerationX;
		float lastAccelerationY = this.accelerationY;
		
		this.accelerationY = this.gravity - this.velocityY * this.decayY;
		
		if (this.mass != 0) {
			this.accelerationX = this.forceX / this.mass;
			this.accelerationY += this.forceY / this.mass;
		}
		
		float avgAccelerationX = (lastAccelerationX + this.accelerationX) / 2f;
		float avgAccelerationY = (lastAccelerationY + this.accelerationY) / 2f;
		
		float oldVelocityX = this.velocityX;
		float oldVelocityY = this.velocityY;
		
		this.velocityX += avgAccelerationX * timeStep;
		this.velocityY += avgAccelerationY * timeStep;
		
		if (Math.abs(this.velocityX) > this.maxVelocityX) {
			this.velocityX = oldVelocityX;
		}
		
		if (Math.abs(this.velocityY) > this.maxVelocityY) {
			this.velocityY = oldVelocityY;
		}

		
		float distanceX = this.velocityX * timeStep + (0.5f * avgAccelerationX * timeStep * timeStep);
		float distanceY = this.velocityY * timeStep + (0.5f * avgAccelerationY * timeStep * timeStep);
		
		this.pose.setPos(this.pose.getPosX() + distanceX, this.pose.getPosY() + distanceY);
		
		this.resetForce();
	}
	
	private void resetForce() {
		this.forceX = 0f;
		this.forceY = 0f;
	}
	
	public void reset() {
		this.resetForce();
		this.velocityX = 0f;
		this.velocityY = 0f;
		this.accelerationX = 0f;
		this.accelerationY = 0f;
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

	@Override
	public PhysicsObject setMaxVelocity(float maxVelocityX, float maxVelocityY) {
		this.maxVelocityX = maxVelocityX;
		this.maxVelocityY = maxVelocityY;
		return this;
	}
	

}

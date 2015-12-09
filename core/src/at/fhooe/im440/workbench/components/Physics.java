package at.fhooe.im440.workbench.components;

import at.fhooe.im440.workbench.entities.Spring;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.PhysicsEngine.PhysicsEngine;
import at.fhooe.im440.workbench.services.PhysicsEngine.PhysicsObject;

public class Physics extends BaseComponent implements PhysicsObject {

	private Pose pose;
	private PhysicsEngine physicsEngine;
	private Spring spring;
	
	private float mass = 10f;
	private float acceleration = 0;
	private float decay = 0.5f;
	private float velocity = 0f;
	private float force = 0f;
	private float gravity;

	public Physics(/*Spring spring*/) {
		this.physicsEngine = ServiceManager.getService(PhysicsEngine.class);
		this.acceleration = this.physicsEngine.getGravity();
		//this.spring = spring;
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
	
	public void applyForce(float force) {
		this.force += force;
	}

	@Override
	public void update(float timeStep) {
		this.gravity = this.physicsEngine.getGravity();
		
		float lastAcceleration = this.acceleration;
		
		this.acceleration = this.gravity * this.pose.getPosY() - this.velocity * this.decay;
		//this.acceleration += this.mass / this.force;
		
		float avgAcceleration = (lastAcceleration + this.acceleration) / 2f;
		
		this.velocity += avgAcceleration * timeStep;
		
		float position = this.velocity * timeStep + (0.5f * avgAcceleration * timeStep * timeStep);
		
		this.pose.setPos(this.pose.getPosX(), this.pose.getPosY() + position);
		
		this.force = 0f;
		
		//this.force = this.mass / this.acceleration;
		
		
		//this.spring.applyForce(0f, this.force);
		
		
		//this.acceleration = this.spring.getForce() / this.mass;
		
		//this.force = this.mass / this.acceleration;
		
		
		
		
		//this.velocity.x += this.acceleration * Math.cos(this.pose.getAngle()) * delta;
		//this.velocity.y += this.acceleration * /*Math.sin(this.pose.getAngle()) */ delta;
		
//		float x = this.pose.getPosX()+this.velocity.x*delta;
//		float y = this.pose.getPosY()+this.velocity.y*delta;
		
		
//		float lastAcceleration = this.acceleration;
//		float position = (float) (velocity.x * timeStep + (0.5 * lastAcceleration * timeStep * timeStep));
//		this.acceleration = this.spring.getForce() / this.mass;
//		float avgAcceleration = (lastAcceleration + this.acceleration) / 2;
//		this.velocity.x += avgAcceleration * timeStep;

		// this.velocity.x += acceleration * Math.cos(this.pose.getAngle()) *
		// delta;
		// this.velocity.y += acceleration * Math.sin(this.pose.getAngle()) *
		// delta;
		//
		// this.pose.setAngle((float)Math.toRadians(90));
		//
		// this.pose.setPos(
		// this.pose.getPosX() + this.velocity.x,
		// this.pose.getPosY() + this.velocity.y
		// );
		//
		// this.velocity.x *= this.velocityDecay;
		// this.velocity.y *= this.velocityDecay;

		// System.out.println("Velocity:");
		// System.out.println(this.velocity);
		// System.out.println();
		// System.out.println("Position:");
		// System.out.print(this.pose.getPosX());
		// System.out.print(" ");
		// System.out.println(this.pose.getPosY());
		// System.out.println("\n");
	}
	
	public Physics setMass(float mass) {
		this.mass = mass;
		return this;
	}

	public float getMass() {
		return this.mass;
	}

	public float getVelocity() {
		return this.velocity;
	}
	
	public float getForce() {
		return this.force;
	}
	
	public float getDecay() {
		return this.decay;
	}
	
	public float getAcceleration() {
		return this.acceleration;
	}

}

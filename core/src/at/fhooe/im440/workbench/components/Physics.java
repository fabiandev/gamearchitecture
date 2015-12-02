package at.fhooe.im440.workbench.components;

import com.badlogic.gdx.math.Vector2;

import at.fhooe.im440.workbench.entities.Spring;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.PhysicsEngine.PhysicsEngine;
import at.fhooe.im440.workbench.services.PhysicsEngine.PhysicsObject;

public class Physics extends BaseComponent implements PhysicsObject {

	private Pose pose;
	private Spring spring;
	private float mass = 10f;
	public float acceleration = 0;
	//private Vector2 velocity = new Vector2(0, 0);
	private float velocity = 0f;
	//private float force = 0f;
	// private float velocityDecay = 0.9f;

	@Override
	public void activate() {
		ServiceManager.getService(PhysicsEngine.class).addPhysicsObject(this);
		this.pose = this.getEntity().getComponent(Pose.class);
	}

	@Override
	public void deactivate() {
		ServiceManager.getService(PhysicsEngine.class).removePhysicsObject(this);

	}

	public void attachToSpring(Spring spring) {
		this.spring = spring;
	}

	public void detachFromSpring() {
		this.spring = null;
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

	@Override
	public void update(float delta) {
		float lastAcceleration = this.acceleration;
		//this.acceleration = this.spring.getForce() / this.mass;
		this.acceleration = -9.81f * this.pose.getPosY() - this.velocity * 0.5f;
		float avgAcceleration = (lastAcceleration + this.acceleration) / 2f;
		this.velocity += avgAcceleration * delta;
		float position = this.velocity * delta - (0.5f * avgAcceleration * delta * delta);
		//this.force = this.mass / this.acceleration;
		this.pose.setPos(this.pose.getPosX(), this.pose.getPosY() + position);
		
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

}

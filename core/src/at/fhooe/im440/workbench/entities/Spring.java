package at.fhooe.im440.workbench.entities;

import java.util.ArrayList;

import at.fhooe.im440.workbench.components.Physics;
import at.fhooe.im440.workbench.services.EntityManager.Entity;
import at.fhooe.im440.workbench.services.PhysicsEngine.PhysicsObject;

public class Spring extends Entity {

	ArrayList<PhysicsObject> attachedObjects = new ArrayList<PhysicsObject>();
	
	float force;
	//float stretch;
	float acceleration = 9.81f;
	float decay = 0.5f;
	
	public boolean attachObject(PhysicsObject object) {
		object.attachToSpring(this);
		return this.attachedObjects.add(object);
	}
	
	public boolean detachObject(PhysicsObject object) {
		object.detachFromSpring();
		return this.attachedObjects.remove(object);
	}
	
	public float getForce() {
		return calculateForce();
	}

	private float calculateForce() {
		float mass = 0f;
		
		for (PhysicsObject object : this.attachedObjects) {
			mass += object.getMass();
		}
		
		this.force = this.acceleration * -1 * mass;
		
		return this.force;
	}
	
//	private void calculateStretch() {
//		this.stretch = (float) (0.00406 * this.force + 3.43f * 10e-5);
//	}
	
	public float getAcceleration() {
		return this.acceleration;
	}
	
	@Override
	public void update() {
		// calculateForce();
		//calculateStretch();
	}
	
}

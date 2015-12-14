package at.fhooe.im440.workbench.services.PhysicsEngine;

import com.badlogic.gdx.Gdx;

import at.fhooe.im440.workbench.entities.Spring;
import at.fhooe.im440.workbench.services.Service;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.EntityManager.EntityManager;
import at.fhooe.im440.workbench.utilities.GenericArrayList;

public class PhysicsEngine implements Service {

	private GenericArrayList<PhysicsObject> physicsObjects = new GenericArrayList<PhysicsObject>();
//	private GenericArrayList<Spring> springs = new GenericArrayList<Spring>();
	private float accumulator = 0;
	private final float timeStep = 1f/120f;

	private float gravity = -9.81f;
	
	public boolean addPhysicsObject(PhysicsObject physicsObject) {
		return this.physicsObjects.add(physicsObject);
	}

	public boolean removePhysicsObject(PhysicsObject physicsObject) {
		return this.physicsObjects.remove(physicsObject);
	}

	public <T> int removePhysicsObject(Class<T> type) {
		return this.physicsObjects.removeAll(type);
	}
	
//	public boolean addSpring(Spring spring) {
//		return this.springs.add(spring);
//	}
//
//	public boolean removeSpring(Spring spring) {
//		return this.springs.remove(spring);
//	}
//
//	public <T> int removeAllSprings(Class<T> type) {
//		return this.springs.removeAll(type);
//	}

	public float getGravity() {
		return this.gravity;
	}
	
	public void setGravity(float gravity) {
		this.gravity = gravity;
	}
	
	@Override
	public void update() {
		float dt = Gdx.graphics.getDeltaTime();

		this.accumulator += dt;
		
    	while (this.accumulator >= this.timeStep) {
    		for (PhysicsObject physicsObject : this.physicsObjects) {
    			physicsObject.update(timeStep);
    		}
    		
        	this.accumulator -= this.timeStep;
    	}
    	
//    	for (PhysicsObject physicsObject : this.physicsObjects) {
//			physicsObject.resetForce();
//		}
	}

}

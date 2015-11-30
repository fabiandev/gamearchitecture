package at.fhooe.im440.workbench.services.PhysicsEngine;

import com.badlogic.gdx.Gdx;

import at.fhooe.im440.workbench.services.Service;
import at.fhooe.im440.workbench.utilities.GenericArrayList;

public class PhysicsEngine implements Service {

	private GenericArrayList<PhysicsObject> physicsObjects = new GenericArrayList<PhysicsObject>();

	public boolean addPhysicsObject(PhysicsObject physicsObject) {
		return this.physicsObjects.add(physicsObject);
	}

	public boolean removePhysicsObject(PhysicsObject physicsObject) {
		return this.physicsObjects.remove(physicsObject);
	}

	public <T> int removePhysicsObject(Class<T> type) {
		return this.physicsObjects.removeAll(type);
	}

	@Override
	public void update() {
		for (PhysicsObject physicsObject : this.physicsObjects) {
			float delta = Gdx.graphics.getDeltaTime();
			physicsObject.update(delta);
		}
	}

}

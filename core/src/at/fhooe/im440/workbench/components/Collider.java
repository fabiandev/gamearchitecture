package at.fhooe.im440.workbench.components;

import java.util.ArrayList;

import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.ColliderSystem.Collideable;
import at.fhooe.im440.workbench.services.ColliderSystem.ColliderSystem;

public abstract class Collider extends BaseComponent implements Collideable {
	
	protected ArrayList<Collideable> collisions = new ArrayList<Collideable>();

	@Override
	public void addToManager() {
		ServiceManager.getService(ColliderSystem.class).addCollider(this);
		this.activate();
	}

	@Override
	public void removeFromManager() {
		ServiceManager.getService(ColliderSystem.class).removeCollider(this);
		this.deactivate();
	}

	@Override
	public boolean isHit(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHit(Collideable c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHit(BoxCollider c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHit(CircleCollider c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addCollision(Collideable c) {
		return this.collisions.add(c);
	}

	@Override
	public void clearCollisions() {
		this.collisions.clear();
	}

}

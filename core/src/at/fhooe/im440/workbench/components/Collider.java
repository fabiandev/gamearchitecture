package at.fhooe.im440.workbench.components;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.ColliderSystem.Collideable;
import at.fhooe.im440.workbench.services.ColliderSystem.ColliderSystem;

public abstract class Collider extends Component implements Collideable {

	protected ArrayList<Collideable> collisions = new ArrayList<Collideable>();

	protected float hRad;
	protected float vRad;

	// Constructors and getters/setters for fixed height/width included.

	public float getHalfWidth() {
		return hRad;
	}

	public void setWidth(float width) {
		this.hRad = width / 2;
	}

	public float getHalfHeight() {
		return vRad;
	}

	public void setHeight(float height) {
		this.vRad = height / 2;
	}

	public Vector2 getCenter() {
		return new Vector2(this.getEntity().getComponent(Pose.class).getPosX(),
				this.getEntity().getComponent(Pose.class).getPosY());
	}
	
	public ArrayList<Collideable> getCollisions() {
		return this.collisions;
	}

	@Override
	public void activate() {
		ServiceManager.getService(ColliderSystem.class).addCollider(this);
	}

	@Override
	public void deactivate() {
		ServiceManager.getService(ColliderSystem.class).removeCollider(this);
	}

//	@Override
//	public boolean isHit(Collideable c) {
//
//		if (c instanceof CircleCollider) {
//			return this.isHit((CircleCollider) c);
//		}
//
//		if (c instanceof BoxCollider) {
//			return this.isHit((BoxCollider) c);
//		}
//
//		return false;
//	}

	// Self included method
	@Override
	public boolean isColliding() {
		return !this.collisions.isEmpty();
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

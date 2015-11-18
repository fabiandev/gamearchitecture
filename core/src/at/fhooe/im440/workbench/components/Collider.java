package at.fhooe.im440.workbench.components;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.ColliderSystem.Collideable;
import at.fhooe.im440.workbench.services.ColliderSystem.ColliderSystem;

public abstract class Collider extends BaseComponent implements Collideable {

	protected ArrayList<Collideable> collisions = new ArrayList<Collideable>();

	protected float width;
	protected float height;

	// Constructors and getters/setters for fixed height/width included.
	public Collider(float width) {
		super();
		this.width = width;
	}

	public Collider(float width, float height) {
		super();
		this.width = width;
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public Vector2 getCenter() {
		return new Vector2(this.getEntity().getComponent(Pose.class).getPosX(),
				this.getEntity().getComponent(Pose.class).getPosY());
	}

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
	public boolean isHit(Collideable c) {

		if (c instanceof CircleCollider) {
			return this.isHit((CircleCollider) c);
		}

		if (c instanceof BoxCollider) {
			return this.isHit((BoxCollider) c);
		}

		return false;
	}

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

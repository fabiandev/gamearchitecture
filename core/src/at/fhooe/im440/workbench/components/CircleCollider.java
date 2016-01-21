package at.fhooe.im440.workbench.components;

import com.badlogic.gdx.math.Vector2;

import at.fhooe.im440.workbench.services.ColliderSystem.Collideable;

public class CircleCollider extends Collider {

	public CircleCollider(float width) {
		this.hRad = width / 2f;
	}

	@Override
	public boolean isHit(float x, float y) {

		float w = this.getHalfWidth();
		Vector2 center = this.getCenter();

		float deltaX = (float) Math.pow(x - center.x, 2);
		float deltaY = (float) Math.pow(y - center.y, 2);
		float deltaRad = (float) Math.sqrt(deltaX + deltaY);

		return deltaRad <= w;
	}

	@Override
	public boolean isHit(BoxCollider c) {
		// TODO Auto-generated method stub
		return c.isHit(this);
	}

	@Override
	public boolean isHit(CircleCollider c) {

		float w = this.getHalfWidth();
		Vector2 center = this.getCenter();

		float otherW = c.getEntity().getComponent(Collider.class).getHalfWidth();
		Vector2 otherCenter = c.getCenter();
		
		float deltaX = (float) Math.pow(otherCenter.x - center.x, 2);
		float deltaY = (float) Math.pow(otherCenter.y - center.y, 2);
		float deltaRad = (float) Math.sqrt(deltaX + deltaY);

		return deltaRad <= (w + otherW);
	}
	
	@Override
	public boolean isHit(Collideable other) {
		return other.isHit(this);
	}

}

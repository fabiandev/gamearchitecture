package at.fhooe.im440.workbench.components;

import com.badlogic.gdx.math.Vector2;

public class CircleCollider extends Collider {

	public CircleCollider(float width) {
		super(width);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isHit(float x, float y) {

		float w = this.getWidth();
		Vector2 center = this.getCenter();

		float deltaX = (float) Math.pow(x - center.x, 2);
		float deltaY = (float) Math.pow(y - center.y, 2);
		float deltaRad = (float) Math.sqrt(deltaX + deltaY);

		return deltaRad <= w;
	}

	@Override
	public boolean isHit(BoxCollider c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHit(CircleCollider c) {

		float w = this.getWidth();
		Vector2 center = this.getCenter();

		float otherW = c.getEntity().getComponent(Collider.class).getWidth();
		Vector2 otherCenter = c.getCenter();
		
		float deltaX = (float) Math.pow(otherCenter.x - center.x, 2);
		float deltaY = (float) Math.pow(otherCenter.y - center.y, 2);
		float deltaRad = (float) Math.sqrt(deltaX + deltaY);

		return deltaRad <= (w + otherW);
	}

}

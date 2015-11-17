package at.fhooe.im440.workbench.components;

import com.badlogic.gdx.math.Vector2;

public class CircleCollider extends Collider {

	@Override
	public boolean isHit(float x, float y) {
		
		float w = this.getEntity().getComponent(SpriteVisual.class).getWidth();
		Vector2 center = this.getEntity().getComponent(SpriteVisual.class).getCenterCoordinates();
		
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
		
		float w = this.getEntity().getComponent(SpriteVisual.class).getWidth();
		Vector2 center = this.getEntity().getComponent(SpriteVisual.class).getCenterCoordinates();
		
		float otherW = c.getEntity().getComponent(SpriteVisual.class).getWidth();
		Vector2 otherCenter = c.getEntity().getComponent(SpriteVisual.class).getCenterCoordinates();
		
		float deltaX = (float) Math.pow(otherCenter.x - center.x, 2);
		float deltaY = (float) Math.pow(otherCenter.y - center.y, 2);
		float deltaRad = (float) Math.sqrt(deltaX + deltaY);
		
		return deltaRad <= (w + otherW);
	}

}

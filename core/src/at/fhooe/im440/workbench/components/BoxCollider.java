package at.fhooe.im440.workbench.components;

import com.badlogic.gdx.math.Vector2;

public class BoxCollider extends Collider {

	public BoxCollider(float width, float height) {
		this.hRad = width / 2f;
		this.vRad = height / 2f;
	}
	
	private Vector2 getProjectedCoordinates(float x, float y) {
		Pose pose = this.getEntity().getComponent(Pose.class);
		
		float angle = pose.getAngle();
		angle += angle < 0f ? (float) Math.PI : 0f;	// Assure to map negative degree values to 90 - 180 degrees.
		float centerX = pose.getPosX();
		float centerY = pose.getPosY();
		
		/*
		 * Set cursor position & angle in relation to center of box
		 */
		float cursorX = x - centerX;
		float cursorY = y - centerY;
		
		float cursorDelta = (float) Math.sqrt(Math.pow(cursorX, 2) + Math.pow(cursorY, 2));
		float cursorAngle = (float) Math.atan(cursorY / cursorX);	// in radians!
		
		// Also map the angle to 90 - 180 degrees, if initial value would be negative
		cursorAngle += cursorAngle < 0f ? (float) Math.PI : 0f;
		
		/*
		 * Otherwise, calculate angle along which cursor position will be projected
		 * New X & Y coordinates are result of transforming polar coordinates in cartesian coordinates
		 */
		float corrAngle = Math.abs(cursorAngle - angle);
		cursorX = cursorDelta * (float) Math.cos(corrAngle);
		cursorY = cursorDelta * (float) Math.sin(corrAngle);
		
		/*
		 * Return cartesian coordinates in relation to the rectangle, where center is
		 * the origin of the coordinate system
		 */
		return new Vector2(cursorX, cursorY);
	}

	@Override
	public boolean isHit(float x, float y) {
		Vector2 projected = this.getProjectedCoordinates(x, y);
		
		/*
		 * If projected is null, then distance is great enough to not collide with the rectangle
		 */
		if (projected == null) {
			return false;
		}
		
		/*
		 * If projected x & y are both within a projected quarter of the rectangle,
		 * then a hit is performed
		 */
		if (projected.x < hRad && projected.y < vRad) {
			return true;
		}
		return false;
		
	}

	@Override
	public boolean isHit(BoxCollider c) {
		
		// Warning; only contains colliding checks if angle is 0
//		float x = c.getCenter().x;
//		float y = c.getCenter().y;
//		float w = c.getHalfWidth();
//		float h = c.getHalfHeight();
//		
//		Vector2[] corners = { new Vector2(x + w, y + h), 
//				new Vector2(x + w, y - h), 
//				new Vector2(x - w, y + h), 
//				new Vector2(x - w, y - h) };
//		
//		for (Vector2 v : corners) {
//			if (this.isHit(v.x, v.y)) {
//				return true;
//			}
//		}
		
		return false;
	}
	
	public boolean isHit(CircleCollider c) {
		
		/*
		 * Get already projected coordinates of the circle
		 */
		Vector2 circle = this.getProjectedCoordinates(c.getCenter().x, c.getCenter().y);
		float radius = c.getHalfWidth();
		
		/*
		 * Because of the rectangle center being the origin of the coordinate system,
		 * the deltas are the projected circle center coordinates
		 */
		float deltaX = Math.abs(circle.x);
		float deltaY = Math.abs(circle.y);
		
		/*
		 * If both deltas subtracted by radius are smalle than halfHeight & halfWidth,
		 * the circle collides with the rectangle
		 */
		if ((deltaX - radius) < hRad && (deltaY - radius) < vRad) {
			return true;
		}
		
		return false;
	}

}

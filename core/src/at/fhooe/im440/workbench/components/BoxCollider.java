package at.fhooe.im440.workbench.components;

import com.badlogic.gdx.math.Vector2;

public class BoxCollider extends Collider {

	public BoxCollider(float width, float height) {
		this.hRad = width / 2f;
		this.vRad = height / 2f;
	}

	@Override
	public boolean isHit(float x, float y) {
		Vector2 center = this.getCenter();
		float angle = this.getEntity().getComponent(Pose.class).getAngle();
		
		// Get the distance (delta) between this center point and mouse pointer
		float deltaX = (float) Math.pow(center.x - x, 2);
		float deltaY = (float) Math.pow(center.y - y, 2);
		float delta = (float) Math.sqrt(deltaX + deltaY);
		
		// Calculate angle of mouse pointer to center point
		float centerAngle = (float) Math.toDegrees(Math.asin(Math.sqrt(deltaY) / delta));

		// Map the rotation of this rectangle to the position of the mouse pointer
		float xCorr = center.x + delta * (float) Math.cos(Math.toRadians(centerAngle + angle));
		float yCorr = center.y + delta * (float) Math.sin(Math.toRadians(centerAngle + angle));

		// Calculate, if position of mouse pointer is inside upper right quadrant of this rectangle
		// (Due to only positive deltaX/deltaY values, pointer position gets mapped to a coordinate system
		// with origin on center point of rectangle)
		if ((xCorr < (center.x + this.hRad) && yCorr < (center.y + this.vRad)) 
				// Following comparison needed because of pointing pose to mouse pointer position
				|| x == center.x && y == center.y) {
			return true;
		}
		return false;
	}
	
//	@Override
//	public boolean isHit(float x, float y) {
//		/*
//		 * Abstand Cursor von Rechteckposition (radius): Math.sqrt(Math.pow(pose.getX() - cursor.getX(), 2) + Math.pow(pose.getY() - cursor.getY(), 2))
//		 * Winkel Cursor zu Rechteck: Math.atan(Math.abs(pose.getY() - cursor.getY()) / Math.abs(pose.getX() - cursor.getX()))
//		 * Korrigierter winkel: Math.abs(pose.getAngle() - cursorAngle)
//		 * 
//		 * Umrechnung Polarkoordinaten zu kartesische Koordinaten: X -> radius * Math.cos(corrAngle), Y -> radius * Math.sin(corrAngle)
//		 * 
//		 * Vergleich ob projizierte Position kürzer als halbeLänge, oder niedriger als halbeHöhe
//		 */
//		
//		return false;
//	}

	@Override
	public boolean isHit(BoxCollider c) {
		
		// Warning; only contains colliding checks if angle is 0
		float x = c.getCenter().x;
		float y = c.getCenter().y;
		float w = c.getHalfWidth();
		float h = c.getHalfHeight();
		
		Vector2[] corners = { new Vector2(x + w, y + h), 
				new Vector2(x + w, y - h), 
				new Vector2(x - w, y + h), 
				new Vector2(x - w, y - h) };
		
		for (Vector2 v : corners) {
			if (this.isHit(v.x, v.y)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isHit(CircleCollider c) {
		/*
		 * Roadmap: 
		 * ---------
		 * 
		 * 1. Calculate angle between this.hRad and delta of center points 
		 * 2. Get maximum delta inside the box. 
		 * ----- 
		 * 4. Do the same shit when angle of box collider is other than 0.
		 */
		float hRad = this.getHalfWidth();
		float vRad = this.getHalfHeight();
		float angle = this.getEntity().getComponent(Pose.class).getAngle();
		float radius = c.getHalfWidth();

		Vector2 centerBox = this.getCenter();
		Vector2 centerCircle = c.getCenter();
		Vector2 circleCorrected;

		// Get diagonal angle of box (when hypotenuse is pointing directly into
		// the corner)
		float diagonalAngle = (float) Math.toDegrees(Math.atan(vRad / hRad));

		// 1. Calculate angle in center of box
		float deltaX = (float) Math.pow(centerBox.x - centerCircle.x, 2);
		float deltaY = (float) Math.pow(centerBox.y - centerCircle.y, 2);
		float delta = (float) Math.sqrt(deltaX + deltaY);

		float centerAngle = (float) Math.toDegrees(Math.asin(Math.sqrt(deltaY) / delta));

		// 4. Rotate the colliding circle around angle of box
		centerAngle += angle;
		
		// Calculate new coordinates after circle transformation
		// Calculation: delta * cos(centerAngle + angle) + current centerBox.x,
		// y dimension with sin
		circleCorrected = new Vector2(centerBox.x + (float) (delta * Math.cos(Math.toRadians(centerAngle))),
				centerBox.y + (float) (delta * Math.sin(Math.toRadians(centerAngle))));
		deltaX = (float) Math.sqrt(Math.pow(centerBox.x - circleCorrected.x, 2));
		deltaY = (float) Math.sqrt(Math.pow(centerBox.y - circleCorrected.y, 2));
		centerAngle = (float) Math.toDegrees(Math.asin(deltaY / delta));

		// 2. Get maximum delta inside box
		float maxDelta;
		float halfParam = hRad;

		if (centerAngle > diagonalAngle) {
			// If centerAngle is greater than the diagonal angle of the box,
			// invert the angle and use vRad as reference length.
			centerAngle = 90f - centerAngle;
			halfParam = vRad;
		}
		
		maxDelta = halfParam / (float) Math.cos(Math.toRadians(centerAngle));

		// As a second condition, perform check if sum of vRad or hRad + Radius
		// < delta of its axis
		// Needed due to a lack of exact values towards the edges.
		boolean x = deltaX < (hRad + radius);
		boolean y = deltaY < (vRad + radius);

		// When rectangle is turned, there seems to be an issue with x && y... No clue.
		return delta < (maxDelta + radius) || (x && y);

	}

}

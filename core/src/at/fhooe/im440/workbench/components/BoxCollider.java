package at.fhooe.im440.workbench.components;

import com.badlogic.gdx.math.Vector2;

public class BoxCollider extends Collider {

	public BoxCollider(float width, float height) {
		this.hRad = width / 2f;
		this.vRad = height / 2f;
	}

	@Override
	public boolean isHit(float x, float y) {
		Pose pose = this.getEntity().getComponent(Pose.class);
		
		float hRad = this.getHalfWidth();
		float vRad = this.getHalfHeight();
		float halfDiagonal = (float) Math.sqrt(Math.pow(hRad, 2) + Math.pow(vRad, 2));
		float angle = pose.getAngle();
		float centerX = pose.getPosX();
		float centerY = pose.getPosY();
		
		/*
		 * Set cursor position & angle in relation to center of box
		 */
		float cursorX = Math.abs(x - centerX);
		float cursorY = Math.abs(y - centerY);
		
		float cursorDelta = (float) Math.sqrt(Math.pow(cursorX, 2) + Math.pow(cursorY, 2));
		float cursorAngle = (float) Math.atan(cursorY / cursorX);	// in radians!
		
		/*
		 * Check if distance of cursor to center is greater than half diagonal
		 * If true, it doesn't hit (halfDiagonal is longest distance inside box)
		 */
		if (cursorDelta > halfDiagonal) {
			return false;
		}
		
		/*
		 * Otherwise, calculate angle along which cursor position will be projected
		 * New X & Y coordinates are result of transforming polar coordinates in cartesian coordinates
		 */
		angle = Math.abs(angle);
		float corrAngle = Math.abs(cursorAngle - angle);
		
		cursorX = cursorDelta * (float) Math.cos(corrAngle);
		cursorY = cursorDelta * (float) Math.sin(corrAngle);
//		System.out.println("Angle: " + Math.toDegrees(angle));
//		System.out.println("X: " + cursorX);
//		System.out.println("Y: " + cursorY);
		
		if (cursorX < hRad && cursorY < vRad) {
			return true;
		}
		return false;
		
	}

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
		
		float circleX = c.getCenter().x;
		float circleY = c.getCenter().y;
		float circleWidth = c.getHalfWidth();
		
		float centerX = this.getCenter().x;
		float centerY = this.getCenter().y;
		
		/*
		 * Get deltaX & deltaY to calculate the angle of the hypotenuse
		 * between circle & rectangle
		 */
		float deltaX = Math.abs(circleX - centerX);
		float deltaY = Math.abs(circleY - centerY);
		float circleAngle = (float) Math.atan(deltaX/deltaY);	// in radians
		
		/*
		 * Get projected coordinates of circle border by transforming 
		 * polar coordinates to cartesian coordinates
		 */
		float borderX = circleWidth * (float) Math.cos(circleAngle);
		float borderY = circleWidth * (float) Math.sin(circleAngle);
		
		/*
		 * Set coordinates to correct value, when comparing position of circle to rectangle
		 */
		borderX *= (centerX - circleX) < 0f ? -1f : 1f;		// Shift x to left, when circle right of rectangle
		borderY *= (centerY - circleY) < 0f ? -1f : 1f;		// Shift y to bottom, when circle above rectangle
		
		float circularX = circleX + borderX;
		float circularY = circleY + borderY;
		
		return this.isHit(circularX, circularY);
	}
	
//	public boolean isHit(CircleCollider c) {
//		/*
//		 * Roadmap: 
//		 * ---------
//		 * 
//		 * 1. Calculate angle between this.hRad and delta of center points 
//		 * 2. Get maximum delta inside the box. 
//		 * ----- 
//		 * 4. Do the same shit when angle of box collider is other than 0.
//		 */
//		float hRad = this.getHalfWidth();
//		float vRad = this.getHalfHeight();
//		float angle = this.getEntity().getComponent(Pose.class).getAngle();
//		float radius = c.getHalfWidth();
//
//		Vector2 centerBox = this.getCenter();
//		Vector2 centerCircle = c.getCenter();
//		Vector2 circleCorrected;
//
//		// Get diagonal angle of box (when hypotenuse is pointing directly into
//		// the corner)
//		float diagonalAngle = (float) Math.toDegrees(Math.atan(vRad / hRad));
//
//		// 1. Calculate angle in center of box
//		float deltaX = (float) Math.pow(centerBox.x - centerCircle.x, 2);
//		float deltaY = (float) Math.pow(centerBox.y - centerCircle.y, 2);
//		float delta = (float) Math.sqrt(deltaX + deltaY);
//
//		float centerAngle = (float) Math.toDegrees(Math.asin(Math.sqrt(deltaY) / delta));
//
//		// 4. Rotate the colliding circle around angle of box
//		centerAngle += angle;
//		
//		// Calculate new coordinates after circle transformation
//		// Calculation: delta * cos(centerAngle + angle) + current centerBox.x,
//		// y dimension with sin
//		circleCorrected = new Vector2(centerBox.x + (float) (delta * Math.cos(Math.toRadians(centerAngle))),
//				centerBox.y + (float) (delta * Math.sin(Math.toRadians(centerAngle))));
//		deltaX = (float) Math.sqrt(Math.pow(centerBox.x - circleCorrected.x, 2));
//		deltaY = (float) Math.sqrt(Math.pow(centerBox.y - circleCorrected.y, 2));
//		centerAngle = (float) Math.toDegrees(Math.asin(deltaY / delta));
//
//		// 2. Get maximum delta inside box
//		float maxDelta;
//		float halfParam = hRad;
//
//		if (centerAngle > diagonalAngle) {
//			// If centerAngle is greater than the diagonal angle of the box,
//			// invert the angle and use vRad as reference length.
//			centerAngle = 90f - centerAngle;
//			halfParam = vRad;
//		}
//		
//		maxDelta = halfParam / (float) Math.cos(Math.toRadians(centerAngle));
//
//		// As a second condition, perform check if sum of vRad or hRad + Radius
//		// < delta of its axis
//		// Needed due to a lack of exact values towards the edges.
//		boolean x = deltaX < (hRad + radius);
//		boolean y = deltaY < (vRad + radius);
//
//		// When rectangle is turned, there seems to be an issue with x && y... No clue.
//		return delta < (maxDelta + radius) || (x && y);
//
//	}

}

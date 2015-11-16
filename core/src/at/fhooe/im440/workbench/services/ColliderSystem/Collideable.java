package at.fhooe.im440.workbench.services.ColliderSystem;

import at.fhooe.im440.workbench.components.BoxCollider;
import at.fhooe.im440.workbench.components.CircleCollider;

public interface Collideable {

	public boolean isHit(float x, float y);
	public boolean isHit(Collideable c);
	public boolean isHit(BoxCollider c);
	public boolean isHit(CircleCollider c);
	public boolean addCollision(Collideable c);
	public void clearCollisions();
	
}

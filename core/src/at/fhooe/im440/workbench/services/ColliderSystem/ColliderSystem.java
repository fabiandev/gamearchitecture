package at.fhooe.im440.workbench.services.ColliderSystem;

import java.util.Iterator;

import at.fhooe.im440.workbench.components.BoxCollider;
import at.fhooe.im440.workbench.components.CircleCollider;
import at.fhooe.im440.workbench.services.Service;
import at.fhooe.im440.workbench.utilities.GenericArrayList;

public class ColliderSystem implements Service {

	private GenericArrayList<Collideable> colliders = new GenericArrayList<Collideable>();

	public boolean addCollider(Collideable c) {
		return this.colliders.add(c);
	}

	public boolean removeCollider(Collideable c) {
		return this.colliders.remove(c);
	}

	public <T> int removeAll(Class<T> type) {
		return this.colliders.removeAll(type);
	}

	@Override
	public void update() {
		Iterator<Collideable> iter = this.colliders.iterator();
		while (iter.hasNext()) {
			Collideable temp = iter.next();
			temp.clearCollisions();

			Iterator<Collideable> subIter = this.colliders.iterator();

			while (subIter.hasNext()) {
				Collideable other = subIter.next();

				// Checking if both objects are not the same (by comparing memory address)
				if (temp != other) {
					
					if ((other instanceof BoxCollider && temp.isHit((BoxCollider) other))
							|| (other instanceof CircleCollider && temp.isHit((CircleCollider) other))) {

						// Throw exception if following returns false?
						temp.addCollision(other);
					}

				}
			}
			
			if (temp instanceof CircleCollider) {
				if (temp.isColliding()) {
					
				} else {
					
				}
			} else if (temp instanceof BoxCollider) {
				if (temp.isColliding()) {
					
				} else {
					
				}
			}
		}
	}

}

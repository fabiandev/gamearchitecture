package at.fhooe.im440.workbench.services.ColliderSystem;

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
		for (Collideable c1 : this.colliders) {
			c1.clearCollisions();
			
			for (Collideable c2 : this.colliders) {
				if (c1 != c2) {
					if (c1.isHit(c2)) {
						c1.addCollision(c2);
					}
				}
			}
		}
	}

}

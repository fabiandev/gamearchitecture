package at.fhooe.im440.workbench.entities;

import at.fhooe.im440.workbench.services.EntityManager.Entity;

public class Spring extends Entity {

	Entity e1;
	Entity e2;
	
	
	float acceleration = 9.81f;
	float decay = 0.5f;
	float stiffness = 10f; // k
	
	
	public Spring(Entity e1, Entity e2) {
		this.e1 = e1;
		this.e2 = e2;
	}
	
//	private void calculateStretch() {
//		this.stretch = (float) (0.00406 * this.force + 3.43f * 10e-5);
//	}
	
	@Override
	public void update() {
		
	}
	
}

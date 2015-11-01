package at.fhooe.im440.workbench.entities;

import at.fhooe.im440.workbench.services.entityManager.Entity;
import at.fhooe.im440.workbench.services.renderSystem.Visual;

public class TestEntity extends Entity implements Visual {

	@Override
	public void update() {
		
	}

	@Override
	public void draw() {
		// System.out.println("Drawing TestEntity..");
	}

}

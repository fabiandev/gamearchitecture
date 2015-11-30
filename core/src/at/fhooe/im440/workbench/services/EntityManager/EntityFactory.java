package at.fhooe.im440.workbench.services.EntityManager;

import at.fhooe.im440.workbench.components.BoxCollider;
import at.fhooe.im440.workbench.components.CircleCollider;
import at.fhooe.im440.workbench.components.Collider;
import at.fhooe.im440.workbench.components.Pose;
import at.fhooe.im440.workbench.entities.CogwheelEntity;
import at.fhooe.im440.workbench.entities.WallEntity;
import at.fhooe.im440.workbench.services.Service;

public class EntityFactory implements Service {
	
	public Entity createCogwheel(float x, float y) {
		return new CogwheelEntity(x, y);
	}
	
	public Entity createWall(float x, float y) {
		return new WallEntity(x, y);
	}
	
	public Entity cloneEntity(Entity entity) {
		if (entity.getComponent(Collider.class) instanceof CircleCollider) {
			return this.createCogwheel(entity.getComponent(Pose.class).getPosX(), entity.getComponent(Pose.class).getPosY());
		}
		if (entity.getComponent(Collider.class) instanceof BoxCollider) {
			return this.createWall(entity.getComponent(Pose.class).getPosX(), entity.getComponent(Pose.class).getPosY());
		}
		return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}

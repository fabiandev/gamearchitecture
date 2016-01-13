package at.fhooe.im440.workbench.services.EntityManager;

import com.badlogic.gdx.graphics.Color;

import at.fhooe.im440.workbench.components.BoxCollider;
import at.fhooe.im440.workbench.components.CircleCollider;
import at.fhooe.im440.workbench.components.Collider;
import at.fhooe.im440.workbench.components.Pose;
import at.fhooe.im440.workbench.entities.Cogwheel;
import at.fhooe.im440.workbench.entities.Wall;
import at.fhooe.im440.workbench.services.Service;
import at.fhooe.im440.workbench.services.ServiceManager;

public class EntityFactory implements Service {
	
	public Entity createCogwheel(float x, float y) {
		return new Cogwheel(x, y);
	}
	
	public Entity createCogwheel(float x, float y, Color color) {
		return new Cogwheel(x, y, color);
	}
	
	public Entity createWall(float x, float y) {
		return new Wall(x, y);
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
	public void activate() {
		ServiceManager.addService(this);
	}

	@Override
	public void deactivate() {
		ServiceManager.removeService(this.getClass());
	}
	
	@Override
	public void update() {
		
	}

}

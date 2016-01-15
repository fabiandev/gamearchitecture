package at.fhooe.im440.workbench.services.EntityManager;

import com.badlogic.gdx.graphics.Color;

import at.fhooe.im440.workbench.components.BoxCollider;
import at.fhooe.im440.workbench.components.CircleCollider;
import at.fhooe.im440.workbench.components.Collider;
import at.fhooe.im440.workbench.components.Component;
import at.fhooe.im440.workbench.components.Pose;
import at.fhooe.im440.workbench.components.Visual;
import at.fhooe.im440.workbench.components.persistables.CogwheelPersistable;
import at.fhooe.im440.workbench.components.persistables.WallPersistable;
import at.fhooe.im440.workbench.entities.Cogwheel;
import at.fhooe.im440.workbench.entities.Wall;
import at.fhooe.im440.workbench.services.Service;
import at.fhooe.im440.workbench.services.ServiceManager;

public class EntityFactory implements Service {
	
	public Entity createCogwheel(float x, float y) {
		return new Cogwheel(x, y);
	}
	
	public Entity createCogwheel(float x, float y, Color color) {
		return new Cogwheel(x, y, 0f, color);
	}
	
	public Entity createCogwheel(float x, float y, float angleRadians) {
		return new Cogwheel(x, y, angleRadians);
	}
	
	public Entity createCogwheel(float x, float y, float angleRadians, Color color) {
		return new Cogwheel(x, y, angleRadians, color);
	}
	
	public Entity createWall(float x, float y) {
		return new Wall(x, y);
	}
	
	public Entity createWall(float x, float y, Color color) {
		return new Wall(x, y, 0f, color);
	}
	
	public Entity createWall(float x, float y, float angleRadians) {
		return new Wall(x, y, angleRadians);
	}
	
	public Entity createWall(float x, float y, float angleRadians, Color color) {
		return new Wall(x, y, angleRadians, color);
	}
	
	public Entity cloneEntity(Entity entity, Component...components) {
		Entity newEntity = null;
		Pose pose = entity.getComponent(Pose.class);
		
		if (entity.getComponent(Collider.class) instanceof CircleCollider) {
			newEntity = this.createCogwheel(pose.getPosX(), pose.getPosY(), pose.getAngle(), entity.getComponent(Visual.class).getColor());
		}
		
		if (entity.getComponent(Collider.class) instanceof BoxCollider) {
			newEntity = this.createWall(pose.getPosX(), pose.getPosY(), pose.getAngle(), entity.getComponent(Visual.class).getColor());
		}
		
		if (newEntity != null) {
			newEntity.addComponents(components);
		}
		
		return newEntity;
	}
	
	public Entity clonePersistableEntity(Entity entity) {
		if (entity.getComponent(Collider.class) instanceof CircleCollider) {
			return this.cloneEntity(entity, new CogwheelPersistable());
		}
		
		if (entity.getComponent(Collider.class) instanceof BoxCollider) {
			return this.cloneEntity(entity, new WallPersistable());
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

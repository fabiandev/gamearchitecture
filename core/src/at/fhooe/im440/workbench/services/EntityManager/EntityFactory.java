package at.fhooe.im440.workbench.services.EntityManager;

import at.fhooe.im440.workbench.components.CircleCollider;
import at.fhooe.im440.workbench.components.CollisionVisual;
import at.fhooe.im440.workbench.components.Component;
import at.fhooe.im440.workbench.components.Editable;
import at.fhooe.im440.workbench.components.SpriteVisual;
import at.fhooe.im440.workbench.components.StaticPose;
import at.fhooe.im440.workbench.entities.TestEntity;
import at.fhooe.im440.workbench.services.Service;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.AssetManager.AssetManager;

public class EntityFactory implements Service {
	
	private Entity lastEntity;
	
	public Entity getLastEntity() {
		return this.lastEntity;
	}
	
	@SuppressWarnings("unchecked")
	private <e extends Entity> Entity createEntity(Class<e> entity, Component... components) {
		// TODO: Change, if too weird.
		e temp = null;
		try {
			temp = (e) entity.newInstance().addComponents(components);
			temp.addComponentsToManagers();
			temp.activate();
			this.lastEntity = temp;
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return temp;
	}
	
	public Entity createCogwheel(float x, float y) {
		CollisionVisual collisionVisual = new CollisionVisual().setRegion(ServiceManager.getService(AssetManager.class).getRegion("cog1_shadow"));
		SpriteVisual spriteVisual = new SpriteVisual(ServiceManager.getService(AssetManager.class).getRegion("cog1")).width(1f).height(1f).setOriginCenter();
		return this.createEntity(TestEntity.class, new StaticPose(x, y), spriteVisual, collisionVisual, new CircleCollider(1f));
	}
	
	public Entity cloneEntity(Entity entity) {
		// Clone Entity here, make sure that properties are copied by value!
		return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}

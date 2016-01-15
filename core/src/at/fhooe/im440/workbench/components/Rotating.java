package at.fhooe.im440.workbench.components;

import com.badlogic.gdx.Gdx;

import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.EntityManager.Entity;
import at.fhooe.im440.workbench.services.UpdateService.UpdateService;
import at.fhooe.im440.workbench.services.UpdateService.Updateable;

public class Rotating extends Component implements Updateable {

	private Entity entity;
	private float speed = 0.1f;
	
	public Rotating() {
		
	}
	
	public Rotating(float speed) {
		this.speed = speed;
	}
	
	@Override
	public void activate() {
		ServiceManager.getService(UpdateService.class).addUpdateable(this);
	}

	@Override
	public void deactivate() {
		ServiceManager.getService(UpdateService.class).removeUpdateable(this);
	}
	
	public Rotating setSpeed(float speed) {
		this.speed = speed;
		
		return this;
	}

	@Override
	public void update() {
		if (entity != null && entity.hasComponent(Pose.class)) {
			float dt = Gdx.graphics.getDeltaTime();
			Pose pose = entity.getComponent(Pose.class);
			
			pose.setAngle(pose.getAngle() + this.speed * dt);
		}
	}

}

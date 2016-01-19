package at.fhooe.im440.workbench.components;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.ColliderSystem.Collideable;
import at.fhooe.im440.workbench.services.EntityManager.Entity;
import at.fhooe.im440.workbench.services.UpdateService.UpdateService;
import at.fhooe.im440.workbench.services.UpdateService.Updateable;

public class FollowRotation extends Component implements Updateable {

	@Override
	public void activate() {
		ServiceManager.getService(UpdateService.class).addUpdateable(this);
	}

	@Override
	public void deactivate() {
		ServiceManager.getService(UpdateService.class).removeUpdateable(this);
	}

	@Override
	public void update() {
		Entity entity = this.getEntity();
		if (entity != null 
				&& entity.hasComponent(Collider.class) 
				&& entity.getComponent(Collider.class).isColliding()) {
			ArrayList<Collideable> collisions = entity.getComponent(Collider.class).getCollisions();
			Collider c = (Collider) collisions.get(0);
			if (c.getEntity().hasComponent(Rotating.class) 
					&& c.getEntity().hasComponent(Pose.class)) {
				
				Vector2 center = entity.getComponent(Collider.class).getCenter();			
				Vector2 collidingCenter = c.getCenter();
				//float radius = c.getHalfWidth() + entity.getComponent(Collider.class).getHalfWidth();
				float angle = c.getEntity().getComponent(Pose.class).getAngle();
				angle += angle < 0f ? (float) Math.PI : 0f;
				
				float deltaX = center.x - collidingCenter.x;
				float deltaY = center.y - collidingCenter.y;
				float radius = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
				float deltaAngle = (float) Math.atan(deltaY / deltaX);
				deltaAngle += deltaAngle < 0f ? (float) Math.PI : 0f;
				
				float corrAngle = Math.abs(deltaAngle - angle);
				
				System.out.println(angle + ", " + corrAngle);
				
				float x = center.x + (radius * (float) Math.cos(corrAngle));
				float y = center.y + (radius * (float) Math.sin(corrAngle));
				entity.getComponent(Pose.class).setPos(x, y);
			}
		}
	}

}

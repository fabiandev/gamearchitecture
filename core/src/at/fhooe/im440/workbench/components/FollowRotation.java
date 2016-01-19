package at.fhooe.im440.workbench.components;

import java.util.ArrayList;

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
		
		if (entity != null && entity.hasComponent(Collider.class)) {
			Collider collider = entity.getComponent(Collider.class);
			
			if (collider.isColliding()) {
				
				ArrayList<Collideable> collisions = entity.getComponent(Collider.class).getCollisions();
				Collider collidingElement = (Collider) collisions.get(0);
				Entity collidingEntity = collidingElement.getEntity();
				
				if (collidingEntity != null && collidingEntity.hasComponent(Rotating.class) && collidingEntity.hasComponent(Pose.class)) {
					
					Rotating rotating = collidingEntity.getComponent(Rotating.class);
					Pose collidingPose = collidingEntity.getComponent(Pose.class);
					Pose pose = entity.getComponent(Pose.class);
					
					float angle1 = rotating.getEntity().getComponent(Pose.class).getAngle();
					//float angle2 = (float) Math.asin(Math.sin(angle1));
					
//					float angle2 = (float) Math.atan2(pose.getPosY() - collidingPose.getPosY(), pose.getPosX() - collidingPose.getPosX());
				
//					float diff = Math.abs(angle1 - angle2);
//					angle2 -= diff;
					
//					Gdx.app.log("angle1", Math.toDegrees(angle1)+"");
//					Gdx.app.log("angle2", Math.toDegrees(angle2)+"");
					
					float radius = (collidingElement.getHalfWidth() + collider.getHalfWidth() - 0.05f);
					
					float x = (float) (collidingPose.getPosX() + (radius * Math.cos(angle1)));
					float y = (float) (collidingPose.getPosY() + (radius * Math.sin(angle1)));
					
//					float deltaX = collidingPose.getPosX() - pose.getPosX();
//					float deltaY = collidingPose.getPosY() - pose.getPosY();
//					float radius = (float) Math.sqrt(deltaX*deltaX+deltaY*deltaY);
//					float orthoX = -deltaY*d/radius;
//					orthoY = deltaX*d/radius;
//					newDeltaX = deltaX+orthoX; newDeltaY = deltaY+orthoY;
//					newLength = sqrt(newDeltaX*newDeltaX+newDeltaY*newDeltaY);
//					aX = cX+newDeltaX*radius/newLength; aY = cY+newDeltaY*radius/newLength;
					
					pose.setPos(x, y);
				}
				
			}
		}
	}

}

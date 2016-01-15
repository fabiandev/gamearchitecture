package at.fhooe.im440.workbench.components.persistables;

import com.badlogic.gdx.graphics.Color;

import at.fhooe.im440.workbench.components.Persistable;
import at.fhooe.im440.workbench.components.Pose;
import at.fhooe.im440.workbench.components.Visual;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.EntityManager.EntityFactory;
import at.fhooe.im440.workbench.services.PersistenceSystem.Storeable;

public class WallPersistable extends Persistable {

	@Override
	public Storeable getStoreable() {
		return new Restorer(this);
	}
	
	private static class Restorer implements Storeable {

		private static final long serialVersionUID = -6980085341881405607L;
		private float posX;
		private float posY;
		private float angleRadians;
		private float[] color;
		
		public Restorer(Persistable persistable) {
			Pose pose = persistable.getEntity().getComponent(Pose.class);
			Color color = persistable.getEntity().getComponent(Visual.class).getColor();
			
			this.color = new float[] {
				color.r, color.g, color.b, color.a
			};
			
			this.posX = pose.getPosX();
			this.posY = pose.getPosY();
			this.angleRadians = pose.getAngle();
		}
		
		@Override
		public void restore() {
			ServiceManager.getService(EntityFactory.class)
				.createWall(this.posX, this.posY, this.angleRadians, new Color(this.color[0], this.color[1], this.color[2], this.color[3]))
				.activateComponents()
				.activate();
		}
		
	}

}

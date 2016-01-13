package at.fhooe.im440.workbench.components.persistables;

import at.fhooe.im440.workbench.components.Persistable;
import at.fhooe.im440.workbench.components.Pose;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.EntityManager.EntityFactory;
import at.fhooe.im440.workbench.services.PersistenceSystem.Storeable;

public class CogwheelPersistable extends Persistable {

	@Override
	public Storeable getStoreable() {
		return new Restorer(this);
	}
	
	private static class Restorer implements Storeable {

		private static final long serialVersionUID = -6980085341881405607L;
		private float posX;
		private float posY;
		
		public Restorer(Persistable persistable) {
			Pose pose = persistable.getEntity().getComponent(Pose.class);
			
			this.posX = pose.getPosX();
			this.posY = pose.getPosY();
		}
		
		@Override
		public void restore() {
			ServiceManager.getService(EntityFactory.class)
				.createCogwheel(this.posX, this.posY)
				.activateComponents()
				.activate();
		}
		
	}

}

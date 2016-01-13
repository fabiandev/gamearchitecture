package at.fhooe.im440.workbench.components;

import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.PersistenceSystem.PersistenceSystem;
import at.fhooe.im440.workbench.services.PersistenceSystem.Storeable;

public abstract class Persistable extends Component {

	public abstract Storeable getStoreable();
	
	@Override
	public void activate() {
		ServiceManager.getService(PersistenceSystem.class).add(this);
	}
	
	@Override
	public void deactivate() {	
		ServiceManager.getService(PersistenceSystem.class).remove(this);
	}
	
}

package at.fhooe.im440.workbench.services.PersistenceSystem;

import java.io.Serializable;

public interface Storeable extends Serializable {

	public abstract void restore();
	
}

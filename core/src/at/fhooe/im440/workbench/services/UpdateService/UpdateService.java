package at.fhooe.im440.workbench.services.UpdateService;

import at.fhooe.im440.workbench.services.Service;
import at.fhooe.im440.workbench.utilities.GenericArrayList;

public class UpdateService implements Service {

	private GenericArrayList<Updateable> updateables = new GenericArrayList<Updateable>();

	public boolean addUpdateable(Updateable updateable) {
		return this.updateables.add(updateable);
	}
	
	public boolean removeUpdateable(Updateable updateable) {
		return this.updateables.remove(updateable);
	}
	
	public <T> int removeAll(Class<T> type) {
		return this.updateables.removeAll(type);
	}
	
	@Override
	public void update() {
		for (Updateable updateable : this.updateables) {
			updateable.update();
		}
	}
	
}

package at.fhooe.im440.workbench.services;

import at.fhooe.im440.workbench.utilities.GenericArrayList;

public class ServiceManager implements Service {
	
	private GenericArrayList<Service> services = new GenericArrayList<Service>();
	
	public <T extends Service> T getService(Class<T> type) {
		return this.services.getFirst(type);
	}
	
	public boolean addService(Service service) {
		return this.services.add(service);
	}
	
	public <T extends Service> T removeService(Class<T> type) {
		return this.services.removeFirst(type);
	}
	
	@Override
	public void update() {
		for (Service service : this.services) {
			service.update();
		}
	}
	
}

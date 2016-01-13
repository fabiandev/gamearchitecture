package at.fhooe.im440.workbench.services;

import at.fhooe.im440.workbench.utilities.GenericArrayList;

public abstract class ServiceManager {

	private static GenericArrayList<Service> services = new GenericArrayList<Service>();

	private static boolean active = false;
	
	public static <T extends Service> T getService(Class<T> type) {
		return services.getFirst(type);
	}

	public static boolean addService(Service service) {
		return services.add(service);
	}

	public static void addServices(Service... services) {
		for (Service service : services) {
			addService(service);
		}
	}

	public static <T extends Service> T removeService(Class<T> type) {
		if (services.hasOne(type)) {
			return services.removeFirst(type);
		}
		
		return null;
	}
	
	public static <T extends Service> boolean hasService(Class<T> type) {
		return services.hasOne(type);
	}
	
	public static void activate() {
		active = true;
	}
	
	public static void deactivate() {
		active = false;
	}
	
	public static boolean isActive() {
		return active;
	}

	public static void update() {
		if (!active) return;
		
		for (Service service : services) {
			service.update();
		}
	}

}

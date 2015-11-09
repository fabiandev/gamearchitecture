package at.fhooe.im440.workbench.services;

import java.util.ArrayList;
import java.util.Iterator;

public class ServiceManager {
	
	private static Iterator<Service> iterator;
	private static ArrayList<Service> services = new ArrayList<Service>();
	
	@SuppressWarnings("unchecked")
	private static <T extends Service> T firstEntry(Class<T> type) {
		boolean found = false;
		T service = null;
		iterator = services.iterator();
		
		while (iterator.hasNext() && !found) {
			Service s = iterator.next();
			if (s.getClass().equals(type)) {
				found = true;
				service = (T) s;
			}
		}
		
		return service;
	}
	
	public static <T extends Service> T getService(Class<T> type) {
		return firstEntry(type);
	}
	
	public static boolean addService(Service service) {
		return services.add(service);
	}
	
	public static <T extends Service> T removeService(Class<T> type) {
		T s = firstEntry(type);
		return services.remove(s) ? s : null;
	}
	
	public static void update() {
		iterator = services.iterator();
		
		while (iterator.hasNext()) {
			iterator.next().update();
		}
	}
	
}

package at.fhooe.im440.workbench.utilities;

import java.util.ArrayList;
import java.util.Iterator;

public class GenericArrayList<I> implements Iterable<I> {

	protected ArrayList<I> list = new ArrayList<I>();
	
	public ArrayList<I> getList() {
		return this.list;
	}
	
	public void setList(ArrayList<I> list) {
		this.list = list;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends I> T get(T instance) {
		for (I element : this.list) {
			if (element.equals(instance)) {
				return (T) element;
			}
		}
		
		return null;
	}
	
	public <T extends I> boolean has(T instance) {
		if (this.get(instance) != null) {
			return true;
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends I> T getFirst(Class<T> type) {
		for (I element : this.list) {
			if (type.isAssignableFrom(element.getClass())) {
				return (T) element;
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends I> ArrayList<T> getAll(Class<T> type) {
		ArrayList<T> list = new ArrayList<T>();
		
		for (I element : this.list) {
			if (type.isAssignableFrom(element.getClass())) {
				list.add((T)element);
			}
		}
		
		return list;
	}
	
	public <T extends I> boolean hasOne(Class<T> type) {
		if (this.getFirst(type) != null) {
			return true;
		}
		
		return false;
	}
	
	public <T extends I> int indexOfFirst(Class<T> type) {
		return this.list.indexOf(this.getFirst(type));
	}
	
	public <T extends I> int indexOf(T instance) {
		return this.list.indexOf(instance);
	}
	
	public boolean add(I element) {
		return this.list.add(element);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends I> T removeFirst(Class<T> type) {
		return (T) this.list.remove(this.indexOfFirst(type));
	}
	
	public boolean remove(I instance) {
		return this.list.remove(instance);
	}
	
	public <T> int removeAll(Class<T> type) {
		int removed = 0;
		
		for (I element : this.list) {
			if (type.isAssignableFrom(element.getClass())) {
				if (this.remove(element)) {
					removed++;
				}
			}
		}
		
		return removed;
	}
	
	public void clear() {
		this.list.clear();
	}
	
	@SuppressWarnings("unchecked")
	public GenericArrayList<I> clone() {
		GenericArrayList<I> list = new GenericArrayList<I>();
		list.setList((ArrayList<I>) this.list.clone());
		return list;
	}

	@Override
	public Iterator<I> iterator() {
		return this.list.iterator();
	}
	
	@SuppressWarnings({ "hiding", "unchecked" })
	public <I extends Object> I[] toArray() {
		return (I[]) this.list.toArray();
	}
	
}

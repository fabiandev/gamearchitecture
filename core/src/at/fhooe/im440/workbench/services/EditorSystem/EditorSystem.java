package at.fhooe.im440.workbench.services.EditorSystem;

import at.fhooe.im440.workbench.components.Editable;
import at.fhooe.im440.workbench.components.Pose;
import at.fhooe.im440.workbench.components.Visual;
import at.fhooe.im440.workbench.services.Service;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.EntityManager.Entity;
import at.fhooe.im440.workbench.services.Messenger.CoordinatesMessage;
import at.fhooe.im440.workbench.services.Messenger.Message;
import at.fhooe.im440.workbench.services.Messenger.MessageData;
import at.fhooe.im440.workbench.services.Messenger.Messenger;
import at.fhooe.im440.workbench.services.Messenger.Subscribeable;
import at.fhooe.im440.workbench.utilities.GenericArrayList;

public class EditorSystem implements Service, Subscribeable {
	
	private GenericArrayList<Entity> entities = new GenericArrayList<Entity>();
	
	private SelectState selectState = SelectState.IDLE;
	private ActionState actionState = ActionState.IDLE;
	
	private String[] listenTo = new String[] {"TOUCH_DOWN", "MOUSE_MOVED"};
	
	public boolean addEditable(Entity entity) {
		System.out.println(entity);
		if (!entity.hasComponent(Editable.class)) {
			throw new IllegalArgumentException("An editable must have an Editable component.");
		}
		
		if (!entity.hasComponent(Visual.class)) {
			throw new IllegalArgumentException("An editable must have a Visual component.");
		}
		
		return this.entities.add(entity);
	}
	
	public int addEditables(Entity... entities) {
		int count = 0;
		
		for (Entity entity : entities) {
			if (this.addEditable(entity)) {
				count++;
			}
		}
		
		return count;
	}
	
	public boolean removeEditable(Entity entity) {
		return this.entities.remove(entity);
	}
	
	public void clearEditables() {
		this.entities.clear();
	}
	
	public boolean select(float x, float y) {
		for (Entity entity : this.entities) {
			entity.getComponent(Pose.class).setPos(x, y);
			if (entity.getComponent(Visual.class).contains(x, y)) {
				entity.getComponent(Editable.class).select();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void message(Message message) {
		MessageData data = message.getData();

		if (message.getType() == "TOUCH_DOWN") {
			if (data instanceof CoordinatesMessage) {
				CoordinatesMessage coordinates = (CoordinatesMessage)data;		
				this.select(coordinates.getX(), coordinates.getY());
			}
		}
		
		if (message.getType() == "MOUSE_MOVED") {
			if (data instanceof CoordinatesMessage) {
				CoordinatesMessage coordinates = (CoordinatesMessage)data;		
				this.select(coordinates.getX(), coordinates.getY());
			}
		}

	}

	@Override
	public void subscribe() {
		Messenger messenger = ServiceManager.getService(Messenger.class);
		messenger.subscribe(this, this.listenTo);
	}

	@Override
	public void unsubscribe() {
		Messenger messenger = ServiceManager.getService(Messenger.class);
		messenger.unsubscribe(this, listenTo);
	}

}

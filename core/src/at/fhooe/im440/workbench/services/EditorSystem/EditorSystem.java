package at.fhooe.im440.workbench.services.EditorSystem;

import com.badlogic.gdx.math.Vector2;

import at.fhooe.im440.workbench.components.Editable;
import at.fhooe.im440.workbench.components.Pose;
import at.fhooe.im440.workbench.components.Visual;
import at.fhooe.im440.workbench.services.Service;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.EntityManager.Entity;
import at.fhooe.im440.workbench.services.Messenger.PositionMessage;
import at.fhooe.im440.workbench.services.Messenger.Message;
import at.fhooe.im440.workbench.services.Messenger.MessageData;
import at.fhooe.im440.workbench.services.Messenger.MessageType;
import at.fhooe.im440.workbench.services.Messenger.Messenger;
import at.fhooe.im440.workbench.services.Messenger.Subscribeable;
import at.fhooe.im440.workbench.utilities.GenericArrayList;

public class EditorSystem implements Service, Subscribeable {
	
	private GenericArrayList<Entity> entities = new GenericArrayList<Entity>();
	
	private SelectState selectState = SelectState.SINGLE_SELECTING;
	private ActionState actionState = ActionState.IDLE;
	
	private MessageType[] listenTo = new MessageType[] { MessageType.TOUCH_DOWN, MessageType.MOUSE_MOVED };
	
	public boolean addEditable(Entity entity) {
		if (!entity.hasComponent(Editable.class)) {
			throw new IllegalArgumentException("An editable must have an Editable component.");
		}
		
		if (!entity.hasComponent(Pose.class)) {
			throw new IllegalArgumentException("An editable must have a Pose component.");
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
		MessageType type = message.getType();
		
		switch(type) {
		case TOUCH_DOWN:
			PositionMessage clickPosition = message.get(PositionMessage.class);
			if (clickPosition != null) {
				this.handleTouchDown(clickPosition.getVector());
			}
			break;
		case MOUSE_MOVED:
			PositionMessage mousePosition = message.get(PositionMessage.class);
			if (mousePosition != null) {
				this.handleMouseMoved(mousePosition.getVector());
			}
			break;
		default:
			break;
		}
	}
	
	private void handleMouseMoved(Vector2 position) {
		for (Entity entity : this.entities) {
			if (entity.getComponent(Editable.class).isSelected()) {
				entity.getComponent(Pose.class).setPos(position.x, position.y);
			}
		}
	}
	
	private void handleTouchDown(Vector2 position) {
		for (Entity entity : this.entities) {
			if(entity.getComponent(Visual.class).contains(position.x, position.y)) {
				if (this.selectState == SelectState.SINGLE_SELECTED) {
					entity.getComponent(Editable.class).deselect();
					this.selectState = SelectState.SINGLE_SELECTING;
				} else {
					entity.getComponent(Editable.class).select();
					if (this.selectState == SelectState.SINGLE_SELECTING) {
						this.selectState = SelectState.SINGLE_SELECTED;
						break;
					}
				}
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

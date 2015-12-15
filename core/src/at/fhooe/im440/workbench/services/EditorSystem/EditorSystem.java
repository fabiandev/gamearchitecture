package at.fhooe.im440.workbench.services.EditorSystem;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

import at.fhooe.im440.workbench.components.Collider;
import at.fhooe.im440.workbench.components.CollisionMarker;
import at.fhooe.im440.workbench.components.Editable;
import at.fhooe.im440.workbench.components.Pose;
import at.fhooe.im440.workbench.components.Visual;
import at.fhooe.im440.workbench.services.Service;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.EntityManager.Entity;
import at.fhooe.im440.workbench.services.EntityManager.EntityFactory;
import at.fhooe.im440.workbench.services.Messenger.IntegerMessage;
import at.fhooe.im440.workbench.services.Messenger.Message;
import at.fhooe.im440.workbench.services.Messenger.MessageType;
import at.fhooe.im440.workbench.services.Messenger.Messenger;
import at.fhooe.im440.workbench.services.Messenger.PositionMessage;
import at.fhooe.im440.workbench.services.Messenger.Subscribeable;
import at.fhooe.im440.workbench.utilities.GenericArrayList;

public class EditorSystem implements Service, Subscribeable {

	private GenericArrayList<Editable> editables = new GenericArrayList<Editable>();

	private SelectState selectState = SelectState.SINGLE_SELECTING;
	private SelectState cloneState = null;

	private ActionState actionState = ActionState.IDLE;

	private MessageType[] listenTo = new MessageType[] { MessageType.TOUCH_DOWN, MessageType.MOUSE_MOVED,
			MessageType.KEY_DOWN, MessageType.KEY_UP };

	public boolean addEditable(Editable editable) {
		Entity entity = editable.getEntity();

		if (!entity.hasComponent(Pose.class)) {
			throw new IllegalArgumentException("An editable must have a Pose component.");
		}

		if (!entity.hasComponent(Visual.class)) {
			throw new IllegalArgumentException("An editable must have a Visual component.");
		}

		if (!entity.hasComponent(CollisionMarker.class)) {
			throw new IllegalArgumentException("An editable must have a CollisionVisual component.");
		}

		return this.editables.add(editable);
	}

	public int addEditables(Editable... editables) {
		int count = 0;

		for (Editable editable : editables) {
			if (this.addEditable(editable)) {
				count++;
			}
		}

		return count;
	}

	public boolean removeEditable(Editable editable) {
		return this.editables.remove(editable);
	}

	public void clearEditables() {
		this.editables.clear();
	}

	public boolean select(float x, float y) {
		for (Editable editable : this.editables) {
			Entity entity = editable.getEntity();
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
//		for (Editable editable : this.editables) {
//			Entity entity = editable.getEntity();
//			if (entity.getComponent(Collider.class).isColliding()) {
//				entity.getComponent(CollisionVisual.class).activate();
//			} else {
//				entity.getComponent(CollisionVisual.class).deactivate();
//			}
//		}
	}

	@Override
	public void message(Message message) {
		MessageType type = message.getType();

		switch (type) {
		case TOUCH_DOWN:
			PositionMessage clickPosition = message.get(PositionMessage.class);
			if (this.cloneState == SelectState.CLONE) {
				this.handleClone(clickPosition.getVector());
			} else if (clickPosition != null) {
				this.handleTouchDown(clickPosition.getVector());
				break;
			}
			break;
		case MOUSE_MOVED:
			PositionMessage mousePosition = message.get(PositionMessage.class);
			if (mousePosition != null) {
				this.handleMouseMoved(mousePosition.getVector());
			}
			break;
		case KEY_DOWN:
			int keyCode = message.get(IntegerMessage.class).getValue();
			if (keyCode == Keys.ALT_LEFT) {
				this.cloneState = SelectState.CLONE;
			}
			break;
		case KEY_UP:
			keyCode = message.get(IntegerMessage.class).getValue();
			if (keyCode == Keys.ALT_LEFT) {
				this.cloneState = null;
			}
			break;
		default:
			break;
		}
	}

	private void handleMouseMoved(Vector2 position) {
		for (Editable editable : this.editables) {
			Entity entity = editable.getEntity();
			if (entity.getComponent(Editable.class).isSelected()) {
				entity.getComponent(Pose.class).setPos(position.x, position.y);
			}
		}
	}

	private void handleTouchDown(Vector2 position) {
		for (Editable editable : this.editables) {
			Entity entity = editable.getEntity();
			if (entity.getComponent(Visual.class).contains(position.x, position.y)
					&& !entity.getComponent(Collider.class).isColliding()) {
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

	private void handleClone(Vector2 position) {
		if (this.selectState == SelectState.SINGLE_SELECTED) {
			this.handleTouchDown(position);
			return;
		}
		
		// Attention! Crap might follow below, as well as in EntityFactory!
		for (Editable editable : this.editables) {
			Entity entity = editable.getEntity();
			if (entity.getComponent(Visual.class).contains(position.x, position.y)) {
				if (this.selectState == SelectState.SINGLE_SELECTING) {
					Entity cloned = ServiceManager.getService(EntityFactory.class).cloneEntity(entity).addComponent(new Editable());
					cloned.activateComponents();
					this.addEditable(cloned.getComponent(Editable.class));
					entity.getComponent(Editable.class).select();
					this.selectState = SelectState.SINGLE_SELECTED;
					break;
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
		messenger.unsubscribe(this, this.listenTo);
	}

}

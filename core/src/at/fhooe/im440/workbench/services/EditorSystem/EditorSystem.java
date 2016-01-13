package at.fhooe.im440.workbench.services.EditorSystem;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

import at.fhooe.im440.workbench.components.Collider;
import at.fhooe.im440.workbench.components.CollisionMarker;
import at.fhooe.im440.workbench.components.Editable;
import at.fhooe.im440.workbench.components.Physics;
import at.fhooe.im440.workbench.components.Pose;
import at.fhooe.im440.workbench.components.Visual;
import at.fhooe.im440.workbench.services.Service;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.EditorSystem.states.CloneState;
import at.fhooe.im440.workbench.services.EditorSystem.states.EditorState;
import at.fhooe.im440.workbench.services.EditorSystem.states.IdleState;
import at.fhooe.im440.workbench.services.EditorSystem.states.SingleSelectedState;
import at.fhooe.im440.workbench.services.EditorSystem.states.SingleSelectingState;
import at.fhooe.im440.workbench.services.EditorSystem.states.State;
import at.fhooe.im440.workbench.services.EntityManager.Entity;
import at.fhooe.im440.workbench.services.EntityManager.EntityFactory;
import at.fhooe.im440.workbench.services.Messenger.IntegerMessage;
import at.fhooe.im440.workbench.services.Messenger.Message;
import at.fhooe.im440.workbench.services.Messenger.MessageType;
import at.fhooe.im440.workbench.services.Messenger.Messenger;
import at.fhooe.im440.workbench.services.Messenger.Subscribeable;
import at.fhooe.im440.workbench.utilities.GenericArrayList;

public class EditorSystem implements Service, Subscribeable {

	private GenericArrayList<Editable> editables = new GenericArrayList<Editable>();
	private Map<EditorState, State> states = new HashMap<EditorState, State>();
	private Map<Integer, EditorState> stateKeyMapping = new HashMap<Integer, EditorState>();
	private EditorState activeState;
	private EditorState defaultState = EditorState.IDLE;
	private EditorState previousState;
	private MessageType[] listenTo = new MessageType[] { MessageType.KEY_UP, MessageType.KEY_DOWN };

	public EditorSystem() {
		this.states.put(EditorState.IDLE, new IdleState(this));
		this.states.put(EditorState.SINGLE_SELECTING, new SingleSelectingState(this));
		this.states.put(EditorState.SINGLE_SELECTED, new SingleSelectedState(this));
		this.states.put(EditorState.CLONE, new CloneState(this));
	
		this.stateKeyMapping.put(Keys.S, EditorState.SINGLE_SELECTING);
		this.stateKeyMapping.put(Keys.C, EditorState.CLONE);
		
		this.activeState = this.defaultState;
		this.previousState = this.defaultState;
	}
	
	public State getState(EditorState stateType) {
		if (this.states.containsKey(stateType)) {
			return this.states.get(stateType);
		}
		
		return null;
	}
	
	public boolean setState(EditorState stateType) {
		if (this.states.containsKey(stateType)) {
			this.states.get(this.activeState).off();
			this.states.get(stateType).on();
			this.previousState = this.activeState;
			this.activeState = stateType;
			
			return true;
		}
		
		return false;
	}
	
	public boolean setPreviousState() {
		if (this.states.containsKey(this.previousState)) {
			this.states.get(this.activeState).off();
			this.states.get(this.previousState).on();
			EditorState temp = this.activeState;
			this.activeState = this.previousState;
			this.previousState = temp;
			
			return true;
		}
		
		return false;
	}
	
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

	}

	public void positionSelectedEditables(Vector2 position) {
		for (Editable editable : this.editables) {
			Entity entity = editable.getEntity();
			if (entity.getComponent(Editable.class).isSelected()) {
				entity.getComponent(Pose.class).setPos(position.x, position.y);
			}
		}
	}

	public boolean selectCollidingEditable(Vector2 position) {
		for (Editable editable : this.editables) {
			Entity entity = editable.getEntity();
			if (entity.getComponent(Visual.class).contains(position.x, position.y)) {
				
				Physics physicsComponent = entity.getComponent(Physics.class);
				
				if (physicsComponent != null) {
					physicsComponent.deactivate();
				}
				
				entity.getComponent(Editable.class).select();
				
				return true;
			}
		}
		
		return false;
	}
	
	public boolean deselectCollideables() {
		for (Editable editable : this.editables) {
			Entity entity = editable.getEntity();
			
			if (editable.isSelected()) {
				if (entity.getComponent(Collider.class).isColliding()) {
					return false;
				}
			}
		}
		
		for (Editable editable : this.editables) {
			if (editable.isSelected()) {
				Entity entity = editable.getEntity();
				
				editable.deselect();
				
				Physics physicsComponent = entity.getComponent(Physics.class);
				
				if (physicsComponent != null) {
					physicsComponent.activate();
				}
			}
		}
		
		return true;
	}
	
	public void forceDeselectCollideables() {
		for (Editable editable : this.editables) {
			if (editable.isSelected()) {
				Entity entity = editable.getEntity();
				
				editable.deselect();
				
				Physics physicsComponent = entity.getComponent(Physics.class);
				
				if (physicsComponent != null) {
					physicsComponent.activate();
				}
			}
		}
	}
	
	public boolean cloneCollidingEditable(Vector2 position) {
		for (Editable editable : this.editables) {
			Entity entity = editable.getEntity();
			if (entity.getComponent(Visual.class).contains(position.x, position.y)) {
				Entity cloned = ServiceManager.getService(EntityFactory.class).cloneEntity(entity).addComponent(new Editable());
				cloned.activateComponents();
				cloned.activate();
				this.addEditable(cloned.getComponent(Editable.class));
				cloned.getComponent(Editable.class).select();
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void activate() {
		this.setState(this.defaultState);
		this.getState(this.activeState).on();
		ServiceManager.addService(this);
		this.subscribe();
	}

	@Override
	public void deactivate() {
		this.unsubscribe();
		ServiceManager.removeService(this.getClass());
		this.setState(this.defaultState);
		this.getState(this.activeState).off();
	}

	@Override
	public void message(Message message) {
		MessageType type = message.getType();
		int keyCode;
		
		switch (type) {
		case KEY_UP:
			keyCode = message.get(IntegerMessage.class).getValue();
			this.handleKeyUp(keyCode);
			
			break;
		case KEY_DOWN:
			keyCode = message.get(IntegerMessage.class).getValue();
			this.handleKeyDown(keyCode);
			
			break;
		default:
			break;
		}
	}

	@Override
	public void subscribe() {
		ServiceManager.getService(Messenger.class).subscribe(this, this.listenTo);
	}

	@Override
	public void unsubscribe() {
		ServiceManager.getService(Messenger.class).unsubscribe(this, this.listenTo);
	}
	
	private void handleKeyUp(int keyCode) {
		if (this.stateKeyMapping.containsKey(keyCode)) {
			this.forceDeselectCollideables();
			this.setState(this.defaultState);
		}
	}
	
	private void handleKeyDown(int keyCode) {
		if (this.stateKeyMapping.containsKey(keyCode)) {
			this.setState(this.stateKeyMapping.get(keyCode));
		}
	}

}

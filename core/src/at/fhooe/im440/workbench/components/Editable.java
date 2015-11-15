package at.fhooe.im440.workbench.components;

import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.EditorSystem.EditorSystem;

public class Editable extends BaseComponent {

	private boolean selected = false;
	
	@Override
	public void addToManager() {
		ServiceManager.getService(EditorSystem.class).addEditable(this.getEntity());
		this.activate();
	}

	@Override
	public void removeFromManager() {
		ServiceManager.getService(EditorSystem.class).removeEditable(this.getEntity());
		this.deactivate();
	}
	
	public void select() {
		this.selected = true;
	}
	
	public void deselect() {
		this.selected = false;
	}
	
	public boolean isSelected() {
		return this.selected;
	}
	
}
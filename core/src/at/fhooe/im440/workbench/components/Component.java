package at.fhooe.im440.workbench.components;

public abstract class Component {

	protected boolean active = true;
	
	public void activate() {
		this.active = true;
	}
	
	public void deactivate() {
		this.active = false;
	}
	
}

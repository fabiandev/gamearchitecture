package at.fhooe.im440.workbench.components;

import com.badlogic.gdx.graphics.Color;

import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.EntityManager.Entity;
import at.fhooe.im440.workbench.services.UpdateService.UpdateService;
import at.fhooe.im440.workbench.services.UpdateService.Updateable;

public class CollisionMarker extends Component implements Updateable {

	private Color initialColor;
	private Color markerColor;
	
	public CollisionMarker() {
		this(Color.GRAY);
	}
	
	public CollisionMarker(Color markerColor) {
		this.markerColor = markerColor;
	}
	
	public CollisionMarker setColor(Color markerColor) {
		this.markerColor = markerColor;
		return this;
	}
	
	@Override
	public void activate() {
		if (this.initialColor == null) {
			this.initialColor = this.getEntity().getComponent(Visual.class).getColor();
		}
		
		ServiceManager.getService(UpdateService.class).addUpdateable(this);
	}

	@Override
	public void deactivate() {
		ServiceManager.getService(UpdateService.class).removeUpdateable(this);
	}

	@Override
	public void update() {
		Entity entity = this.getEntity();
		Visual visual = entity.getComponent(Visual.class);
		
		if (entity.getComponent(Collider.class).isColliding()) {
			visual.setColor(this.markerColor);
		} else {
			visual.setColor(initialColor);
		}
	}

}

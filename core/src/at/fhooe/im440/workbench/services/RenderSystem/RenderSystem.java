package at.fhooe.im440.workbench.services.RenderSystem;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import at.fhooe.im440.workbench.components.Visual;
import at.fhooe.im440.workbench.services.Service;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.CameraSystem.CameraSystem;
import at.fhooe.im440.workbench.utilities.GenericArrayList;

public class RenderSystem implements Service {

	private GenericArrayList<Visual> visuals = new GenericArrayList<Visual>();
	
	private SpriteBatch batch;
	
	public RenderSystem(SpriteBatch batch) {
		this.batch = batch;
	}
	
	public boolean addVisual(Visual entity) {
		return this.visuals.add(entity);
	}
	
	public boolean removeVisual(Visual entity) {
		return this.visuals.remove(entity);
	}
	
	public <T> int removeAll(Class<T> type) {
		return this.visuals.removeAll(type);
	}
	
	@Override
	public void update() {
		Camera camera = ServiceManager.getService(CameraSystem.class).getCamera();
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		for (Visual visual : this.visuals) {
			visual.render(this.batch);
		}
		batch.end();
	}

}

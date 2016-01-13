package at.fhooe.im440.workbench.services.CameraSystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import at.fhooe.im440.workbench.Workbench;
import at.fhooe.im440.workbench.components.Editable;
import at.fhooe.im440.workbench.components.StaticPose;
import at.fhooe.im440.workbench.services.Service;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.EntityManager.Entity;

public class CameraSystem implements Service {

	CameraTarget defaultTarget;
	CameraTarget target;

	private Viewport viewport;
	private Camera camera;

	public CameraSystem(float worldWidth, float worldHeight) {
		this(worldWidth, worldHeight, new CameraTarget(new StaticPose(worldWidth / 2f, worldHeight / 2f)));
	}
	
	public CameraSystem(float worldWidth, float worldHeight, CameraTarget target) {
		this.defaultTarget = target;
		this.target = this.defaultTarget;
		
		this.camera = new OrthographicCamera();

		this.viewport = new FitViewport(Workbench.VIEWPORT_WIDTH, Workbench.VIEWPORT_HEIGHT, camera);
		this.viewport.apply();

		this.camera.position.set(Workbench.VIEWPORT_WIDTH / 2f, Workbench.VIEWPORT_HEIGHT / 2f, 0f);
		this.camera.update();
	}

	public void update(int width, int height) {
		this.viewport.update(width, height);
		this.camera.position.set(this.target.getPosX(), this.target.getPosY(), 0f);
		this.camera.update();
	}
	
	public CameraSystem setTarget(CameraTarget target) {
		this.target = target;
		
		return this;
	}
	
	public CameraSystem useDefaultTarget() {
		this.target = this.defaultTarget;
		
		return this;
	}
	
	public CameraSystem setDefaultTarget(CameraTarget target) {
		this.defaultTarget = target;
		
		return this;
	}
	
	public CameraSystem forceTargetPosition() {
		this.camera.position.set(this.target.getPosX(), this.target.getPosY(), 0f);
		this.camera.update();
		
		return this;
	}

	public Camera getCamera() {
		return this.camera;
	}

	public Viewport getViewport() {
		return this.viewport;
	}

	public Vector3 toWorldCoordinates(int screenX, int screenY) {
		return this.toWorldCoordinates((float) screenX, (float) screenY);
	}

	public Vector3 toWorldCoordinates(float screenX, float screenY) {
		return this.camera.unproject(new Vector3(screenX, screenY, 0), this.viewport.getScreenX(),
				this.viewport.getScreenY(), this.viewport.getScreenWidth(), this.viewport.getScreenHeight());
	}

	@Override
	public void update() {
		Entity targetEntity = this.target.getPose().getEntity();
		
		if (targetEntity != null && targetEntity.hasComponent(Editable.class)) {
			if(targetEntity.getComponent(Editable.class).isSelected()) {
				return;
			}
		}
		
		float dt = Gdx.graphics.getDeltaTime();
		Vector3 position = this.getCamera().position;
		float lerp = this.target.getLerp() * dt;
		
		float distanceX = this.target.getPosX() - position.x;
		float distanceY = this.target.getPosY() - position.y;
		
		float posX = position.x + distanceX * lerp;
		float posY = position.y + distanceY * lerp;
		
		this.camera.position.set(posX, posY, 0f);
		this.camera.update();
	}
	
	@Override
	public void activate() {
		ServiceManager.addService(this);
	}

	@Override
	public void deactivate() {
		ServiceManager.removeService(this.getClass());
	}

}

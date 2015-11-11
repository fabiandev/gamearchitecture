package at.fhooe.im440.workbench.services.CameraSystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import at.fhooe.im440.workbench.Workbench;
import at.fhooe.im440.workbench.services.Service;

public class CameraSystem implements Service {

	CameraTarget target;

	private Viewport viewport;
	private Camera camera;

	private float worldWidth;
	private float worldHeight;

	private float movement = 0;

	public CameraSystem(float worldWidth, float worldHeight, CameraTarget target) {
		float aspectRatio = (float) Workbench.WINDOW_HEIGHT / (float) Workbench.WINDOW_WIDTH;
		float aspectRatio2 = (float) Workbench.WINDOW_WIDTH / (float) Workbench.WINDOW_HEIGHT;
		System.out.println(aspectRatio);
		System.out.println(Workbench.VIEWPORT_WIDTH*aspectRatio);
		System.out.println(Workbench.VIEWPORT_HEIGHT * aspectRatio2);
		
		camera = new OrthographicCamera();
		
		viewport = new FitViewport(Workbench.VIEWPORT_WIDTH, Workbench.VIEWPORT_HEIGHT, camera);
		viewport.apply();
		
	    camera.position.set(Workbench.VIEWPORT_WIDTH/2f, Workbench.VIEWPORT_HEIGHT/2f, 0f);
	    camera.update();
	}

	public void update(int width, int height) {
		this.viewport.update(width, height);
		//camera.viewportHeight = (Workbench.VIEWPORT_HEIGHT / Workbench.VIEWPORT_WIDTH) * width;
		camera.position.set(Workbench.VIEWPORT_WIDTH/2f, Workbench.VIEWPORT_HEIGHT/2f, 0f);
        camera.update();
	}

	public Camera getCamera() {
		return this.camera;
	}

	public Viewport getViewport() {
		return this.viewport;
	}

	public boolean isMoving() {
		return (movement > 0);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}

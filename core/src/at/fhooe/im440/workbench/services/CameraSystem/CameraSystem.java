package at.fhooe.im440.workbench.services.CameraSystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

import at.fhooe.im440.workbench.services.Service;

public class CameraSystem implements Service {
	
	private final int U = 30;
	private final int V = 30;
	
	private OrthographicCamera camera;
	private float h;
	private float w;
	
	public CameraSystem() {
		this.h = Gdx.graphics.getHeight();
		this.w = Gdx.graphics.getWidth();
		
		this.camera = new OrthographicCamera(U, V);
		this.camera.position.set(this.camera.viewportWidth / 2f, this.camera.viewportHeight / 2f, 0);
		this.update();
	}
	
	public void setViewport(float u, float v) {
		if (u > (this.w - this.camera.viewportWidth / 2f)) {
			u = this.w - this.camera.viewportWidth / 2f;
		}
		if (v > (this.h - this.camera.viewportHeight / 2f)) {
			v = this.h - this.camera.viewportHeight / 2f;
		}
		
		this.camera.lookAt(u, v, 0);
		this.update();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		this.camera.update();
	}

}

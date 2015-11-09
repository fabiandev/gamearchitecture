package at.fhooe.im440.workbench.services.CameraSystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

import at.fhooe.im440.workbench.services.Service;

public class CameraSystem implements Service {
	
	private final int U = 50;
	private final int V = 50;
	
	private OrthographicCamera camera;
	private float h;
	private float w;
	
	private float animationProcess = 0;
	
	public CameraSystem() {
		this.h = Gdx.graphics.getHeight();
		this.w = Gdx.graphics.getWidth();
		
		this.camera = new OrthographicCamera(U, V);
		this.camera.position.set(this.camera.viewportWidth / 2f, this.camera.viewportHeight / 2f, 0);
		this.update();
	}
	
	public OrthographicCamera getCamera() {
		return this.camera;
	}
	
	public void setPosition(float u, float v) {
		if (u > (this.w - this.camera.viewportWidth / 2f)) {
			u = u - this.camera.viewportWidth / 2f;
		}
		if (v > (this.h - this.camera.viewportHeight / 2f)) {
			v = v - this.camera.viewportHeight / 2f;
		}
		
		this.camera.position.set(u, v, 0);
		this.update();
	}
	
	public void setViewport(float u, float v) {
		this.camera.viewportHeight = v;
		this.camera.viewportWidth = u;
		this.update();
	}
	
	/*
	 * Warning!
	 * Real crappy shit is following!
	 */
	public void animateViewport(float start, float size, float duration, float delta) {
		float animationPart;
		float deltaSize = size - start;
		
		if (this.camera.viewportHeight < size && this.animationProcess < duration) {
			this.animationProcess += delta;
			animationPart = this.animationProcess / duration;
			
			float process = animationPart * deltaSize;
			this.setViewport(start + process, start + process);
		} else {
			this.animationProcess = 0;
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		this.camera.update();
	}

}

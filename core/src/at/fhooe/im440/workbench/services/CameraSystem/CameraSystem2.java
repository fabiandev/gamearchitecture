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

public class CameraSystem2 implements Service, InputProcessor {

	CameraTarget target;

	private Viewport viewport;
	private Camera camera;

	private float worldWidth;
	private float worldHeight;

	private float movement = 0;

	// public CameraSystem(float worldWidth, float worldHeight) {
	//
	// // new Translate
	//
	// //this.camera.position.set(this.camera.viewportWidth / 2f,
	// this.camera.viewportHeight / 2f, 0);
	// //this.setPosition(0, 0);
	// //this.update();
	// }

	public CameraSystem2(float worldWidth, float worldHeight, CameraTarget target) {
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;

		float aspectRatio = (float) Workbench.WINDOW_HEIGHT / (float) Workbench.WINDOW_WIDTH;

		// Camera camera = new OrthographicCamera(Workbench.GAME_WORLD_WIDTH *
		// aspectRatio, Workbench.GAME_WORLD_HEIGHT);
		this.camera = new OrthographicCamera();
		// camera.translate(camera.viewportWidth/2, camera.viewportHeight/2,
		// 0f);

		// camera.update();
		this.viewport = new FitViewport(worldWidth * aspectRatio, worldHeight, camera);
		this.viewport.apply();
		this.camera.position.set(worldWidth / 2f, worldHeight / 2f, 0f);
		this.camera.update();
		// camera.translate(camera.viewportWidth/2f, camera.viewportWidth/2f,
		// 0f);
		// this.target = target;
		// camera.position.set(0f, 0f, 0f);
		// camera.position.set(worldWidth/2f, worldHeight/2f, 0f);

		Gdx.input.setInputProcessor(this);
	}

	public void update(int width, int height) {
		this.worldWidth = (float) width;
		this.worldHeight = (float) height;

		this.viewport.update(width, height);
		this.camera.position.set(this.worldWidth / 2f, this.worldHeight / 2f, 0f);
		// this.camera.position.set(0,0,0);
		// camera.translate(camera.viewportWidth/2f, camera.viewportWidth/2f,
		// 0f);
	}

	public Camera getCamera() {
		return this.viewport.getCamera();
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

	@Override
	public boolean keyDown(int keycode) {

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.LEFT) {
			this.viewport.getCamera().translate(-10f, 0f, 0f);
		}

		if (keycode == Input.Keys.RIGHT) {
			this.viewport.getCamera().translate(10f, 0f, 0f);
		}

		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	// public void setPosition(float u, float v) {
	// if (u > (this.width - this.camera.viewportWidth / 2f)) {
	// u = u - this.camera.viewportWidth / 2f;
	// }
	// if (v > (this.height - this.camera.viewportHeight / 2f)) {
	// v = v - this.camera.viewportHeight / 2f;
	// }
	//
	// this.camera.position.set(u, v, 0);
	// this.update();
	// }
	//
	// public void setViewport(float u, float v) {
	// this.camera.viewportHeight = v * (this.height / this.width);
	// this.camera.viewportWidth = u;
	// this.update();
	// }

	/*
	 * Warning! Real crappy shit is following! //
	 */
	// public void animateViewport(float start, float size, float duration,
	// float delta) {
	// float animationPart;
	// float deltaSize = size - start;
	//
	// if (this.camera.viewportHeight < size && this.animationProcess <
	// duration) {
	// this.animationProcess += delta;
	// animationPart = this.animationProcess / duration;
	//
	// float process = animationPart * deltaSize;
	// this.setViewport(start + process, start + process);
	// } else {
	// this.animationProcess = 0;
	// }
	// }

}

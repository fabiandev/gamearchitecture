package at.fhooe.im440.workbench.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import at.fhooe.im440.workbench.Workbench;
import at.fhooe.im440.workbench.helpers.Picasso;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.EntityManager.EntityManager;
import at.fhooe.im440.workbench.services.Messenger.Messenger;
import at.fhooe.im440.workbench.services.RenderSystem.RenderSystem;
import at.fhooe.im440.workbench.world.World;

public class GameScreen extends ScreenAdapter implements Screen, InputProcessor {
	
	private Workbench workbench;
	private Stage stage;
	private World world;

	public GameScreen(Workbench workbench) {
		this.workbench = workbench;
		this.stage = new Stage();				// Create new stage or use old one from MenuScreen?
		
		this.world = new World(this.workbench);	// Initialize Game logic
	}

	@Override
	public void render(float delta) {
		Picasso.paintBackground(Picasso.GAME_BLUEGREEN);
		this.stage.act(delta);
		this.stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		this.stage.getViewport().getCamera().position.set(0, 0, 0);
		this.stage.getViewport().update(width, height);
	}

	@Override
	public void show() {
		super.show();
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
		super.hide();
	}

	@Override
	public void dispose() {
		super.dispose();
		this.stage.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.ESCAPE) {
			this.workbench.setScreen(new MenuScreen(this.workbench));
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}

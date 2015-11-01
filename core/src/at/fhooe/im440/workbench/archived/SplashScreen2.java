package at.fhooe.im440.workbench.archived;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import at.fhooe.im440.workbench.Workbench;
import at.fhooe.im440.workbench.helpers.Picasso;
import at.fhooe.im440.workbench.screens.MenuScreen;

public class SplashScreen2 extends ScreenAdapter implements InputProcessor {

//	private static final Color BG_COL = new Color(1f, 1f,  0.62f, 1f);
//	private static final Color BG_COL = new Color(0.75f, 0.92f,  0.62f, 1f);
	private static final float FADE_TIME = 1f;
	private static final float DISPLAY_TIME = 10;
	private Workbench workbench;
	private Texture texture;
	private Stage stage;
	private boolean dead;
	
	public SplashScreen2(Workbench workbench) {
		this.workbench = workbench;
		stage = workbench.getStage();
	}

	@Override
	public void show() {
		// initialize control variables
		dead = false;
		
		// load resources
		Texture texture = new Texture("splash.png");
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		// assemble stage
		Actor img = new Image(texture);
		img.setPosition(0,  0, Align.center);
		img.getColor().a = 0;
		img.setName("splash");
		
		img.addAction(Actions.sequence(
				Actions.delay(0.5f),
				Actions.fadeIn(FADE_TIME),
				Actions.delay(DISPLAY_TIME),
				Actions.run(new Runnable() {
					@Override
					public void run() {
						fadeOut();
					}
				})
				));

		stage.addActor(img);
		stage.setViewport(new FitViewport(Workbench.V_WIDTH, Workbench.V_HEIGHT));
		
		// configure input processing
		Gdx.input.setInputProcessor(this);
	}
	
	private void fadeOut() {
		if (dead) return;
		dead = true;

		Actor img = stage.getRoot().findActor("splash");
		assert(img != null);
		img.clearActions();
		img.addAction(Actions.sequence(
				Actions.fadeOut(FADE_TIME),
				Actions.run(new Runnable() {
					@Override
					public void run() {
						endOfState();
					}
				})));
	}
	
	
	private void endOfState() {
		dead = true;
		workbench.setScreen(new MenuScreen(workbench));
	}
	
	@Override
	public void render(float delta) {
		Picasso.paintBackground(Picasso.GAME_GREEN);
		
		stage.act();
		stage.draw();
	}

	@Override
	public void hide() {
		super.hide();
	}

	@Override
	public void dispose() {
		texture.dispose();
		stage.clear();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().getCamera().position.set(0, 0, 0);
		stage.getViewport().update(width, height, false);
	}

	/////////////////////////////////////////////////
	/////// Interface InputProcessor
	/////////////////////////////////////////////////
	
	@Override
	public boolean keyDown(int keycode) {
		fadeOut();
		return true;
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
		fadeOut();
		return true;
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

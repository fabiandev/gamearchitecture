package at.fhooe.im440.workbench.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

import at.fhooe.im440.workbench.Workbench;
import at.fhooe.im440.workbench.helpers.Picasso;

public class SplashScreen extends BaseScreen {
	private static final float FADE_TIME = 1.0f;
	private static final float DELAY_TIME = 5.0f;
	private Workbench workbench;
	private Stage stage;
	private Texture texture;
	private boolean dead;
	
	public SplashScreen(Workbench workbench) {
		this.workbench = Workbench.get();
		this.stage = workbench.getStage();
		texture = new Texture("splash.png");
	}

	@Override
	public void show() {
		Image img = new Image(texture);
		img.setPosition(0, 0, Align.center);
		img.getColor().a = 0f;
		img.setName("Arnold");
		
		img.addAction(Actions.sequence(
				Actions.delay(0.5f),
				Actions.fadeIn(FADE_TIME),
				Actions.delay(DELAY_TIME),
				Actions.run(new Runnable() {
					
					@Override
					public void run() {
						fadeOut();
					}
				})
		));
		
		stage.addActor(img);
		
		stage.getViewport().getCamera().position.set(0, 0, 0);
		stage.getViewport().update((int)Workbench.VIEWPORT_WIDTH, (int)Workbench.VIEWPORT_HEIGHT);
		
		//stage.setViewport(new FitViewport(Workbench.V_WIDTH, Workbench.V_HEIGHT));
		//stage.setViewport(ServiceManager.getService(CameraSystem.class).getViewport());
		
		dead = false;
	}
	
	public void fadeOut() {
		if (dead) {
			return;
		}
		dead = true;
		Image img = stage.getRoot().findActor("Arnold");
		img.clearActions();
		img.addAction(Actions.sequence(
				Actions.fadeOut(FADE_TIME),
				Actions.hide(),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						endOfState();
					}
					
				})		
		));
	}
	
	public void endOfState() {
		workbench.setScreen(new MenuScreen());
	}

	@Override
	public void render(float delta) {
		Picasso.paintBackground(Picasso.GAME_PALEGREEN);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		stage.getViewport().getCamera().position.set(0, 0, 0);
		stage.getViewport().update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

//	@Override
//	public boolean keyDown(int keycode) {
//		fadeOut();
//		return false;
//	}

}

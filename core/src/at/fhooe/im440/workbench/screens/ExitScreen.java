package at.fhooe.im440.workbench.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import at.fhooe.im440.workbench.Workbench;
import at.fhooe.im440.workbench.helpers.Picasso;

public class ExitScreen extends BaseScreen {
	
	private Stage stage;

	public ExitScreen() {
		this.stage = Workbench.get().getStage();
	}

	@Override
	public void render(float delta) {
		Picasso.paintBackground(Picasso.WHITE);
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
		Gdx.app.exit();
		super.show();
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

}

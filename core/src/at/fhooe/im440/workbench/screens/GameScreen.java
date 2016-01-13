package at.fhooe.im440.workbench.screens;

import at.fhooe.im440.workbench.helpers.Picasso;

public class GameScreen extends BaseScreen {
	public GameScreen() {

	}

	@Override
	public void render(float delta) {
		Picasso.paintBackground(Picasso.GAME_BLUEGREEN);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void show() {
		super.show();
	}

	@Override
	public void hide() {
		super.hide();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

}

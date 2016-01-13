package at.fhooe.im440.workbench.screens;

import at.fhooe.im440.workbench.Workbench;
import at.fhooe.im440.workbench.helpers.Picasso;

public class TestScreen extends BaseScreen {
	
	//private Workbench workbench;
	//private Stage stage;
	//private World world;
	
	// TODO: Camera testing variable
	//private float start;

	public TestScreen(Workbench workbench) {
		//this.workbench = workbench;
		//this.stage = new Stage();				// Create new stage or use old one from MenuScreen?
		
		//this.world = new World(this.workbench);	// Initialize Game logic
		
		//ServiceManager.getService(CameraSystem.class).setPosition(50f, 50f);
		
		// TODO: Camera testing purpose
		//this.start = ServiceManager.getService(CameraSystem.class).getCamera().viewportHeight;
	}

	@Override
	public void render(float delta) {
		Picasso.paintBackground(Picasso.GAME_BLUEGREEN);
//		this.stage.act(delta);
//		this.stage.draw();
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// this.workbench.getBatch().setProjectionMatrix(ServiceManager.getService(CameraSystem.class).getCamera().combined);
		
		// TODO: Camera testing purpose
		//ServiceManager.getService(CameraSystem.class).animateViewport(this.start, Workbench.V_WIDTH / 2f, 2f, delta);
		//ServiceManager.update();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		//this.stage.getViewport().getCamera().position.set(0, 0, 0);
		//this.stage.getViewport().update(width, height);
		//ServiceManager.update();
	}

	@Override
	public void show() {
		super.show();
		//Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
		super.hide();
	}

	@Override
	public void dispose() {
		super.dispose();
		//this.stage.dispose();
	}

}

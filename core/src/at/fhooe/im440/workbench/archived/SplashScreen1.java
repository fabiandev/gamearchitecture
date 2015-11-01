package at.fhooe.im440.workbench.archived;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import at.fhooe.im440.workbench.Workbench;

public class SplashScreen1 extends ScreenAdapter {

	private static final Color BG_COL = new Color(1f, 0.38f, 0.22f, 1f);
	private static final float DISPLAY_TIME = 1.0f;

	private SpriteBatch batch;
	private Workbench workbench;
	private Texture texture;
	private OrthographicCamera camera;
	private float displayTime;
	private boolean dead;
	
	public SplashScreen1(Workbench workbench) {
		this.workbench = workbench;
		this.batch = workbench.getBatch();
		this.camera = new OrthographicCamera();
	}

	@Override
	public void show() {
		displayTime = DISPLAY_TIME;
		texture = new Texture("splash.png");
		dead = false;
	}	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(BG_COL.r, BG_COL.g, BG_COL.b, BG_COL.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		float w = texture.getWidth();
		float h = texture.getHeight();
		batch.draw(texture,  -w / 2,  -h / 2);
		batch.end();
		
		if (displayTime > 0) {
			displayTime -= delta;			
		} else {
			endOfState();
		}
	}

	private void endOfState() {
		if (dead) return;
		dead = true;
		workbench.setScreen(new SplashScreen2(workbench));
	}

	@Override
	public void resize(int width, int height) {
		float ar = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
		camera.setToOrtho(false, Workbench.V_WIDTH, Workbench.V_WIDTH / ar);
		camera.position.set(0, 0, 0);
		camera.update();
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		texture.dispose();
	}

}

package at.fhooe.im440.workbench.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

public class Picasso extends Color {

	public static final Color GAME_BLUE = new Color(0.49f, 0.91f, 1f, 1f);
	public static final Color GAME_BLUEGREEN = new Color(0.45f, 0.91f, 0.9f, 1f);
	public static final Color GAME_PALEGREEN = new Color(0.54f, 1f, 0.86f, 1f);
	public static final Color GAME_GREEN = new Color(0.45f, 0.91f, 0.64f, 1f);
	public static final Color GAME_LIGHTGREEN = new Color(0.49f, 1f, 0.56f, 1f);
	
	public static void paintBackground(Color color) {
		Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
}

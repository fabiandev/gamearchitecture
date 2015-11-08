package at.fhooe.im440.workbench.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Assets class for loading and managing in-game sprites.
 * Wiki for animations: https://github.com/libgdx/libgdx/wiki/2D-Animation
 * 
 * @author sascha
 *
 */

public class AssetManager {
	
	public final static int DEFAULT_SIZE = 48;
	
	// All files go into assets folder
	private final static String SPRITES_PATH = "sprites/spritemap.png";
	
	// Time duration for frame
	private final static float FRAME_DURATION = 0.166666f;
	
	// Variable for main spriteMap
	public static Texture spritesTexture;
	
	// Variables for different sprites out of spritesTexture
	// Be aware to use only "power-of-2" sizes
	public static TextureRegion enemy;
	public static TextureRegion character;
	public static TextureRegion staticSprite;
	
	// Gearwheel animation
	public static Animation gearwheel;
	private static TextureRegion gearwheelRegion;
	private static TextureRegion[] gearwheelFrames;
	private static final int GEAR_COL = 6;
	private static final int GEAR_ROW = 2;
	
	
	/**
	 * Static method to load sprites needed from the beginning
	 */
	public static void load() {
		spritesTexture = new Texture(Gdx.files.internal(SPRITES_PATH));
		
		gearwheelRegion = new TextureRegion(spritesTexture, 0, 0, DEFAULT_SIZE * GEAR_COL, DEFAULT_SIZE * GEAR_ROW);
		gearwheelFrames = new TextureRegion[GEAR_COL * GEAR_ROW];
		TextureRegion[][] tmp = gearwheelRegion.split(gearwheelRegion.getRegionWidth() / GEAR_COL, gearwheelRegion.getRegionHeight() / GEAR_ROW);
		int index = 0;
		for (int i = 0; i < GEAR_ROW; i++) {
			for (int j = 0; j < GEAR_COL; j++) {
				gearwheelFrames[index++] = tmp[i][j];
			}
		}
		gearwheel = new Animation(FRAME_DURATION, gearwheelFrames);
	}
	
	/**
	 * Dispose all the textures
	 */
	public static void dispose() {
		
		spritesTexture.dispose();
	}
}

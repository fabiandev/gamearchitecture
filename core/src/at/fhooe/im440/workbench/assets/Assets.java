package at.fhooe.im440.workbench.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Assets class for loading and managing in-game sprites.
 * Wiki for animations: https://github.com/libgdx/libgdx/wiki/2D-Animation
 * 
 * @author sascha
 *
 */

public class Assets {
	
	// All files go into assets folder
	private final static String spritesPath = "sprites/spritesTexture.png";
	
	// Variable for main spriteMap
	public static Texture spritesTexture;
	
	// Variables for different sprites out of spritesTexture
	// Be aware to use only "power-of-2" sizes
	public static TextureRegion enemy;
	public static TextureRegion character;
	public static TextureRegion staticSprite;
	
	/**
	 * Static method to load sprites needed from the beginning
	 */
	public static void load() {
		spritesTexture = new Texture(Gdx.files.internal(spritesPath));
		
		enemy = new TextureRegion(spritesTexture, 0, 0, 64, 64);
	}
}

package at.fhooe.im440.workbench.services.renderSystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface Visual {

	public void setRegion(TextureRegion region);
	public void draw(SpriteBatch batch);
	
}

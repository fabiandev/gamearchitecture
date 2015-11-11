package at.fhooe.im440.workbench.services.RenderSystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Visual {

	public void activate();
	public void deactivate();
	public int getLayer();
	public void render(SpriteBatch batch);
	
}

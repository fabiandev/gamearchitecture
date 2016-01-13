package at.fhooe.im440.workbench.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Visual extends Component {

	public abstract void setRegion(TextureRegion region);
	public abstract TextureRegion getRegion();
	public abstract void activate();
	public abstract void deactivate();
	public abstract int getLayer();
	public abstract void render(SpriteBatch batch);
	public abstract boolean contains(float x, float y);
	public abstract Visual setColor(Color color);
	public abstract Color getColor();
	
}

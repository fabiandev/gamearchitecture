package at.fhooe.im440.workbench.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Visual extends Component {

	public void activate();
	public void deactivate();
	public int getLayer();
	public void render(SpriteBatch batch);
	public boolean contains(float x, float y);
	
}

package at.fhooe.im440.workbench.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.RenderSystem.RenderSystem;

public class CollisionVisual extends BaseComponent {

	private TextureRegion region;

	private void switchTexture() {
		TextureRegion temp = this.region;
		this.region = this.getEntity().getComponent(Visual.class).getRegion();
		this.getEntity().getComponent(Visual.class).setRegion(temp);
	}

	public CollisionVisual setRegion(TextureRegion region) {
		this.region = region;
		super.deactivate();
		return this;
	}

	@Override
	public void addToManager() {

	}

	@Override
	public void removeFromManager() {

	}

	@Override
	public void activate() {
		if (!this.isActive()) {
			this.switchTexture();
			super.activate();
		}
	}

	@Override
	public void deactivate() {
		if (this.isActive()) {
			this.switchTexture();
			super.deactivate();
		}
	}

}

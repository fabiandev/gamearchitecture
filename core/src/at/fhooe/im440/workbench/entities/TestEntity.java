package at.fhooe.im440.workbench.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import at.fhooe.im440.workbench.components.Position;
import at.fhooe.im440.workbench.services.entityManager.Entity;
import at.fhooe.im440.workbench.services.renderSystem.Visual;

public class TestEntity extends Entity implements Visual {
	
	private TextureRegion region;

	@Override
	public void update() {
		
	}

	@Override
	public void setRegion(TextureRegion region) {
		// TODO Auto-generated method stub
		this.region = region;
	}

	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
		batch.draw(this.region, this.getComponent(Position.class).getX(), this.getComponent(Position.class).getY());
	}

}

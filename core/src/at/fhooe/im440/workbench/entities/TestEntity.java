package at.fhooe.im440.workbench.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import at.fhooe.im440.workbench.components.Position;
import at.fhooe.im440.workbench.services.EntityManager.Entity;
import at.fhooe.im440.workbench.services.RenderSystem.Visual;

public class TestEntity extends Entity implements Visual {
	
	private TextureRegion region;
	private Animation animation;
	private SpriteBatch batch;
	float stateTime;
	
	public TestEntity(SpriteBatch batch) {
		this.batch = batch;
	}

	@Override
	public void update() {
		this.draw(this.batch);
	}

	@Override
	public void setRegion(TextureRegion region) {
		// TODO Auto-generated method stub
		this.region = region;
	}
	
	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);  
		this.stateTime += Gdx.graphics.getDeltaTime();
		
		if (this.animation != null) {
			this.setRegion(this.animation.getKeyFrame(this.stateTime, true));
		}
		
		batch.begin();
		batch.draw(this.region, this.getComponent(Position.class).getX(), this.getComponent(Position.class).getY());
		batch.end();
	}

}

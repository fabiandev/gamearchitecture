package at.fhooe.im440.workbench.entities;

import at.fhooe.im440.workbench.services.EntityManager.Entity;

public class TestEntity extends Entity {

	@Override
	public void update() {
		
	}


//	@Override
//	public void update() {
//		this.draw(this.batch);
//	}
//	
//	public void setAnimation(Animation animation) {
//		this.animation = animation;
//	}

//	@Override
//	public void draw(SpriteBatch batch) {
//		// TODO Auto-generated method stub
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);  
//		this.stateTime += Gdx.graphics.getDeltaTime();
//		
//		if (this.animation != null) {
//			this.setRegion(this.animation.getKeyFrame(this.stateTime, true));
//		}
//		
//		batch.begin();
//		batch.draw(this.region, this.getComponent(Position.class).getX(), this.getComponent(Position.class).getY());
//		batch.end();
//	}

}

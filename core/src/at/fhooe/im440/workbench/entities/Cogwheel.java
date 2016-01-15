package at.fhooe.im440.workbench.entities;

import com.badlogic.gdx.graphics.Color;

import at.fhooe.im440.workbench.components.CircleCollider;
import at.fhooe.im440.workbench.components.CollisionMarker;
import at.fhooe.im440.workbench.components.SpriteVisual;
import at.fhooe.im440.workbench.components.StaticPose;
import at.fhooe.im440.workbench.helpers.Picasso;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.AssetManager.AssetManager;
import at.fhooe.im440.workbench.services.EntityManager.Entity;

public class Cogwheel extends Entity {
	
	public Cogwheel(float x, float y) {
		this(x, y, 1f, 0f, Picasso.GAME_PALEGREEN);
	}
	
	public Cogwheel(float x, float y, Color color) {
		this(x, y, 1f, 0f, color);
	}
	
	public Cogwheel(float x, float y, float angleRadians) {
		this(x, y, 1f, angleRadians, Picasso.GAME_PALEGREEN);
	}
	
	public Cogwheel(float x, float y, float angleRadians, Color color) {
		this(x, y, 1f, angleRadians, color);
	}
	
	public Cogwheel(float x, float y, float w, float angleRadians, Color color) {
		SpriteVisual spriteVisual = new SpriteVisual(ServiceManager.getService(AssetManager.class).getRegion("cog_n")).width(w).height(w).setOriginCenter();
		spriteVisual.setColor(color);
		
		this.addComponents(new StaticPose(x, y, angleRadians), spriteVisual, new CircleCollider(w), new CollisionMarker());
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

}

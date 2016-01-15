package at.fhooe.im440.workbench.entities;

import com.badlogic.gdx.graphics.Color;

import at.fhooe.im440.workbench.components.BoxCollider;
import at.fhooe.im440.workbench.components.CollisionMarker;
import at.fhooe.im440.workbench.components.SpriteVisual;
import at.fhooe.im440.workbench.components.StaticPose;
import at.fhooe.im440.workbench.helpers.Picasso;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.AssetManager.AssetManager;
import at.fhooe.im440.workbench.services.EntityManager.Entity;

public class Wall extends Entity {
	
	public Wall(float x, float y) {
		this(x, y, 5f, 0f, Picasso.GAME_PALEGREEN);
	}
	
	public Wall(float x, float y, Color color) {
		this(x, y, 5f, 0f, color);
	}
	
	public Wall(float x, float y, float angleRadians) {
		this(x, y, 5f, angleRadians, Picasso.GAME_PALEGREEN);
	}
	
	public Wall(float x, float y, float angleRadians, Color color) {
		this(x, y, 5f, angleRadians, color);
	}
	
	/**
	 * Creates a new WallEntity with the given parameters:
	 * 
	 * @param x - The x coordinate of the center point
	 * @param y - The y coordinate of the center point
	 * @param w - The width (equals 10 times the size of height)
	 */
	public Wall(float x, float y, float w, float angleRadians, Color color) {
		SpriteVisual spriteVisual = new SpriteVisual(ServiceManager.getService(AssetManager.class).getRegion("bar_n")).width(w).height((float) Math.floor((w * 100)) / 1000).setOriginCenter();
		spriteVisual.setColor(color);
		this.addComponents(new StaticPose(x, y, angleRadians), spriteVisual, new BoxCollider(w, (float) Math.floor((w * 100)) / 1000), new CollisionMarker());
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}

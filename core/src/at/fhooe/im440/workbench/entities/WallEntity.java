package at.fhooe.im440.workbench.entities;

import at.fhooe.im440.workbench.components.BoxCollider;
import at.fhooe.im440.workbench.components.CircleCollider;
import at.fhooe.im440.workbench.components.CollisionMarker;
import at.fhooe.im440.workbench.components.SpriteVisual;
import at.fhooe.im440.workbench.components.StaticPose;
import at.fhooe.im440.workbench.helpers.Picasso;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.AssetManager.AssetManager;
import at.fhooe.im440.workbench.services.EntityManager.Entity;

public class WallEntity extends Entity {
	
	public WallEntity(float x, float y) {
		this(x, y, 5f);
	}
	
	/**
	 * Creates a new WallEntity with the given parameters:
	 * 
	 * @param x - The x coordinate of the center point
	 * @param y - The y coordinate of the center point
	 * @param w - The width (equals 10 times the size of height)
	 */
	public WallEntity(float x, float y, float w) {
		SpriteVisual spriteVisual = new SpriteVisual(ServiceManager.getService(AssetManager.class).getRegion("bar_n")).width(w).height((float) Math.floor((w * 100)) / 1000).setOriginCenter();
		spriteVisual.setColor(Picasso.GAME_PALEGREEN);
		this.addComponents(new StaticPose(x, y), spriteVisual, new BoxCollider(w, (float) Math.floor((w * 100)) / 1000), new CollisionMarker());
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}

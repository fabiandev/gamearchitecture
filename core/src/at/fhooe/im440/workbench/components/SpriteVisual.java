/*
 * Copyright (c) 2013 Roman Divotkey, Univ. of Applied Sciences Upper Austria.
* All rights reserved.
*
* THIS CODE IS PROVIDED AS EDUCATIONAL MATERIAL AND NOT INTENDED TO ADDRESS
* ALL REAL WORLD PROBLEMS AND ISSUES IN DETAIL.
 */

package at.fhooe.im440.workbench.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.ColliderSystem.Collideable;
import at.fhooe.im440.workbench.services.RenderSystem.RenderSystem;

/**
 * Visual that renders bitmap graphics (sprites) using a SpriteBatch.
 *
 * This class offers setter to be used for method chaining.This is particularly
 * useful to set a lot of parameters during construction time without haven a
 * very complicated constructor.
 *
 * <p>
 * <strong>Example:</strong>
 * 
 * <pre>
 * TextureRegion reg = getRegionFromSomewhere();
 *
 * Entity entity = new Entity();
 *
 * entity.addComponent(new SpriteVisual(reg).width(2.5f).height(1.3f).color(Color.GREEN).setOriginCenter());
 * </pre>
 * </p>
 *
 * @author Roman Divotkey
 */
public class SpriteVisual extends BaseComponent implements Visual {

	/** Texture region used for rendering. */
	private TextureRegion region;

	/** X-Offset of this sprite. */
	private float offX;

	/** Y-Offset of this sprite. */
	private float offY;

	/** Reference to the pose of our entity. */
	private Pose pose;

	/** X-Origin within the texture region used for rotation and scaling. */
	private float originX;

	/** Y-Origin within the texture region used for rotation and scaling. */
	private float originY;

	/** Width of this visual in world units. */
	private float width;

	/** Height of this visual in world units. */
	private float height;

	/** Half width of this visual in world units. */
	private float hRad;

	/** Half height of this visual in world units. */
	private float vRad;

	/** X-Scaling of this visual. */
	private float scaleX = 1;

	/** Y-Scaling of this visual. */
	private float scaleY = 1;

	/** Tint color of this visual. */
	private Color color = new Color(Color.WHITE);
	/** Layer value of this visual. */
	private int layer = 0;

	/**
	 * Creates a new instance with the specified texture region.
	 *
	 * @param region
	 *            Texture region to be used by this visual.
	 */
	public SpriteVisual(TextureRegion region) {
		this.region = region;
		this.width = region.getRegionWidth();
		this.height = region.getRegionHeight();
	}
	
	// Getters needed for Colliders
	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
	
	public Vector2 getCenterCoordinates() {
		Vector2 center = new Vector2();
		center.x = this.offX + this.originX;
		center.y = this.offY + this.originY;
		return center;
	}

	/**
	 * Sets the layer value of this visual. The default layer value is zero.
	 *
	 * @param value
	 *            layer value of this visual
	 * @return reference to this visual, used for method chaining
	 */
	public SpriteVisual layer(int value) {
		this.layer = value;
		return this;
	}

	@Override
	public int getLayer() {
		return this.layer;
	}

	/**
	 * Sets the tint color of this visual.
	 *
	 * @param value
	 *            color used to tint this visual.
	 * @return reference to this visual, used for method chaining
	 */
	public SpriteVisual color(Color value) {
		this.color.set(value);

		return this;
	}

	/**
	 * Sets the origin of this visual to its center. The center is calculated
	 * according to the current width and height.
	 *
	 * @return reference to this visual, used for method chaining
	 */
	public SpriteVisual setOriginCenter() {
		this.originX = this.width / 2;
		this.originY = this.height / 2;
		return this;
	}

	/**
	 * Sets the origin of this visual.
	 *
	 * @param ox
	 *            x coordinate of the origin
	 * @param oy
	 *            y coordinate of the origin
	 * @return reference to this visual, used for method chaining
	 */
	public SpriteVisual origin(float ox, float oy) {
		this.originX = ox;
		this.originY = oy;
		return this;
	}

	/**
	 * Sets the offset of this visual. The visual is renderd at the position of
	 * its entity + the specified offset. The offset is zero by default.
	 *
	 * @param ox
	 *            x value of the offset
	 * @param oy
	 *            y value of the offset
	 * @return reference to this visual, used for method chaining
	 */
	public SpriteVisual offset(float ox, float oy) {
		this.offX = ox;
		this.offY = oy;
		return this;
	}

	/**
	 * Sets the width of this visual.
	 *
	 * @param value
	 *            width of this visual in world units
	 * @return reference to this visual, used for method chaining
	 */
	public SpriteVisual width(float value) {
		this.width = value;
		this.hRad = this.width / 2;
		return this;
	}

	/**
	 * Sets the height of this visual.
	 *
	 * @param value
	 *            height of this visual in world units
	 * @return reference to this visual, used for method chaining
	 */
	public SpriteVisual height(float value) {
		this.height = value;
		this.vRad = this.height / 2;
		return this;
	}

	/**
	 * Sets the scaling of this visual. By default visuals are not scaled, hence
	 * scaling is set to 1, 1.
	 *
	 * @param sx
	 *            x scaling value
	 * @param sy
	 *            y scaling value
	 * @return reference to this visual, used for method chaining
	 */
	public SpriteVisual scale(float sx, float sy) {
		this.scaleX = sx;
		this.scaleY = sy;
		return this;
	}

	// TODO: change those to activate() and remove the boolean activated flag?
	@Override
	public void addToManager() {
		ServiceManager.getService(RenderSystem.class).addVisual(this);
		this.pose = this.getEntity().getComponent(Pose.class);
		this.activate();
	}

	@Override
	public void removeFromManager() {
		ServiceManager.getService(RenderSystem.class).removeVisual(this);
		this.deactivate();
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.setColor(color);
		batch.draw(this.region, this.offX + this.pose.getPosX() - this.hRad, // x position
				this.offY + this.pose.getPosY() - vRad, // y position
				this.originX, this.originY, // origin
				this.width, this.height, // size in world space
				this.scaleX, this.scaleY, // scaling factor
				MathUtils.radDeg * this.pose.getAngle()); // angle in degrees
	}

	/**
	 * Returns the tint color of this visual. Due to performance reasons the *
	 * return value is plain reference of the used color. Changes to the
	 * returned color will have affect to this visual. Use with care!
	 *
	 * @return current tint color
	 */
	public Color getColor() {
		return this.color;
	}

	@Override
	public boolean contains(float x, float y) {
		
		if (this.getEntity().hasComponent(CircleCollider.class)) {
			return this.getEntity().getComponent(CircleCollider.class).isHit(x, y);
		}

		if (this.getEntity().hasComponent(BoxCollider.class)) {
			return this.getEntity().getComponent(BoxCollider.class).isHit(x, y);
		}
		
		return false;
	}
}
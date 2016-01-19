package at.fhooe.im440.workbench.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

import at.fhooe.im440.workbench.components.CircleCollider;
import at.fhooe.im440.workbench.components.Collider;
import at.fhooe.im440.workbench.components.CollisionMarker;
import at.fhooe.im440.workbench.components.Physics;
import at.fhooe.im440.workbench.components.SpriteVisual;
import at.fhooe.im440.workbench.components.StaticPose;
import at.fhooe.im440.workbench.helpers.Picasso;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.AssetManager.AssetManager;
import at.fhooe.im440.workbench.services.EntityManager.Entity;
import at.fhooe.im440.workbench.services.Messenger.IntegerMessage;
import at.fhooe.im440.workbench.services.Messenger.Message;
import at.fhooe.im440.workbench.services.Messenger.MessageType;
import at.fhooe.im440.workbench.services.Messenger.Messenger;
import at.fhooe.im440.workbench.services.Messenger.Subscribeable;

public class Player extends Entity implements Subscribeable {
	
	private MessageType[] listenTo = new MessageType[] { MessageType.KEY_DOWN };
	private Physics physicsComponent = new Physics();
	
	public Player(float x, float y) {
		this(x, y, 0.25f);
	}
	
	public Player(float x, float y, float r) {
		this(x, y, r, Picasso.BLACK);
	}
	
	public Player(float x, float y, Color color) {
		this(x, y, 0.25f, color);
	}
	
	public Player(float x, float y, float r, Color color) {
		SpriteVisual spriteVisual = new SpriteVisual(ServiceManager.getService(AssetManager.class).getRegion("player")).width(r*2).height(r*2).setOriginCenter();
		spriteVisual.setColor(color);
		
		this.addComponents(new StaticPose(x, y, 0f), spriteVisual, this.physicsComponent, new CircleCollider(r*2), new CollisionMarker(Color.RED));
	}
	
	private void handleJump() {
		this.physicsComponent.applyForce(0f, 1500f);
		Gdx.app.log("Player", "jump");
	}
	
	private void handleMoveUp() {
		this.handleJump();
		Gdx.app.log("Player", "up");
	}
	
	private void handleMoveRight() {
		this.physicsComponent.applyForce(500f, 0f);
		Gdx.app.log("Player", "right");
	}
	
	private void handleMoveLeft() {
		this.physicsComponent.applyForce(-500f, 0f);
		Gdx.app.log("Player", "left");
	}
	
	private void handleMoveDown() {
		this.physicsComponent.applyForce(0f, -500f);
		Gdx.app.log("Player", "down");
	}
	
	private boolean isColliding() {
		return this.getComponent(Collider.class).isColliding();
	}
	
	@Override
	public void update() {
		if (this.isColliding()) {
			this.physicsComponent.deactivate();
		}
	}

	@Override
	public void message(Message message) {
		MessageType type = message.getType();

		switch (type) {
		case KEY_DOWN:
			if (this.isColliding()) {
				this.physicsComponent.activate();
			} else {
				return;
			}
			
			int keyCode = message.get(IntegerMessage.class).getValue();

			if (keyCode == Keys.SPACE) {
				this.handleJump();
			}
			
			if (keyCode == Keys.UP) {
				this.handleMoveUp();
			}
			
			if (keyCode == Keys.RIGHT) {
				this.handleMoveRight();
			}
			
			if (keyCode == Keys.DOWN) {
				this.handleMoveDown();
			}
			
			if (keyCode == Keys.LEFT) {
				this.handleMoveLeft();
			}
			
			break;
		default:
			break;
		}
	}
	
	@Override
	public void subscribe() {
		ServiceManager.getService(Messenger.class).subscribe(this, this.listenTo);
	}

	@Override
	public void unsubscribe() {
		ServiceManager.getService(Messenger.class).unsubscribe(this, this.listenTo);
	}
	
	@Override
	public void activate() {
		this.subscribe();
		super.activate();
	}
	
	@Override
	public void deactivate() {
		super.deactivate();
		this.unsubscribe();
	}
}

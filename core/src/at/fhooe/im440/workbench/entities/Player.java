package at.fhooe.im440.workbench.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

import at.fhooe.im440.workbench.components.CircleCollider;
import at.fhooe.im440.workbench.components.Collider;
import at.fhooe.im440.workbench.components.CollisionMarker;
import at.fhooe.im440.workbench.components.Physics;
import at.fhooe.im440.workbench.components.Pose;
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
	
	private MessageType[] listenTo = new MessageType[] { MessageType.KEY_DOWN, MessageType.KEY_UP };
	private Physics physicsComponent = new Physics();
	private List<Integer> pressedKeys = new ArrayList<Integer>();
	private boolean wasColliding = false;
	private boolean physicsActive = true;
	private int jumpCount = 0;
	private int maxJump = 3;
	
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
		this.physicsComponent.setMaxVelocity(5f, 1000f);
		
		SpriteVisual spriteVisual = new SpriteVisual(ServiceManager.getService(AssetManager.class).getRegion("player")).width(r*2).height(r*2).setOriginCenter();
		spriteVisual.setColor(color);
		
		this.addComponents(new StaticPose(x, y, 0f), spriteVisual, this.physicsComponent, new CircleCollider(r*2), new CollisionMarker(Color.RED));
	}
	
	private void handleJump() {
		if (this.jumpCount++ >= this.maxJump) {
			return;
		}
		
		Pose pose = this.getComponent(Pose.class);
		
		this.physicsComponent.applyForce(0f, 800f);
		
		float y = this.getComponent(Pose.class).getPosY();
		this.getComponent(Pose.class).setPos(pose.getPosX(), this.wasColliding ? y + 0.3f : y);
	}
	
	private void handleMoveUp() {
	}
	
	private void handleMoveRight() {
		this.physicsComponent.applyForce(100f, 0f);
	}
	
	private void handleMoveLeft() {
		this.physicsComponent.applyForce(-100f, 0f);
	}
	
	private void handleMoveDown() {
		this.physicsComponent.applyForce(0f, -100f);
	}
	
	private boolean isColliding() {
		return this.getComponent(Collider.class).isColliding();
	}
	
	private void handlePressedKeys() {
		if (!this.physicsActive) {
			return;
		}
		
		for (Integer keyCode : this.pressedKeys) {
			handlePressedKey(keyCode);
		}
	}
	
	private void handlePressedKey(int keyCode) {
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
	}
	
	@Override
	public void update() {
		this.handlePressedKeys();
		if (this.isColliding()) {
			if (!wasColliding) {
				this.jumpCount = 0;
				this.physicsComponent.deactivate();
				this.physicsComponent.reset();
				this.wasColliding = true;
				this.physicsActive = false;
			}
		} else if (this.wasColliding) {
			this.wasColliding = false;
		}
	}

	@Override
	public void message(Message message) {
		MessageType type = message.getType();
		int keyCode;
		
		switch (type) {
		case KEY_DOWN:
			keyCode = message.get(IntegerMessage.class).getValue();
			
			this.pressedKeys.add(keyCode);

			if (keyCode == Keys.SPACE) {
				if (!this.physicsActive) {
					this.physicsActive = true;
					this.physicsComponent.activate();
				}
				
				this.handleJump();
			}
			
			break;
		case KEY_UP:
			keyCode = message.get(IntegerMessage.class).getValue();
			
			this.pressedKeys.remove(this.pressedKeys.indexOf(keyCode));
			
			break;
		default:
			break;
		}
		//this.handlePressedKeys();
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

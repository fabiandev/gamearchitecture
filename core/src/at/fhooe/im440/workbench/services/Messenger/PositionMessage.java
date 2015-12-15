package at.fhooe.im440.workbench.services.Messenger;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.CameraSystem.CameraSystem;

public class PositionMessage extends MessageData {

	private Vector2 position;
	private Vector2 worldPosition;
	
	public PositionMessage(float x, float y) {
		this.position = new Vector2(x, y);
		this.worldPosition = this.toWorldCoordinates(x, y);
	}
	
	public PositionMessage(int x, int y) {
		this((float)x, (float) y);
	}
	
	public float getX() {
		return this.worldPosition.x;
	}
	
	public float getY() {
		return this.worldPosition.y;
	}
	
	public Vector2 getVector() {
		return this.worldPosition;
	}
	
	public float getOriginallX() {
		return this.position.x;
	}
	
	public float getOriginalY() {
		return this.position.y;
	}
	
	public Vector2 getOriginalVector() {
		return this.position;
	}
	
	private Vector2 toWorldCoordinates(float x, float y) {
		Vector3 vector = ServiceManager.getService(CameraSystem.class).toWorldCoordinates(x, y);
		
		return new Vector2(vector.x, vector.y);
	}

}

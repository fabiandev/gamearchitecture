package at.fhooe.im440.workbench.services.Messenger;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.CameraSystem.CameraSystem;

public class PositionMessage extends MessageData {

	private Vector2 position;
	
	public PositionMessage(float x, float y) {
		this.position = this.toWorldCoordinates(x, y);
	}
	
	public PositionMessage(int x, int y) {
		this((float)x, (float) y);
	}
	
	public float getX() {
		return this.position.x;
	}
	
	public float getY() {
		return this.position.y;
	}
	
	public Vector2 getVector() {
		return this.position;
	}
	
	private Vector2 toWorldCoordinates(float x, float y) {
		Vector3 vector = ServiceManager.getService(CameraSystem.class).toWorldCoordinates(x, y);
		return new Vector2(vector.x, vector.y);
	}

}

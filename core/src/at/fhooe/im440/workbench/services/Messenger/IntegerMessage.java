package at.fhooe.im440.workbench.services.Messenger;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.CameraSystem.CameraSystem;

public class IntegerMessage extends MessageData {

	int value;
	
	public IntegerMessage(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}

}

package at.fhooe.im440.workbench.services.CameraSystem;

import at.fhooe.im440.workbench.Workbench;
import at.fhooe.im440.workbench.components.Component;
import at.fhooe.im440.workbench.components.Pose;
import at.fhooe.im440.workbench.components.StaticPose;

public class CameraTarget extends Component {

	private Pose pose;
	private float lerp = 1f;
	
	public float getLerp() {
		return lerp;
	}

	public void setLerp(float lerp) {
		this.lerp = lerp;
	}

	public CameraTarget(Pose pose) {
		this.pose = pose;
	}
	
	public CameraTarget() {
		this(new StaticPose(Workbench.VIEWPORT_WIDTH / 2f, Workbench.VIEWPORT_HEIGHT / 2f));
	}

	public void setPose(Pose pose) {
		this.pose = pose;
	}
	
	public Pose getPose() {
		return this.pose;
	}
	
	public float getPosX() {
		return pose.getPosX();
	}
	
	public float getPosY() {
		return pose.getPosY();
	}
	
	public float getAngle() {
		return pose.getAngle();
	}

	@Override
	public void activate() {
		// TODO add to camera system
	}

	@Override
	public void deactivate() {
		// TODO remove from camera system
	}
	
}

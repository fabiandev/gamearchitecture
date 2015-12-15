package at.fhooe.im440.workbench.components;

public class StaticPose extends BaseComponent implements Pose {

	private float x;
	private float y;
	private float angleRadians;

	public StaticPose() {
		this(0, 0);
	}
	
	public StaticPose(float x, float y) {
		this(x, y, 0f);
	}

	public StaticPose(float x, float y, float angleRadians) {
		this.x = x;
		this.y = y;
		this.angleRadians = angleRadians;
	}

	@Override
	public float getPosX() {
		return this.x;
	}

	@Override
	public float getPosY() {
		return this.y;
	}

	@Override
	public float getAngle() {
		return this.angleRadians;
	}

	@Override
	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void setAngle(float angleRadians) {
		if (angleRadians > (2 * Math.PI)) {
			this.angleRadians = (float)(angleRadians - 2 * Math.PI);
		} else if (angleRadians < 0) {
			this.angleRadians = (float)(angleRadians + 2 * Math.PI);
		} else {
			this.angleRadians = angleRadians;
		}
	}

	@Override
	public void activate() {
		
	}

	@Override
	public void deactivate() {
		
	}

}

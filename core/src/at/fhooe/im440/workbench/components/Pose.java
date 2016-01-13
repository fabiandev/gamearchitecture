package at.fhooe.im440.workbench.components;

public abstract class Pose extends Component {

	public abstract float getPosX();
	public abstract float getPosY();
	public abstract float getAngle();
	public abstract void setPos(float x, float y);
	public abstract void setAngle(float angleRadians);
	
}

package at.fhooe.im440.workbench.components;

public interface Pose extends Component {

	public float getPosX();
	public float getPosY();
	public float getAngle();
	public void setPos(float x, float y);
	public void setAngle(float angleRadians);
	
}

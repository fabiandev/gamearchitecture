package at.fhooe.im440.workbench.components;

public class Position extends Component implements Pose {

	private float x = 0;
	private float y = 0;
	
	public Position(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
}

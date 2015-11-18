package at.fhooe.im440.workbench.components;

public class BoxCollider extends Collider {

	public BoxCollider(float width, float height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isHit(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHit(BoxCollider c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHit(CircleCollider c) {
		// TODO Auto-generated method stub
		return false;
	}
	
}

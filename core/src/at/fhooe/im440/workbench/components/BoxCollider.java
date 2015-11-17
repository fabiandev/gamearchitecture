package at.fhooe.im440.workbench.components;

public class BoxCollider extends Collider {

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

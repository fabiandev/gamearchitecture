package at.fhooe.im440.workbench.entities;

import at.fhooe.im440.workbench.services.EntityManager.Entity;
import at.fhooe.im440.workbench.services.PhysicsEngine.PhysicsObject;

public class Spring extends Entity {

	PhysicsObject p1;
	PhysicsObject p2;
	
	
	// float acceleration = 9.81f;
	float decay = 0.5f; // b
	float stiffness = 3f; // k
	float lengthX; // d
	float lengthY; // d
	
	
	public Spring(PhysicsObject p1, PhysicsObject p2) {
		this.p1 = p1;
		this.p2 = p2;
		
		// Math.abs(p1.getPose().getPosX() - p2.getPose().getPosX());
		
		this.lengthX = 0f;
		this.lengthY = 2f;
		
		// distance p1 p2 = d
	}
	
	public Spring setDesiredLengthY(float lengthY) {
		this.lengthY = lengthY;
		return this;
	}
	
	public Spring setDesiredLengthX(float lengthX) {
		this.lengthX = lengthX;
		return this;
	}
	
	public Spring setDecay(float decay) {
		this.decay = decay;
		return this;
	}
	
	public Spring setStiffness(float stiffness) {
		this.stiffness = stiffness;
		return this;
	}
	
//	private void calculateStretch() {
//		this.stretch = (float) (0.00406 * this.force + 3.43f * 10e-5);
//	}
	
	@Override
	public void update() {
		float posX1 = p1.getPose().getPosX();
		float posY1 = p1.getPose().getPosY();
		
		float posX2 = p2.getPose().getPosX();
		float posY2 = p2.getPose().getPosY();
		
		float forceX1 = this.calc(posX1, posX2, this.lengthX, posX1 - posX2, p1.getVelocityX());
		float forceX2 = this.calc(posX1, posX2, this.lengthX, posX2 - posX1, p2.getVelocityX());
		
		float forceY1 = this.calc(posY1, posY2, this.lengthY, posY1 - posY2, p1.getVelocityY());
		float forceY2 = this.calc(posY1, posY2, this.lengthY, posY2 - posY1, p2.getVelocityY());
		
		System.out.print("spring: ");
		System.out.print(" ");
		System.out.print(forceX1);
		System.out.print(" ");
		System.out.println(forceX2);
		
		p1.applyForce(forceX1, forceY1);
		p2.applyForce(forceX2, forceY2);
	}
	
	private float calc(float position1, float position2, float desiredLength, float length, float velocity) {
		float direction = length == 0 ? 0 : length/Math.abs(length);
		return (this.stiffness*-1) * (Math.abs(position1 - position2) - desiredLength) * direction - this.decay * velocity;
	}
	
}

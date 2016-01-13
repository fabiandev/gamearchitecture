package at.fhooe.im440.workbench.screens;

import java.util.ArrayList;
import java.util.Arrays;

import at.fhooe.im440.workbench.components.Editable;
import at.fhooe.im440.workbench.components.NoPhysics;
import at.fhooe.im440.workbench.components.Physics;
import at.fhooe.im440.workbench.entities.Spring;
import at.fhooe.im440.workbench.helpers.Picasso;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.EditorSystem.EditorSystem;
import at.fhooe.im440.workbench.services.EntityManager.Entity;
import at.fhooe.im440.workbench.services.EntityManager.EntityFactory;

public class PhysicsScreen extends BaseScreen {
	
	private EditorSystem editorSystem;
	private ArrayList<Entity> entities;
	
	public PhysicsScreen() {
		this.entities = new ArrayList<Entity>();
		this.editorSystem = new EditorSystem();
		
		Physics p1 = new Physics();
		Physics p2 = new Physics();
		Entity e1 = ServiceManager
				.getService(EntityFactory.class)
				.createCogwheel(20f, 12f, Picasso.GREEN)
				.addComponent(new Editable())
				.addComponent(p1);
		
		Entity e2 = ServiceManager
				.getService(EntityFactory.class)
				.createCogwheel(10f, 8f, Picasso.RED)
				.addComponent(new Editable())
				.addComponent(p2);
		
		NoPhysics p3 = new NoPhysics();
		
		Entity e3 = ServiceManager
				.getService(EntityFactory.class)
				.createCogwheel(10f, 15f, Picasso.GOLD)
				.addComponent(new Editable())
				.addComponent(p3);
		
		Entity s1 = new Spring(p1, p2).setStiffness(15f).drawSpring();
		Entity s2 = new Spring(p3, p2).setStiffness(50f).setDesiredLengthY(1f).drawSpring();
		
		this.entities.addAll(Arrays.asList(new Entity[] {
			e1, e2, e3, s1, s2
		}));
		
		this.createSprings();

	}
	
	private void createSprings() {
		
		float mass = 5f;
		
		NoPhysics noPhysics = new NoPhysics();
		Physics p1 = new Physics().setMass(mass);
		Physics p2 = new Physics().setMass(mass);
		Physics p3 = new Physics().setMass(mass);
		Physics p4 = new Physics().setMass(mass);
		
		Entity e4 = ServiceManager
				.getService(EntityFactory.class)
				.createCogwheel(5f, 15f, Picasso.GOLD)
				.addComponent(new Editable())
				.addComponent(noPhysics);
		
		Entity e5 = ServiceManager
				.getService(EntityFactory.class)
				.createCogwheel(5f, 10f, Picasso.BLUE)
				.addComponent(new Editable())
				.addComponent(p1);
		
		Entity s1 = new Spring(noPhysics, p1).setStiffness(500f).setDesiredLengthY(5f).drawSpring();
		
		Entity e6 = ServiceManager
				.getService(EntityFactory.class)
				.createCogwheel(5f, 5f, Picasso.GREEN)
				.addComponent(new Editable())
				.addComponent(p2);
		
		Entity e7 = ServiceManager
				.getService(EntityFactory.class)
				.createCogwheel(10f, 5f, Picasso.RED)
				.addComponent(new Editable())
				.addComponent(p3);
		
		Entity e8 = ServiceManager
				.getService(EntityFactory.class)
				.createCogwheel(10f, 10f, Picasso.BROWN)
				.addComponent(new Editable())
				.addComponent(p4);
		
		float stiffness = 300f;
		
		Entity s2 = new Spring(p1, p2).setStiffness(stiffness).setDesiredLengthY(5f).setDesiredLengthX(5f).drawSpring();
		
		Entity s3 = new Spring(p2, p3).setStiffness(stiffness).setDesiredLengthY(5f).setDesiredLengthX(5f).drawSpring();
		
		Entity s4 = new Spring(p3, p4).setStiffness(stiffness).setDesiredLengthY(5f).setDesiredLengthX(5f).drawSpring();
		
		Entity s5 = new Spring(p4, p1).setStiffness(stiffness).setDesiredLengthY(5f).setDesiredLengthX(5f).drawSpring();
		
		
		
		Entity s6 = new Spring(p1, p3).setStiffness(stiffness).setDesiredLengthY(5f).setDesiredLengthX(5f).drawSpring();
		
		Entity s7 = new Spring(p2, p4).setStiffness(stiffness).setDesiredLengthY(5f).setDesiredLengthX(5f).drawSpring();
		
		this.entities.addAll(Arrays.asList(new Entity[] {
				e4, e5, e6, e7, e8, s1, s2, s3, s4, s5, s6, s7
			}));
		
	}
	
	@Override
	public void render(float delta) {
		Picasso.paintBackground(Picasso.WHITE);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void show() {
		super.show();
		
		this.editorSystem.activate();
		
		for (Entity entity : this.entities) {
			entity.activateComponents();
			entity.activate();
			// Angle testing for rectangle below:
//			if (entity instanceof WallEntity) {
//				entity.getComponent(Pose.class).setAngle((float) Math.toRadians(12.3465)); 
//			}
		}
	}

	@Override
	public void hide() {
		super.hide();
		
		for (Entity entity : this.entities) {
			entity.deactivateComponents();
			entity.deactivate();
		}
		
		this.editorSystem.deactivate();
	}

	@Override
	public void dispose() {
		super.dispose();
		this.hide();
	}

}

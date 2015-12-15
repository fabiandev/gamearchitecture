package at.fhooe.im440.workbench.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;

import at.fhooe.im440.workbench.Workbench;
import at.fhooe.im440.workbench.components.Editable;
import at.fhooe.im440.workbench.components.NoPhysics;
import at.fhooe.im440.workbench.components.Physics;
import at.fhooe.im440.workbench.entities.Spring;
import at.fhooe.im440.workbench.helpers.Picasso;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.EditorSystem.EditorSystem;
import at.fhooe.im440.workbench.services.EntityManager.Entity;
import at.fhooe.im440.workbench.services.EntityManager.EntityFactory;

public class EditorScreen extends ScreenAdapter implements Screen {
	
	//private Workbench workbench;
	private EditorSystem editorSystem;
	private ArrayList<Entity> entities;
	
	public EditorScreen(Workbench workbench) {
		//this.workbench = workbench;
		this.editorSystem = new EditorSystem();
		this.entities = new ArrayList<Entity>();
		
		ServiceManager.addService(this.editorSystem);
		
//		Entity te = ServiceManager.getService(EntityFactory.class).createCogwheel(7f, 10f).addComponent(new Editable()).addComponent(new Physics());
//		
//		this.entities.add(te);
//		this.entities.add(ServiceManager.getService(EntityFactory.class).createCogwheel(6.5f, 1f).addComponent(new Editable()));
//		this.entities.add(ServiceManager.getService(EntityFactory.class).createWall(7f, 6f).addComponent(new Editable()));
//		
		
		
		Physics p1 = new Physics();
		Physics p2 = new Physics();
		Entity e1 = ServiceManager
				.getService(EntityFactory.class)
				.createCogwheel(20f, 12f, Picasso.GREEN)
				.addComponent(p1);
		
		Entity e2 = ServiceManager
				.getService(EntityFactory.class)
				.createCogwheel(10f, 8f, Picasso.RED)
				.addComponent(p2);
		
		NoPhysics p3 = new NoPhysics();
		
		Entity e3 = ServiceManager
				.getService(EntityFactory.class)
				.createCogwheel(10f, 15f, Picasso.GOLD)
				.addComponent(new Editable())
				.addComponent(p3);
		
		e1.activateComponents();
		e1.activate();
		e2.activateComponents();
		e2.activate();
		e3.activateComponents();
		e3.activate();
		
		Entity s = new Spring(p1, p2).setStiffness(15f).drawSpring();
		s.activateComponents();
		s.activate();
		
		Entity s2 = new Spring(p3, p2).setStiffness(50f).setDesiredLengthY(1f).drawSpring();
		s2.activateComponents();
		s2.activate();
		
		this.createSprings();

	}
	
	private void createSprings() {
		
		NoPhysics noPhysics = new NoPhysics();
		Physics p1 = new Physics();
		Physics p2 = new Physics();
		Physics p3 = new Physics();
		Physics p4 = new Physics();
		
		Entity e4 = ServiceManager
				.getService(EntityFactory.class)
				.createCogwheel(5f, 15f, Picasso.GOLD)
				.addComponent(new Editable())
				.addComponent(noPhysics);
		
		Entity e5 = ServiceManager
				.getService(EntityFactory.class)
				.createCogwheel(5f, 10f, Picasso.BLUE)
				.addComponent(p1);
		
		Entity s1 = new Spring(noPhysics, p1).setStiffness(500f).setDesiredLengthY(5f).drawSpring();
		
		Entity e6 = ServiceManager
				.getService(EntityFactory.class)
				.createCogwheel(5f, 5f, Picasso.GREEN)
				.addComponent(p2);
		
		Entity e7 = ServiceManager
				.getService(EntityFactory.class)
				.createCogwheel(10f, 5f, Picasso.RED)
				.addComponent(p3);
		
		Entity e8 = ServiceManager
				.getService(EntityFactory.class)
				.createCogwheel(10f, 10f, Picasso.BROWN)
				.addComponent(p4);
		
		float stiffness = 300f;
		
		Entity s2 = new Spring(p1, p2).setStiffness(stiffness).setDesiredLengthY(5f).setDesiredLengthX(5f).drawSpring();
		
		Entity s3 = new Spring(p2, p3).setStiffness(stiffness).setDesiredLengthY(5f).setDesiredLengthX(5f).drawSpring();
		
		Entity s4 = new Spring(p3, p4).setStiffness(stiffness).setDesiredLengthY(5f).setDesiredLengthX(5f).drawSpring();
		
		Entity s5 = new Spring(p4, p1).setStiffness(stiffness).setDesiredLengthY(5f).setDesiredLengthX(5f).drawSpring();
		
		
		
		Entity s6 = new Spring(p1, p3).setStiffness(stiffness).setDesiredLengthY(5f).setDesiredLengthX(5f).drawSpring();
		
		Entity s7 = new Spring(p2, p4).setStiffness(stiffness).setDesiredLengthY(5f).setDesiredLengthX(5f).drawSpring();
		
		e4.activateComponents();
		e4.activate();
		
		e5.activateComponents();
		e5.activate();
		
		e6.activateComponents();
		e6.activate();
		
		e7.activateComponents();
		e7.activate();
		
		e8.activateComponents();
		e8.activate();
		
		s1.activateComponents();
		s1.activate();
		
		s2.activateComponents();
		s2.activate();
		
		s3.activateComponents();
		s3.activate();
		
		s4.activateComponents();
		s4.activate();
		
		s5.activateComponents();
		s5.activate();
		
		s6.activateComponents();
		s6.activate();
		
		s7.activateComponents();
		s7.activate();
		
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
		for (Entity entity : this.entities) {
			entity.activateComponents();
			entity.activate();
			// Angle testing for rectangle below:
//			if (entity instanceof WallEntity) {
//				entity.getComponent(Pose.class).setAngle((float) Math.toRadians(12.3465)); 
//			}
		}
		this.editorSystem.subscribe();
	}

	@Override
	public void hide() {
		super.hide();
		for (Entity entity : this.entities) {
			entity.deactivateComponents();
			entity.deactivate();
		}
		this.editorSystem.unsubscribe();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

}

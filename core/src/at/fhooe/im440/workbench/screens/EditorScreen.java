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
	
	private Workbench workbench;
	private EditorSystem editorSystem;
	private ArrayList<Entity> entities;
	
	public EditorScreen(Workbench workbench) {
		this.workbench = workbench;
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
		//p1.setMass(15f);
		Entity e1 = ServiceManager
				.getService(EntityFactory.class)
				.createCogwheel(5f, 12f, Picasso.GREEN)
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
		
		Entity s = new Spring(p1, p2).setStiffness(15f);
		s.activateComponents();
		s.activate();
		
		Entity s2 = new Spring(p3, p2).setStiffness(50f).setDesiredLengthY(1f);
		s2.activateComponents();
		s2.activate();
		
		//Spring spring = new Spring();
		//spring.attachObject(te.getComponent(Physics.class));
		
		//this.entities.add(spring);

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

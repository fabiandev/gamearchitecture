package at.fhooe.im440.workbench.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;

import at.fhooe.im440.workbench.Workbench;
import at.fhooe.im440.workbench.components.Editable;
import at.fhooe.im440.workbench.components.Physics;
import at.fhooe.im440.workbench.components.SpriteVisual;
import at.fhooe.im440.workbench.components.StaticPose;
import at.fhooe.im440.workbench.entities.Spring;
import at.fhooe.im440.workbench.entities.TestEntity;
import at.fhooe.im440.workbench.components.Pose;
import at.fhooe.im440.workbench.entities.WallEntity;
import at.fhooe.im440.workbench.helpers.Picasso;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.EditorSystem.EditorSystem;
import at.fhooe.im440.workbench.services.EntityManager.Entity;
import at.fhooe.im440.workbench.services.EntityManager.EntityFactory;
import at.fhooe.im440.workbench.services.EntityManager.EntityManager;
import at.fhooe.im440.workbench.services.PhysicsEngine.PhysicsEngine;

public class EditorScreen extends ScreenAdapter implements Screen {
	
	private Workbench workbench;
	private EditorSystem editorSystem;
	private ArrayList<Entity> entities;
	
	public EditorScreen(Workbench workbench) {
		this.workbench = workbench;
		this.editorSystem = new EditorSystem();
		this.entities = new ArrayList<Entity>();
		
		Entity te = ServiceManager.getService(EntityFactory.class).createCogwheel(7f, 10f).addComponent(new Editable()).addComponent(new Physics());
		
		this.entities.add(te);
		this.entities.add(ServiceManager.getService(EntityFactory.class).createCogwheel(6.5f, 1f).addComponent(new Editable()));
		this.entities.add(ServiceManager.getService(EntityFactory.class).createWall(7f, 6f).addComponent(new Editable()));
		
		Spring spring = new Spring();
		spring.attachObject(te.getComponent(Physics.class));
		
		this.entities.add(spring);

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
		ServiceManager.addService(this.editorSystem);
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
		ServiceManager.removeService(EditorSystem.class);
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

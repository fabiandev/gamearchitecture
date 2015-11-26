package at.fhooe.im440.workbench.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;

import at.fhooe.im440.workbench.Workbench;
import at.fhooe.im440.workbench.components.CircleCollider;
import at.fhooe.im440.workbench.components.Editable;
import at.fhooe.im440.workbench.components.SpriteVisual;
import at.fhooe.im440.workbench.components.StaticPose;
import at.fhooe.im440.workbench.entities.TestEntity;
import at.fhooe.im440.workbench.helpers.Picasso;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.AssetManager.AssetManager;
import at.fhooe.im440.workbench.services.CameraSystem.CameraSystem;
import at.fhooe.im440.workbench.services.EditorSystem.EditorSystem;
import at.fhooe.im440.workbench.services.EntityManager.Entity;
import at.fhooe.im440.workbench.services.EntityManager.EntityFactory;
import at.fhooe.im440.workbench.services.EntityManager.EntityManager;

public class EditorScreen extends ScreenAdapter implements Screen {
	
	private Workbench workbench;
	private EditorSystem editorSystem;
	private ArrayList<Entity> entities;
	
	public EditorScreen(Workbench workbench) {
		this.workbench = workbench;
		this.editorSystem = new EditorSystem();
		this.entities = new ArrayList<Entity>();
		this.entities.add(ServiceManager.getService(EntityFactory.class).createCogwheel(5f, 5f).addComponent(new Editable()));
		this.entities.add(ServiceManager.getService(EntityFactory.class).createCogwheel(.5f, .5f).addComponent(new Editable()));
		this.entities.add(ServiceManager.getService(EntityFactory.class).createBar(2f, 10f).addComponent(new Editable()));
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
			this.editorSystem.addEditable(entity.getComponent(Editable.class));
		}
		this.editorSystem.subscribe();
	}

	@Override
	public void hide() {
		super.hide();
		ServiceManager.removeService(EditorSystem.class);
		for (Entity entity : this.entities) {
			this.editorSystem.removeEditable(entity.getComponent(Editable.class));
			entity.removeComponentsFromManagers();
		}
		this.editorSystem.unsubscribe();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

}

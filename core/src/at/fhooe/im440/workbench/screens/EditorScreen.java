package at.fhooe.im440.workbench.screens;

import at.fhooe.im440.workbench.components.Editable;
import at.fhooe.im440.workbench.helpers.Picasso;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.EditorSystem.EditorSystem;
import at.fhooe.im440.workbench.services.EntityManager.Entity;
import at.fhooe.im440.workbench.services.EntityManager.EntityFactory;

public class EditorScreen extends BaseScreen {
	
	private EditorSystem editorSystem;
	private Entity wheel;
	private Entity wall;
	
	public EditorScreen() {
		if (ServiceManager.hasService(EditorSystem.class)) {
			this.editorSystem = ServiceManager.getService(EditorSystem.class);
		} else {
			this.editorSystem = new EditorSystem();
		}
		
		this.wheel = ServiceManager
				.getService(EntityFactory.class)
				.createCogwheel(10f, 10f)
				.addComponent(new Editable());
		
		this.wall = ServiceManager
				.getService(EntityFactory.class)
				.createWall(6f, 10f)
				.addComponent(new Editable());
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
		
		this.wheel.activateComponents();
		this.wheel.activate();
		
		this.wall.activateComponents();
		this.wall.activate();
	}

	@Override
	public void hide() {
		super.hide();
		
		this.wheel.deactivateComponents();
		this.wheel.deactivate();
		
		this.wall.deactivateComponents();
		this.wall.deactivate();
		
		this.editorSystem.deactivate();
	}

	@Override
	public void dispose() {
		super.dispose();
		this.hide();
	}

}

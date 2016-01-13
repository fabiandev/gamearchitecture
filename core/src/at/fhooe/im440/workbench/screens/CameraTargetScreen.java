package at.fhooe.im440.workbench.screens;

import at.fhooe.im440.workbench.components.Editable;
import at.fhooe.im440.workbench.components.Pose;
import at.fhooe.im440.workbench.helpers.Picasso;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.CameraSystem.CameraSystem;
import at.fhooe.im440.workbench.services.CameraSystem.CameraTarget;
import at.fhooe.im440.workbench.services.EditorSystem.EditorSystem;
import at.fhooe.im440.workbench.services.EntityManager.Entity;
import at.fhooe.im440.workbench.services.EntityManager.EntityFactory;

public class CameraTargetScreen extends BaseScreen {
	
	private EditorSystem editorSystem;
	private Entity entity;
	
	public CameraTargetScreen() {
		if (ServiceManager.hasService(EditorSystem.class)) {
			this.editorSystem = ServiceManager.getService(EditorSystem.class);
		} else {
			this.editorSystem = new EditorSystem();
		}
		
		this.entity = ServiceManager
				.getService(EntityFactory.class)
				.createCogwheel(10f, 10f, Picasso.GREEN)
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
		this.entity.activateComponents();
		this.entity.activate();
		
		CameraTarget cameraTarget = new CameraTarget(this.entity.getComponent(Pose.class));
		ServiceManager.getService(CameraSystem.class).setTarget(cameraTarget);
	}

	@Override
	public void hide() {
		super.hide();
		
		this.entity.deactivateComponents();
		this.entity.deactivate();
		
		this.editorSystem.deactivate();
	}

	@Override
	public void dispose() {
		super.dispose();
		this.hide();
	}

}

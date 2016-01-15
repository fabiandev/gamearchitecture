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
import at.fhooe.im440.workbench.services.EntityManager.EntityManager;

public class CameraTargetScreen extends BaseScreen {
	
	private EditorSystem editorSystem;
	private Entity entity;
	
	public CameraTargetScreen() {
		this.editorSystem = ServiceManager.getService(EditorSystem.class);
		
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
		
		//this.editorSystem.setState(EditorState.SINGLE_SELECTING);
		this.editorSystem.subscribe();
		
		this.entity.activateComponents();
		this.entity.activate();
		
		CameraTarget cameraTarget = new CameraTarget(this.entity.getComponent(Pose.class));
		ServiceManager.getService(CameraSystem.class).setTarget(cameraTarget);
	}

	@Override
	public void hide() {
		super.hide();
		
		this.editorSystem.unsubscribe();
		//this.editorSystem.setState(EditorState.IDLE);
		
		ServiceManager.getService(CameraSystem.class).useDefaultTarget().forceTargetPosition();
	}

	@Override
	public void dispose() {
		ServiceManager.getService(EntityManager.class).deactivateAllEntities();
		this.hide();
		super.dispose();
	}

}

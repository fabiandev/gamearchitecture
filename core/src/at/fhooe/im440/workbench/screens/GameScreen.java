package at.fhooe.im440.workbench.screens;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

import at.fhooe.im440.workbench.Workbench;
import at.fhooe.im440.workbench.components.Component;
import at.fhooe.im440.workbench.components.FollowRotation;
import at.fhooe.im440.workbench.components.Pose;
import at.fhooe.im440.workbench.components.Rotating;
import at.fhooe.im440.workbench.entities.Cogwheel;
import at.fhooe.im440.workbench.entities.Player;
import at.fhooe.im440.workbench.helpers.Picasso;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.CameraSystem.CameraSystem;
import at.fhooe.im440.workbench.services.CameraSystem.CameraTarget;
import at.fhooe.im440.workbench.services.EntityManager.EntityManager;
import at.fhooe.im440.workbench.services.PersistenceSystem.PersistenceSystem;

public class GameScreen extends BaseScreen {
	
	private PersistenceSystem persistenceSystem;
	private CameraTarget cameraTarget;
	private Player player;
	
	public GameScreen() {
		this.persistenceSystem = ServiceManager.getService(PersistenceSystem.class);
		this.player = new Player(Workbench.VIEWPORT_WIDTH / 2f, Workbench.VIEWPORT_HEIGHT / 2f);
		this.player.addComponent(new FollowRotation());
		this.cameraTarget = new CameraTarget();
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
		
		try {
			this.persistenceSystem.restore(Workbench.EDITOR_SAV);
			this.player.activateComponents();
			this.player.activate();
		} catch (IOException e) {
			Gdx.app.log("EditorScreen", e.getMessage());
		}
		
		ArrayList<Cogwheel> cogwheels = ServiceManager.getService(EntityManager.class).getCogwheels();
		for (Cogwheel wheel : cogwheels) {
			Component c = new Rotating();
			wheel.deactivateComponents();
			wheel.addComponent(c);
			wheel.activateComponents();
			c.activate();
		}
		
		this.cameraTarget.setPose(this.player.getComponent(Pose.class));
		ServiceManager.getService(CameraSystem.class).setTarget(this.cameraTarget);
	}

	@Override
	public void hide() {
		ServiceManager.getService(CameraSystem.class).useDefaultTarget().forceTargetPosition();
		this.player.deactivateComponents();
		this.player.deactivate();
		super.hide();
	}

	@Override
	public void dispose() {
		ServiceManager.getService(EntityManager.class).deactivateAllEntities();
		this.hide();
		super.dispose();
	}

}

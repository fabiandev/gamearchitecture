package at.fhooe.im440.workbench.screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;

import at.fhooe.im440.workbench.Workbench;
import at.fhooe.im440.workbench.entities.Player;
import at.fhooe.im440.workbench.helpers.Picasso;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.EntityManager.EntityManager;
import at.fhooe.im440.workbench.services.PersistenceSystem.PersistenceSystem;

public class GameScreen extends BaseScreen {
	
	private PersistenceSystem persistenceSystem;
	private Player player;
	
	public GameScreen() {
		this.persistenceSystem = ServiceManager.getService(PersistenceSystem.class);
		this.player = new Player(2f, 10f);
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
	}

	@Override
	public void hide() {
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

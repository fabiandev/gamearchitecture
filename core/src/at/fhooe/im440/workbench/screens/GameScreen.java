package at.fhooe.im440.workbench.screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;

import at.fhooe.im440.workbench.Workbench;
import at.fhooe.im440.workbench.helpers.Picasso;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.EntityManager.EntityManager;
import at.fhooe.im440.workbench.services.PersistenceSystem.PersistenceSystem;

public class GameScreen extends BaseScreen {
	
	private PersistenceSystem persistenceSystem;
	
	public GameScreen() {
		this.persistenceSystem = ServiceManager.getService(PersistenceSystem.class);
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
			this.persistenceSystem.restore(Gdx.files.absolute(Workbench.EDITOR_SAV).file().getAbsoluteFile());
		} catch (IOException e) {
			Gdx.app.log("EditorScreen", e.getMessage());
		}
	}

	@Override
	public void hide() {
		super.hide();
	}

	@Override
	public void dispose() {
		ServiceManager.getService(EntityManager.class).deactivateAllEntities();
		this.hide();
		super.dispose();
	}

}

package at.fhooe.im440.workbench.screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

import at.fhooe.im440.workbench.Workbench;
import at.fhooe.im440.workbench.components.Editable;
import at.fhooe.im440.workbench.components.persistables.CogwheelPersistable;
import at.fhooe.im440.workbench.components.persistables.WallPersistable;
import at.fhooe.im440.workbench.helpers.Picasso;
import at.fhooe.im440.workbench.services.ServiceManager;
import at.fhooe.im440.workbench.services.EditorSystem.EditorSystem;
import at.fhooe.im440.workbench.services.EntityManager.Entity;
import at.fhooe.im440.workbench.services.EntityManager.EntityFactory;
import at.fhooe.im440.workbench.services.EntityManager.EntityManager;
import at.fhooe.im440.workbench.services.Messenger.IntegerMessage;
import at.fhooe.im440.workbench.services.Messenger.Message;
import at.fhooe.im440.workbench.services.Messenger.MessageType;
import at.fhooe.im440.workbench.services.Messenger.Messenger;
import at.fhooe.im440.workbench.services.Messenger.Subscribeable;
import at.fhooe.im440.workbench.services.PersistenceSystem.PersistenceSystem;

public class EditorScreen extends BaseScreen implements Subscribeable {
	
	private EditorSystem editorSystem;
	private PersistenceSystem persistenceSystem;
	private Entity wheel;
	private Entity wall;
	
	private MessageType[] listenTo = new MessageType[] { MessageType.KEY_UP };
	
	public EditorScreen() {
		this.editorSystem = ServiceManager.getService(EditorSystem.class);
		this.persistenceSystem = ServiceManager.getService(PersistenceSystem.class);
	}

	private void createStandardEntities() {
		this.wheel = ServiceManager
				.getService(EntityFactory.class)
				.createCogwheel(10f, 10f, Color.DARK_GRAY)
				.addComponents(new Editable(), new CogwheelPersistable());
		
		this.wall = ServiceManager
				.getService(EntityFactory.class)
				.createWall(6f, 10f, Color.BROWN)
				.addComponents(new Editable(), new WallPersistable());
		
		this.wheel.activateComponents();
		this.wheel.activate();
		
		this.wall.activateComponents();
		this.wall.activate();
	}

	@Override
	public void subscribe() {
		ServiceManager.getService(Messenger.class).subscribe(this, this.listenTo);
	}

	@Override
	public void unsubscribe() {
		ServiceManager.getService(Messenger.class).unsubscribe(this, this.listenTo);
	}
	
	@Override
	public void message(Message message) {
		super.message(message);
		
		MessageType type = message.getType();

		switch (type) {
		case KEY_UP:
			int keyCode = message.get(IntegerMessage.class).getValue();
			
			if (keyCode == Keys.P) {
				try {
					this.persistenceSystem.store(Workbench.EDITOR_SAV);
				} catch (IOException e) {
					Gdx.app.log("EditorScreen", e.getMessage());
				}
			}
			
			if (keyCode == Keys.BACKSPACE) {
				ServiceManager.getService(EntityManager.class).deactivateAllEntities();
				this.createStandardEntities();
			}
			
			break;
		default:
			break;
		}
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
		this.createStandardEntities();
		this.subscribe();
		this.editorSystem.subscribe();
	}

	@Override
	public void hide() {
		super.hide();
		this.unsubscribe();
		this.editorSystem.unsubscribe();
	}

	@Override
	public void dispose() {
		ServiceManager.getService(EntityManager.class).deactivateAllEntities();
		this.hide();
		super.dispose();
	}

}

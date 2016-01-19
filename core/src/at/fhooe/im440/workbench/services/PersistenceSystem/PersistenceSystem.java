package at.fhooe.im440.workbench.services.PersistenceSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;

import at.fhooe.im440.workbench.components.Persistable;
import at.fhooe.im440.workbench.services.Service;
import at.fhooe.im440.workbench.services.ServiceManager;

public class PersistenceSystem implements Service {

	private static List<Storeable> storeables = new ArrayList<Storeable>();
	private List<Persistable> persistables = new ArrayList<Persistable>();
	
	private File getFileFromString(String file) {
		return Gdx.files.local(file).file().getAbsoluteFile();
	}
	
	private void prepare() {
		storeables.clear();
		for (int i = 0; i < persistables.size(); ++i) {
			Persistable persistable = persistables.get(i);
			storeables.add(persistable.getStoreable());
		}
	}
	
	public void add(Persistable persistable) {
		assert(!persistables.contains(persistable));
		persistables.add(persistable);
	}

	public void remove(Persistable persistable) {
		// silently ignore if persistable is unknown (best practice)
		persistables.remove(persistable);
	}
	
	public void store (String file) throws IOException {
		this.store(this.getFileFromString(file));
	}
	
	public void store(File file) throws IOException {
		prepare();
		
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
		
			out.writeObject(storeables);

			Gdx.app.log("PersistenceSystem", file.getAbsolutePath() + " saved");
		}		
	}
	
	public void restore(String file) throws FileNotFoundException, IOException {
		this.restore(this.getFileFromString(file));
	}
	
	public void restore(File file) throws FileNotFoundException, IOException {
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
			
			@SuppressWarnings("unchecked")
			List<Storeable> storeables = (List<Storeable>) in.readObject();
			for (Storeable storeable : storeables) {
				storeable.restore();
			}
			Gdx.app.log("PersistenceSystem", file.getAbsolutePath() + " loaded");
			
		} catch (ClassNotFoundException e) {
			throw new IOException("invalid world file ", e);
		}
	}
	
	public void restore() {
		for (int i = 0; i < storeables.size(); ++i) {
			storeables.get(i).restore();
		}
	}

	@Override
	public void activate() {
		ServiceManager.addService(this);
	}

	@Override
	public void deactivate() {
		ServiceManager.removeService(this.getClass());
	}
	
	@Override
	public void update() {
		
	}

}

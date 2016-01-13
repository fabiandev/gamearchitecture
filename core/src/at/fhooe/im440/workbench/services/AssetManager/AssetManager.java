package at.fhooe.im440.workbench.services.AssetManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

import at.fhooe.im440.workbench.services.Service;
import at.fhooe.im440.workbench.services.ServiceManager;

public class AssetManager implements Service {

	private String map = "atlas.pack";

	private TextureAtlas atlas;

	public AssetManager() {
		this.init();
	}

	public AssetManager(String map) {
		this.map = map;
		this.init();
	}

	public void init() {
		this.atlas = new TextureAtlas(Gdx.files.internal(this.map));
	}

	public AtlasRegion getRegion(String name) {
		return this.atlas.findRegion(name);
	}
	
	public TextureAtlas getAtlas() {
		return this.atlas;
	}

	public void dispose() {
		this.atlas.dispose();
	}

	@Override
	public void update() {

	}

	@Override
	public void activate() {
		ServiceManager.addService(this);
	}

	@Override
	public void deactivate() {
		ServiceManager.removeService(this.getClass());
	}
}

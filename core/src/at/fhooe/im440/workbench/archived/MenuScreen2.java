package at.fhooe.im440.workbench.archived;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import at.fhooe.im440.workbench.Workbench;
import at.fhooe.im440.workbench.helpers.Picasso;

public class MenuScreen2 extends ScreenAdapter implements InputProcessor {
	
	private Workbench workbench;
	private BitmapFont font;
	private Actor[] actors;
	private Stage stage;
	private MenuChoice choices;
	
	private static final int MARGIN = 20;
	private int deltaX = 0;
	private int menuPointer = 0;

	public MenuScreen2(Workbench workbench) {
		// TODO Auto-generated constructor stub
		this.font = new BitmapFont(Gdx.files.internal("arial_black_32.fnt"));
		//this.font = this.workbench.getFont();
		this.workbench = workbench;
		this.stage = this.workbench.getStage();
		
		String[] labels = {"PLAY", "SETTINGS", "EXIT"};
		actors = new Actor[labels.length];
		for(int i = 0; i < labels.length; i++) {
			actors[i] = new Label(labels[i].subSequence(0, labels[i].length()), new LabelStyle(font, Picasso.BLACK));
			actors[i].setName(labels[i]);
		}
		
		this.choices = new MenuChoice(actors);
	}

	@Override
	public void render(float delta) {
		Picasso.paintBackground(Picasso.GAME_PALEGREEN);
		// Gdx.gl.glClearColor(workbench.COLOR_BG.r, workbench.COLOR_BG.g, workbench.COLOR_BG.b, 1);
		// Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
		
		stage.getViewport().getCamera().position.set(0, 0, 0);
		stage.getViewport().update(width, height);
		
		positionMenu();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		
		for (int i = 0; i < actors.length; i++) {
			stage.addActor(actors[i]);
		}
		
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		super.hide();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
		stage.dispose();
		font.dispose();
	}
	
	public void positionMenu() {
		float offset = 0;
		
		for (int i = 0; i < actors.length; i++) {
			actors[i].setColor(Picasso.BLACK);
			//MoveToAction a = new MoveToAction();
			actors[i].setPosition(deltaX + offset - 100, actors[i].getY());
			//a.setDuration(MAIN_DUR);
			//actors[i].addAction(a);
			offset += actors[i].getWidth() + MARGIN;
		}
		actors[menuPointer].setColor(Picasso.MAGENTA);
	}
	
	public void selectedAction() {
		actors[menuPointer].addAction(Actions.sequence(
				Actions.run(new Runnable() {

					@Override
					public void run() {
						choices.setAction(menuPointer);
					}
					
				})));
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.RIGHT && menuPointer < (actors.length - 1)) { 
			actors[menuPointer++].setColor(Picasso.BLACK);
		} else if (keycode == Keys.LEFT && menuPointer > 0) {
			actors[menuPointer--].setColor(Picasso.BLACK);
		} else if (keycode == Keys.ENTER) {
			this.selectedAction();
		}
		//positionMenu();
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}

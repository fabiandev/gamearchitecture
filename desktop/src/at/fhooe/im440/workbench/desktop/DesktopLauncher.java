package at.fhooe.im440.workbench.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import at.fhooe.im440.workbench.Workbench;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = (int) Workbench.WINDOW_WIDTH;
		config.height = (int) Workbench.WINDOW_HEIGHT;
		config.fullscreen = false;
		
		new LwjglApplication(new Workbench(), config);
	}
}

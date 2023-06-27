package com.janwue.pong;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.janwue.pong.Pong;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(800, 500);
		config.setForegroundFPS(60);
		config.useVsync(true);
		config.setTitle("Pong");
		new Lwjgl3Application(new Pong(), config);
		
	}
}

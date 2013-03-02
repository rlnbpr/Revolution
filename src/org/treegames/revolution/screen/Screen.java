package org.treegames.revolution.screen;

import org.treegames.revolution.Main;

public abstract class Screen {
	public boolean isOverlay=false;

	public abstract void render(Main main);

	public abstract void update(int delta);

	public void initGL() {

	}
}
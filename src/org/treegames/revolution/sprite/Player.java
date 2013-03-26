package org.treegames.revolution.sprite;

import org.lwjgl.util.vector.Vector2f;
import org.treegames.revolution.gfx.Sprites;

public class Player extends Sprite {
	public Player(Vector2f position) {
		super(position);
        this.texture=Sprites.getTexture("player1");
	}

	public void update(int delta) {
		super.update(delta);
	}
}
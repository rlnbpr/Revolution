package org.treegames.revolution.sprite;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.treegames.revolution.GameSettings;
import org.treegames.revolution.gfx.Sprites;
import org.treegames.revolution.gfx.Texture;
import org.treegames.revolution.sound.Sounds;

public class Player extends AnimatedSprite {
    private int animationTimer = 0;
    public boolean doAnim = false;
	public Player(Vector2f position) {
		super(position);
        initAnimations(tex("player1"), tex("player2"), tex("player3"), tex("player4"));
        setFrame(0);
	}

	public void update(int delta) {
        doAnim = false;
        while (Keyboard.next()) {
           if ((Keyboard.getEventKey() == Keyboard.KEY_D || Keyboard.getEventKey() == Keyboard.KEY_A)&& Keyboard.getEventKeyState()) {
               nextFrame();
           }
           if (Keyboard.isKeyDown(Keyboard.KEY_F1)) {
               GameSettings.wireframe = !GameSettings.wireframe;
           }
           if (Keyboard.isKeyDown(Keyboard.KEY_F2)) {
               GameSettings.lighting = !GameSettings.lighting;
           }
        }
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.x += delta * 0.008f;
            doAnim = true;
            setLookDirection(1);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.x -= delta * 0.008f;
            doAnim = true;
            setLookDirection(0);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            position.y += delta * 0.008f;
            animationTimer = 0;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            position.y -= delta * 0.008f;
            animationTimer = 0;
        }
        if (doAnim) {
           animationTimer += 1;
           if (animationTimer == 15) {
               nextFrame();
               Sounds.playSound("footstep");
               animationTimer = 0;
           }
        } else {
           resetAnim();
        }
	}
}
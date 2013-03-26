package org.treegames.revolution.sprite;

import org.lwjgl.util.vector.Vector2f;
import org.treegames.revolution.gfx.Texture;

public class AnimatedSprite extends Sprite {
    private Texture[] animation;
    private int currentFrame;
    private int defaultFrame = 0;
    public AnimatedSprite(Vector2f position) {
        super(position);
    }

    public void initAnimations(Texture... textures) {
        this.animation = new Texture[textures.length];
        for (int i = 0; i < textures.length; i++) {
            this.animation[i] = textures[i];
        }
    }

    public void setDefaultFrame(int defaultFrame) {
        this.defaultFrame = defaultFrame;
    }

    public int getDefaultFrame() {
        return defaultFrame;
    }

    public void resetAnim() {
        setFrame(defaultFrame);
    }

    public boolean setFrame(int frame) {
        currentFrame = frame;
        this.texture = this.animation[frame];
        return true;
    }

    public void nextFrame() {
        if (currentFrame == animation.length - 1) {
            setFrame(0);
            return;
        }
        setFrame(currentFrame + 1);
    }

    public int getFrame() {
        return currentFrame;
    }
}

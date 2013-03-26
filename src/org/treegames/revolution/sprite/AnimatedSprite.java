package org.treegames.revolution.sprite;

import org.lwjgl.util.vector.Vector2f;
import org.treegames.revolution.GameSettings;
import org.treegames.revolution.gfx.Models;
import org.treegames.revolution.gfx.Texture;

import static org.lwjgl.opengl.GL11.*;

public class AnimatedSprite extends Sprite {
    private Texture[] animation;
    private int currentFrame;
    private int defaultFrame = 0;
    private int lookDirection = 1;
    public AnimatedSprite(Vector2f position) {
        super(position);
    }

    public void initAnimations(Texture... textures) {
        this.animation = new Texture[textures.length];
        for (int i = 0; i < textures.length; i++) {
            this.animation[i] = textures[i];
        }
    }

    public void setLookDirection(int lookDirection) {
        this.lookDirection = lookDirection;
    }

    public int getLookDirection() {
        return lookDirection;
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

    public void draw() {
        glPushMatrix();
        if (GameSettings.lighting) {
            glEnable(GL_LIGHTING);
            glEnable(GL_LIGHT0);
        } else {
            glDisable(GL_LIGHTING);
            glDisable(GL_LIGHT0);
        }
        glEnable(GL_BLEND);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glTranslatef(position.x, position.y, -32.5f);
        glRotatef(lookDirection == 0 ? 180 : -90f, 0, 0, 1);
        if (texture != null) texture.use();
        if (GameSettings.wireframe) {
            Texture.unbindAll();
            glDisable(GL_LIGHTING);
            glColor3f(0.0f, 1.0f, 1.0f);
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        }
        glCallList(lookDirection == 0 ? Models.flippedSprite : Models.sprite);
        Texture.unbindAll();
        glDisable(GL_BLEND);
        glDisable(GL_CULL_FACE);
        glDisable(GL_LIGHT0);
        glDisable(GL_LIGHTING);
        glPopMatrix();
    }
}

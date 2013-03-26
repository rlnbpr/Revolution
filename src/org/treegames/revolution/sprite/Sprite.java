package org.treegames.revolution.sprite;

import org.lwjgl.util.vector.Vector2f;
import org.treegames.revolution.GameSettings;
import org.treegames.revolution.gfx.Models;
import org.treegames.revolution.gfx.Sprites;
import org.treegames.revolution.gfx.Texture;

import static org.lwjgl.opengl.GL11.*;

public class Sprite {
    public Texture texture;
    public Vector2f position = new Vector2f(0, 0);

    public Sprite(Vector2f position) {
        this.position = position;
    }

    public Sprite(float x, float y) {
        this(new Vector2f(x, y));
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
        glRotatef(-90f, 0, 0, 1);
        if (texture != null) texture.use();
        if (GameSettings.wireframe) {
            Texture.unbindAll();
            glDisable(GL_LIGHTING);
            glColor3f(0.0f, 1.0f, 1.0f);
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        }
        glCallList(Models.sprite);
        Texture.unbindAll();
        glDisable(GL_BLEND);
        glDisable(GL_CULL_FACE);
        glDisable(GL_LIGHT0);
        glDisable(GL_LIGHTING);
        glPopMatrix();
    }

    public Texture tex(String name) {
        return Sprites.getTexture(name);
    }

    public void update(int delta) {

    }
}

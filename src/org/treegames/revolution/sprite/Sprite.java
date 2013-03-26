package org.treegames.revolution.sprite;

import org.lwjgl.util.vector.Vector2f;
import org.treegames.revolution.gfx.Shapes;
import org.treegames.revolution.gfx.Texture;
import org.treegames.revolution.gfx.Tiles;

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
        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);
        glEnable(GL_BLEND);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glTranslatef(position.x, position.y, -32f);
        glRotatef(-90f, 0, 0, 1);
//        glScalef(2.0f, 2.0f, 0.0f);
        if (texture != null) texture.use();
        glCallList(Shapes.sprite);
        Texture.unbindAll();
        glDisable(GL_BLEND);
        glDisable(GL_CULL_FACE);
        glDisable(GL_LIGHT0);
        glDisable(GL_LIGHTING);
        glPopMatrix();
    }

    public void update(int delta) {

    }
}

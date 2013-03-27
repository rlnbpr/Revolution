package org.treegames.revolution.gfx;

import org.treegames.revolution.GameSettings;

import static org.lwjgl.opengl.GL11.*;

public class TileRenderer {
    private int texture = 0;

    public TileRenderer(int id) {
        this.texture = id;
    }

    public void drawTile(int x,int y,boolean inBackground) {
        if(texture==0)
            return;
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glRotatef(0,0,0,1);
        if(GameSettings.wireframe){
            Texture.unbindAll();
            glDisable(GL_LIGHTING);
            glColor3f(0.0f,1.0f,1.0f);
            glPolygonMode(GL_FRONT_AND_BACK,GL_LINE);
        }else{
            if(GameSettings.lighting){
                glEnable(GL_LIGHTING);
                glEnable(GL_LIGHT0);
            }
            glColor3f(1.0f,1.0f,1.0f);
            glPolygonMode(GL_FRONT_AND_BACK,GL_FILL);
        }

        glPushMatrix();
        if(!GameSettings.wireframe)
            Tiles.textureMap.get(texture).use();
        glTranslatef(x*2,y*2,inBackground?-34:-32);
        glRotatef(-90f,0.0f,0.0f,1.0f);
        glCallList(Models.cube);
        Texture.unbindAll();
        glPopMatrix();
        glDisable(GL_POLYGON_SMOOTH);
        glDisable(GL_CULL_FACE);
        glDisable(GL_LIGHT0);
        glDisable(GL_LIGHTING);
    }
}

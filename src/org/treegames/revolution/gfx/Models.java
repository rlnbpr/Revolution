package org.treegames.revolution.gfx;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

public class Models {
	public static int cube;
    public static int sprite;
	private static List<Integer> shapes=new ArrayList<Integer>();

	public static void initShapes() {
        /* Renders a cube, mostly used in Grid.java to render tiles */
		cube=glGenLists(1);
		glNewList(cube,GL_COMPILE);
		glBegin(GL_QUADS);
		// Bottom Face
		glTexCoord2f(0.0f,1.0f);
		glVertex3f(-1.0f,-1.0f,-1.0f);
		glTexCoord2f(1.0f,1.0f);
		glVertex3f(1.0f,-1.0f,-1.0f);
		glTexCoord2f(1.0f,0.0f);
		glVertex3f(1.0f,-1.0f,1.0f);
		glTexCoord2f(0.0f,0.0f);
		glVertex3f(-1.0f,-1.0f,1.0f);
		// Front Face
		glTexCoord2f(0.0f,0.0f);
		glVertex3f(-1.0f,-1.0f,1.0f);
		glTexCoord2f(1.0f,0.0f);
		glVertex3f(1.0f,-1.0f,1.0f);
		glTexCoord2f(1.0f,1.0f);
		glVertex3f(1.0f,1.0f,1.0f);
		glTexCoord2f(0.0f,1.0f);
		glVertex3f(-1.0f,1.0f,1.0f);
		// Back Face
		glTexCoord2f(1.0f,0.0f);
		glVertex3f(-1.0f,-1.0f,-1.0f);
		glTexCoord2f(1.0f,1.0f);
		glVertex3f(-1.0f,1.0f,-1.0f);
		glTexCoord2f(0.0f,1.0f);
		glVertex3f(1.0f,1.0f,-1.0f);
		glTexCoord2f(0.0f,0.0f);
		glVertex3f(1.0f,-1.0f,-1.0f);
		// Right face
		glTexCoord2f(1.0f,0.0f);
		glVertex3f(1.0f,-1.0f,-1.0f);
		glTexCoord2f(1.0f,1.0f);
		glVertex3f(1.0f,1.0f,-1.0f);
		glTexCoord2f(0.0f,1.0f);
		glVertex3f(1.0f,1.0f,1.0f);
		glTexCoord2f(0.0f,0.0f);
		glVertex3f(1.0f,-1.0f,1.0f);
		// Left Face
		glTexCoord2f(0.0f,0.0f);
		glVertex3f(-1.0f,-1.0f,-1.0f);
		glTexCoord2f(1.0f,0.0f);
		glVertex3f(-1.0f,-1.0f,1.0f);
		glTexCoord2f(1.0f,1.0f);
		glVertex3f(-1.0f,1.0f,1.0f);
		glTexCoord2f(0.0f,1.0f);
		glVertex3f(-1.0f,1.0f,-1.0f);
		// Top Face
		glTexCoord2f(0.0f,1.0f);
		glVertex3f(-1.0f,1.0f,-1.0f);
		glTexCoord2f(0.0f,0.0f);
		glVertex3f(-1.0f,1.0f,1.0f);
		glTexCoord2f(1.0f,0.0f);
		glVertex3f(1.0f,1.0f,1.0f);
		glTexCoord2f(1.0f,1.0f);
		glVertex3f(1.0f,1.0f,-1.0f);
		glEnd();
		glEndList();
		shapes.add(cube);

        /* Used to render NORMAL sprites */
        sprite = glGenLists(1);
        glNewList(sprite, GL_COMPILE);
        glBegin(GL_QUADS);
        glTexCoord2f(0.0f,0.0f);
        glVertex3f(-1.0f,-1.0f,1.0f);
        glTexCoord2f(1.0f,0.0f);
        glVertex3f(1.0f,-1.0f,1.0f);
        glTexCoord2f(1.0f,1.0f);
        glVertex3f(1.0f,1.0f,1.0f);
        glTexCoord2f(0.0f,1.0f);
        glVertex3f(-1.0f,1.0f,1.0f);
        glEnd();
        glEndList();
        shapes.add(sprite);
	}

	public static void deleteShapes() {
		for (int i:shapes){
			glDeleteLists(i,1);
		}
		shapes.clear();
	}
}
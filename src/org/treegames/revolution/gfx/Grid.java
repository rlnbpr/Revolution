package org.treegames.revolution.gfx;

import static org.lwjgl.opengl.GL11.*;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.treegames.revolution.level.Level;

public class Grid {
	public int grid[][]=new int[32][24];
	public int background[][]=new int[32][24];

	public Map<String,String> properties=new HashMap<String,String>();

	private static int cube;

	public int heightAboveGround=0;

	public boolean wireframe=false;
	public boolean lighting=false;

	public static void initGraphics() {
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
	}

	public static void deleteGraphics() {
		glDeleteLists(cube,1);
	}

	public Grid(int background) {
		generate();
	}

	public void drawTile(int x,int y,int tile,boolean inBackground) {
		if(tile==0)
			return;
		glEnable(GL_CULL_FACE);
		// glCullFace(GL_BACK);
		glRotatef(0,0,0,1);
		if(wireframe){
			Texture.unbindAll();
			glDisable(GL_LIGHTING);
			glColor3f(0.0f,1.0f,1.0f);
			glPolygonMode(GL_FRONT_AND_BACK,GL_LINE);
		}else{
			if(lighting){
				glEnable(GL_LIGHTING);
				glEnable(GL_LIGHT0);
			}
			glColor3f(1.0f,1.0f,1.0f);
			glPolygonMode(GL_FRONT_AND_BACK,GL_FILL);
		}

		glPushMatrix();
		// ShaderUtils.useProgram(Main.invertedProgram);
		if(!wireframe)
			Tiles.textureMap.get(tile).use();
		glTranslatef(x*2,y*2,inBackground?-34:-32);
		glRotatef(-90f,0.0f,0.0f,1.0f);
		glCallList(cube);
		Texture.unbindAll();
		// ShaderUtils.useFixedFunctions();
		glPopMatrix();
		glDisable(GL_CULL_FACE);
		glDisable(GL_LIGHTING);
	}

	public void generate() {

	}

	public void loadLevel(Level level) {
		level.buildLevel(this);
	}

	public void draw() {
		while(Keyboard.next()){
			if(Keyboard.isKeyDown(Keyboard.KEY_F5)){
				generate();
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_F1)){
				wireframe=!wireframe;
			}
		}
		for (int x=0;x<grid.length;x++){
			for (int y=0;y<grid[0].length;y++){
				drawTile(x,y,grid[x][y],false);
			}
		}
		for (int x=0;x<background.length;x++){
			for (int y=0;y<background[0].length;y++){
				drawTile(x,y,background[x][y],true);
			}
		}
	}
}
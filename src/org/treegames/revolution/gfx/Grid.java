package org.treegames.revolution.gfx;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDeleteLists;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;

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

	public static void initGraphics() {
		cube=glGenLists(1);
		glNewList(cube,GL_COMPILE);
		glBegin(GL_QUADS);
		// Bottom Face
		glTexCoord2f(1.0f,1.0f);
		glVertex3f(-1.0f,-1.0f,-1.0f);
		glTexCoord2f(0.0f,1.0f);
		glVertex3f(1.0f,-1.0f,-1.0f);
		glTexCoord2f(0.0f,0.0f);
		glVertex3f(1.0f,-1.0f,1.0f);
		glTexCoord2f(1.0f,0.0f);
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
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		if(wireframe){
			Texture.unbindAll();
			glColor3f(0.0f,1.0f,1.0f);
			glPolygonMode(GL_FRONT_AND_BACK,GL_LINE);
		}else{
			glColor3f(1.0f,1.0f,1.0f);
			glPolygonMode(GL_FRONT_AND_BACK,GL_FILL);
		}
		glPushMatrix();
		// ShaderUtils.useProgram(Main.invertedProgram);
		if(!wireframe)
			Tiles.textureMap.get(tile).use();
		glTranslatef(x*2-15.5f,y*2-10,inBackground?-34:-32);
		glCallList(cube);
		Texture.unbindAll();
		// ShaderUtils.useFixedFunctions();
		glPopMatrix();
		glDisable(GL_CULL_FACE);
	}

	public void generate() {
		for (int i=0;i<grid.length;i++){
			for (int j=0;j<grid[0].length;j++){
				grid[i][j]=0;
				background[i][j]=0;
			}
		}

		for (int i=0;i<grid.length;i++){
			for (int j=0;j<9;j++){
				background[i][j]=2;
			}
		}

		for (int i=0;i<grid.length;i++){
			background[i][9]=1;
			grid[i][9]=1;
		}

		for (int i=0;i<2;i++){
			for (int j=0;j<grid.length;j++){
				grid[j][i]=3;
			}
		}
		for (int i=0;i<grid.length;i++){
			grid[i][8]=1;
		}
		grid[0][7]=1;
		grid[0][6]=1;
		grid[0][5]=1;
		grid[0][4]=1;
		grid[grid.length-1][7]=1;
		grid[grid.length-1][6]=1;
		grid[grid.length-1][5]=1;
		grid[grid.length-1][4]=1;
		grid[grid.length-1][3]=1;
		grid[grid.length-1][2]=1;
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
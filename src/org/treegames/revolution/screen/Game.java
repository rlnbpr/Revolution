package org.treegames.revolution.screen;

import static org.lwjgl.opengl.GL11.GL_AMBIENT_AND_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_SHININESS;
import static org.lwjgl.opengl.GL11.GL_SPECULAR;
import static org.lwjgl.opengl.GL11.glColorMaterial;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLight;
import static org.lwjgl.opengl.GL11.glLightModel;
import static org.lwjgl.opengl.GL11.glMaterial;
import static org.lwjgl.opengl.GL11.glMaterialf;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.treegames.revolution.Main;
import org.treegames.revolution.gfx.Grid;
import org.treegames.revolution.level.Level;

public class Game extends Screen {
	private Grid grid=new Grid(1);
	private float cameraX=0;
	private float cameraY=0;

	// ------------- LIGHTING --------------//
	private FloatBuffer matSpecular;
	private FloatBuffer lightPosition;
	private FloatBuffer whiteLight;
	private FloatBuffer lModelAmbient;

	// ------------- LIGHTING --------------//

	public Game() {
		cameraX=0;
		cameraY=0;
		grid.loadLevel(new Level(){
			public void buildLevel(Grid grid) {
				this.buildFromStream(grid,getClass().getResourceAsStream("/maps/test.tmap"));
			}
		});
	}

	public void initGL() {
		matSpecular=BufferUtils.createFloatBuffer(4);
		matSpecular.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

		lightPosition=BufferUtils.createFloatBuffer(4);
		lightPosition.put(1.0f).put(1.0f).put(1.0f).put(-32.0f).flip();

		whiteLight=BufferUtils.createFloatBuffer(4);
		whiteLight.put(.3f).put(.3f).put(.3f).put(1.0f).flip();

		lModelAmbient=BufferUtils.createFloatBuffer(4);
		lModelAmbient.put(0.5f).put(0.5f).put(0.5f).put(1.0f).flip();

		glMaterial(GL_FRONT,GL_SPECULAR,matSpecular);
		glMaterialf(GL_FRONT,GL_SHININESS,10.0f);

		glLight(GL_LIGHT0,GL_POSITION,lightPosition);
		glLight(GL_LIGHT0,GL_SPECULAR,whiteLight);
		glLight(GL_LIGHT0,GL_DIFFUSE,whiteLight);

		glLightModel(GL_LIGHT_MODEL_AMBIENT,lModelAmbient);

		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		glEnable(GL_COLOR_MATERIAL);
		glColorMaterial(GL_FRONT,GL_AMBIENT_AND_DIFFUSE);
	}

	public void render(Main main) {
		GL11.glTranslatef(cameraX,cameraY,0);
		grid.draw();
	}

	public void update(int delta) {
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			cameraX-=1;
			if(cameraX<=-10){
				cameraX=-9;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			cameraX+=1;
			if(cameraX>=10){
				cameraX=9;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			cameraY+=1;
			if(cameraY>=10){
				cameraY=9;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
			cameraY-=1;
			if(cameraY<=-10){
				cameraY=-9;
			}
		}

		if(!Keyboard.isKeyDown(Keyboard.KEY_RIGHT)&&!Keyboard.isKeyDown(Keyboard.KEY_LEFT)&&!Keyboard.isKeyDown(Keyboard.KEY_DOWN)&&!Keyboard.isKeyDown(Keyboard.KEY_UP)){
			// move camera back to default position when the camera isn't being controlled
			if(cameraX>0){
				cameraX-=0.5f;
			}else if(cameraX<0){
				cameraX+=0.5f;
			}

			if(cameraY>0){
				cameraY-=0.5f;
			}else if(cameraY<0){
				cameraY+=0.5f;
			}
		}
	}
}
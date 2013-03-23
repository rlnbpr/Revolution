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

import java.io.File;
import java.nio.FloatBuffer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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
	private float cameraZ=0;

	// ------------- LIGHTING --------------//
	private FloatBuffer matSpecular;
	private FloatBuffer lightPosition;
	private FloatBuffer whiteLight;
	private FloatBuffer lModelAmbient;

	// ------------- LIGHTING --------------//

	public Game() {
		promptMap();
	}

	public void promptMap() {
		JFileChooser chooser=new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("TreEngine Maps (*.tmap)","tmap"));
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
		chooser.showOpenDialog(null);

		final File f=chooser.getSelectedFile();
		grid.loadLevel(new Level(){
			public void buildLevel(Grid grid) {
				this.buildFromFile(grid,f);
			}
		});
	}
	
	public void initGL() {
		matSpecular=BufferUtils.createFloatBuffer(4);
		matSpecular.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

		lightPosition=BufferUtils.createFloatBuffer(4);
		lightPosition.put(1.0f).put(1.0f).put(1.0f).put(-30.0f).flip();

		whiteLight=BufferUtils.createFloatBuffer(4);
		whiteLight.put(.48f).put(.48f).put(.48f).put(1.0f).flip();

		lModelAmbient=BufferUtils.createFloatBuffer(4);
		lModelAmbient.put(0f).put(0f).put(0f).put(0f).flip();

		glMaterial(GL_FRONT,GL_SPECULAR,matSpecular);
		glMaterialf(GL_FRONT,GL_SHININESS,128.0f);

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
		GL11.glTranslatef(cameraX,cameraY,cameraZ);
		grid.draw();
	}

	public void update(int delta) {
		float norm=1;
		float ctrlMod=0.2f;
		float shiftMod=2f;
		float modifier=norm;

		if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
			modifier=ctrlMod;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			modifier=shiftMod;
		}else if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)&&!Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
			modifier=norm;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			cameraX-=modifier;
			// if(cameraX<=-20){
			// cameraX=-19;
			// }
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			cameraX+=modifier;
			// if(cameraX>=10){
			// cameraX=9;
			// }
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_E)){
			cameraY+=modifier;
			// if(cameraY>=5){
			// cameraY=4;
			// }
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
			cameraY-=modifier;
			// if(cameraY<=-20){
			// cameraY=-19;
			// }
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			cameraZ+=modifier;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			cameraZ-=modifier;
		}

		/*
		 * if(!Keyboard.isKeyDown(Keyboard.KEY_RIGHT)&&!Keyboard.isKeyDown(Keyboard.KEY_LEFT)&&!Keyboard.isKeyDown(Keyboard.KEY_DOWN)&&!Keyboard.isKeyDown(Keyboard.KEY_UP)){ // move camera back to default position when the camera isn't being controlled if(cameraX>0){ cameraX-=0.5f; }else if(cameraX<0){ cameraX+=0.5f; } if(cameraY>0){ cameraY-=0.5f; }else if(cameraY<0){ cameraY+=0.5f; } }
		 */
	}
}
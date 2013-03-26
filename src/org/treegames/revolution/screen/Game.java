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
import org.treegames.revolution.gfx.Sprites;
import org.treegames.revolution.level.Level;

public class Game extends Screen {
    private Grid grid;
    public float cameraX = 0;
    public float cameraY = 0;
    public float cameraZ = 12;

    // ------------- LIGHTING --------------//
    private FloatBuffer matSpecular;
    private FloatBuffer lightPosition;
    private FloatBuffer whiteLight;
    private FloatBuffer lModelAmbient;

    // ------------- LIGHTING --------------//

    public Game() {
        grid = new Grid(this);
    }

//    public void promptMap() {
//        JFileChooser chooser = new JFileChooser();
//        chooser.setAcceptAllFileFilterUsed(false);
//        chooser.setFileFilter(new FileNameExtensionFilter("TreEngine Maps (*.tmap)", "tmap"));
//        chooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
//        chooser.showOpenDialog(null);
//
//        final File f = chooser.getSelectedFile();
//        grid.loadLevel(new Level() {
//            public void buildLevel(Grid grid) {
//                this.buildFromFile(grid, f);
//            }
//        });
//    }

    public void initGL() {
        matSpecular = BufferUtils.createFloatBuffer(4);
        matSpecular.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

        lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(1.0f).put(1.0f).put(1.0f).put(-30.0f).flip();

        whiteLight = BufferUtils.createFloatBuffer(4);
        whiteLight.put(.48f).put(.48f).put(.48f).put(1.0f).flip();

        lModelAmbient = BufferUtils.createFloatBuffer(4);
        lModelAmbient.put(0f).put(0f).put(0f).put(0f).flip();

        glMaterial(GL_FRONT, GL_SPECULAR, matSpecular);
        glMaterialf(GL_FRONT, GL_SHININESS, 128.0f);

        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
        glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);
        glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);

        glLightModel(GL_LIGHT_MODEL_AMBIENT, lModelAmbient);

        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);
        glEnable(GL_COLOR_MATERIAL);
        glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);

        Sprites.initDefaultSprites();
        grid.loadLevel(new Level() {
            public void buildLevel(Grid grid) {
                this.buildFromStream(grid, getClass().getResourceAsStream("/maps/story/ep1/start.tmap"));
            }
        });
    }

    public void render(Main main) {
        GL11.glTranslatef(cameraX, cameraY, cameraZ);
        grid.draw();
    }

    public void update(int delta) {
        grid.update(delta);
    }
}
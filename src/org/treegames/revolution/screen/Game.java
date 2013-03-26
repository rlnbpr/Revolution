package org.treegames.revolution.screen;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.treegames.revolution.Main;
import org.treegames.revolution.gfx.Grid;
import org.treegames.revolution.gfx.Sprites;
import org.treegames.revolution.level.Level;

public class Game extends Screen {
    public Grid grid;
    public float cameraX = 0;
    public float cameraY = 0;
    public float cameraZ = 12;

    // ------------- LIGHTING --------------//
    private FloatBuffer matSpecular;
    private FloatBuffer lightPosition;
    private FloatBuffer lModelAmbient;
    private FloatBuffer diffuseLight;

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
        matSpecular.put(0.5f).put(0.5f).put(0.5f).put(1.0f).flip();

        lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(-1.0f).put(1.0f).put(1.0f).put(-30.0f).flip();

        lModelAmbient = BufferUtils.createFloatBuffer(4);
        lModelAmbient.put(0.5f).put(0.5f).put(0.5f).put(1.0f).flip();

        diffuseLight = BufferUtils.createFloatBuffer(4);
        diffuseLight.put(0.5f).put(0.5f).put(0.5f).put(1.0f).flip();

        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
        glLight(GL_LIGHT0, GL_DIFFUSE, diffuseLight);

        glLightModel(GL_LIGHT_MODEL_AMBIENT, lModelAmbient);

        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);
        glEnable(GL_COLOR_MATERIAL);
        glColorMaterial(GL_FRONT, GL_DIFFUSE);

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
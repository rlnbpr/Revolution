package org.treegames.revolution;

import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;
import org.treegames.revolution.gfx.Models;
import org.treegames.revolution.gfx.Tiles;
import org.treegames.revolution.level.TreEngineFormat;
import org.treegames.revolution.screen.Game;
import org.treegames.revolution.screen.Screen;
import org.treegames.revolution.sound.Sounds;

public class Main {
    private Thread gameThread;

    public static Screen screen = new Game();

    public long lastFrame, lastFPS;
    public int fps;

    public static TreEngineFormat mapFormat = new TreEngineFormat();

    public Main() {
        System.out.println("Using LWJGL Version " + Sys.getVersion());

        gameThread = new Thread("main-game-thread") {
            public void run() {
                long then = System.currentTimeMillis();
                try {
                    final DisplayMode dm = new DisplayMode(1024, 768);
                    Display.setDisplayMode(dm);
                    Display.setTitle("Revolution");
                    Display.create(new PixelFormat().withDepthBits(8).withSamples(8).withSRGB(true));
                    AL.create();
                } catch (LWJGLException e) {
                    e.printStackTrace();
                }

                System.out.println("Using OpenGL version " + GL11.glGetString(GL11.GL_VERSION));

                GL11.glMatrixMode(GL11.GL_PROJECTION);
                GL11.glLoadIdentity();

                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glShadeModel(GL11.GL_SMOOTH);
                GL11.glClearColor(0.3921568627451f, 0.5843137254902f, 0.92941176470588f, 0.0f);
                GL11.glClearDepth(1.0f);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glDepthFunc(GL11.GL_LEQUAL);
                GL11.glDisable(GL11.GL_CULL_FACE);

                GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);

                GLU.gluPerspective(45.0f, (float) Display.getDisplayMode().getWidth() / (float) Display.getDisplayMode().getHeight(), 0.1f, 100f);

                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glLoadIdentity();

                System.out.println("Making Tiles...");
                Tiles.initTiles();
                System.out.println("Building Shapes...");
                Models.initShapes();
                System.out.println("Loading Sounds...");
                Sounds.initDefaultSounds();

                screen.initGL();

                System.out.println("Done initializing OpenGL.");
                System.out.println("Using OpenGL Version " + GL11.glGetString(GL11.GL_VERSION));

                lastFPS = getTime();

                long now = System.currentTimeMillis();
                long total = now - then;
                System.out.println("Took " + total + "ms");
                System.out.println();
                while (!Display.isCloseRequested()) {
                    Display.sync(60);
                    Display.update();

                    gameLoop();
                    updateFPS();
                }
                Sounds.destroySounds();
                Models.deleteShapes();
                Tiles.unload();
                System.out.println("Closing..");
                AL.destroy();
                Display.destroy();
            }
        };
        gameThread.start();
    }

    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("Revolution. FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;
        return delta;
    }

    public void gameLoop() {
        update(getDelta());
        render();
    }

    public void update(int delta) {
        screen.update(delta);
    }

    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity();

        screen.render(this);

        GL11.glFlush();
    }

    public static void loadLWJGL() {
        System.out.println("Loading LWJGL...");
        try {
            if (!new File("lib").exists() || !new File("lib").isDirectory()) {
                System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
            } else {
                System.setProperty("org.lwjgl.librarypath", new File("lib/natives").getAbsolutePath());
            }
            Sys.getVersion();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Couldn't assign natives to LWJGL!!\nDoes LWJGL support your platform?\nIf so, try reinstalling the game.", "TreEngine Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        System.out.println("Starting game...");
        System.out.println("Using Java version " + System.getProperty("java.version").substring(0, 3));
        loadLWJGL();
        new Main();
    }
}
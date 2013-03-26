package org.treegames.revolution.gfx;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.treegames.revolution.GameSettings;
import org.treegames.revolution.level.Level;
import org.treegames.revolution.screen.Game;
import org.treegames.revolution.sprite.Player;
import org.treegames.revolution.sprite.Sprite;

public class Grid {
    public int grid[][] = new int[32][24];
    public int background[][] = new int[32][24];

    public Game game;

    public Map<String, String> properties = new HashMap<String, String>();

    public int heightAboveGround = 0;

    public List<Sprite> sprites = new ArrayList<Sprite>();

    public static void initGraphics() {
    }

    public Player player;

    public Grid(Game game) {
        this.game = game;
        generate();
    }

    public void drawTile(int x, int y, int tile, boolean inBackground) {
        if (tile == 0)
            return;
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glRotatef(0, 0, 0, 1);
        if (GameSettings.wireframe) {
            Texture.unbindAll();
            glDisable(GL_LIGHTING);
            glColor3f(0.0f, 1.0f, 1.0f);
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        } else {
            if (GameSettings.lighting) {
                glEnable(GL_LIGHTING);
                glEnable(GL_LIGHT0);
            }
            glColor3f(1.0f, 1.0f, 1.0f);
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        }

        glPushMatrix();
        // ShaderUtils.useProgram(Main.invertedProgram);
        if (!GameSettings.wireframe) Tiles.textureMap.get(tile).use();
        glTranslatef(x * 2, y * 2, inBackground ? -34 : -32);
        glRotatef(-90f, 0.0f, 0.0f, 1.0f);
        glCallList(Models.cube);
        Texture.unbindAll();
        // ShaderUtils.useFixedFunctions();
        glPopMatrix();
        glDisable(GL_CULL_FACE);
        glDisable(GL_LIGHT0);
        glDisable(GL_LIGHTING);
    }

    public void generate() {

    }

    public void loadLevel(Level level) {
        level.buildLevel(this);
        int mapHeight = Integer.parseInt(properties.get("mapHeight"));
        int rawX = Integer.parseInt(properties.get("spawnX"));
        int rawY = Integer.parseInt(properties.get("spawnY"));
        float spawnX = (rawX / 32) * 2;
        float spawnY = mapHeight * 2 - (((rawY / 32) * 2) + 2);
        game.cameraX = -spawnX;
        game.cameraY = -spawnY;
        System.out.println("Spawning player at [" + spawnX + ", " + spawnY + "]");
        player = new Player(new Vector2f(spawnX, spawnY));
        sprites.add(player);
    }

    public void draw() {
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                drawTile(x, y, grid[x][y], false);
            }
        }
        for (int x = 0; x < background.length; x++) {
            for (int y = 0; y < background[0].length; y++) {
                drawTile(x, y, background[x][y], true);
            }
        }
        for (Sprite s : sprites) {
            s.draw();
        }
    }

    public void update(int delta) {
        for (Sprite s : sprites) {
            s.update(delta);
        }
        game.cameraX = -player.position.x;
        game.cameraY = -player.position.y;
    }
}
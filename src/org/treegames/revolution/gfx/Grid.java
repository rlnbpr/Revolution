package org.treegames.revolution.gfx;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.lwjgl.util.vector.Vector2f;
import org.treegames.revolution.GameSettings;
import org.treegames.revolution.level.Level;
import org.treegames.revolution.screen.Game;
import org.treegames.revolution.sprite.Player;
import org.treegames.revolution.sprite.Sprite;

public class Grid {
	public int grid[][]=new int[32][24];
	public int background[][]=new int[32][24];

	public Game game;

	public Map<String,String> properties=new HashMap<String,String>();

	public int heightAboveGround=0;

	public List<Sprite> sprites=new ArrayList<Sprite>();

	public final World world=new World(new Vec2(0,-13f),false);
	public final Set<Body> tiles=new HashSet<Body>();

	public static void initGraphics() {}

	public Player player;

	public Grid(Game game) {
		this.game=game;
	}

	public void loadLevel(Level level) {
		level.buildLevel(this);
		int mapHeight=Integer.parseInt(properties.get("mapHeight"));
		int rawX=Integer.parseInt(properties.get("spawnX"));
		int rawY=Integer.parseInt(properties.get("spawnY"));
		for (int x=0;x<grid.length;x++){
			for (int y=0;y<grid[0].length;y++){
				if(grid[x][y]==0)
					continue;
				BodyDef tileDef=new BodyDef();
				tileDef.position.set(x*2,y*2);
				tileDef.type=BodyType.KINEMATIC;
				PolygonShape tileShape=new PolygonShape();
				tileShape.setAsBox(1,1);
				Body tile=world.createBody(tileDef);
				FixtureDef tileFixture=new FixtureDef();
				tileFixture.density=1;
				tileFixture.shape=tileShape;
				tile.createFixture(tileFixture);
				tiles.add(tile);
			}
		}
		float spawnX=(rawX/32)*2;
		float spawnY=mapHeight*2-(((rawY/32)*2)+2);
		game.cameraX=-spawnX;
		game.cameraY=-spawnY;
		System.out.println("Spawning player at ["+spawnX+", "+spawnY+"]");
		player=new Player(new Vector2f(spawnX,spawnY));
		sprites.add(player);
	}

	public void draw() {
		for (int x=0;x<grid.length;x++){
			for (int y=0;y<grid[0].length;y++){
				Tiles.drawTile(x, y, grid[x][y], false);
			}
		}
		for (int x=0;x<background.length;x++){
			for (int y=0;y<background[0].length;y++){
				Tiles.drawTile(x, y, background[x][y], true);
			}
		}
		for (Sprite s:sprites){
			s.draw();
		}
	}

	public void update(int delta) {
		for (Sprite s:sprites){
			s.update(delta);
		}
		game.cameraX=-player.spriteBody.getPosition().x;
		game.cameraY=-player.spriteBody.getPosition().y;
		world.step(1/60f,8,3);
	}
}
package org.treegames.revolution.sprite;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.util.vector.Vector2f;
import org.treegames.revolution.GameSettings;
import org.treegames.revolution.Main;
import org.treegames.revolution.gfx.Grid;
import org.treegames.revolution.gfx.Models;
import org.treegames.revolution.gfx.Sprites;
import org.treegames.revolution.gfx.Texture;
import org.treegames.revolution.screen.Game;

public class Sprite {
	public Texture texture;
	public float speed=0.2f;
	public int jumpStart=-12;
	public int jumpSpeed=Integer.MAX_VALUE;
	public float gravity=1.5f;
	
	public BodyDef spriteDef;
	public PolygonShape spriteShape;
	public Body spriteBody;
	public FixtureDef spriteFixture;
	
	public Grid grid;

	public Sprite(Vector2f position) {
		grid = ((Game)Main.screen).grid;
		spriteDef = new BodyDef();
		spriteDef.position.set(position.x,position.y+0.5f);
		spriteDef.type = BodyType.DYNAMIC;
		spriteShape = new PolygonShape();
		spriteShape.setAsBox(1, 1);
		spriteBody = ((Game)Main.screen).grid.world.createBody(spriteDef);
		spriteFixture = new FixtureDef();
		spriteFixture.density = 1f;
		spriteFixture.friction = 6f;
		spriteFixture.shape = spriteShape;
    spriteBody.createFixture(spriteFixture);
	}

	public Sprite(float x,float y) {
		this(new Vector2f(x,y));
	}

	public void draw() {
		glPushMatrix();
		if(GameSettings.lighting){
			glEnable(GL_LIGHTING);
			glEnable(GL_LIGHT0);
		}else{
			glDisable(GL_LIGHTING);
			glDisable(GL_LIGHT0);
		}
		glEnable(GL_BLEND);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);
		glTranslatef(spriteBody.getPosition().x*32,spriteBody.getPosition().y*32,-32.5f);
		glRotatef(-90f,0,0,1);
		if(texture!=null)
			texture.use();
		if(GameSettings.wireframe){
			Texture.unbindAll();
			glDisable(GL_LIGHTING);
			glColor3f(0.0f,1.0f,1.0f);
			glPolygonMode(GL_FRONT_AND_BACK,GL_LINE);
		}
		glCallList(Models.sprite);
		Texture.unbindAll();
		glDisable(GL_BLEND);
		glDisable(GL_CULL_FACE);
		glDisable(GL_LIGHT0);
		glDisable(GL_LIGHTING);
		glPopMatrix();
	}

	public boolean isWalkable(int xt,int yt) {
		return ((Game)Main.screen).grid.grid[xt][yt]!=0;
	}

	public Texture tex(String name) {
		return Sprites.getTexture(name);
	}

	public void update(int delta) {
		//speed=delta*0.008f;
	}
}

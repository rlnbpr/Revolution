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

import org.jbox2d.common.Vec2;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.treegames.revolution.GameSettings;
import org.treegames.revolution.gfx.Models;
import org.treegames.revolution.gfx.Texture;
import org.treegames.revolution.sound.Sounds;

public class Player extends AnimatedSprite {
	private int animationTimer=0;
	public boolean doAnim=false;

	public Player(Vector2f position) {
		super(position);
		initAnimations(tex("player1"),tex("player2"),tex("player3"),tex("player4"));
		setFrame(0);
		spriteDef.position.set(position.x,position.y+0.5f);
		spriteShape.setAsBox(0.35f, 1);
	}

	public void update(int delta) {
		super.update(delta);
		doAnim=false;
		while(Keyboard.next()){
			if((Keyboard.getEventKey()==Keyboard.KEY_D||Keyboard.getEventKey()==Keyboard.KEY_A)&&Keyboard.getEventKeyState()){
				nextFrame();
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_F1)){
				GameSettings.wireframe=!GameSettings.wireframe;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_F2)){
				GameSettings.lighting=!GameSettings.lighting;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_W)||Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
				if((spriteBody.getPosition().y-(int)spriteBody.getPosition().y)<0.02f){
					spriteBody.applyLinearImpulse(new Vec2(0,spriteBody.getMass()*7.5f),spriteBody.getWorldCenter());
				}
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			spriteBody.setTransform(new Vec2(spriteBody.getPosition().x+0.1f,spriteBody.getPosition().y),0);
			if((spriteBody.getPosition().y-(int)spriteBody.getPosition().y)<0.02f)
				doAnim=true;
			setLookDirection(1);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			spriteBody.setTransform(new Vec2(spriteBody.getPosition().x-0.1f,spriteBody.getPosition().y),0);
			if((spriteBody.getPosition().y-(int)spriteBody.getPosition().y)<0.02f)
				doAnim=true;
			setLookDirection(-1);
		}
		if(doAnim){
			animationTimer+=1;
			if(animationTimer==10){
				nextFrame();
				Sounds.playSound("footstep");
				animationTimer=0;
			}
		}else{
			resetAnim();
		}
		if((spriteBody.getPosition().y-(int)spriteBody.getPosition().y)>0.02f)
			setFrame(1);
	}

	public void draw() {
    glPushMatrix();
    if (GameSettings.lighting) {
        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);
    } else {
        glDisable(GL_LIGHTING);
        glDisable(GL_LIGHT0);
    }
    glEnable(GL_BLEND);
    glEnable(GL_CULL_FACE);
    glCullFace(GL_BACK);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glTranslatef(spriteBody.getPosition().x, spriteBody.getPosition().y, -32.5f);
    glRotatef(getLookDirection() == -1 ? 180 : -90f, 0, 0, 1);
    if (texture != null) texture.use();
    if (GameSettings.wireframe) {
        Texture.unbindAll();
        glDisable(GL_LIGHTING);
        glColor3f(1.0f, 0.0f, 1.0f);
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    }else{
    	glColor3f(1.0f, 1.0f, 0.0f);
    }
    glCallList(getLookDirection() == -1 ? Models.flippedSprite : Models.sprite);
    Texture.unbindAll();
    glDisable(GL_BLEND);
    glDisable(GL_CULL_FACE);
    glDisable(GL_LIGHT0);
    glDisable(GL_LIGHTING);
    glPopMatrix();
	}
}
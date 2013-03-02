package org.treegames.revolution.gfx;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

public class Texture {
	private static final int BYTES_PER_PIXEL=4;
	private int id=0;

	public Texture(int id) {
		this.id=id;
	}

	public static Texture makeFromFile(String path) {
		BufferedImage img=null;
		try{
			img=loadImg(path);
		}catch(IOException e){
			System.err.println("Can't load texture "+path+"!!");
			System.err.println(e.getMessage());
		}

		int[] pixels=new int[img.getWidth()*img.getHeight()];
		img.getRGB(0,0,img.getWidth(),img.getHeight(),pixels,0,img.getWidth());

		ByteBuffer buffer=BufferUtils.createByteBuffer(img.getWidth()*img.getHeight()*BYTES_PER_PIXEL);

		for (int x=0;x<img.getWidth();x++){
			for (int y=0;y<img.getHeight();y++){
				int pixel=pixels[y*img.getWidth()+x];
				buffer.put((byte)((pixel>>16)&0xFF));
				buffer.put((byte)((pixel>>8)&0xFF));
				buffer.put((byte)(pixel&0xFF));
				buffer.put((byte)((pixel>>24)&0xFF));
			}
		}

		buffer.flip();

		int textureID=glGenTextures();
		glBindTexture(GL_TEXTURE_2D,textureID);

		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_S,GL12.GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T,GL12.GL_CLAMP_TO_EDGE);

		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);

		glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA8,img.getWidth(),img.getHeight(),0,GL_RGBA,GL_UNSIGNED_BYTE,buffer);

		glBindTexture(GL_TEXTURE_2D,0);

		return new Texture(textureID);
	}

	private static BufferedImage loadImg(String path) throws IOException {
		return ImageIO.read(Texture.class.getResource(path));
	}

	public void use() {
		glBindTexture(GL_TEXTURE_2D,id);
	}

	public void delete() {
		glDeleteTextures(id);
		System.out.println("Deleted texture "+id);
	}

	public static void unbindAll() {
		glBindTexture(GL_TEXTURE_2D,0);
	}
}
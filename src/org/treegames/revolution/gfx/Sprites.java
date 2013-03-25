package org.treegames.revolution.gfx;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Sprites {
	private static Map<String,Texture> textureMap=new HashMap<String,Texture>();

	public static void initDefaultSprites() {
		addTexture("player","/sprites/player/player_armless.png");
	}

	public static Texture getTexture(String name) {
		return textureMap.get(name);
	}

	public static void addTexture(String name,String path) {
		try{
			textureMap.put(name,loadTexture(Sprites.class.getResourceAsStream(path)));
		}catch(IOException e){
			System.err.println("Error while loading texture "+name+":");
			e.printStackTrace();
		}
	}

	public static Texture loadTexture(InputStream stream) throws IOException {
		return Texture.makeFromImage(ImageIO.read(stream));
	}
}
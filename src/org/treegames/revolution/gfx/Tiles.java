package org.treegames.revolution.gfx;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
	public static Map<Integer,Texture> textureMap=new HashMap<Integer,Texture>();
	private static Map<Integer,Boolean> specularMap=new HashMap<Integer,Boolean>();

	public static void initTiles() {
		textureMap.put(1,Texture.makeFromFile("/tiles/planks.png"));
		textureMap.put(2,Texture.makeFromFile("/tiles/rwall_1.png"));
		textureMap.put(3,Texture.makeFromFile("/tiles/marble.png"));
		textureMap.put(4,Texture.makeFromFile("/tiles/marblelight.png"));
		textureMap.put(5,Texture.makeFromFile("/tiles/cloth_white.png"));
		textureMap.put(6,Texture.makeFromFile("/tiles/cloth_red.png"));
		textureMap.put(7,Texture.makeFromFile("/tiles/cloth_blue.png"));
		textureMap.put(8,Texture.makeFromFile("/tiles/cloth_yellow.png"));
		textureMap.put(9,Texture.makeFromFile("/tiles/cloth_lime.png"));


		specularMap.put(2,true);
		specularMap.put(3,true);
		specularMap.put(4,true);
	}

	public static void unload() {
		for (int i:textureMap.keySet()){
			Texture tex=textureMap.get(i);
			tex.delete();
		}
	}
}
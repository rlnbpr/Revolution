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

		specularMap.put(2,true);
		specularMap.put(3,true);
	}

	public static void unload() {
		for (int i:textureMap.keySet()){
			Texture tex=textureMap.get(i);
			tex.delete();
		}
	}
}
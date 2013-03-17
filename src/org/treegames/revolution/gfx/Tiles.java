package org.treegames.revolution.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Tiles {
	public static Map<Integer,Texture> textureMap=new HashMap<Integer,Texture>();
	private static Map<Integer,Boolean> specularMap=new HashMap<Integer,Boolean>();

	public static void initTiles() {
		BufferedImage tiles=null;
		try{
			tiles=Texture.loadImg("/tiles.png");
		}catch(IOException e){
			e.printStackTrace();
		}

		for (int y=0;y<16;y++){
			for (int x=0;x<16;x++){
				textureMap.put(16*y+x+1,Texture.makeFromSheet(tiles,x,y,64));
			}
		}

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
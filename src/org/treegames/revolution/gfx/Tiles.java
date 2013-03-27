package org.treegames.revolution.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Tiles {
	public static Map<Integer,Texture> textureMap=new HashMap<Integer,Texture>();
    private static Map<Integer, TileRenderer> tileRendererMap = new HashMap<Integer, TileRenderer>();

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

        registerTileRenderer(1, new TileRenderer(1));
        registerTileRenderer(2, new TileRenderer(2));
        registerTileRenderer(3, new TileRenderer(3));
        registerTileRenderer(4, new TileRenderer(4));
        registerTileRenderer(5, new TileRenderer(5));
        registerTileRenderer(6, new TileRenderer(6));
        registerTileRenderer(7, new TileRenderer(7));
        registerTileRenderer(8, new TileRenderer(8));
        registerTileRenderer(9, new TileRenderer(9));
        registerTileRenderer(10, null);
        registerTileRenderer(11, null);
        registerTileRenderer(12, null);
        registerTileRenderer(13, new TileRenderer(13));
        registerTileRenderer(14, new TileRenderer(14));
	}

    public static void registerTileRenderer(int id, TileRenderer renderer) {
        tileRendererMap.put(id, renderer);
    }

    public static TileRenderer getRenderer(int id) {
        return tileRendererMap.get(id);
    }

    public static void drawTile(int x, int y, int id, boolean inBackground) {
        TileRenderer renderer = getRenderer(id);
        if (renderer == null) return;
        renderer.drawTile(x, y, inBackground);
    }

	public static void unload() {
		for (int i:textureMap.keySet()){
			Texture tex=textureMap.get(i);
			tex.delete();
		}
	}
}
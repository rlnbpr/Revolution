package org.treegames.revolution.gfx;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.awt.image.BufferedImage;

public class Sprites {
	private static Map<String,Texture> textureMap=new HashMap<String,Texture>();

	public static void initDefaultSprites() {
        try {
            BufferedImage player_armless = loadImage(streamFromPath("/player/player_armless.png"));
            addSheetTexture(player_armless, 0, 0, 32, "player1");
            addSheetTexture(player_armless, 1, 0, 32, "player2");
            addSheetTexture(player_armless, 0, 1, 32, "player3");
            addSheetTexture(player_armless, 1, 1, 32, "player4");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public static Texture getTexture(String name) {
		return textureMap.get(name);
	}

    public static InputStream streamFromPath(String path) {
        return Sprites.class.getResourceAsStream("/sprites" + path);
    }

    public static void addSheetTexture(BufferedImage img, int x, int y, int tileWidth, int tileHeight, String name) {
        BufferedImage subImg = img.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
        textureMap.put(name, toTexture(subImg));
    }

    public static void addSheetTexture(BufferedImage img, int x, int y, int tileSize, String name) {
        addSheetTexture(img, x, y, tileSize, tileSize, name);
    }

    public static BufferedImage loadImage(InputStream stream) throws IOException {
        return ImageIO.read(stream);
    }

	public static void addTexture(String name,String path) {
		try{
			textureMap.put(name,loadTexture(Sprites.class.getResourceAsStream(path)));
		}catch(IOException e){
			System.err.println("Error while loading texture "+name+":");
			e.printStackTrace();
		}
	}

    public static Texture toTexture(BufferedImage img) {
        return Texture.makeFromImage(img);
    }

	public static Texture loadTexture(InputStream stream) throws IOException {
		return Texture.makeFromImage(loadImage(stream));
	}
}
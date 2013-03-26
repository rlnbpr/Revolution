package org.treegames.revolution.level;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.treegames.revolution.GameSettings;
import org.treegames.revolution.Main;
import org.treegames.revolution.gfx.Grid;

public abstract class Level {
	public abstract void buildLevel(Grid grid);

	public void buildFromData(Grid grid,LevelData l) {
        GameSettings.lighting = true;
		grid.grid=new int[l.layers[1].grid.length][l.layers[1].grid[0].length];
		grid.background=new int[l.layers[0].grid.length][l.layers[0].grid[0].length];
		for (int x=0;x<grid.grid.length;x++){
			for (int y=0;y<grid.grid[0].length;y++){
				grid.grid[x][y]=l.layers[1].grid[x][grid.grid[0].length-1-y];
				grid.background[x][y]=l.layers[0].grid[x][grid.background[0].length-1-y];
			}
		}
		grid.properties=l.properties;
	}

	public void buildFromStream(Grid grid,InputStream stream) {
		LevelData data=Main.mapFormat.read(stream);
		buildFromData(grid,data);
	}

	public void buildFromFile(Grid grid,File file) {
		try{
			buildFromStream(grid,new FileInputStream(file));
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
}
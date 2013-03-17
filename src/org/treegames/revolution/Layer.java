package org.treegames.revolution;

public class Layer {
	public int[][] grid;
	public int position=0;

	public void initGrid(int width,int height) {
		grid=new int[width][height];
	}
}
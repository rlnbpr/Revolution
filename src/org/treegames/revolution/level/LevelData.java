package org.treegames.revolution.level;

import java.util.HashMap;
import java.util.Map;

import org.treegames.revolution.Layer;

public class LevelData {
	public int grid[][];
	public Map<String,String> properties=new HashMap<String,String>();
	public Layer[] layers=new Layer[128];
}
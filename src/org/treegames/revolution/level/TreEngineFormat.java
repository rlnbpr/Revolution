package org.treegames.revolution.level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.treegames.revolution.Layer;

public class TreEngineFormat {
	private char[] numbers=new char[]{'0','1','2','3','4','5','6','7','8','9'};

	// not needed in the actual game, only used in TreeSDK
	// public void write(LevelData l,OutputStream os) {
	// try{
	// os.write(("// Generated by TreeSDK on "+new SimpleDateFormat("dd/MM/yyyy").format(new Date().getTime())+"\n").getBytes());
	// if(!l.properties.isEmpty())
	// os.write(("// Properties\n").getBytes());
	// for (String s:l.properties.keySet()){
	// os.write((s+"="+l.properties.get(s)+"\n").getBytes());
	// }
	// os.write("\n".getBytes());
	// os.write(("// Tiles\n").getBytes());
	// for (int y=0;y<l.grid[0].length;y++){
	// String output="";
	// for (int x=0;x<l.grid.length;x++){
	// int id=l.grid[x][y];
	// output+=id+",";
	// }
	// output=output.substring(0,output.length()-1);
	// output+="\n";
	// os.write(output.getBytes());
	// }
	// os.flush();
	// os.close();
	// }catch(FileNotFoundException e){
	// e.printStackTrace();
	// }catch(IOException e){
	// e.printStackTrace();
	// }
	// }

	public LevelData read(InputStream in) {
		long start=System.currentTimeMillis();
		LevelData l=new LevelData();
		Map<String,String> properties=new HashMap<String,String>();
		Map<Integer,Integer> layerMap=new HashMap<Integer,Integer>();
		String[] lines=null;
		String lin=null;
		int currentLine=-1;
		int layerCount=2;
		int width,maxRead=width=0;
		try{
			lines=new String[1024];
			InputStreamReader isr=new InputStreamReader(in);
			BufferedReader br=new BufferedReader(isr);
			while((lin=br.readLine())!=null){
				currentLine++;
				lines[currentLine]=lin;
				if(lin.isEmpty()||lin.startsWith("#")||lin.startsWith("//")){
					continue;
				}
				if(lin.startsWith("layer:")){
					// found a layer
					int id=Integer.parseInt(lin.split(":")[1]);
					System.out.println("Found Layer "+id);
					layerMap.put(currentLine,id);
				}else if(lin.contains("=")){
					// property
					String[] s=lin.trim().split("=");
					String key=s[0];
					String value=s[1];
					properties.put(key,value);
					System.out.println("Added property "+key+" with value "+value);
				}
			}
			width=Integer.parseInt(properties.get("mapWidth"));
			maxRead=Integer.parseInt(properties.get("mapHeight"));
		}catch(IOException e){
			e.printStackTrace();
		}

		l.layers=new Layer[layerCount];

		for (int lineNum:layerMap.keySet()){
			int layerId=layerMap.get(lineNum);
			lineNum-=1;
			System.out.println("Handling Layer "+layerId+" on line "+lineNum);
			currentLine=0;
			int startLine=lineNum+1;
			System.out.println("Start: "+startLine);
			int endLine=startLine+maxRead-1;
			System.out.println("End: "+endLine);
			ArrayList<ArrayList<Integer>> layout=new ArrayList<ArrayList<Integer>>();

			for (String line:lines){
				currentLine++;
				if(line==null){
					continue;
				}
				if(line.isEmpty()||!isNumber(line.charAt(0))||line.startsWith("#")||line.startsWith("//")||currentLine<startLine){
					continue;
				}
				if(currentLine==endLine+3){
					break;
				}
				if(currentLine>=startLine){
					// we're in a valid row
					ArrayList<Integer> row=new ArrayList<Integer>();
					String[] values=line.split(",");
					for (String s:values){
						row.add(Integer.parseInt(s));
					}
					layout.add(row);
				}
			}

			l.properties=properties;

			if(layout.isEmpty()||layout.get(0).isEmpty()){
				System.err.println("Empty map");
				return l;
			}

			l.layers[layerId]=new Layer();
			l.layers[layerId].position=layerId;
			l.layers[layerId].grid=new int[width][maxRead];
			for (int y=0;y<maxRead;y++){
				for (int x=0;x<width;x++){
					l.layers[layerId].grid[x][y]=layout.get(y).get(x);
				}
			}
		}

		for (int i=0;i<l.layers.length;i++){
			Layer lay=l.layers[i];
			if(lay==null){
				lay=new Layer();
				lay.initGrid(width,maxRead);
				lay.position=i;
				l.layers[i]=lay;
			}
		}

		long end=System.currentTimeMillis();
		long total=end-start;
		System.out.println("Took "+total+"ms to load");
		return l;
	}

	private boolean isNumber(char c) {
		for (char ch:numbers){
			if(c==ch){
				return true;
			}
		}
		return false;
	}
}
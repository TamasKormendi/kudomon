package model;

import java.util.ArrayList;

public class Kudomon {
	
	private String species;
	
	private int positionX;
	private int positionY;
	
	private Type type;
	
	private static ArrayList<Kudomon> kudomonList = new ArrayList<Kudomon>();
	
	public Kudomon(String sp, int x, int y, Type t){
		species = sp;
		positionX = x;
		positionY = y;
		type = t;
		
		kudomonList.add(this);
	}
	
	public String getSpecies(){
		return species;
	}
	
	public int getX(){
		return positionX;
	}
	
	public int getY(){
		return positionY;
	}
	
	public Type getType(){
		return type;
	}
	
	public static ArrayList<Kudomon> getKudomonList(){
		return kudomonList;
	}
}

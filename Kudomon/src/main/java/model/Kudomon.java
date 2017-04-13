package model;

public class Kudomon {
	
	private String species;
	
	private int positionX;
	private int positionY;
	
	private Type type;
	
	public Kudomon(String sp, int x, int y, Type t){
		species = sp;
		positionX = x;
		positionY = y;
		type = t;
	}
}

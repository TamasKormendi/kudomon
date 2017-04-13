package model;

import java.util.ArrayList;
import java.util.Objects;

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
	
	@Override
	public boolean equals(Object obj){
		if (obj == this){
			return true;
		}
		if (!(obj instanceof Kudomon)){
			return false;
		}
		Kudomon kudo = (Kudomon) obj;
		
		return positionX == kudo.positionX && positionY == kudo.positionY &&
				Objects.equals(species, kudo.species) && Objects.equals(type, kudo.type);
	}
	
	@Override
	public int hashCode(){
		return Objects.hash(species, positionX, positionY, type);
	}
	
	@Override
	public String toString(){
		return species;
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

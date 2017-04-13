package model;

import java.util.ArrayList;

public class Trainer {
	
	private String name;
	
	private int positionX;
	private int positionY;
	
	public Trainer(String n, int x, int y){
		name = n;
		positionX = x;
		positionY = y;
	}
	
	public ArrayList<Kudomon> getNearbyKudomons(){
		ArrayList<Kudomon> nearbyList = new ArrayList<Kudomon>();
		
		for (Kudomon kudo : Kudomon.getKudomonList()){
			int kudoX = kudo.getX();
			int kudoY = kudo.getY();
			
			double distance = Math.sqrt(Math.pow(positionX-kudoX, 2) + Math.pow(positionY-kudoY, 2));
			
			if (distance <= 100){
				nearbyList.add(kudo);
			}
		}
		return nearbyList;
	}
}

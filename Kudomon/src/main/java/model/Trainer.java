package model;

import java.util.ArrayList;
import java.util.List;

public class Trainer {
	
	private String name;
	
	private int positionX;
	private int positionY;
	
	private List<Kudomon> capturedKudomon;
	
	public Trainer(String n, int x, int y){
		name = n;
		positionX = x;
		positionY = y;
		
		capturedKudomon = new ArrayList<Kudomon>();
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
	
	public void captureKudomon(int index){
		Kudomon toCapture = Kudomon.getKudomonList().get(index);
		
		capturedKudomon.add(toCapture);
		Kudomon.getKudomonList().remove(toCapture);
	}
}

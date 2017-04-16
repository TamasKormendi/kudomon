package model;

import java.util.ArrayList;

public class Trainer {
	
	private String name;
	
	private int positionX;
	private int positionY;
	
	private Kudomon captureInProgress;
	
	private ArrayList<Kudomon> capturedKudomon;
	
	private static ArrayList<Trainer> trainerList = new ArrayList<Trainer>();
	
	public Trainer(String n, int x, int y){
		name = n;
		positionX = x;
		positionY = y;
		
		captureInProgress = null;
		
		capturedKudomon = new ArrayList<Kudomon>();
		
		trainerList.add(this);
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
	
	public void startCapture(int index){
		ArrayList<Kudomon> nearbyKudomons = getNearbyKudomons();
		Kudomon toCapture = nearbyKudomons.get(index);
		
		captureKudomon(toCapture);
		
		if (toCapture.getRemainingTurnsToCapture() != 0){
			captureInProgress = toCapture;
		}
	}
	
	public void captureKudomon(Kudomon kudomon){
		kudomon.decrementRemainingTurnsToCapture();
		
		if (kudomon.getRemainingTurnsToCapture() == 0){
			capturedKudomon.add(kudomon);
			Kudomon.getKudomonList().remove(kudomon);
			captureInProgress = null;
			System.out.println(name + " captured " + kudomon.getSpecies() + "!");
		}
		else{
			System.out.println(name + " needs " + kudomon.getRemainingTurnsToCapture() + " turns to capture " + kudomon.getSpecies() + "!");
		}
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	public String getName(){
		return name;
	}
	
	public Kudomon getCaptureInProgress(){
		return captureInProgress;
	}
	
	public ArrayList<Kudomon> getCapturedKudomons(){
		return capturedKudomon;
	}
	
	public static ArrayList<Trainer> getTrainerList(){
		return trainerList;
	}
}

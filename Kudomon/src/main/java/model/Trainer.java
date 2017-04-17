package model;

import java.util.ArrayList;

public class Trainer {
	
	private String name;
	
	private int positionX;
	private int positionY;
	
	//This field keeps track if a Trainer is in the process of capturing a Kudomon
	private Kudomon captureInProgress;
	
	//Keeps track of a Trainer's Kudomons
	private ArrayList<Kudomon> capturedKudomon;
	
	//A static Trainer ArrayList, every time a Trainer is created it gets
	//added to this, so it's possible to loop over them for the turn-based
	//game system
	private static ArrayList<Trainer> trainerList = new ArrayList<Trainer>();
	
	/**
	 * Creates a Trainer object
	 * @param n Name of the Trainer
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public Trainer(String n, int x, int y){
		name = n;
		positionX = x;
		positionY = y;
		
		captureInProgress = null;
		
		capturedKudomon = new ArrayList<Kudomon>();
		
		trainerList.add(this);
	}
	
	/**
	 * Retrieves a list of Kudomons who are within 100 units of distance to the Trainer
	 * @return The list of nearby Kudomons
	 */
	public ArrayList<Kudomon> getNearbyKudomons(){
		ArrayList<Kudomon> nearbyList = new ArrayList<Kudomon>();
		
		//Loop through every existing Kudomon
		for (Kudomon kudo : Kudomon.getKudomonList()){
			int kudoX = kudo.getX();
			int kudoY = kudo.getY();
			
			//Simple Euclidean distance calculation
			double distance = Math.sqrt(Math.pow(positionX-kudoX, 2) + Math.pow(positionY-kudoY, 2));
			
			//If the distance is lower or equal to 100 units then the Kudomon is added to the nearbyList
			if (distance <= 100){
				nearbyList.add(kudo);
			}
		}
		return nearbyList;
	}
	
	/**Start capturing a Kudomon
	 * @param index The index of the Kudomon within the nearbyList
	 */
	public void startCapture(int index){
		ArrayList<Kudomon> nearbyKudomons = getNearbyKudomons();
		Kudomon toCapture = nearbyKudomons.get(index);
		
		//It calls the captureKudomon method
		//which does most of the heavy lifting, startCapture mainly assures that the
		//Trainer keeps capturing the Kudomon until the remaining turns to capture
		//of the Kudomon is more than 0
		captureKudomon(toCapture);
		
		//This method is, of course, also responsible for setting the
		//captureInProgress field, if necessary
		//(This conditional could go to the captureKudomon method's else branch, but I feel it makes more sense
		//if the method that starts the capture also sets the captureInProgress field initially and captureKudomon
		//sets that field to null when appropriate)
		if (toCapture.getRemainingTurnsToCapture() != 0){
			captureInProgress = toCapture;
		}
	}
	
	/**
	 * Captures a Kudomon
	 * @param kudomon The Kudomon to capture
	 */
	public void captureKudomon(Kudomon kudomon){
		kudomon.decrementRemainingTurnsToCapture();
		
		//If the Kudomon's ready to be captured then it gets removed from the kudomonList and
		//gets added to the Trainer's personal Kudomon list and the captureInProgress field is set to null
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
	
	//toString override, for easy printability
	@Override
	public String toString(){
		return name;
	}
	
	//Getter methods
	
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

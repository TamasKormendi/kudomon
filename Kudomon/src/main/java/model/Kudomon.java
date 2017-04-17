package model;

import java.util.ArrayList;
import java.util.Random;

public class Kudomon {
	
	private String species;
	
	private int positionX;
	private int positionY;
	
	//I gave the Kudomons a default and a remaining turns to capture fields
	//so that I could check which Kudomon is currently being captured
	private int defaultTurnsToCapture;
	private int remainingTurnsToCapture;
	
	//Again, 2 fields so I can restore the HP of the victorious Kudomon after a fight
	private int defaultHP;
	private int remainingHP;
	
	private int cp;
	
	private Type type;
	
	//A static Kudomon ArrayList, every time a Kudomon is created it gets
	//added to this, so it's possible to loop over them and check which ones
	//are nearby to a given Trainer
	private static ArrayList<Kudomon> kudomonList = new ArrayList<Kudomon>();
	
	private Random rng;
	
	/**
	 * Creates a Kudomon object
	 * @param sp Name of the species
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param captureTurns Amount of turns to capture
	 * @param healthP Starting HP
	 * @param combatP Combat Points (damage)
	 * @param t Type
	 */
	public Kudomon(String sp, int x, int y, int captureTurns, int healthP, int combatP, Type t){
		species = sp;
		positionX = x;
		positionY = y;
		type = t;
		rng = new Random();
		
		
		//These conditionals handle error prevention - 
		//i.e. the turns to capture, the HP and CP
		//shouldn't be 0 or negative - if any is
		//it defaults to 1
		if (captureTurns > 0){
			defaultTurnsToCapture = captureTurns;
		}
		else{
			defaultTurnsToCapture = 1;
		}
		
		remainingTurnsToCapture = defaultTurnsToCapture;
		
		if (healthP > 0){
			defaultHP = healthP;
		}
		else{
			defaultHP = 1;
		}
		
		remainingHP = defaultHP;
		
		if (combatP > 0){
			cp = combatP;
		}
		else{
			cp = 1;
		}
		
		kudomonList.add(this);
	}
	
	
	/**
	 * Checks if the calling Kudomon is super effective against the argument Kudomon
	 * @param toBattle Kudomon to battle
	 * @return If the caller is super effective against the argument
	 */
	private boolean isSuperEffectiveAgainst(Kudomon toBattle){
		switch(type){
			case GRASS:
				if(toBattle.getType().equals(Type.ROCK)){
					return true;
				}
				else{
					return false;
				}
			case FIRE:
				if(toBattle.getType().equals(Type.GRASS)){
					return true;
				}
				else{
					return false;
				}
			case ELECTRIC:
				if(toBattle.getType().equals(Type.WATER)){
					return true;
				}
				else{
					return false;
				}
			case WATER:
				if(toBattle.getType().equals(Type.FIRE)){
					return true;
				}
				else{
					return false;
				}
			case ROCK:
				if(toBattle.getType().equals(Type.ELECTRIC)){
					return true;
				}
				else{
					return false;
				}
			case PSYCHIC:
				if(!toBattle.getType().equals(Type.PSYCHIC)){
					return true;
				}
				else{
					return false;
				}
			default:
				return false;
		}
	}
	
	/**
	 * Simulates a battle between two Kudomons
	 * @param toBattle The Kudomon to battle
	 * @return The winner Kudomon
	 */
	public Kudomon battle(Kudomon toBattle){
		//This section retrieves a random boolean
		//and decides which Kudomon goes first.
		//If the random boolean is false the
		//initiating Kudomon goes first, if not
		//then the defending one
		boolean toStart = rng.nextBoolean();
		Kudomon first;
		Kudomon second;
		
		if(!toStart){
			first = this;
			second = toBattle;
		}
		else{
			first = toBattle;
			second = this;
		}
		
		//Checks if either of the battling Kudomons is super effective against the other
		boolean isFirstEffectiveAgainstSecond = first.isSuperEffectiveAgainst(second);
		boolean isSecondEffeciveAgainstFirst = second.isSuperEffectiveAgainst(first);
		
		//A variable that keeps track which Kudomon is supposed to attack in the current turn
		boolean currentTurn = false;
		
		//The main battle loop, it ends when a Kudomon dies
		while(true){
			//If currentTurn is false then it's the "first" Kudomon's turn
			if(!currentTurn){
				System.out.println("It is " + first + "'s turn!");
				//If first is super effective against second then first wins instantly
				if(isFirstEffectiveAgainstSecond){
					System.out.println(first + " is super effective against " + second + "!");
					second.remainingHP = 0;
				}
				//If not, then first reduces second's remaining HP by its cp
				else{
					System.out.println(first + " hits " + second + " for " + first.cp + " points!" );
					second.remainingHP -= first.cp;
					System.out.println(second + " has " + second.remainingHP + " HP remaining!");
				}
			}
			//If currentTurn is true then it's second's time to shine - the rest of the logic is the same
			//(just with first and second swapped, of course)
			else{
				System.out.println("It is " + second + "'s turn!");
				if(isSecondEffeciveAgainstFirst){
					System.out.println(second + " is super effective against " + first + " !");
					first.remainingHP = 0;
				}
				else{
					System.out.println(second + " hits " + first + " for " + second.cp + " points!" );
					first.remainingHP -= second.cp;
					System.out.println(first + " has " + first.remainingHP + " HP remaining!");
				}
			}
			
			//If one Kudomon is dead then the winner gets its HP restored and the winner also gets returned
			if(first.remainingHP<=0){
				System.out.println(first + " Kudomon died, the winner is " + second + "!");
				second.resetHP();
				return second;
			}
			else if(second.remainingHP<=0){
				System.out.println(second + " Kudomon died, the winner is " + first + "!");
				first.resetHP();
				return first;
			}
			//If both of them are still alive then the battle continues and the other Kudomon gets its turn
			else{
				currentTurn = !currentTurn;
				System.out.println();
			}		
		}
	}
	
	/**
	 * Decrements the remaining turns to capture a Kudomon by one
	 */
	public void decrementRemainingTurnsToCapture(){
		--remainingTurnsToCapture;
	}
	
	/**
	 * Resets a Kudomon's HP to its default value
	 */
	public void resetHP(){
		remainingHP = defaultHP;
	}
	
	//toString override, for easy printability
	@Override
	public String toString(){
		return species;
	}
	
	//Getters for the fields
	
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
	
	public int getDefaultTurnsToCapture(){
		return defaultTurnsToCapture;
	}
	
	public int getRemainingTurnsToCapture(){
		return remainingTurnsToCapture;
	}
	
	public static ArrayList<Kudomon> getKudomonList(){
		return kudomonList;
	}
}

package model;

import java.util.ArrayList;
import java.util.Random;

public class Kudomon {
	
	private String species;
	
	private int positionX;
	private int positionY;
	private int defaultTurnsToCapture;
	private int remainingTurnsToCapture;
	
	private int defaultHP;
	private int remainingHP;
	private int cp;
	
	private Type type;
	
	private static ArrayList<Kudomon> kudomonList = new ArrayList<Kudomon>();
	
	private Random rng;
	
	public Kudomon(String sp, int x, int y, int captureTurns, int healthP, int combatP, Type t){
		species = sp;
		positionX = x;
		positionY = y;
		type = t;
		rng = new Random();
		
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
	
	public boolean isSuperEffectiveAgainst(Kudomon toBattle){
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
	
	public Kudomon battle(Kudomon toBattle){
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
		
		boolean isFirstEffectiveAgainstSecond = first.isSuperEffectiveAgainst(second);
		boolean isSecondEffeciveAgainstFirst = second.isSuperEffectiveAgainst(first);
		
		boolean currentTurn = false;
		
		while(true){
			if(!currentTurn){
				System.out.println("It is " + first + "'s turn!");
				if(isFirstEffectiveAgainstSecond){
					System.out.println(first + " is super effective against " + second + "!");
					second.remainingHP = 0;
				}
				else{
					System.out.println(first + " hits " + second + " for " + first.cp + " points!" );
					second.remainingHP -= first.cp;
					System.out.println(second + " has " + second.remainingHP + " HP remaining!");
				}
			}
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
			else{
				currentTurn = !currentTurn;
				System.out.println();
			}		
		}
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
	
	public int getDefaultTurnsToCapture(){
		return defaultTurnsToCapture;
	}
	
	public int getRemainingTurnsToCapture(){
		return remainingTurnsToCapture;
	}
	
	public static ArrayList<Kudomon> getKudomonList(){
		return kudomonList;
	}
	
	public void decrementRemainingTurnsToCapture(){
		--remainingTurnsToCapture;
	}
	
	public void resetHP(){
		remainingHP = defaultHP;
	}
}

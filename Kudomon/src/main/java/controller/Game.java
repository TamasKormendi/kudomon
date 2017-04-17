package controller;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import java.util.InputMismatchException;

import model.Kudomon;
import model.Trainer;

public class Game {
	private Scanner scanner;
	private Random rng;
	
	/**
	 * Constructor for Game, initialises the scanner and the random number generator
	 */
	public Game(){
		scanner = new Scanner(System.in);
		rng = new Random();
	}
	
	/**
	 * Starts the Trainer creator and then the game
	 */
	public void startGame(){
		System.out.println("Welcome to Kudomon!");
		System.out.print("Please create a Trainer for every player - type \"q\" as the name when you're done");
		
		int playerNumber = 0;
		//As long as the players want to add new Trainers it keeps looping
		while(true){
			System.out.println("\n" + "Please enter a name for player " + playerNumber + "'s Trainer: ");
			String name = scanner.next();
			int posX;
			int posY;
			
			if(name.equals("q") && playerNumber == 0){
				System.out.println("You need at least one Trainer to play!");
				//Restarts the loop if no Trainers exist and the player wants to continue to the game
				continue;
			}
			else if(name.equals("q")){
				break;
			}
			
			//Gets the two coordinates from the coordinateReader method
			posX = coordinateReader("X");	
			posY = coordinateReader("Y");
			
			//If every information is entered correctly then a Trainer is created and the playerNumber incremented
			Trainer trainer = new Trainer(name, posX, posY);
			++playerNumber;
		}
		
		ArrayList<Trainer> trainerList = Trainer.getTrainerList();
		playerNumber = 0;
		
		System.out.println("\n" + "Let the game start! - type the number of an option to select it");
		
		
		//Main loop of the game, it runs until a player selects the Quit option
		gameLoop:
		while(true){
			//Determines whose turn it is
			Trainer currentTrainer = trainerList.get(playerNumber);
			System.out.println("\n" + "It is " + currentTrainer.getName() + "'s turn!" + "\n");
			
			//If the Trainer is capturing a Kudomon then it automatically continues to do so
			//for this turn
			if(currentTrainer.getCaptureInProgress() != null){
				currentTrainer.captureKudomon(currentTrainer.getCaptureInProgress());
			}
			//If not, then the current player is asked for input
			else{
				System.out.println("Trainer, the following options are available to you: ");
				System.out.println("0 - Skip turn");
				System.out.println("1 - Capture a nearby Kudomon");
				System.out.println("2 - Battle another Trainer");
				System.out.println("3 - Quit" + "\n");
				
				int choice = optionSelector();
				
				//The loop of the player choice, it loops as long as the player doesn't select a valid option
				choiceLoop:
				while(true){
					switch(choice){
						case 0:
							System.out.println(currentTrainer.getName() + " skips the turn!");
							break choiceLoop;
						case 1:
							ArrayList<Kudomon> nearbyKudomons = currentTrainer.getNearbyKudomons();
						
							if(nearbyKudomons.isEmpty()){
								System.out.println("There are no Kudomons nearby, please select another action!");
								//If this happens the player is prompted to input a new choice and then the choiceLoop
								//is restarted - the logic is the same in the "case 2:" part as well
								choice = optionSelector();
								continue choiceLoop;
							}
							else{
								System.out.println("These Kudomons are near you:");
								for (int i = 0; i<nearbyKudomons.size(); ++i){
									System.out.print(i + " - " + nearbyKudomons.get(i) + " ");
								}
								System.out.println("\n" +  "Which one do you want to capture?");
								int toCapture = entitySelector(nearbyKudomons);
								
								//If the Kudomon's default and remaining turns to capture are the same then its capturing can be started
								if(nearbyKudomons.get(toCapture).getDefaultTurnsToCapture() == nearbyKudomons.get(toCapture).getRemainingTurnsToCapture()){
									currentTrainer.startCapture(toCapture);
								}
								//If not, the player loses his/her turn, this could be used as a risk-reward mechanic in a future extension,
								//e.g. try to capture a Kudomon in a populated area, where they are likely to be targets of other Trainers,
								//or try to look for less populated areas where the success of the capture is higher
								else{
									System.out.println("Oh no, this Kudomon is already getting captured by someone else!");
								}
							}
							//If it gets a valid input then it exits the choiceLoop
							break choiceLoop;
						case 2:
							if(currentTrainer.getCapturedKudomons().isEmpty()){
								System.out.println("You have no Kudomons! Please capture one first!");
								choice = optionSelector();
								continue choiceLoop;
							}
							
							ArrayList<Trainer> selectableTrainers = new ArrayList<Trainer>();
							
							//A Trainer can't battle him-/herself or a Trainer who has no Kudomons
							for(Trainer tr : Trainer.getTrainerList()){
								if (!tr.equals(currentTrainer) && !tr.getCapturedKudomons().isEmpty()){
									selectableTrainers.add(tr);
								}
							}
							
							if(selectableTrainers.isEmpty()){
								System.out.println("There are no Trainers you can fight right now, please select another action!");
								choice = optionSelector();
								continue choiceLoop;
							}
							else{
								System.out.println("These trainers can be battled:");
								for (int i = 0; i<selectableTrainers.size(); ++i){
									System.out.print(i + " - " + selectableTrainers.get(i) + " ");
								}
								
								//Asks the player for input...
								int toSelect = entitySelector(selectableTrainers);
								//...and then retrieves the selected Trainer's object from the selectableTrainers list 
								Trainer selectedTrainer = selectableTrainers.get(toSelect);
								
								//Selects a random Kudomon from both the attacking and defending Trainer's Kudomon list
								int indexOfAttacker = rng.nextInt(currentTrainer.getCapturedKudomons().size());
								int indexOfDefender = rng.nextInt(selectedTrainer.getCapturedKudomons().size());
								
								Kudomon attacker = currentTrainer.getCapturedKudomons().get(indexOfAttacker);
								Kudomon defender = selectedTrainer.getCapturedKudomons().get(indexOfDefender);
								
								//Simulates the battle
								Kudomon winner = attacker.battle(defender);
								
								//The loser Kudomon dies and gets removed from its Trainer's list
								if (winner.equals(attacker)){
									System.out.println(currentTrainer + " wins! " + selectedTrainer + "'s Kudomon dies!");
									selectedTrainer.getCapturedKudomons().remove(defender);
								}
								else{
									System.out.println(selectedTrainer + " wins! " + currentTrainer + "'s Kudomon dies!");
									currentTrainer.getCapturedKudomons().remove(attacker);
								}
							}
							//If it gets a valid input then it exits the choiceLoop
							break choiceLoop;
						case 3:
							System.out.println("Thank you for playing Kudomon Go!");
							//Exits the main gameLoop and thus, the program
							break gameLoop;
					}
				}
			}
			//The turn is over, the next player gets his/her turn
			++playerNumber;
			//This conditional ensures that we loop back to the first Trainer after everyone's turn is over
			if (playerNumber % trainerList.size() == 0){
				playerNumber = 0;
			}
		}
	}
	
	/**
	 * Reads a valid coordinate from the console
	 * @param axis The axis of the coordinate
	 * @return The coordinate
	 */
	private int coordinateReader(String axis){
		int coordinate = 0;
		boolean waitingForValidInput = true;
		
		//This is technically a retry-catch block - 
		//it loops as long as the user doesn't input a number
		while(waitingForValidInput){
			try{
				System.out.println("Give the " + axis + " co-ordinate for the trainer: ");
				coordinate = scanner.nextInt();
				waitingForValidInput = false;
			}
			catch(InputMismatchException e){
				System.out.println("Please input a valid number!" + "\n");
				//This line makes sure the scanner can be used after an exception
				scanner.next();
			}
		}
		return coordinate;
	}
	
	
	/**
	 * Reads the index of an option from the console
	 * @return The index of the option
	 */
	private int optionSelector(){
		int choice = 0;
		boolean waitingForValidInput = true;
		
		//General idea is the same as in coordinateReader,
		//it, however, also loops if the input is below 0 or above 3 - 
		//since it is known that there are no other options in the game
		while(waitingForValidInput){
			try{
				choice = scanner.nextInt();
				if (choice < 0 || choice > 3){
					System.out.println("Please input a number between 0-3 inclusive!" + "\n");
					continue;
				}
				waitingForValidInput = false;
			}
			catch(InputMismatchException e){
				System.out.println("Please input a valid number!" + "\n");
				scanner.next();
			}
		}
		return choice;
	}
	
	/**
	 * Reads the index of an entity (Kudomon or Trainer) from the console
	 * @param entityList The list of the selectable entities
	 * @return The index of the entity
	 */
	private int entitySelector(ArrayList<?> entityList){
		int toSelect = 0;
		boolean waitingForValidInput = true;
		
		//Similar to optionSelector, however, it checks for an input
		//between 0 (inclusive) and the size of the argument ArrayList (exclusive)
		while(waitingForValidInput){
			try{
				toSelect = scanner.nextInt();
				if (toSelect < 0 || toSelect >= entityList.size()){
					System.out.println("Please input a valid number!" + "\n");
					continue;
				}
				waitingForValidInput = false;
			}
			catch(InputMismatchException e){
				System.out.println("Please input a valid number!" + "\n");
				scanner.next();
			}
		}
		return toSelect;
	}
}

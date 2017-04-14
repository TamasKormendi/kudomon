package controller;

import java.awt.Choice;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.Kudomon;
import model.Trainer;

public class Game {
	private Scanner scanner;
	
	public Game(){
		scanner = new Scanner(System.in);
	}
	
	public void startGame(){
		System.out.println("Welcome to Kudomon!");
		System.out.print("Please create a trainer for every player - type \"q\" as the name when you're done");
		
		int playerNumber = 0;
		while(true){
			System.out.println("\n" + "Please enter a name for player " + playerNumber + "'s trainer: ");
			String name = scanner.next();
			int posX;
			int posY;
			
			if(name.equals("q") && playerNumber == 0){
				System.out.println("You need at least one trainer to play!");
				continue;
			}
			else if(name.equals("q")){
				break;
			}
			
			posX = coordinateReader("X");	
			posY = coordinateReader("Y");
			
			Trainer trainer = new Trainer(name, posX, posY);
			++playerNumber;
		}
		
		ArrayList<Trainer> trainerList = Trainer.getTrainerList();
		playerNumber = 0;
		
		System.out.println("\n" + "Let the game start! - type the number of an option to select it");
		
		gameLoop:
		while(true){
			Trainer currentTrainer = trainerList.get(playerNumber);
			System.out.println("\n" + "It is " + currentTrainer.getName() + "'s turn!" + "\n");
			
			if(currentTrainer.getCaptureInProgress() != null){
				currentTrainer.captureKudomon(currentTrainer.getCaptureInProgress());
			}
			else{
				System.out.println("Trainer, the following options are available to you: ");
				System.out.println("0 - Skip turn");
				System.out.println("1 - Capture a nearby Kudomon");
				System.out.println("2 - Battle another Trainer");
				System.out.println("3 - Quit" + "\n");
				
				int choice = optionSelector();
				
				choiceLoop:
				while(true){
					switch(choice){
						case 0:
							System.out.println(currentTrainer.getName() + " skips the turn!");
							break choiceLoop;
						case 1:
							ArrayList<Kudomon> nearbyKudomons = currentTrainer.getNearbyKudomons();
						
							if(nearbyKudomons.size() == 0){
								System.out.println("There are no Kudomons nearby, please select another action!");
								choice = optionSelector();
								continue choiceLoop;
							}
							else{
								System.out.println("These Kudomons are near you:");
								for (int i = 0; i<nearbyKudomons.size(); ++i){
									System.out.print(i + " - " + nearbyKudomons.get(i) + " ");
								}
								System.out.println("\n" +  "Which one do you want to capture?");
								int toCapture = captureSelector(nearbyKudomons);
								
								if(nearbyKudomons.get(toCapture).getDefaultTurnsToCapture() == nearbyKudomons.get(toCapture).getRemainingTurnsToCapture()){
									currentTrainer.startCapture(toCapture);
								}
								else{
									System.out.println("Oh no, this Kudomon is already getting captured by someone else!");
								}
							}
						
							break choiceLoop;
						case 2:
							//Part 4 function(s) get called here
							break;
						case 3:
							System.out.println("Thank you for playing Kudomon Go!");
							break gameLoop;
					}
				}
			}
			++playerNumber;
			if (playerNumber % trainerList.size() == 0){
				playerNumber = 0;
			}
		}
	}
	
	private int coordinateReader(String axis){
		int coordinate = 0;
		while(true){
			try{
				System.out.println("Give the " + axis + " co-ordinate for the trainer: ");
				coordinate = scanner.nextInt();
				break;
			}
			catch(InputMismatchException e){
				System.out.println("Please input a valid number!" + "\n");
				scanner.next();
			}
		}
		return coordinate;
	}
	
	private int optionSelector(){
		int choice = 0;
		
		while(true){
			try{
				choice = scanner.nextInt();
				if (choice < 0 || choice > 3){
					System.out.println("Please input a number between 0-3 inclusive!" + "\n");
					continue;
				}
				break;
			}
			catch(InputMismatchException e){
				System.out.println("Please input a valid number!" + "\n");
				scanner.next();
			}
		}
		return choice;
	}
	
	private int captureSelector(ArrayList<Kudomon> nearbyList){
		int toCapture = 0;
		
		while(true){
			try{
				toCapture = scanner.nextInt();
				if (toCapture < 0 || toCapture >= nearbyList.size()){
					System.out.println("Please input a valid Kudomon number!" + "\n");
					continue;
				}
				break;
			}
			catch(InputMismatchException e){
				System.out.println("Please input a valid number!" + "\n");
				scanner.next();
			}
		}
		return toCapture;
	}
}

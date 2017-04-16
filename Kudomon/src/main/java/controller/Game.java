package controller;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import model.Kudomon;
import model.Trainer;

public class Game {
	private Scanner scanner;
	private Random rnd;
	
	public Game(){
		scanner = new Scanner(System.in);
		rnd = new Random();
	}
	
	public void startGame(){
		System.out.println("Welcome to Kudomon!");
		System.out.print("Please create a Trainer for every player - type \"q\" as the name when you're done");
		
		int playerNumber = 0;
		while(true){
			System.out.println("\n" + "Please enter a name for player " + playerNumber + "'s Trainer: ");
			String name = scanner.next();
			int posX;
			int posY;
			
			if(name.equals("q") && playerNumber == 0){
				System.out.println("You need at least one Trainer to play!");
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
						
							if(nearbyKudomons.isEmpty()){
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
								int toCapture = entitySelector(nearbyKudomons);
								
								if(nearbyKudomons.get(toCapture).getDefaultTurnsToCapture() == nearbyKudomons.get(toCapture).getRemainingTurnsToCapture()){
									currentTrainer.startCapture(toCapture);
								}
								else{
									System.out.println("Oh no, this Kudomon is already getting captured by someone else!");
								}
							}
						
							break choiceLoop;
						case 2:
							if(currentTrainer.getCapturedKudomons().isEmpty()){
								System.out.println("You have no Kudomons! Please capture one first!");
								choice = optionSelector();
								continue choiceLoop;
							}
							
							ArrayList<Trainer> selectableTrainers = new ArrayList<Trainer>();
							
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
								
								int toSelect = entitySelector(selectableTrainers);
								Trainer selectedTrainer = selectableTrainers.get(toSelect);
								
								int indexOfAttacker = rnd.nextInt(currentTrainer.getCapturedKudomons().size());
								int indexOfDefender = rnd.nextInt(selectedTrainer.getCapturedKudomons().size());
								
								Kudomon attacker = currentTrainer.getCapturedKudomons().get(indexOfAttacker);
								Kudomon defender = selectedTrainer.getCapturedKudomons().get(indexOfDefender);
								
								Kudomon winner = attacker.battle(defender);
								
								if (winner.equals(attacker)){
									System.out.println(currentTrainer + " wins! " + selectedTrainer + "'s Kudomon dies!");
									selectedTrainer.getCapturedKudomons().remove(defender);
								}
								else{
									System.out.println(selectedTrainer + " wins! " + currentTrainer + "'s Kudomon dies!");
									currentTrainer.getCapturedKudomons().remove(attacker);
								}
							}
							break choiceLoop;
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
		boolean waitingForValidInput = true;
		
		while(waitingForValidInput){
			try{
				System.out.println("Give the " + axis + " co-ordinate for the trainer: ");
				coordinate = scanner.nextInt();
				waitingForValidInput = false;
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
		boolean waitingForValidInput = true;
		
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
	
	private int entitySelector(ArrayList<?> nearbyList){
		int toSelect = 0;
		boolean waitingForValidInput = true;
		
		while(waitingForValidInput){
			try{
				toSelect = scanner.nextInt();
				if (toSelect < 0 || toSelect >= nearbyList.size()){
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

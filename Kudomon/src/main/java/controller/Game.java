package controller;

import java.util.InputMismatchException;
import java.util.Scanner;

import model.Trainer;

public class Game {
	private Scanner scanner;
	
	public Game(){
		scanner = new Scanner(System.in);
	}
	
	public void startGame(){
		System.out.println("Welcome to Kudomon!");
		System.out.println("Please create a trainer for every player - type \"q\" when you're done");
		
		int playerNumber = 0;
		while(true){
			System.out.println("Please enter a name for player " + playerNumber + "'s trainer: ");
			String name = scanner.next();
			int posX = 0;
			int posY = 0;
			
			if(name.equals("q") && playerNumber == 0){
				System.out.println("You need at least one trainer to play!");
				continue;
			}
			else if(name.equals("q")){
				break;
			}
			
			while(true){
				try{
					System.out.println("Give an X co-ordinate for the trainer: ");
					posX = scanner.nextInt();
					break;
				}
				catch(InputMismatchException e){
					System.out.println("Please input a valid number!");
					System.out.println();
					scanner.next();
				}
			}
			
			while(true){
				try{
					System.out.println("Give a Y co-ordinate for the trainer: ");
					posY = scanner.nextInt();
					break;
				}
				catch(InputMismatchException e){
					System.out.println("Please input a valid number!");
					System.out.println();
					scanner.next();
				}
			}
			
			Trainer trainer = new Trainer(name, posX, posY);
			++playerNumber;
		}
	}
}

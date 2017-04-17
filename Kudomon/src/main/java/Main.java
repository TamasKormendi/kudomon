import controller.Game;
import model.Kudomon;
import model.Type;

public class Main {

	public static void main(String[] args) {		
		Kudomon kudo1 = new Kudomon("Chikapu", 15, 15, 1, 50, 10, Type.ELECTRIC);
		Kudomon kudo2 = new Kudomon("Mancharred", 7, 7, 3, 40, 15, Type.FIRE);
		Kudomon kudo3 = new Kudomon("Sourbulb", 1000, 1000, 2, 100, 2, Type.GRASS);
		
		Game game = new Game();
		
		game.startGame();
	}
}

import controller.Game;
import model.Kudomon;
import model.Type;

public class Main {

	public static void main(String[] args) {		
		Kudomon kudo1 = new Kudomon("TestSpecies1", 15, 15, 1, Type.ELECTRIC);
		Kudomon kudo2 = new Kudomon("TestSpecies2", 7, 7, 3, Type.FIRE);
		Kudomon kudo3 = new Kudomon("TestSpecies3", 1000, 1000, 2, Type.GRASS);
		
		Game game = new Game();
		
		game.startGame();
	}
}

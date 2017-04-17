import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import model.Kudomon;
import model.Type;

public class BattleTest {
	
	Kudomon kudoWeakPsych = new Kudomon("TestPsych", 15, 15, 1, 50, 10, Type.PSYCHIC);
	Kudomon kudoRock = new Kudomon("TestRock", 7, 7, 1, 40, 15, Type.ROCK);
	Kudomon kudoWeakFire = new Kudomon("TestGrass", 10, 10, 1, 1, 2, Type.FIRE);
	Kudomon kudoStrongPsych = new Kudomon("TestPsych", 20, 20, 1, 500, 10, Type.PSYCHIC);
	
	@After
	public void cleanUp(){
		Kudomon.getKudomonList().clear();
	}
	
	@Test
	public void battleAgainstAPsychic(){
		Kudomon winner = kudoWeakPsych.battle(kudoRock);
		
		assertEquals(kudoWeakPsych, winner);
	}
	
	@Test
	public void battleBetweenTwoPsychics(){
		Kudomon winner = kudoWeakPsych.battle(kudoStrongPsych);
		
		assertEquals(kudoStrongPsych, winner);
	}
	
	@Test
	public void ordinaryBattle(){
		Kudomon winner = kudoRock.battle(kudoWeakFire);
		
		assertEquals(kudoRock, winner);
	}
}

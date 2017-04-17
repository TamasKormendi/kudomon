import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Test;

import model.Kudomon;
import model.Trainer;
import model.Type;

public class CaptureTest {

	Trainer testTrainer = new Trainer("Mr Test", 10, 10);
	
	Kudomon kudoElectric = new Kudomon("TestElectric", 15, 15, 1, 50, 10, Type.ELECTRIC);
	Kudomon kudoFire = new Kudomon("TestFire", 7, 7, 1, 40, 15, Type.FIRE);
	Kudomon kudoGrass = new Kudomon("TestGrass", 1000, 1, 1000, 100, 2, Type.GRASS);
	
	@After
	public void cleanUp(){
		Kudomon.getKudomonList().clear();
		Trainer.getTrainerList().clear();
	}
	
	@Test
	public void testKudomonList(){
	
		ArrayList<Kudomon> testList = new ArrayList<Kudomon>();
		
		testList.add(kudoElectric);
		testList.add(kudoFire);
		testList.add(kudoGrass);
		
		assertEquals(testList, Kudomon.getKudomonList());
	}
	
	@Test
	public void testCapture(){
		testTrainer.startCapture(1);
		
		ArrayList<Kudomon> testFreeList = new ArrayList<Kudomon>();
		
		testFreeList.add(kudoElectric);
		testFreeList.add(kudoGrass);
		
		assertEquals(testFreeList, Kudomon.getKudomonList());
	}
}

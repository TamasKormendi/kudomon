import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.Kudomon;
import model.Trainer;
import model.Type;

public class CaptureTest {

	Trainer testTrainer1 = new Trainer("Mr Test", 10, 10);
	
	Kudomon kudo1 = new Kudomon("TestSpecies1", 15, 15, 1, Type.ELECTRIC);
	Kudomon kudo2 = new Kudomon("TestSpecies2", 7, 7, 1, Type.FIRE);
	Kudomon kudo3 = new Kudomon("TestSpecies3", 1000, 1, 1000, Type.GRASS);
	
	@After
	public void setup(){
		Kudomon.getKudomonList().clear();
	}
	
	@Test
	public void testKudomonList(){

		
		ArrayList<Kudomon> testList = new ArrayList<Kudomon>();
		
		testList.add(kudo1);
		testList.add(kudo2);
		testList.add(kudo3);
		
		assertEquals(testList, Kudomon.getKudomonList());
	}
	
	@Test
	public void testCapture(){
		testTrainer1.startCapture(1);
		
		ArrayList<Kudomon> testFreeList = new ArrayList<Kudomon>();
		
		testFreeList.add(kudo1);
		testFreeList.add(kudo3);
		
		assertEquals(testFreeList, Kudomon.getKudomonList());
	}
}

import static org.junit.Assert.*;
import org.junit.*;
public class ScoreSheetTest {
	ScoreSheet testSheet;
	
	@Before public void initialize() {
	}
	
	//TODO test toString?
	@Test public void testOneThrow() {
		// test valid pincount between 0 and 10
		for (int i = 0; i <= 10; ++i) {
			testSheet = new ScoreSheet();
			assertTrue(testSheet.addThrow(i));
			assertEquals(testSheet.getGameScore(),i);
			assertEquals(testSheet.getScoreInIndividualFrame(1),i);
		}
		// test invalid pincount
		testSheet = new ScoreSheet();
		assertFalse(testSheet.addThrow(-1)); // negative pincount
		assertEquals(testSheet.getGameScore(),0);
		assertEquals(testSheet.getScoreInIndividualFrame(1),0);
		
		testSheet = new ScoreSheet();
		testSheet.addThrow(11); // greater than 10 pincount
		assertFalse(testSheet.addThrow(11));
		assertEquals(testSheet.getGameScore(),0);
		assertEquals(testSheet.getScoreInIndividualFrame(1),0);
		
	}
	
	@Test public void testTwoThrows() {
		// test two valid pincounts
		int pinCount1 = 4;
		int pinCount2 = 5;
		testSheet = new ScoreSheet();
		assertTrue(testSheet.addThrow(pinCount1));
		assertTrue(testSheet.addThrow(pinCount2));
		assertEquals(testSheet.getGameScore(), pinCount1+pinCount2);
		assertEquals(testSheet.getScoreInIndividualFrame(1),pinCount1+pinCount2);
		
		// test zero (edge case)
		pinCount1 = 0;
		pinCount2 = 5;
		
		// test strike
		
		// test spare
		
		// test invalid pincounts
	}
	
	public void testThreeThrows() {
		
	}
}

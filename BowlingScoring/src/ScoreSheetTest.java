import static org.junit.Assert.*;
import org.junit.*;
public class ScoreSheetTest {
	ScoreSheet testSheet;
	int pinCount1 = 0;
	int pinCount2 = 0;
	int pinCount3 = 0;
	
	@Before public void initialize() {
	}
	
	// TODO test toString?
	// TODO test remaining frame scores?
	@Test public void testOneThrow() {
		// test valid pincount between 0 and 10
		for (int i = 0; i <= 10; ++i) {
			testSheet = new ScoreSheet();
			assertTrue(testSheet.addThrow(i));
			assertEquals(testSheet.getScoreInIndividualFrame(1), i);//TODO ? assertEquals parameters all need to be reversed! The expected value is the first parameter.
			assertEquals(testSheet.getGameScore(),i);
		}
		// test invalid pincount
		testSheet = new ScoreSheet();
		assertFalse(testSheet.addThrow(-1)); // negative pincount
		assertEquals(testSheet.getScoreInIndividualFrame(1), 0);
		assertEquals(testSheet.getGameScore(),0);
		
		testSheet = new ScoreSheet();
		testSheet.addThrow(11); // greater than 10 pincount
		assertFalse(testSheet.addThrow(11));
		assertEquals(testSheet.getScoreInIndividualFrame(1), 0);
		assertEquals(testSheet.getGameScore(),0);
	}
	
	@Test public void testTwoThrows() {
		// test two valid pincounts
		pinCount1 = 4;
		pinCount2 = 5;
		testSheet = new ScoreSheet();
		assertTrue(testSheet.addThrow(pinCount1));
		assertTrue(testSheet.addThrow(pinCount2));
		assertEquals(testSheet.getScoreInIndividualFrame(1), pinCount1+pinCount2);
		assertEquals(testSheet.getGameScore(), pinCount1+pinCount2);
		
		// test zero
		pinCount1 = 0;
		pinCount2 = 0;
		testSheet = new ScoreSheet();
		assertTrue(testSheet.addThrow(pinCount1));
		assertTrue(testSheet.addThrow(pinCount2));
		assertEquals(testSheet.getScoreInIndividualFrame(1), pinCount1+pinCount2);
		assertEquals(testSheet.getGameScore(), pinCount1+pinCount2);
		
		pinCount1 = 2;
		pinCount2 = 0;
		testSheet = new ScoreSheet();
		assertTrue(testSheet.addThrow(pinCount1));
		assertTrue(testSheet.addThrow(pinCount2));
		assertEquals(testSheet.getScoreInIndividualFrame(1), pinCount1+pinCount2);
		assertEquals(testSheet.getGameScore(), pinCount1+pinCount2);
		
		pinCount1 = 0;
		pinCount2 = 3;
		testSheet = new ScoreSheet();
		assertTrue(testSheet.addThrow(pinCount1));
		assertTrue(testSheet.addThrow(pinCount2));
		assertEquals(testSheet.getScoreInIndividualFrame(1), pinCount1+pinCount2);
		assertEquals(testSheet.getGameScore(), pinCount1+pinCount2);
		
		// test strike
		pinCount1 = 10;
		pinCount2 = 8;
		testSheet = new ScoreSheet();
		assertTrue(testSheet.addThrow(pinCount1));
		assertTrue(testSheet.addThrow(pinCount2)); // second throw should be in second frame
		assertEquals(testSheet.getScoreInIndividualFrame(2), 8);
		assertEquals(testSheet.getScoreInIndividualFrame(1), 18); // first frame includes second frame score
		assertEquals(testSheet.getGameScore(), 26);
		
		pinCount1 = 10;
		pinCount2 = 10;
		testSheet = new ScoreSheet();
		assertTrue(testSheet.addThrow(pinCount1));
		assertTrue(testSheet.addThrow(pinCount2));
		assertEquals(testSheet.getScoreInIndividualFrame(2), 10);
		assertEquals(testSheet.getScoreInIndividualFrame(1), 20);
		assertEquals(testSheet.getGameScore(), 30);
		
		pinCount1 = 10;
		pinCount2 = 0;
		testSheet = new ScoreSheet();
		assertTrue(testSheet.addThrow(pinCount1));
		assertTrue(testSheet.addThrow(pinCount2));
		assertEquals(testSheet.getScoreInIndividualFrame(2), 0);
		assertEquals(testSheet.getScoreInIndividualFrame(1), 10);
		assertEquals(testSheet.getGameScore(), 10);
		
		// test spare, not a special case in the first frame
		pinCount1 = 3;
		pinCount2 = 7;
		testSheet = new ScoreSheet();
		assertTrue(testSheet.addThrow(pinCount1));
		assertTrue(testSheet.addThrow(pinCount2));
		assertEquals(testSheet.getScoreInIndividualFrame(1), pinCount1+pinCount2);
		assertEquals(testSheet.getGameScore(), pinCount1+pinCount2);
		
		pinCount1 = 0;
		pinCount2 = 10;
		testSheet = new ScoreSheet();
		assertTrue(testSheet.addThrow(pinCount1));
		assertTrue(testSheet.addThrow(pinCount2));
		assertEquals(testSheet.getScoreInIndividualFrame(1), pinCount1+pinCount2);
		assertEquals(testSheet.getGameScore(), pinCount1+pinCount2);
		
		// test invalid pincounts
		pinCount1 = -2;
		pinCount2 = 13;
		testSheet = new ScoreSheet();
		assertFalse(testSheet.addThrow(pinCount1));
		assertFalse(testSheet.addThrow(pinCount2));
		assertEquals(testSheet.getScoreInIndividualFrame(1), 0);
		assertEquals(testSheet.getGameScore(), 0);
		
		pinCount1 = 4;
		pinCount2 = -15;
		testSheet = new ScoreSheet();
		assertTrue(testSheet.addThrow(pinCount1));
		assertFalse(testSheet.addThrow(pinCount2));
		assertEquals(testSheet.getScoreInIndividualFrame(1), 4);
		assertEquals(testSheet.getGameScore(), 4);
		
		pinCount1 = 20;
		pinCount2 = 5;
		testSheet = new ScoreSheet();
		assertFalse(testSheet.addThrow(pinCount1));
		assertTrue(testSheet.addThrow(pinCount2));
		assertEquals(testSheet.getScoreInIndividualFrame(1), 5);
		assertEquals(testSheet.getGameScore(), 5);
		
		// spares and strikes with invalid pincounts has the same outcome as normal pincounts
	}
	
	@Test public void testThreeThrows() {
		// test three valid pincounts
		pinCount1 = 4;
		pinCount2 = 5;
		pinCount3 = 6;
		testSheet = new ScoreSheet();
		assertTrue(testSheet.addThrow(pinCount1));
		assertTrue(testSheet.addThrow(pinCount2));
		assertTrue(testSheet.addThrow(pinCount3));
		assertEquals(testSheet.getScoreInIndividualFrame(1), pinCount1+pinCount2);
		assertEquals(testSheet.getScoreInIndividualFrame(2), pinCount3);
		assertEquals(testSheet.getGameScore(), pinCount1+pinCount2+pinCount3);
		
		// test three consecutive strikes
		pinCount1 = 10;
		pinCount2 = 10;
		pinCount3 = 10;
		testSheet = new ScoreSheet();
		assertTrue(testSheet.addThrow(pinCount1));
		assertTrue(testSheet.addThrow(pinCount2));
		assertTrue(testSheet.addThrow(pinCount3));
		assertEquals(testSheet.getScoreInIndividualFrame(3), 10);
		assertEquals(testSheet.getScoreInIndividualFrame(2), 20);
		assertEquals(testSheet.getScoreInIndividualFrame(1), 40);//TODO ? needs to be 30 (max score in a frame)
		assertEquals(testSheet.getGameScore(), 70);//TODO ? needs to be 60 (30+20+10)
		
		// test one strike and one spare
		pinCount1 = 10;
		pinCount2 = 4;
		pinCount3 = 6;
		testSheet = new ScoreSheet();
		assertTrue(testSheet.addThrow(pinCount1));
		assertTrue(testSheet.addThrow(pinCount2));
		assertTrue(testSheet.addThrow(pinCount3));
		System.out.println(testSheet.toString());
		assertEquals(testSheet.getScoreInIndividualFrame(2), 10);
		assertEquals(testSheet.getScoreInIndividualFrame(1), 20);
		assertEquals(testSheet.getGameScore(), 30);
		
		// test one strike and one spare with zero
		pinCount1 = 10;
		pinCount2 = 0;
		pinCount3 = 10;
		testSheet = new ScoreSheet();
		assertTrue(testSheet.addThrow(pinCount1));
		assertTrue(testSheet.addThrow(pinCount2));
		assertTrue(testSheet.addThrow(pinCount3));
		assertEquals(testSheet.getScoreInIndividualFrame(2), 10);
		assertEquals(testSheet.getScoreInIndividualFrame(1), 20);
		assertEquals(testSheet.getGameScore(), 30);
		
		// test one spare and one strike
		pinCount1 = 8;
		pinCount2 = 2;
		pinCount3 = 10;
		testSheet = new ScoreSheet();
		assertTrue(testSheet.addThrow(pinCount1));
		assertTrue(testSheet.addThrow(pinCount2));
		assertTrue(testSheet.addThrow(pinCount3));
		assertEquals(testSheet.getScoreInIndividualFrame(2), 10);
		assertEquals(testSheet.getScoreInIndividualFrame(1), 20);
		assertEquals(testSheet.getGameScore(), 30);
		
		// test invalid pincount
		pinCount1 = 10;
		pinCount2 = 11;
		pinCount3 = 10;
		testSheet = new ScoreSheet();
		assertTrue(testSheet.addThrow(pinCount1));
		assertFalse(testSheet.addThrow(pinCount2));
		assertTrue(testSheet.addThrow(pinCount3));
		assertEquals(testSheet.getScoreInIndividualFrame(2), 10);
		assertEquals(testSheet.getScoreInIndividualFrame(1), 20);
		assertEquals(testSheet.getGameScore(), 30);
	}
}

//TODO Chris
//Chris here: imma be doing this with copy/paste...so here goes...
import static org.junit.Assert.*;
import org.junit.*;
public class ScoreSheetTest2 {
	ScoreSheet testSheet;
	
	@Before public void initialize() {
	}
	/*
	 * throw a spare in a frame and make sure itsscore is correct 
	 * (counting the following frame, which should also be completed)
	 */
	@Test public void testSpareCountsNextFrameScore() {
		testSheet = new ScoreSheet();
		//test before throwing something after the spare
		assertTrue(testSheet.addThrow(4));
		assertTrue(testSheet.addThrow(6));
		assertEquals(10, testSheet.getGameScore());
		//test once something new has been thrown
		testSheet.addThrow(5);
		testSheet.addThrow(0);
		assertEquals(15, testSheet.getScoreInIndividualFrame(1));
		assertEquals(20, testSheet.getGameScore());
		
	}
	
	/*
	 * ensure that a strike frame may not have two throws
	 */
	@Test public void testStrikeMovesToNextFrame() {
		testSheet = new ScoreSheet();
		//add strike to a sheet
		assertTrue(testSheet.addThrow(10));
		assertTrue(testSheet.addThrow(5));
		//if strike didn't move to the next frame then it would
		//add 5 to the first frame
		assertEquals(5, testSheet.getScoreInIndividualFrame(2));
		
	}
	
	/*
	 * throw a strike in a frame and make sure its
	 * score is correct (counting the following frames, which should also be completed)
	 */
	
	@Test public void testStrikeCountsNextFrameScores() {
		testSheet = new ScoreSheet();
		//test one strike
		//add strike
		assertTrue(testSheet.addThrow(10));
		//add next throw of 8
		assertTrue(testSheet.addThrow(4));
		assertTrue(testSheet.addThrow(4));
		//test strike score
		assertEquals(18, testSheet.getScoreInIndividualFrame(1));
		//test total score (18 + 8 = 26)
		assertEquals(26, testSheet.getGameScore());
		
		//add next score (4) to the sheet
		assertTrue(testSheet.addThrow(4));
		//score of strike should be 10 + 8 + 4 = 22
		assertEquals(22, testSheet.getScoreInIndividualFrame(1));
		//test total score of the game (22 + 8 + 4 = 34)
		assertEquals(34, testSheet.getGameScore());
		//test 2 consecutive strikes
		
		testSheet = new ScoreSheet();
		assertTrue(testSheet.addThrow(10));
		assertTrue(testSheet.addThrow(10));
		assertTrue(testSheet.addThrow(4));
		assertTrue(testSheet.addThrow(4));
		//test first frame correct scoring
		assertEquals(28, testSheet.getScoreInIndividualFrame(1));
		//test second frame correct scoring
		assertEquals(18, testSheet.getScoreInIndividualFrame(2));
		//test correct game score
		assertEquals(28+18+8, testSheet.getGameScore());
		
	}
}

import static org.junit.Assert.*;
import org.junit.*;
public class ScoreSheetTest {
	@Before
	public void before() {
		
	}
	
	@Test
	//TODO test toString?
	public void testOneThrow() {
		ScoreSheet mySheet;
		// test valid pin inputs between 0 and 10
		for (int i = 0; i <= 10; ++i) {
			mySheet = new ScoreSheet();
			assertTrue(mySheet.addThrow(i));
			assertEquals(mySheet.getGameScore(),i);
			assertEquals(mySheet.getScoreInIndividualFrame(1),i);
		}
		// test invalid pin inputs
		mySheet = new ScoreSheet();
		assertFalse(mySheet.addThrow(-1)); // negative pin value
		assertEquals(mySheet.getGameScore(),0);
		assertEquals(mySheet.getScoreInIndividualFrame(1),0);
		
		mySheet = new ScoreSheet();
		mySheet.addThrow(11); // pin value too large
		assertFalse(mySheet.addThrow(11));
		assertEquals(mySheet.getGameScore(),0);
		assertEquals(mySheet.getScoreInIndividualFrame(1),0);
		
	}
	
	public void testTwoThrows() {
		
	}
	
	public void testThreeThrows() {
		
	}
	
	/*
	 * CHRIS
	 * throw a spare in a frame and make sure itsscore is correct 
	 * (counting the following frame, which should also be completed)
	 */
	public void testSpareCountsNextFrameScore() {
	}
	
	/*
	 * CHRIS
	 * ensure that a strike frame may not have two throws
	 */
	public void testStrikeMovesToNextFrame() {
		
	}
	
	/*
	 * CHRIS
	 * throw a strike in a frame and make sure its
	 * score is correct (counting the following frames, which should also be completed)
	 */
	
	public void testStrikeCountsNextFrameScores() {
		
	}
	
	/*
	 * JOHN
	 * test throwing a spare on the 10th frame
	 */
	public void testSpareOnLastFrame() {
		
	}
	
	/*
	 * JOHN
	 * test throwing a strike on the 8th, 9th, and 10th frames
	 */
	public void testStrikeOnLastFrames() {
		
	}
	
	/*
	 * JOHN
	 * ensure throwing on the 11th frame is not allowed (in
	 * some form)
	 */
	public void testThrowOn11thFrame() {
		
	}
}

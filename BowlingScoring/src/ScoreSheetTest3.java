import static org.junit.Assert.*;
import org.junit.*;



public class ScoreSheetTest3 {
	ScoreSheet testSheet;
	
	@Before 
	public void initialize() {
		
		testSheet=new ScoreSheet();
		for(int i=0;i<8;i++){
			testSheet.addThrow(10);
		}
		
	}
	/*
	 * test throwing a spare on the 10th frame
	 */
	@Test public void testSpareOnLastFrame() {
		testSheet.addThrow(10);
		testSheet.addThrow(10);
		testSheet.addThrow(5);
		testSheet.addThrow(5);
		assertEquals(10,testSheet.getScoreInIndividualFrame(10));
	}
	
	/*
	 * test throwing a strike on the 8th, 9th, and 10th frames
	 */
	@Test 
	public void testStrikeOnLastFrames() {
		testSheet.addThrow(10);
		testSheet.addThrow(10);
		testSheet.addThrow(10);
		assertEquals(30,testSheet.getScoreInIndividualFrame(8));
		assertEquals(20,testSheet.getScoreInIndividualFrame(9));
		assertEquals(10,testSheet.getScoreInIndividualFrame(10));
	}
	
	/*
	 * ensure throwing on the 11th frame is not allowed (in
	 * some form)
	 */
	@Test 
	public void testThrowOn11thFrame() {
		testSheet.addThrow(10);
		testSheet.addThrow(10);
		testSheet.addThrow(10);
		assertFalse(testSheet.addThrow(10));
	}
}

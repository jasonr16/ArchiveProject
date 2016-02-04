import static org.junit.Assert.*;
import org.junit.*;
public class ScoreSheetTest {
	@Before
	public void before() {
		
	}
	
	@Test
	public void testOneThrow() {
		ScoreSheet mySheet;
		// test valid pin inputs between 0 and 10
		for (int i = 0; i <= 10; ++i) {
			mySheet = new ScoreSheet();
			mySheet.addThrow(i);
			assertEquals(mySheet.getGameScore(),i);
			assertEquals(mySheet.getScoreInIndividualFrame(1),i);
			// test toString?
		}
		// test invalid pin inputs
		mySheet = new ScoreSheet();
		assertFalse(mySheet.addThrow(-1)); // negative pin value
		
		mySheet = new ScoreSheet();
		mySheet.addThrow(11); // pin value too large
		assertFalse(mySheet.addThrow(11));
		
	}
	
	public void testTwoThrows() {
		
	}
}

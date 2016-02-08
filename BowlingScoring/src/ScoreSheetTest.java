import static org.junit.Assert.*;
import org.junit.*;
public class ScoreSheetTest {
	ScoreSheet testSheet;
	int pinCount1 = 0;
	int pinCount2 = 0;
	int pinCount3 = 0;
	boolean isThrow1Valid = false;
	boolean isThrow2Valid = false;
	boolean isThrow3Valid = false;
	int frame1Score = 0;
	int frame2Score = 0;
	int frame3Score = 0;
	int gameScore = 0;
	
	@Before public void initialize() {
	}
	
	// TODO test toString?
	@Test public void testOneThrow() {
		// test valid pincount between 0 and 10
		for (int i = 0; i <= 10; ++i) {
			testSheet = new ScoreSheet();
			assertTrue(testSheet.addThrow(i));
			assertEquals(i, testSheet.getScoreInIndividualFrame(1));
			assertEquals(i, testSheet.getGameScore());
		}
		// test invalid pincount
		testSheet = new ScoreSheet();
		assertFalse(testSheet.addThrow(-1)); // negative pincount
		assertEquals(0, testSheet.getScoreInIndividualFrame(1));
		assertEquals(0, testSheet.getGameScore());
		
		testSheet = new ScoreSheet();
		testSheet.addThrow(11); // greater than 10 pincount
		assertFalse(testSheet.addThrow(11));
		assertEquals(0, testSheet.getScoreInIndividualFrame(1));
		assertEquals(0, testSheet.getGameScore());
	}
	
	@Test public void testTwoThrows() {
		for(int i = -1; i <= 11; ++i) {
			pinCount1 = i;
			for(int j = -1; j <= 11; ++j) {
				pinCount2 = j;
				testSheet = new ScoreSheet();
				isThrow1Valid = testSheet.addThrow(pinCount1);
				isThrow2Valid = testSheet.addThrow(pinCount2);
				frame2Score = testSheet.getScoreInIndividualFrame(2);
				frame1Score = testSheet.getScoreInIndividualFrame(1);
				gameScore = testSheet.getGameScore();
				// tree test structure with three outcomes for each successive throw
				// outcome1: pincount is out of range and invalid
				// outcome2: pincount is valid and <=9
				// outcome3: pincount is 10
				if (pinCount1 < 0 || pinCount1 > 10) {
					if (pinCount2 < 0 || pinCount2 > 10) {
						assertFalse(isThrow1Valid);
						assertFalse(isThrow2Valid);
						assertEquals(0, frame1Score);
						assertEquals(0, gameScore);
					}
					else if (pinCount2 >= 0 && pinCount2 <= 9) {
						assertFalse(isThrow1Valid);
						assertTrue(isThrow2Valid);
						assertEquals(pinCount2, frame1Score);
						assertEquals(pinCount2, gameScore);
					}
					else { // pinCount2 == 10, strike
						assertFalse(isThrow1Valid);
						assertTrue(isThrow2Valid);
						assertEquals(10, frame1Score);
						assertEquals(10, gameScore);
					}
				}
				else if (pinCount1 >= 0 && pinCount1 <= 9) {
					if (pinCount2 < 0 || pinCount2 > 10) {
						assertTrue(isThrow1Valid);
						assertFalse(isThrow2Valid);
						assertEquals(pinCount1, frame1Score);
						assertEquals(pinCount1, gameScore);
					}
					else if (pinCount2 >= 0 && pinCount2 <= 9) {
						assertTrue(isThrow1Valid);
						if ((pinCount1 + pinCount2) > 10) { // second throw is invalid if sum of first two throws > 10, e.g. 9+8
							assertFalse(isThrow2Valid);
							assertEquals(pinCount1, frame1Score);
							assertEquals(pinCount1, gameScore);
						}
						else {
							assertTrue(isThrow2Valid);
							assertEquals(pinCount1+pinCount2, frame1Score);
							assertEquals(pinCount1+pinCount2, gameScore);
						}
					}
					else { // pinCount2 == 10
						assertTrue(isThrow1Valid);
						if ((pinCount1 + pinCount2) > 10) { // second throw is invalid if sum of first two throws > 10, e.g. 9+8
							assertFalse(isThrow2Valid);
							assertEquals(pinCount1, frame1Score);
							assertEquals(pinCount1, gameScore);
						}
						else { // spare
							assertTrue(isThrow2Valid);
							assertEquals(pinCount1+10, frame1Score);
							assertEquals(pinCount1+10, gameScore);
						}
					}
				}
				else { // pinCount1 == 10, strike
					if (pinCount2 < 0 || pinCount2 > 10) {
						assertTrue(isThrow1Valid);
						assertFalse(isThrow2Valid);
						assertEquals(10, frame1Score);
						assertEquals(10, gameScore);
					}
					else if (pinCount2 >= 0 && pinCount2 <= 9) {
						assertTrue(isThrow1Valid);
						assertTrue(isThrow2Valid);
						assertEquals(pinCount2, frame2Score);
						assertEquals(10+pinCount2, frame1Score);
						assertEquals(10+pinCount2+pinCount2, gameScore);
					}
					else { // pinCount1 == 10, second strike
						assertTrue(isThrow1Valid);
						assertTrue(isThrow2Valid);
						assertEquals(10, frame2Score);
						assertEquals(20, frame1Score);
						assertEquals(30, gameScore);
					}
				}
				// two invalid pincounts
				if (!isPinCountValid(pinCount1) && !isPinCountValid(pinCount2)) {
					assertFalse(isThrow1Valid);
					assertFalse(isThrow2Valid);
					assertEquals(0, frame1Score);
					assertEquals(0, gameScore);
				}
			}
		}
	}
	
	@Test public void testThreeThrows() {
		for(int i = -1; i <= 11; ++i) {
			pinCount1 = i;
			for(int j = -1; j <= 11; ++j) {
				pinCount2 = j;
				for (int k = -1; k <= 11; ++k) {
					pinCount3 = k;
					testSheet = new ScoreSheet();
					isThrow1Valid = testSheet.addThrow(pinCount1);
					isThrow2Valid = testSheet.addThrow(pinCount2);
					isThrow3Valid = testSheet.addThrow(pinCount3);
					frame3Score = testSheet.getScoreInIndividualFrame(3);
					frame2Score = testSheet.getScoreInIndividualFrame(2);
					frame1Score = testSheet.getScoreInIndividualFrame(1);
					gameScore = testSheet.getGameScore();
					
					// three invalid pincounts (assume testTwoThrows() is enough to test other combinations of valid and invalid pincounts)
					if (!isPinCountValid(pinCount1) && !isPinCountValid(pinCount2) && !isPinCountValid(pinCount3)) {
						assertFalse(isThrow1Valid);
						assertFalse(isThrow2Valid);
						assertFalse(isThrow3Valid);
						assertEquals(0, frame1Score);
						assertEquals(0, gameScore);
					}
					// tree test structure with two outcomes for each successive throw (ignore invalid throws to reduce number of test cases)
					// outcome1: pincount is valid and <= 9
					// outcome2: pincount is 10
					if (pinCount1 >= 0 && pinCount1 <= 9) {
						if (pinCount2 >= 0 && pinCount2 <= 9) {
							if (pinCount3 >= 0 && pinCount3 <= 9) {
								// first two throws must total 10 or less, otherwise second throw is invalid
								if (pinCount1 + pinCount2 > 10) {
									assertTrue(isThrow1Valid);
									assertFalse(isThrow2Valid);
									// first and third throw must total 10 or less, otherwise third throw is invalid
									if (pinCount1 + pinCount3 > 10) {
										assertFalse(isThrow3Valid);
										assertEquals(pinCount1, frame1Score);
										assertEquals(pinCount1, gameScore);
									}
									else {
										assertTrue(isThrow3Valid);
										assertEquals(pinCount1+pinCount3, frame1Score);
										assertEquals(pinCount1+pinCount3, gameScore);
									}
								}
								else {
									assertTrue(isThrow1Valid);
									assertTrue(isThrow2Valid);
									assertTrue(isThrow3Valid);
									assertEquals(pinCount3, frame2Score);
									if (pinCount1 + pinCount2 == 10) { // 1st frame spare
										assertEquals(pinCount1+pinCount2+pinCount3, frame1Score);
										assertEquals(pinCount1+pinCount2+2*pinCount3, gameScore);
									}
									else {
										assertEquals(pinCount1+pinCount2, frame1Score);
										assertEquals(pinCount1+pinCount2+pinCount3, gameScore);
									}
								}
							}
							else if (pinCount3 == 10){
								// first two throws must total 10 or less, otherwise second throw is invalid
								if (pinCount1 + pinCount2 > 10) {
									assertTrue(isThrow1Valid);
									assertFalse(isThrow2Valid);
									// first and third throw must total 10 or less, otherwise third throw is invalid
									if (pinCount1 + pinCount3 > 10) {
										assertFalse(isThrow3Valid);
										assertEquals(pinCount1, frame1Score);
										assertEquals(pinCount1, gameScore);
									}
									else { // spare
										assertTrue(isThrow3Valid);
										assertEquals(pinCount1+10, frame1Score);
										assertEquals(pinCount1+10, gameScore);
									}
								}
								else {
									assertTrue(isThrow1Valid);
									assertTrue(isThrow2Valid);
									assertTrue(isThrow3Valid);
									assertEquals(10, frame2Score);
									if (pinCount1 + pinCount2 == 10) { // 1st frame spare
										assertEquals(pinCount1+pinCount2+10, frame1Score);
										assertEquals(pinCount1+pinCount2+2*10, gameScore);
									}
									else {
										assertEquals(pinCount1+pinCount2, frame1Score);
										assertEquals(pinCount1+pinCount2+10, gameScore);
									}
								}
							}
						}
						else if (pinCount2 == 10){
							if (pinCount3 >= 0 && pinCount3 <= 9) {
								// first two throws must total 10 or less, otherwise second throw is invalid
								if (pinCount1 + pinCount2 > 10) {
									assertTrue(isThrow1Valid);
									assertFalse(isThrow2Valid);
									// first and third throw must total 10 or less, otherwise third throw is invalid
									if (pinCount1 + pinCount3 > 10) {
										assertFalse(isThrow3Valid);
										assertEquals(pinCount1, frame1Score);
										assertEquals(pinCount1, gameScore);
									}
									else {
										assertTrue(isThrow3Valid);
										assertEquals(pinCount1+pinCount3, frame1Score);
										assertEquals(pinCount1+pinCount3, gameScore);
									}
								}
								else { // first frame is spare (0,10)
									assertTrue(isThrow1Valid);
									assertTrue(isThrow2Valid);
									assertTrue(isThrow3Valid);
									assertEquals(pinCount3, frame2Score);
									assertEquals(0+10+pinCount3, frame1Score);
									assertEquals(0+10+2*pinCount3, gameScore);
								}
							}
							else if (pinCount3 == 10){
								// first two throws must total 10 or less, otherwise second throw is invalid
								if (pinCount1 + pinCount2 > 10) {
									assertTrue(isThrow1Valid);
									assertFalse(isThrow2Valid);
									// first and third throw must total 10 or less, otherwise third throw is invalid
									if (pinCount1 + pinCount3 > 10) {
										assertFalse(isThrow3Valid);
										assertEquals(pinCount1, frame1Score);
										assertEquals(pinCount1, gameScore);
									}
									else { // first frame is spare (0,10)
										assertTrue(isThrow3Valid);
										assertEquals(0+10, frame1Score);
										assertEquals(10, gameScore);
									}
								}
								else { // first frame is spare (0,10)
									assertTrue(isThrow1Valid);
									assertTrue(isThrow2Valid);
									assertTrue(isThrow3Valid);
									assertEquals(10, frame2Score);
									assertEquals(0+10+10, frame1Score);
									assertEquals(0+3*10, gameScore);
								}
							}
						}
					}
					else if (pinCount1 == 10){
						if (pinCount2 >= 0 && pinCount2 <= 9) {
							if (pinCount3 >= 0 && pinCount3 <= 9) {
								// first frame is strike
								assertTrue(isThrow1Valid);
								assertTrue(isThrow2Valid);
								// second and third throw must total 10 or less, otherwise third throw is invalid
								if (pinCount2 + pinCount3 > 10) {
									assertFalse(isThrow3Valid);
									assertEquals(pinCount2, frame2Score);
									assertEquals(10+pinCount2, frame1Score);
									assertEquals(10+2*pinCount2, gameScore);
								}
								else {
									assertTrue(isThrow3Valid);
									assertEquals(pinCount2+pinCount3, frame2Score);
									assertEquals(10+pinCount2+pinCount3, frame1Score);
									assertEquals(10+2*(pinCount2+pinCount3), gameScore);
								}
							}
							else if (pinCount3 == 10){
								// first frame is strike
								assertTrue(isThrow1Valid);
								assertTrue(isThrow2Valid);
								// second and third throw must total 10 or less, otherwise third throw is invalid
								if (pinCount2 + pinCount3 > 10) {
									assertFalse(isThrow3Valid);
									assertEquals(pinCount2, frame2Score);
									assertEquals(10+pinCount2, frame1Score);
									assertEquals(10+2*pinCount2, gameScore);
								}
								else { // second frame is spare (0,10)
									assertTrue(isThrow3Valid);
									assertEquals(0+10, frame2Score);
									assertEquals(10+10, frame1Score);
									assertEquals(3*10, gameScore);
								}
							}
						}
						else if (pinCount2 == 10){
							if (pinCount3 >= 0 && pinCount3 <= 9) {
								// first frame and second frame are consecutive strikes
								assertTrue(isThrow1Valid);
								assertTrue(isThrow2Valid);
								assertTrue(isThrow3Valid);
								assertEquals(pinCount3, frame3Score);
								assertEquals(10+pinCount3, frame2Score);
								assertEquals(10+10+pinCount3, frame1Score);
								assertEquals((10+10+pinCount3)+(10+pinCount3)+pinCount3, gameScore);
							}
							else if (pinCount3 == 10){
								// three consecutive strikes
								assertTrue(isThrow1Valid);
								assertTrue(isThrow2Valid);
								assertTrue(isThrow3Valid);
								assertEquals(10, frame3Score);
								assertEquals(20, frame2Score);
								assertEquals(30, frame1Score);
								assertEquals(60, gameScore);
							}
						}
					}
				}
			}
		}
	}
	
	private boolean isPinCountValid(int pinCount) {
		return (pinCount >= 0 && pinCount <= 10);
	}
}

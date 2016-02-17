import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ScoreSheetThreeThrowsRefactored {
	
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
	

	private boolean isPinCountValid(int pinCount) {
		return (pinCount >= 0 && pinCount <= 10);
	}
	
	@Test public void testThreeThrows() {
		for(int i = -1; i <= 11; ++i) {
			pinCount1 = i;
			for(int j = -1; j <= 11; ++j) {
				pinCount2 = j;
				for (int k = -1; k <= 11; ++k) {
					pinCount3 = k;
					initializeTestVariables();
										
					// three invalid pincounts (assume testTwoThrows() is enough to test other combinations of valid and invalid pincounts)
					if (!isPinCountValid(pinCount1) && !isPinCountValid(pinCount2) && !isPinCountValid(pinCount3)) {
						checkThreeInvalidInputs();	
					}
					// tree test structure with two outcomes for each successive throw (ignore invalid throws to reduce number of test cases)
					// outcome1: pincount is valid and <= 9
					// outcome2: pincount is 10
					if (pinCount1 >= 0 && pinCount1 <= 9) {
						if (pinCount2 >= 0 && pinCount2 <= 9) {
							if (pinCount3 >= 0 && pinCount3 <= 9) {
								checkValidityForThreeThrowsNo10s();
							}
							else if (pinCount3 == 10){
								checkValidityForThirdThrowEquals10();
							}
						}
						else if (pinCount2 == 10){
							checkValidityForSecondThrowEquals10();	
						}
					}
					else if (pinCount1 == 10){
						checkValidityForFirstThrowEquals10();
					}
				}
			}
		}
	}
	
	

	private void initializeTestVariables() {
		testSheet = new ScoreSheet();
		isThrow1Valid = testSheet.addThrow(pinCount1);
		isThrow2Valid = testSheet.addThrow(pinCount2);
		isThrow3Valid = testSheet.addThrow(pinCount3);
		frame3Score = testSheet.getScoreInIndividualFrame(3);
		frame2Score = testSheet.getScoreInIndividualFrame(2);
		frame1Score = testSheet.getScoreInIndividualFrame(1);
		gameScore = testSheet.getGameScore();
		
	}

	private void checkThreeInvalidInputs() {
		assertFalse(isThrow1Valid);
		assertFalse(isThrow2Valid);
		assertFalse(isThrow3Valid);
		assertEquals(0, frame1Score);
		assertEquals(0, gameScore);
		
	}

	private void checkValidityForThreeThrowsNo10s() {
		// first two throws must total 10 or less, otherwise second throw is invalid
		if (pinCount1 + pinCount2 > 10) {
			assertTrue(isThrow1Valid);
			assertFalse(isThrow2Valid);
			// first and third throw must total 10 or less, otherwise third throw is invalid
			if (pinCount1 + pinCount3 > 10) {
				checkReplaceSecondThrow();
			}
			else {
				checkValidCompleteFirstFrame();
			}
		}
		else {
			checkForValidThreeThrows();
		}
	}

	private void checkReplaceSecondThrow() {
		assertFalse(isThrow3Valid);
		assertEquals(pinCount1, frame1Score);
		assertEquals(pinCount1, gameScore);
	}
	
	private void checkValidCompleteFirstFrame() {
		assertTrue(isThrow3Valid);
		assertEquals(pinCount1+pinCount3, frame1Score);
		assertEquals(pinCount1+pinCount3, gameScore);
		
	}
	private void checkForValidThreeThrows() {
		assertTrue(isThrow1Valid);
		assertTrue(isThrow2Valid);
		assertTrue(isThrow3Valid);
		assertEquals(pinCount3, frame2Score);
		if (pinCount1 + pinCount2 == 10) { // 1st frame spare
			assertEquals(pinCount1+pinCount2+pinCount3, frame1Score);
			assertEquals(pinCount1+pinCount2+2*pinCount3, gameScore);
		}
		else {//open first frame
			assertEquals(pinCount1+pinCount2, frame1Score);
			assertEquals(pinCount1+pinCount2+pinCount3, gameScore);
		}
		
	}

	private void checkValidityForThirdThrowEquals10() {
		// first two throws must total 10 or less, otherwise second throw is invalid
		if (pinCount1 + pinCount2 > 10) {
			checkValidityForInvalidSecondThrow();
		}
		else {
			checkThreeValidThrows();
		}
	}

	private void checkThreeValidThrows() {
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

	private void checkValidityForInvalidSecondThrow() {
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

	private void checkValidityForSecondThrowEquals10() {
		if (pinCount3 >= 0 && pinCount3 <= 9) {
			checkValidityForThirdThrowNotStrike();	
		}
		else if (pinCount3 == 10){
			checkValidityForThirdThrowStrike();
		}	
	}
	
	private void checkValidityForThirdThrowStrike() {
		// first two throws must total 10 or less, otherwise second throw is invalid
					if (pinCount1 + pinCount2 > 10) {
						checkInvalidSecondThrow();
						
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

	private void checkInvalidSecondThrow() {
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

	private void checkValidityForThirdThrowNotStrike() {
		// first two throws must total 10 or less, otherwise second throw is invalid
					if (pinCount1 + pinCount2 > 10) {
						checkInvalidSecondThrowWhenThirdNot10();
						
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

	private void checkInvalidSecondThrowWhenThirdNot10() {
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

	private void checkValidityForFirstThrowEquals10() {
		if (pinCount2 >= 0 && pinCount2 <= 9) {
			if (pinCount3 >= 0 && pinCount3 <= 9) {
				checkValidityForFirstFrameOnly10();
				
			}
			else if (pinCount3 == 10){
				checkValidityForFirstFrameAndThirdFrame10();
				
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
				assertEquals(Math.min(10+10+pinCount3,30), frame1Score);
				assertEquals(Math.min(10+10+pinCount3,30)+10+pinCount3+pinCount3, gameScore);
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

	private void checkValidityForFirstFrameAndThirdFrame10() {
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

	private void checkValidityForFirstFrameOnly10() {
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
	
	
	
}

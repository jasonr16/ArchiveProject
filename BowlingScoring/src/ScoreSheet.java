
public class ScoreSheet {
		private int[] frameScores = new int[13];
		private boolean[] strikeFrames = new boolean[13];
		private boolean[] spareFrames = new boolean[13];
		private int currentFrame = 1;
		private int currentThrowInFrame = 1;
		
		/**
		 * 
		 * 
		 * @return true if valid throw, false if invalid pincount or frame
		 */
		public boolean addThrow(int pincount) {
			boolean isValid = true;
			if(currentFrame > 10) {
				isValid = false;
			}
			else if (pincount < 0 || pincount > 10) {
				isValid = false;
			}
			else {
				recordScore(pincount);
				advanceThrowAndFrame(pincount);
				updateScores();
			}
			
			return isValid;
		}
		
		private void recordScore(int pincount) {
			frameScores[currentFrame] += pincount;
			if(isStrike(pincount, currentThrowInFrame)) {
				strikeFrames[currentFrame] = true;
			}
			if(isSpare()) {
				spareFrames[currentFrame] = true;
			}
		}

		private boolean isStrike(int pincount, int throwInFrame) {
			return (pincount == 10 && throwInFrame == 1);
		}
		
		private boolean isSpare() {
			return (frameScores[currentFrame] == 10 && strikeFrames[currentFrame] == false);
		}
		

		private void advanceThrowAndFrame(int pincount) {
			//threw second shot
			if(currentThrowInFrame == 2) {		
				currentFrame++;
				currentThrowInFrame = 1;
			}
			//strike thrown
			else if(pincount == 10 && currentThrowInFrame == 1) {		
				currentFrame++;
			}
			//first shot not strike
			else {		
				currentThrowInFrame = 2;
			}
			
		}
		/**
		 * 
		 * @param frame		1 represents the first frame
		 * @return the current value of an individual frame or -1 if invalid frame.
		 * 
		 */
		public int getScoreInIndividualFrame(int frame) {
			int score;
			if(frame < 1 || frame > 10)
				score = -1;
			else
				score = frameScores[frame];
			
			return score; 
		}
		
		/**
		 * 
		 * @return the total sum of all frames and throws already bowled.
		 */
		public int getGameScore() {
			int gameScore = 0;
			for(int i = 1; i < 11; i++) {
				gameScore += frameScores[i]; 
			}
			return gameScore;
		}
		
		private void updateScores() {
			for(int i = 1; i < 11; i++) {
				if(strikeFrames[i]) {
					frameScores[i] = frameScores[i] + frameScores[i + 1] + frameScores[i + 2];
				}
				else if (spareFrames[i]) {
					frameScores[i] = frameScores[i] + frameScores[i + 1];
				}
			}
		}
		
		@Override
		public String toString() {
			String scoreString = "Frame scores {";
			for(int i = 1; i < 11; i++) {
				scoreString += frameScores[i] + " ";
			}
			scoreString += "} ";
			
			scoreString += "Total score - " + getGameScore() + "\n"; 
			
			return scoreString;
			
		}

		
}


public class ScoreSheet {
		private int[] frameScores = new int[13];
		private boolean[] strikeFrames = new boolean[13];
		private int currentFrame = 1;
		private int currentThrowInFrame = 1;
		
		/**
		 * 
		 * 
		 * @return true if valid throw and false if frame is 11 or higher.
		 */
		public boolean addThrow(int pincount) {
			boolean isGameOver = false;
			if(currentFrame > 10) {
				isGameOver = true;
			}
			else {
				frameScores[currentFrame] += pincount;
				if(isStrike(pincount)) {
					strikeFrames[currentFrame] = true;
				}
			}
			advanceThrowAndFrame(pincount);
			updateScores();
			
			return isGameOver;
		}
		
		private boolean isStrike(int pincount) {
			return (pincount == 10 && currentThrowInFrame == 1);
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
		 * 
		 * @return the current value of an individual frame. 
		 * This value may increase after more throws if it is a spare or a strike frame.
		 */
		public int getScoreInIndividualFrame(int frame) {
			return frameScores[frame];
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
				else if (isSpare(i)) {
					frameScores[i] = frameScores[i] + frameScores[i + 1];
				}
			}
		}

		private boolean isSpare(int frame) {
			return (frameScores[frame] == 10 && strikeFrames[frame] == false);
		}
		
}

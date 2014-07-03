package com.mathgame.math;

/**
 * The ScoringSystem class keeps track of the score
 */
public class ScoringSystem {
	
	private double totalScore;
	private long startTime;
	private double roundScore;
	private String gameType;
	
	public ScoringSystem()	{
		startTime = 0;
		roundScore = 0;
		totalScore = 0;
	}
	
	/**
	 * @param type - The game type
	 */
	public void setGameType(String type) {
		gameType = type;
		System.out.println("Game type is: " + gameType);
	}
	
	/**
	 * @param time - The time when the round begins
	 */
	public void setTimeStart(long time) {
		startTime = time;		
	}
	
	/**
	 * @param time - The time (in seconds) needed to complete the round
	 * @return The amount of points won, based on the time needed
	 */
	private double scoringAlgorithm(long time) {
		double score = 120 - time; // 120 seconds, or two minutes
		return score;
	}
	
	/**
	 * Calculates the number of points earned in the round, depending on the game type
	 * @param endTime - The time when the round was won (and thus ended)
	 * @param cardCount - The amount of cards used
	 * @return The amount of points won in the round
	 */
	public double uponWinning(long endTime, int cardCount)	{
		long totalTime = (endTime - startTime) / 1000; // Divide by 1000 to get seconds
		if (gameType == "Speed") {
			roundScore = scoringAlgorithm(totalTime);
		} else if(gameType == "Complexity") {
			roundScore = cardCount * 20;
		} else {
			// "Mix" scoring
			roundScore = scoringAlgorithm(totalTime) * cardCount; // Cards used is a multiplier		
		}
		System.out.println(cardCount + " " + roundScore);
		totalScore += roundScore;
		return roundScore;
	}
	
	/**
	 * Calculated the number of points lost
	 * <p>
	 * 1 - Incorrect answer;
	 * 2 - Reshuffling of cards
	 * @param deductionType - The type of deduction
	 */
	public void uponDeduction(int deductionType) {
		if (deductionType == 1) {
			// Incorrect answer
			totalScore -= 20;
		} else if (deductionType == 2) {
			// Randomization
			totalScore -= 10;
		}
	}
	
	public double getTotalScore() {
		return totalScore;
	}
	
	public boolean checkRandom() {
		return totalScore > 10;
	}

}

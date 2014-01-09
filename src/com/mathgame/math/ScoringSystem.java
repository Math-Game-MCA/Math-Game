package com.mathgame.math;

public class ScoringSystem {
	
	private double totalScore;
	private long StartTime;
	private long EndTime;
	private double roundScore;
	
	public ScoringSystem()	{
		StartTime = 0;
		roundScore = 0;
		totalScore = 0;
	}
	
	public void setTimeStart (long time)	{
		
		StartTime = time;
		
	}
	
	private double scoringAlgorithm(long time)	{
		double score = 0;
		
		score = 120 - time;//120 means 2 minutes
		
		return score;
	}
	
	public double uponWinning(long time)	{
		EndTime = time;
		long totalTime = EndTime - StartTime;
		roundScore = scoringAlgorithm(totalTime / 1000);//divide by 1000 to get seconds
		totalScore += roundScore;
		return roundScore;
	}
	
	public void uponDeduction(int deductionType)	{
		if (deductionType == 1){
			//Incorrect answer
			totalScore -= 20;
		}
		
		else if (deductionType == 2)	{
			//Randomization
			totalScore -= 10;
		}
	}
	
	public double getTotalScore()	{
		return totalScore;
	}
	
	public boolean checkRandom()	{
		if (totalScore > 10)
			return true;
		else
			return false;
	}

}

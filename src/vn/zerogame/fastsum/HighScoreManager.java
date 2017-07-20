package vn.zerogame.fastsum;

public class HighScoreManager 
{	
	
	private static  long[] LimitedScore =new long[6];	
	private static 	long UnlimitedScore =0;
	
	public static void UpdateScore(long score, int gamemode, int limit)
	{
		if(gamemode==GameActivity.GAMEMODE_LIMITED)
		{			
			if(score>LimitedScore[limit])
			{
				LimitedScore[limit] =score;
			}											
			
		}
		
		else 
		{			
			if(score>UnlimitedScore)
			{
				UnlimitedScore=score;
			}
		}
				
	}
	
	public static long GetLimitedScore(int limit)
	{
		return LimitedScore[limit];
	}
	
	
	public static long GetUnlimitedScore()
	{
		return UnlimitedScore;
	}
	
	public static void SetLimitedScore(long score, int limit)
	{
		LimitedScore[limit]=score;
	}
	
	
	public static void SetUnlimitedScore(long unlimitedScore)
	{
		UnlimitedScore=unlimitedScore;
	}

}

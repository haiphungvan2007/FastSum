package vn.zerogame.fastsum;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.NoCopySpan.Concrete;

public class GameState {
	public static long Score=0;		
	public static int GameMode=1;
	public static int Level=1;
	public static int MaxSelected = 3;
	
	
	public static void ResetGame()
	{
		Score=0;		
		Level=1;
	}
	
	public static void SaveGame(Context context)
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);				
		Editor editor = sharedPreferences.edit();						
		editor.putLong("L3", HighScoreManager.GetLimitedScore(3));
		editor.putLong("L4", HighScoreManager.GetLimitedScore(4));
		editor.putLong("UL", HighScoreManager.GetUnlimitedScore());						
	    editor.commit();

	}
	
	public static void LoadGame(Context context)
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		HighScoreManager.SetLimitedScore(sharedPreferences.getLong("L3", 0), 3);
		HighScoreManager.SetLimitedScore(sharedPreferences.getLong("L4", 0), 4);
		HighScoreManager.SetUnlimitedScore(sharedPreferences.getLong("UL", 0));

	}
}

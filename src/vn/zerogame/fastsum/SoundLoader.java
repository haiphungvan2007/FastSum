package vn.zerogame.fastsum;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundLoader {
	private static MediaPlayer StartGameSound=null; 
	private static MediaPlayer CorrectSound=null;
	private static MediaPlayer WrongSound=null;
	private static MediaPlayer ClickSound=null;
	
	public static MediaPlayer GetStartGameSound(Context mContext) 
	{
		if(StartGameSound==null)
		{
			StartGameSound=MediaPlayer.create(mContext, R.raw.start_game);
		}
		
		else
		{
			try
			{
				StartGameSound.stop();
				StartGameSound.reset();
			}
			catch(Exception ex)
			{
				
			}
		}
		
		return StartGameSound;
	}
	
	public static MediaPlayer GetCorrectSound(Context mContext)
	{
		if(CorrectSound==null)
		{
			CorrectSound=MediaPlayer.create(mContext, R.raw.finish);
		}
		
		else
		{
			try
			{
				CorrectSound.stop();
				CorrectSound.reset();
			}
			catch(Exception ex)
			{
				
			}
		}
		
		return CorrectSound;
	}
	
	public static MediaPlayer GetWrongSound(Context mContext)
	{
		if(WrongSound==null)
		{
			WrongSound=MediaPlayer.create(mContext, R.raw.failed);
		}
		
		else
		{
			try
			{
				WrongSound.stop();
				WrongSound.reset();
			}
			catch(Exception ex)
			{
				
			}
		}
		
		return WrongSound;
	}
	
	public static MediaPlayer GetClickSound(Context mContext)
	{
		if(ClickSound==null)
		{
			ClickSound=MediaPlayer.create(mContext, R.raw.btn_clk);
		}
		
		else
		{
			try
			{
				ClickSound.stop();
				ClickSound.reset();
			}
			catch(Exception ex)
			{
				
			}
		}
		
		return ClickSound;
	}
	
}

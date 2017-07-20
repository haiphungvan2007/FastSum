package vn.zerogame.fastsum;

import android.content.Context;
import android.graphics.Typeface;

public class GameFontManager {
	private static Typeface jingjingFont=null;
	private static Typeface buxtonSketchFont=null;
	
	public static Typeface GetJingJingFont(Context context)
	{
		if(jingjingFont==null)
		{
			jingjingFont = Typeface.createFromAsset(context.getAssets(),"JingJing.ttf"); 			  
		}
		
		return jingjingFont;
	}
	
	
	public static Typeface GetBuxtonSketchFont(Context context)
	{
		if(buxtonSketchFont==null)
		{
			buxtonSketchFont = Typeface.createFromAsset(context.getAssets(),"BuxtonSketch.ttf"); 			  
		}
		
		return buxtonSketchFont;
	}
	
}

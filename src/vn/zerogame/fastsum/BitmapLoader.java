package vn.zerogame.fastsum;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;

public class BitmapLoader {
	private static ArrayList<int[]> boards=new ArrayList<int[]>();
	private static Context mContext;
	private static Random random;
		
	
	private static void Init(Context context)
	{
		mContext=context;
		if(boards.size()==0)
		{
			random=new Random();
			
			
			
			int[] bitmapList1=new int[9];
			bitmapList1[0]=R.drawable.i1_1;
			bitmapList1[1]=R.drawable.i1_2;
			bitmapList1[2]=R.drawable.i1_3;
			bitmapList1[3]=R.drawable.i1_4;
			bitmapList1[4]=R.drawable.i1_5;
			bitmapList1[5]=R.drawable.i1_6;
			bitmapList1[6]=R.drawable.i1_7;
			bitmapList1[7]=R.drawable.i1_8;
			bitmapList1[8]=R.drawable.i1_9;			
			boards.add(bitmapList1);
			
		}
	}
	
	public static int[] getBitmapBoard(Context context)
	{
		int[] retval=null;
		
		Init(context);		
		int index=random.nextInt(boards.size());		
		if((index>=0) && (index<boards.size()))
		{
			retval=boards.get(index);
		}
		
		return retval;
	}
}

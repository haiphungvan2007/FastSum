package vn.zerogame.fastsum;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Meteo {
	int StartX;
	int StartY;
	
	int CurX;
	int CurY;
	Bitmap bm;
	
	Rect srcRect=new Rect();
	Rect desRect=new Rect();

	
	int disX=22;
	int disY=13;
	
	Paint paint=new Paint();
	public Meteo(Context context, int res, int x, int y) {
		// TODO Auto-generated constructor stub
		bm=BitmapFactory.decodeResource(context.getResources(), res);
		srcRect.top=0;
		srcRect.left=0;
		srcRect.bottom=bm.getHeight();
		srcRect.right=bm.getWidth();
		
		StartX=x;
		StartY=y;
		
		desRect.top=x;
		desRect.left=y;
		
		CurX=StartX;
		CurY=StartY;
		
		paint.setARGB(102, 255, 255, 255);
	}
	
	
	
	public void draw(Canvas canvas)
	{
		CurX+=disX;
		CurY+=disY;
		
		if(CurX>(canvas.getWidth()+50))
		{
			CurX=StartX;
			CurY=StartY;
		}
		
		int size=canvas.getWidth()*5/100;
		
		desRect.top=CurY;
		desRect.left=CurX;
		desRect.bottom=size+desRect.top;
		desRect.right=size+desRect.left;
		
		canvas.drawBitmap(bm,srcRect,desRect,paint);
	}
	
	public void release()
	{
		bm.recycle();
	}

}

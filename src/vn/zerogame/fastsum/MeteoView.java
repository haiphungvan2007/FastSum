package vn.zerogame.fastsum;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class MeteoView extends View {
	
	Meteo meteo1;
	Meteo meteo2;
	Meteo meteo3;
	Meteo meteo4;
	Meteo meteo5;
	
	Meteo meteo6;
	Meteo meteo7;
	Meteo meteo8;
	Meteo meteo9;
	Meteo meteo10;
	

	public MeteoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		meteo1=new Meteo(context, R.drawable.i1_1, -100, 10);
		meteo2=new Meteo(context, R.drawable.i1_5, -130, 80);
		meteo3=new Meteo(context, R.drawable.i1_8, -120, 110);
		meteo4=new Meteo(context, R.drawable.i1_2, -150, 200);
		meteo5=new Meteo(context, R.drawable.i1_7, -180, 30);
		
		meteo6=new Meteo(context, R.drawable.i1_1, -400, 300);
		meteo7=new Meteo(context, R.drawable.i1_5, -430, 450);
		meteo8=new Meteo(context, R.drawable.i1_8, -420, 520);
		meteo9=new Meteo(context, R.drawable.i1_2, -450, 600);
		meteo10=new Meteo(context, R.drawable.i1_7, -480, 630);
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);		
		
		meteo1.draw(canvas);
		meteo2.draw(canvas);
		meteo3.draw(canvas);
		meteo4.draw(canvas);
		meteo5.draw(canvas);
		meteo6.draw(canvas);
		meteo7.draw(canvas);
		meteo8.draw(canvas);
		meteo9.draw(canvas);
		meteo10.draw(canvas);
		
	}

	
	public void release()
	{
		meteo1.release();
		meteo2.release();
		meteo3.release();
		meteo4.release();
		meteo5.release();
		meteo6.release();
		meteo7.release();
		meteo8.release();
		meteo9.release();
		meteo10.release();		
	}
}

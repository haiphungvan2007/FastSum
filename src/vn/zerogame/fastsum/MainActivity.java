package vn.zerogame.fastsum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView blinkText;
	Handler mHandler=new Handler()
	{
		public void handleMessage(android.os.Message msg) 
		{
			if(msg.what==0)
			{
				if(blinkText.getVisibility()==View.VISIBLE)
				{
					blinkText.setVisibility(View.GONE);
				}
				
				else
				{
					blinkText.setVisibility(View.VISIBLE);
				}
			}
		}
	};
	
	Thread blinkThread=new Thread()
	{
		public void run() 
		{
			while(true)
			{
				mHandler.sendEmptyMessage(0);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	};
	
	ImageView titleImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		blinkText=(TextView) findViewById(R.id.blinkText);
		titleImage=(ImageView)findViewById(R.id.gameTitleImage);
		
		Animation animation=AnimationUtils.loadAnimation(this, R.anim.move);
		animation.setAnimationListener(new Animation.AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				blinkThread.start();
			}
		});
		
		titleImage.startAnimation(animation);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			Intent intent=new Intent(this,GameMenuActivity.class);
			startActivity(intent);
			finish();
		}
		
		return true;
	}
}

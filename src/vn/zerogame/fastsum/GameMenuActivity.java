package vn.zerogame.fastsum;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GameMenuActivity extends Activity {
	
	MeteoView meteoView;

	ImageView threeButton;
	ImageView fourButton;
	ImageView unlimitButton;
	
	LinearLayout menuLayout;	
	Context mContext;

	
	Handler mHandler=new Handler()
	{
		public void handleMessage(android.os.Message msg) 
		{
			if(msg.what==0)
			{
				meteoView.invalidate();
			}
		};
	};
	
	Thread thread=new Thread()
	{
		public void run() 
		{
			while(true)
			{
				mHandler.sendEmptyMessage(0);
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext=this;
		setContentView(R.layout.game_menu2);
					
		meteoView=(MeteoView) findViewById(R.id.meteoView);		
		thread.start();
		
		
		threeButton=(ImageView)findViewById(R.id.threeSelectionButton);
		threeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				v.setAlpha(0.7f);
				GameState.GameMode=GameActivity.GAMEMODE_LIMITED;
				GameState.MaxSelected=3;
				Intent intent = new Intent(mContext, GameActivity.class);
				mContext.startActivity(intent);
				Activity activity = (Activity) mContext;
				activity.finish();
			}
		});
		
		fourButton=(ImageView)findViewById(R.id.fourSelectionButton);
		fourButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.setAlpha(0.7f);
				// TODO Auto-generated method stub
				GameState.GameMode=GameActivity.GAMEMODE_LIMITED;
				GameState.MaxSelected=4;
				Intent intent = new Intent(mContext, GameActivity.class);
				mContext.startActivity(intent);
				Activity activity = (Activity) mContext;
				activity.finish();
			}
		});
		
		unlimitButton=(ImageView) findViewById(R.id.noLimitSelectionButton);
		unlimitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.setAlpha(0.7f);
				// TODO Auto-generated method stub
				GameState.GameMode=GameActivity.GAMEMODE_UNLIMITED;				
				Intent intent = new Intent(mContext, GameActivity.class);
				mContext.startActivity(intent);
				Activity activity = (Activity) mContext;
				activity.finish();
			}
		});
		
		menuLayout=(LinearLayout) findViewById(R.id.menuLayout);
		Animation menuAnimation=AnimationUtils.loadAnimation(this, R.anim.menu_anim);
		menuLayout.startAnimation(menuAnimation);
		
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		meteoView.release();
	}
}

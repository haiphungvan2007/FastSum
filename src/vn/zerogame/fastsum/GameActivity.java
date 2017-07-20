package vn.zerogame.fastsum;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification.Style;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.SumPathEffect;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRouter.VolumeCallback;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GameActivity extends Activity implements OnClickListener {
	private Context mContext;
	private Boolean[] booleanArray = { false, false, false, false, false,
			false, false, false, false };
	int[] boardBitmap;
	private int[] randomNumber = new int[9];
	private Random random = new Random();

	private ProgressBar progressBar;

	private boolean isCalCulated = false;

	private ArrayList<Rect> srcRectList = new ArrayList<Rect>();
	private ArrayList<Rect> desRectList = new ArrayList<Rect>();
	private ArrayList<Rect> tileList = new ArrayList<Rect>();

	public static int GAMEMODE_LIMITED = 0;
	public static int GAMEMODE_UNLIMITED = 1;

	private int SelectedCounter = 0;
	private int CurrentSum = 0;

	private int FinalSum = 0;

	private long bonus = 0;

	private int[] imageViewIDList = { R.id.imageButton1, R.id.imageButton2,
			R.id.imageButton3, R.id.imageButton4, R.id.imageButton5,
			R.id.imageButton6, R.id.imageButton7, R.id.imageButton8,
			R.id.imageButton9 };

	private int[] tempImageViewID = { R.id.imageTemp1, R.id.imageTemp2,
			R.id.imageTemp3, R.id.imageTemp4, R.id.imageTemp5, R.id.imageTemp6,
			R.id.imageTemp7, R.id.imageTemp8, R.id.imageTemp9 };

	private ImageButton imageButton0, imageButton1, imageButton2, imageButton3,
			imageButton4, imageButton5, imageButton6, imageButton7,
			imageButton8;
	private ImageView correctImage;

	private TextView gameScoreText;
	private TextView stageScoreText;
	private TextView bonusText;

	private final int GAME_DURATION = 6000;
	private final int SLEEP_TIME = 30;
	private final int DURATION1 = 1000;
	private final int DURATION2 = 500;
	private final int DURATION3 = 250;

	private boolean isPause = false;
	private boolean isEndGame = false;

	TextView titleTextView1;
	TextView titleTextView2;
	TextView numberTextView;
	TextView sumTextView;
	TextView totalText;

	TextView highScoreText;
	TextView hightScoreTitleText;

	private long oldGameScore;
	private long highScore;

	private MediaPlayer StartGameSound = null;
	private MediaPlayer CorrectSound = null;
	private MediaPlayer WrongSound = null;
	private MediaPlayer ClickSound = null;

	private MediaPlayer scoringSound = null;

	private FrameLayout parentLayout;
	private boolean isBackgroundWhite = false;

	LinearLayout gameOverLayout;
	FrameLayout gameScoreLayout;
	FrameLayout colorFrame;
	FrameLayout colorBackFrame;

	ImageView starImage;

	private AudioManager audioManager;
	

	private final Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if ((!isPause) && (!isEndGame)) {
				if (msg.what == 0) {
					progressBar.setProgress(progressBar.getProgress()
							- SLEEP_TIME);

					if (progressBar.getProgress() <= 2) {
						progressBar.setProgress(0);
						if (CurrentSum != FinalSum) {
							EndGame(false);
						}
					}

				}
								

				else if (msg.what == 2) {
					if (isBackgroundWhite) {
						parentLayout.setBackgroundColor(Color.argb(153, 255, 0, 0));
						isBackgroundWhite = false;
					}

					else {
						parentLayout.setBackgroundColor(Color.argb(153, 255, 255, 255));
						isBackgroundWhite = true;
					}
				}

			}

			

			if (msg.what == 3) {
				long temp = oldGameScore + msg.arg1;
				totalText.setText("" + temp+"      ");

			}

			else if (msg.what == 4) {
				totalText.setText("" + GameState.Score+"      ");

				if (GameState.Score > highScore) {
					starImage.setVisibility(View.VISIBLE);
					Animation animation = AnimationUtils.loadAnimation(
							mContext, R.anim.star_anim);
					starImage.startAnimation(animation);
				}

			}

			else if (msg.what == 5) {
				if(msg.arg1==39)
				{				
					Intent intent = new Intent(mContext, GameActivity.class);
					mContext.startActivity(intent);
					Activity activity = (Activity) mContext;
					activity.finish();
				}
			}
			

		};
	};

	private Thread progressbarThread = new Thread() {
		public void run() {
			while (true) {
				mHandler.sendEmptyMessage(0);
				try {
					sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	};

	private Thread deathTimeThread = new Thread() {
		public void run() {
			while (true) {
				if (progressBar.getProgress() < 1500) {
					mHandler.sendEmptyMessage(2);
				}

				try {
					Thread.sleep(10);
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

		// TODO Auto-generated constructor stub
		setContentView(R.layout.game_layout);
		
		
		

		for (int i = 0; i < 9; i++) {
			booleanArray[i] = false;
		}
		mContext = this;
		GameState.LoadGame(this);
		oldGameScore = GameState.Score;
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		starImage = (ImageView) findViewById(R.id.starImage);
		starImage.setVisibility(View.GONE);

		colorFrame = (FrameLayout) findViewById(R.id.colorFrame);
		colorFrame.setVisibility(View.GONE);
		colorFrame.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		colorFrame.setAlpha(0.7f);

		colorBackFrame = (FrameLayout) findViewById(R.id.colorBackFrame);
		colorBackFrame.setVisibility(View.GONE);

		parentLayout = (FrameLayout) findViewById(R.id.parentLayout);
		gameOverLayout = (LinearLayout) findViewById(R.id.gameoverLayout);
		gameOverLayout.setVisibility(View.GONE);

		gameScoreLayout = (FrameLayout) findViewById(R.id.gameScoreLayout);
		gameScoreLayout.setVisibility(View.GONE);

		ImageView replayImageView = (ImageView) findViewById(R.id.replayButton);
		replayImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				v.setAlpha(0.5f);
				Intent intent = new Intent(mContext, GameActivity.class);
				mContext.startActivity(intent);
				Activity activity = (Activity) mContext;
				activity.finish();

			}
		});

		ImageView homeImageView = (ImageView) findViewById(R.id.homeButton);
		homeImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				v.setAlpha(0.5f);
				
				Intent intent=new Intent(mContext, GameMenuActivity.class);				
				mContext.startActivity(intent);
				Activity activity = (Activity) mContext;
				activity.finish();
				
				

			}
		});

		StartGameSound = MediaPlayer.create(mContext, R.raw.start_game);
		WrongSound = MediaPlayer.create(mContext, R.raw.failed);
		WrongSound
				.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

					@Override
					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub
						colorFrame.setAlpha(0.8f);
						colorFrame.setVisibility(View.VISIBLE);
						gameOverLayout.setVisibility(View.VISIBLE);

					}
				});

		scoringSound = MediaPlayer.create(mContext, R.raw.scoring);

		bonusText = (TextView) findViewById(R.id.bonusText);
		stageScoreText = (TextView) findViewById(R.id.statePointText);
		gameScoreText = (TextView) findViewById(R.id.gamePointText);
		totalText = (TextView) findViewById(R.id.totalText);

		CorrectSound = MediaPlayer.create(mContext, R.raw.finish);
		CorrectSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

					@Override
					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub
						colorFrame.setVisibility(View.VISIBLE);
						colorFrame.setAlpha(0.0f);

						bonusText.setText(String.valueOf(bonus));
						gameScoreLayout.setVisibility(View.VISIBLE);

						gameScoreText.setText(String.valueOf(oldGameScore)+"      ");
						bonusText.setText(String.valueOf(bonus)+"      ");
						stageScoreText.setText(String
								.valueOf(SelectedCounter * 100)+"      ");
						totalText.setText("" + oldGameScore+"      ");

						scoringSound.start();

						Thread animThread = new Thread() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								int timeCounter = 0;

								while (timeCounter < (GameState.Score - oldGameScore)) {
									if(!isPause)
									{
										timeCounter++;
										Message msg = new Message();
										msg.what = 3;
										msg.arg1 = timeCounter;
										mHandler.sendMessage(msg);
									}
	
									try {
										Thread.sleep(2);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
										
								}
								mHandler.sendEmptyMessage(4);
								int sleepCounter=0;
								while(sleepCounter<=40)
								{
									if(!isPause)
									{
										sleepCounter++;
										
										Message newMsg=new  Message();
										newMsg.what=5;
										newMsg.arg1=sleepCounter;
										mHandler.sendMessage(newMsg);
										
										try {
											Thread.sleep(50);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}
								
								

							}
						};

						animThread.start();

					}
				});
		ClickSound = MediaPlayer.create(mContext, R.raw.btn_clk);

		correctImage = (ImageView) findViewById(R.id.correctImage);
		correctImage.setVisibility(View.GONE);
		boolean isStartGame = this.getIntent().getBooleanExtra("StartGame",
				false);
		if (true) {
			StartGameSound.start();
		}

		isEndGame = false;

		titleTextView1 = (TextView) findViewById(R.id.testTitle1);
		titleTextView2 = (TextView) findViewById(R.id.testTitle2);
		numberTextView = (TextView) findViewById(R.id.selectCounterTextView);
		sumTextView = (TextView) findViewById(R.id.sumTextView);

		//titleTextView1.setTypeface(GameFontManager.GetBuxtonSketchFont(this));
		//titleTextView2.setTypeface(GameFontManager.GetBuxtonSketchFont(this));
		//numberTextView.setTypeface(GameFontManager.GetJingJingFont(this));
		//sumTextView.setTypeface(GameFontManager.GetJingJingFont(this));

		TextView levelText = (TextView) findViewById(R.id.levelText);
		//levelText.setTypeface(GameFontManager.GetBuxtonSketchFont(this));
		levelText.setText("Level " + GameState.Level);

		highScoreText = (TextView) findViewById(R.id.highScoreText);
		hightScoreTitleText = (TextView) findViewById(R.id.hightScoreTitleText);
		//highScoreText.setTypeface(GameFontManager.GetBuxtonSketchFont(this));
		//hightScoreTitleText.setTypeface(GameFontManager.GetBuxtonSketchFont(this));
		hightScoreTitleText.setText("High Score");
		if (GameState.GameMode == GameActivity.GAMEMODE_LIMITED) {
			highScore = HighScoreManager.GetLimitedScore(GameState.MaxSelected);
			highScoreText.setText(String.valueOf(HighScoreManager
					.GetLimitedScore(GameState.MaxSelected)) + "  ");
		} else {
			highScore = HighScoreManager.GetUnlimitedScore();
			highScoreText.setText(String.valueOf(HighScoreManager
					.GetUnlimitedScore()) + "  ");
		}

		TextView yourScoreText = (TextView) findViewById(R.id.yourScoreText);
		TextView yourScoreTitleText = (TextView) findViewById(R.id.yourScoreTitleText);
		//yourScoreText.setTypeface(GameFontManager.GetBuxtonSketchFont(this));
		//yourScoreTitleText.setTypeface(GameFontManager.GetBuxtonSketchFont(this));
		yourScoreText.setText("  " + String.valueOf(GameState.Score));

		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		progressBar.setMax(GAME_DURATION);
		progressBar.setProgress(GAME_DURATION);
		boardBitmap = BitmapLoader.getBitmapBoard(mContext);

		CurrentSum = 0;
		RandomBoard();
		RandomSum();

		for (int i = 0; i < 9; i++) {
			ImageView imageView = (ImageView) findViewById(tempImageViewID[i]);
			imageView.setVisibility(View.INVISIBLE);
		}

	}

	private void RandomSum() {
		TextView counterTextView = (TextView) findViewById(R.id.selectCounterTextView);
		TextView sumTextView = (TextView) findViewById(R.id.sumTextView);

		ArrayList<Integer> numberArray = new ArrayList<Integer>();
		numberArray.clear();
		for (int i = 0; i < 9; i++) {
			numberArray.add(this.randomNumber[i]);
		}

		if (GameState.GameMode == GAMEMODE_LIMITED) {
			int tempSum = 0;
			for (int i = 0; i < GameState.MaxSelected; i++) {
				int index = random.nextInt(numberArray.size());
				tempSum += (numberArray.get(index) + 1);
				numberArray.remove(index);
			}
			this.FinalSum = tempSum;

			counterTextView.setText("" + GameState.MaxSelected + " ");

		}

		else {
			int tempSum = 0;
			for (int i = 0; i < 5; i++) {
				int index = random.nextInt(numberArray.size());
				tempSum += numberArray.get(index);
				numberArray.remove(index);
			}
			this.FinalSum = tempSum;
			counterTextView.setText(" ");
		}

		sumTextView.setText(" " + FinalSum);
	}

	private void RandomBoard() {
		ArrayList<Integer> initBoard = new ArrayList<Integer>();
		initBoard.add(0);
		initBoard.add(1);
		initBoard.add(2);
		initBoard.add(3);
		initBoard.add(4);
		initBoard.add(5);
		initBoard.add(6);
		initBoard.add(7);
		initBoard.add(8);

		int i = 0;
		while (initBoard.size() != 0) {
			int index = random.nextInt(initBoard.size());
			randomNumber[i] = initBoard.get(index);
			initBoard.remove(index);
			i++;

		}

		imageButton0 = (ImageButton) findViewById(imageViewIDList[0]);
		imageButton0.setImageResource(boardBitmap[randomNumber[0]]);
		imageButton0.setTag(0);
		imageButton0.setOnClickListener(this);

		imageButton1 = (ImageButton) findViewById(imageViewIDList[1]);
		imageButton1.setImageResource(boardBitmap[randomNumber[1]]);
		imageButton1.setTag(1);
		imageButton1.setOnClickListener(this);

		imageButton2 = (ImageButton) findViewById(imageViewIDList[2]);
		imageButton2.setImageResource(boardBitmap[randomNumber[2]]);
		imageButton2.setTag(2);
		imageButton2.setOnClickListener(this);

		imageButton3 = (ImageButton) findViewById(imageViewIDList[3]);
		imageButton3.setImageResource(boardBitmap[randomNumber[3]]);
		imageButton3.setTag(3);
		imageButton3.setOnClickListener(this);

		imageButton4 = (ImageButton) findViewById(imageViewIDList[4]);
		imageButton4.setImageResource(boardBitmap[randomNumber[4]]);
		imageButton4.setTag(4);
		imageButton4.setOnClickListener(this);

		imageButton5 = (ImageButton) findViewById(imageViewIDList[5]);
		imageButton5.setImageResource(boardBitmap[randomNumber[5]]);
		imageButton5.setTag(5);
		imageButton5.setOnClickListener(this);

		imageButton6 = (ImageButton) findViewById(imageViewIDList[6]);
		imageButton6.setImageResource(boardBitmap[randomNumber[6]]);
		imageButton6.setTag(6);
		imageButton6.setOnClickListener(this);

		imageButton7 = (ImageButton) findViewById(imageViewIDList[7]);
		imageButton7.setImageResource(boardBitmap[randomNumber[7]]);
		imageButton7.setTag(7);
		imageButton7.setOnClickListener(this);

		imageButton8 = (ImageButton) findViewById(imageViewIDList[8]);
		imageButton8.setImageResource(boardBitmap[randomNumber[8]]);
		imageButton8.setTag(8);
		imageButton8.setOnClickListener(this);

		imageButton0.setSoundEffectsEnabled(false);
		imageButton1.setSoundEffectsEnabled(false);
		imageButton2.setSoundEffectsEnabled(false);
		imageButton3.setSoundEffectsEnabled(false);
		imageButton4.setSoundEffectsEnabled(false);
		imageButton5.setSoundEffectsEnabled(false);
		imageButton6.setSoundEffectsEnabled(false);
		imageButton7.setSoundEffectsEnabled(false);
		imageButton8.setSoundEffectsEnabled(false);

		progressbarThread.start();
		deathTimeThread.start();
	}

	private void EndGame(boolean isWon) {
		isEndGame = true;
		parentLayout.setBackgroundColor(Color.WHITE);

		if (isWon) {
			correctImage.setVisibility(View.VISIBLE);
			correctImage.setImageResource(R.drawable.correct);

			bonus = progressBar.getProgress() * 100 * SelectedCounter
					/ progressBar.getMax();

			GameState.Level++;
			GameState.Score += ((SelectedCounter * 100) + bonus);

			CorrectSound.start();
			Animation correctAnimation = AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.correct_anim);
			correctImage.startAnimation(correctAnimation);

		}

		else {
			correctImage.setVisibility(View.VISIBLE);
			correctImage.setImageResource(R.drawable.wrong);
			GameState.ResetGame();

			WrongSound.start();

			Animation correctAnimation = AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.correct_anim);
			correctImage.startAnimation(correctAnimation);
		}
		HighScoreManager.UpdateScore(GameState.Score, GameState.GameMode,
				GameState.MaxSelected);
		GameState.SaveGame(this);
	}

	public void onClick(final View v) {
		if (!isEndGame) {
			final int currentButtonIndex = (Integer) v.getTag();
			if (!booleanArray[currentButtonIndex]) {
				booleanArray[currentButtonIndex] = true;

				ClickSound.start();
				// TODO Auto-generated method stub

				v.setVisibility(View.INVISIBLE);

				// load the animation
				Animation zoomOutAnimation = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.star_anim);
				zoomOutAnimation
						.setAnimationListener(new Animation.AnimationListener() {

							public void onAnimationStart(Animation animation) {
								// TODO Auto-generated method stub

							}

							public void onAnimationRepeat(Animation animation) {
								// TODO Auto-generated method stub

							}

							public void onAnimationEnd(Animation animation) {
								// TODO Auto-generated method stub
							}
						});

				ImageView tempImageButton = (ImageView) findViewById(tempImageViewID[SelectedCounter]);
				tempImageButton.setVisibility(View.VISIBLE);
				tempImageButton
						.setImageResource(boardBitmap[randomNumber[currentButtonIndex]]);
				Animation zoomInAnimation = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.zoom_in);
				tempImageButton.startAnimation(zoomInAnimation);

				SelectedCounter++;
				CurrentSum += (randomNumber[currentButtonIndex] + 1);

				if (CurrentSum > FinalSum) {
					EndGame(false);
					return;
				}

				if (GameState.GameMode == GAMEMODE_LIMITED) {
					if (SelectedCounter == GameState.MaxSelected) {
						if (CurrentSum == FinalSum) {
							EndGame(true);
						}

						else {
							EndGame(false);
						}
					}
				}

				else {

					if (SelectedCounter < 9) {
						if (CurrentSum == FinalSum) {
							EndGame(true);
						}
					}

					else {
						if (CurrentSum != FinalSum) {
							EndGame(false);
						}
					}

				}

			}
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isPause = true;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		isPause = false;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		StartGameSound.release();
		CorrectSound.release();
		WrongSound.release();
		ClickSound.release();
		scoringSound.release();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			isPause = true;
			colorBackFrame.setVisibility(View.VISIBLE);
			final Dialog exitDialog = new Dialog(this,
					R.style.ThemeDialogCustom);
			exitDialog.setContentView(R.layout.exit_confirm_dlg);

			ImageView cancelButton = (ImageView) exitDialog
					.findViewById(R.id.cancelButton);
			cancelButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					v.setAlpha(0.5f);
					exitDialog.dismiss();
					isPause = false;
				}
			});

			ImageView okButton = (ImageView) exitDialog
					.findViewById(R.id.okButton);
			okButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					v.setAlpha(0.5f);
					GameState.ResetGame();
					
					Intent intent= new Intent(mContext, GameMenuActivity.class);
					mContext.startActivity(intent);
					finish();
				}
			});

			exitDialog
					.setOnCancelListener(new DialogInterface.OnCancelListener() {

						@Override
						public void onCancel(DialogInterface dialog) {
							// TODO Auto-generated method stub
							isPause = false;
							colorBackFrame.setVisibility(View.GONE);
						}
					});

			exitDialog
					.setOnDismissListener(new DialogInterface.OnDismissListener() {

						@Override
						public void onDismiss(DialogInterface dialog) {
							// TODO Auto-generated method stub
							isPause = false;
							colorBackFrame.setVisibility(View.GONE);
						}
					});

			exitDialog.show();
		}

		else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
		}

		return true;
	}

}

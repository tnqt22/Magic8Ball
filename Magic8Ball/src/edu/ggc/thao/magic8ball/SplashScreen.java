package edu.ggc.thao.magic8ball;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends Activity{
	
	private long ms=0;
	private long splashTime=4000;
	private boolean splashActive = true;
	private boolean paused=false;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Thread mythread = new Thread() {
			public void run() {
				try {
					while(splashActive && ms < splashTime) {
						if(!paused)
							ms=ms+100;
						sleep(100);
					}
				} catch(Exception e) {}
				finally {
					Intent intent = new Intent(SplashScreen.this, MainActivity.class);
					startActivity(intent);
				}
			}
		};
		mythread.start();
	}

}

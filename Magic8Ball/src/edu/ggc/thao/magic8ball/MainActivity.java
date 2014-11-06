package edu.ggc.thao.magic8ball;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity implements SensorEventListener, OnInitListener{

	private TextToSpeech myTTS;
	private List<String> phrases = new ArrayList<String>();
	boolean isDown = false;
	
	protected void onCreate(Bundle savedInstanceState) {
        
		phrases.add("It is certain");
		phrases.add("It is decidedly so");
		phrases.add("Without a doubt");
		phrases.add("Yes definitely");
		phrases.add("You may rely on it");
		phrases.add("As I see it, yes");
		phrases.add("Most likely");
		phrases.add("Outlook good");
		phrases.add("Yes");
		phrases.add("Signs point to yes");
		phrases.add("Reply hazy try again");
		phrases.add("Ask again later");
		phrases.add("Better not tell you now");
		phrases.add("Cannot predict now");
		phrases.add("Concentrate and ask again");
		phrases.add("Don't count on it");
		phrases.add("My reply is no");
		phrases.add("My sources say no");
		phrases.add("Outlook not so good");
		phrases.add("Very doubtful");
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,
        		sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
        		SensorManager.SENSOR_DELAY_UI);
        
        Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkIntent, 1);
        
        
    }
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		float z = event.values[2];
		if(z > 9 && z < 10) {
			isDown = false;
		} else if(z >-10 && z < -9) {
			isDown = true;
			
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 1) {
			if(resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				myTTS = new TextToSpeech(this, this);
				myTTS.setLanguage(Locale.US);
			} else {
				// TTS data not yet load, try to install it
				Intent ttsLoadIntent = new Intent();
				ttsLoadIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(ttsLoadIntent);
			}
		}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		
		if(status == TextToSpeech.SUCCESS) {
			if(isDown) {
				myTTS.speak("I am ready to answer your question", TextToSpeech.QUEUE_FLUSH, null);
			}
			else {
				int n = (int)(Math.random() * phrases.size());
				myTTS.speak(phrases.get(n), TextToSpeech.QUEUE_FLUSH, null);
			}
		}
		else if(status == TextToSpeech.ERROR) {
			myTTS.shutdown();
		}

	}

}

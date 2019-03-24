package com.example.android.assginment7.clapapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Sensor proximitySensor;
    SensorManager sensorManager;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(mProximityListener, proximitySensor, 2*1000*1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(mProximityListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private SensorEventListener mProximityListener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            float dist = event.values[0];

            if(dist < 2f) {
                mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.clapsound);
                mediaPlayer.start();
            }else {
                if(mediaPlayer != null) {
                    mediaPlayer.stop();
                }
            }
        }
    };
}

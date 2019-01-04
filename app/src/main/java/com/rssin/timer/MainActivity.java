package com.rssin.timer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar controllerSeekBar;
    TextView counterTextView;
    Button stopButton;
    Boolean counterActive=false;
    CountDownTimer countDownTimer;
    public void resetTimer(){
        countDownTimer.cancel();
      controllerSeekBar.setEnabled(true);
      counterActive=false;
      counterTextView.setText("0:30");
      //stopButton.setText("Go");
    }
    public void updateTimer(int secondsLeft){

        int minutes=(int) secondsLeft/60;
        int seconds=secondsLeft-minutes*60;
        String secStr=Integer.toString(seconds);
        if(seconds<=9)
            secStr="0"+secStr;
        counterTextView.setText(Integer.toString(minutes) +":"+ secStr);
    }
    public void counterTimer(View view){
        if(counterActive==false) {
            counterActive=true;
            controllerSeekBar.setEnabled(false);
            //stopButton.setText("Stop");
            countDownTimer=new CountDownTimer(controllerSeekBar.getProgress() * 1000 + 200, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    resetTimer();
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bird);
                    mediaPlayer.start();
                }
            }.start();
        }else{
            resetTimer();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button stopButton=(Button)findViewById(R.id.goButton);
        counterTextView= (TextView)findViewById(R.id.counterTextView);
        controllerSeekBar=(SeekBar)findViewById(R.id.controllerSeekBar);
        controllerSeekBar.setMax(600);
        controllerSeekBar.setProgress(30);


        controllerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
              updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}

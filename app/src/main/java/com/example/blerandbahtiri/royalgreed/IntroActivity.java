package com.example.blerandbahtiri.royalgreed;
/**
 * ROYAL DICE . BLERAND BAHTIRI
 * Created by blerandbahtiri on 16-02-06.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ViewAnimator;

public class IntroActivity extends AppCompatActivity {
    private static final String TAG = "IntroActivity";
    Context mContext;
    private Button mIntroPlay;
    private ImageButton mBack;
    private ImageButton mPlay;
    private EditText mPlayer;
    private ViewAnimator mViewAnimator;
    private Animation fade_in;
    private Animation fade_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        mContext = this;

        //ViewAnimator
        mViewAnimator = (ViewAnimator)findViewById(R.id.viewAnimator);
        fade_in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        mViewAnimator.setInAnimation(fade_in);
        mViewAnimator.setOutAnimation(fade_out);

        // Tillbaka-knapp
        mBack = (ImageButton) findViewById(R.id.back_button);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewAnimator.showPrevious();
            }
        });

        mIntroPlay = (Button) findViewById(R.id.playBtn);
        mIntroPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewAnimator.showNext();
            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
        mPlayer = (EditText) findViewById(R.id.editText2);
        mPlayer.setHint(R.string.name);
        mPlay = (ImageButton) findViewById(R.id.play);
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstPlayer = mPlayer.getText().toString();
                if (firstPlayer.length() == 0) {
                    mPlayer.requestFocus();
                    mPlayer.setError("Please enter your name");
                } else {
                    PlayerData.setUsername(mContext, "username_key", firstPlayer);
                    PlayerData.setScore(mContext, "score_key", 0);
                    Log.d(TAG, "Starting GameActivity");
                    Intent i = new Intent(IntroActivity.this, GameActivity.class);
                    startActivity(i);
                    mViewAnimator.showPrevious();
                }
            }
        });

    }


}

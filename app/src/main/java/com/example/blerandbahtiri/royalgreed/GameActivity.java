package com.example.blerandbahtiri.royalgreed;
/**
 * ROYAL DICE . BLERAND BAHTIRI
 * Created by blerandbahtiri on 16-02-06.
 * GameActitivty is the actitivty where the game is
 * played.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    Context mContext;
    private static final String TAG = "GameActivity";
    private static final String KEY_THROWS = "nrOfThrows";
    private static final String KEY_ROUNDS = "nrOfRounds";
    private static final String KEY_LATEST = "latestScore";
    private static final String KEY_TOTAL = "totalScore";
    private static final String KEY_DICES = "dices";

    private TextView mTxtPlayer;
    private TextView mTxtPlayerScore;
    private TextView mTxtRoundScore;
    private TextView mTxtNrOfRounds;
    private Button mThrowButton;
    private Button mSaveButton;
    private Random mRoller;
    private DiceHandler mDiceHandler;
    ArrayList<Integer> mChosenDices;
    ArrayList<Integer> mAllDices;
    private ImageButton mImageButtons[];

    private int mCountRounds = 0;
    private int mNrOfThrows = 0;

    private final int mNrOfDice = 6;
    private final int mMinScore = 300;
    private final int winScore = 10000;

    private int mLatestThrowScore;
    private int mRoundScore;
    private int mTotalScore;

    private boolean[] flagToggleButtons = {
            false, false, false,
            false, false, false,
    };
    private static final int[] IMAGEBUTTON_IDS = {
            R.id.imageButton1, R.id.imageButton2,
            R.id.imageButton3, R.id.imageButton4,
            R.id.imageButton5, R.id.imageButton6,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        mDiceHandler = new DiceHandler();
        mContext = this;
        mChosenDices = new ArrayList<Integer>();
        mAllDices = new ArrayList<Integer>();
        mRoller = new Random();
        final Toast toast_new_round = Toast.makeText(getApplicationContext(),
                "No points, new round", Toast.LENGTH_SHORT);
        final Toast toast_no_registered = Toast.makeText(getApplicationContext(),
                "No points where registered, new round", Toast.LENGTH_SHORT);
        toast_new_round.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast_no_registered.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);


        //TEXTVIEWS
        mTxtPlayer = (TextView) findViewById(R.id.textSingelPlayer);
        mTxtPlayer.setText(PlayerData.getUsername(mContext, "username_key"));
        mTxtPlayerScore = (TextView) findViewById(R.id.textScore);
        mTxtRoundScore = (TextView) findViewById(R.id.points);
        mTxtNrOfRounds = (TextView) findViewById(R.id.turnpoints);

        //BUTTONS & IMAGEBUTTONS
        mImageButtons = new ImageButton[mNrOfDice];
        for (int i = 0; i < mNrOfDice; i++) {
            mImageButtons[i] = (ImageButton) findViewById(IMAGEBUTTON_IDS[i]);
        }
        mThrowButton = (Button) findViewById(R.id.throw_btn);
        mSaveButton = (Button) findViewById(R.id.save_btn);
        mSaveButton.setEnabled(false);
        mSaveButton.setAlpha(0.7f);


        if (savedInstanceState != null) {
            mNrOfThrows = savedInstanceState.getInt(KEY_THROWS,0);
            mCountRounds = savedInstanceState.getInt(KEY_ROUNDS, 0);
            mTxtNrOfRounds.setText(String.valueOf(mCountRounds));

            mRoundScore = savedInstanceState.getInt(KEY_LATEST, 0);
            mTxtRoundScore.setText(String.valueOf(mRoundScore));

            mTotalScore = savedInstanceState.getInt(KEY_TOTAL);
            mTxtPlayerScore.setText(String.valueOf(mTotalScore));

            mAllDices = savedInstanceState.getIntegerArrayList(KEY_DICES);
            for (int i = 0; i < mAllDices.size(); i++) {
                animateDice(mAllDices.get(i), mImageButtons[i]);
            }
            if(mNrOfThrows == 0){
                if(mRoundScore >= mMinScore){
                    mSaveButton.setEnabled(true);mSaveButton.setAlpha(1f);
                } else{
                    for(int i = 0; i<mImageButtons.length; i++)
                    {mImageButtons[i].setEnabled(false); mImageButtons[i].setAlpha(0.7f);}
                }

            }else{
                if(mRoundScore != 0){
                    mSaveButton.setEnabled(true);mSaveButton.setAlpha(1f);
                } else{
                    for(int i = 0; i<mImageButtons.length; i++)
                    {mImageButtons[i].setEnabled(false); mImageButtons[i].setAlpha(0.7f);}
                }
            }
        }

        mThrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAllDices.clear();
                for(int i = 0; i<mImageButtons.length; i++)
                {mImageButtons[i].setEnabled(true); mImageButtons[i].setAlpha(1f);}
                switch (mNrOfThrows){
                    case 0:
                        for(int i = 0; i<mImageButtons.length; i++)
                        {flagToggleButtons[i]  = false;}
                        mChosenDices.clear();
                        mNrOfThrows++;
                        mLatestThrowScore = 0;
                        rollAllDices(mAllDices);
                        mLatestThrowScore = mDiceHandler.countPoints(mAllDices, mLatestThrowScore);

                        if (mLatestThrowScore < mMinScore) {
                            for(int i = 0; i<mImageButtons.length; i++)
                            {mImageButtons[i].setEnabled(false); mImageButtons[i].setAlpha(0.7f);}
                            mSaveButton.setEnabled(false);mSaveButton.setAlpha(0.7f);
                            toast_new_round.show();
                            mLatestThrowScore = 0;
                            mNrOfThrows = 0;
                            mCountRounds++;

                        }else{
                            mCountRounds++;
                            mRoundScore += mLatestThrowScore;
                            mSaveButton.setEnabled(true);mSaveButton.setAlpha(1f);
                            mLatestThrowScore = 0;
                        }
                        mTxtRoundScore.setText(String.valueOf(mRoundScore));
                        mTxtNrOfRounds.setText(String.valueOf(mCountRounds));
                        break;
                    default:
                        switch (mChosenDices.size()){
                            case 0:
                                for(int i = 0; i<mImageButtons.length; i++)
                                {mImageButtons[i].setEnabled(false); mImageButtons[i].setAlpha(0.7f);}
                                mSaveButton.setEnabled(false);mSaveButton.setAlpha(0.7f);
                                toast_no_registered.show();
                                mRoundScore = 0;
                                mChosenDices.clear();
                                mNrOfThrows = 0;
                                break;
                            case 6:
                                rollAllDices(mAllDices);
                                for(int i = 0; i<mImageButtons.length; i++)
                                {flagToggleButtons[i]  = false;}
                                mLatestThrowScore = mDiceHandler.countPoints(mAllDices, mLatestThrowScore);
                                if (mLatestThrowScore == 0) {
                                    for(int i = 0; i<mImageButtons.length; i++)
                                    {mImageButtons[i].setEnabled(false); mImageButtons[i].setAlpha(0.7f);}
                                    mSaveButton.setEnabled(false);mSaveButton.setAlpha(0.7f);
                                    toast_new_round.show();
                                    mNrOfThrows = 0;
                                    mRoundScore = 0;
                                }else{
                                    mRoundScore += mLatestThrowScore;
                                    mSaveButton.setEnabled(true);mSaveButton.setAlpha(1f);
                                    mLatestThrowScore = 0;
                                }
                                break;
                            default:
                                rollDices(mAllDices);
                                mLatestThrowScore = mDiceHandler.countPoints(mAllDices, mLatestThrowScore);
                                if (mLatestThrowScore == 0) {
                                    for(int i = 0; i<mImageButtons.length; i++)
                                    {mImageButtons[i].setEnabled(false); mImageButtons[i].setAlpha(0.7f);}
                                    mSaveButton.setEnabled(false);mSaveButton.setAlpha(0.7f);
                                    toast_new_round.show();
                                    mNrOfThrows = 0;
                                    mRoundScore = 0;
                                }else{
                                    mRoundScore += mLatestThrowScore;
                                    mSaveButton.setEnabled(true);mSaveButton.setAlpha(1f);
                                    mLatestThrowScore = 0;
                                }
                                break;
                        }
                        mTxtRoundScore.setText(String.valueOf(mRoundScore));
                        mTxtNrOfRounds.setText(String.valueOf(mCountRounds));
                        break;
                }
            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i<mImageButtons.length; i++)
                {mImageButtons[i].setEnabled(false); mImageButtons[i].setAlpha(0.7f);}
                mSaveButton.setEnabled(false);mSaveButton.setAlpha(0.7f);
                mTotalScore += mRoundScore;
                mTxtPlayerScore.setText(String.valueOf(mTotalScore));
                PlayerData.setScore(mContext, "score_key", mTotalScore);
                PlayerData.setScore(mContext,"throw_key", mCountRounds);

                mNrOfThrows = 0;
                mRoundScore = 0;
                mTxtRoundScore.setText(String.valueOf(mRoundScore));
                mTxtNrOfRounds.setText(String.valueOf(mCountRounds));
                if(mTotalScore >= winScore){
                    Log.d(TAG, "Starting FinalActivity");
                    Intent i = new Intent(GameActivity.this, FinalActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_THROWS,mNrOfThrows);
        savedInstanceState.putInt(KEY_ROUNDS, mCountRounds);
        savedInstanceState.putIntegerArrayList(KEY_DICES, mAllDices);
        savedInstanceState.putInt(KEY_LATEST,mRoundScore);
        savedInstanceState.putInt(KEY_TOTAL, mTotalScore);
    }

    public void setImageButtonsOnClick(View v) {
        for (int i = 0; i < mAllDices.size(); i++) {
            if (v == mImageButtons[i]) {
                if (flagToggleButtons[i]) {
                    mChosenDices.remove(mAllDices.get(i));
                    v.setAlpha(1f);
                    flagToggleButtons[i] = false;//TÄRNING AVRYCKT
                } else {
                    mChosenDices.add(mAllDices.get(i));
                    v.setAlpha(0.7f);
                    flagToggleButtons[i] = true;//TÄRNING ITRYCKT
                }
            }
        }
    }

    public void rollDices(List<Integer> dices){
        for (int i = 0; i < 6; i++) {
            if(flagToggleButtons[i]){
                dices.add(i, 0);
                mImageButtons[i].setEnabled(false);
            }
            else {
                dices.add(mRoller.nextInt(6) + 1);
                mImageButtons[i].setEnabled(true);
            }
            animateDice(dices.get(i), mImageButtons[i]);
        }
    }

    public void rollAllDices(List<Integer> dices){
        for (int i = 0; i < 6; i++) {
            dices.add(mRoller.nextInt(6) + 1);
            mImageButtons[i].setEnabled(true);
            mImageButtons[i].setAlpha(1f);
            animateDice(dices.get(i), mImageButtons[i]);
        }
    }

    private void animateDice(int number, View v){

        switch(number){
            case 1:
                v.setBackgroundResource(R.drawable.t1);
                break;
            case 2:
                v.setBackgroundResource(R.drawable.t2);
                break;
            case 3:
                v.setBackgroundResource(R.drawable.t3);
                break;
            case 4:
                v.setBackgroundResource(R.drawable.t4);
                break;
            case 5:
                v.setBackgroundResource(R.drawable.t5);
                break;
            case 6:
                v.setBackgroundResource(R.drawable.t6);
                break;
            case 0:
                v.setBackgroundResource(R.drawable.td);
                break;
        }

    }


}

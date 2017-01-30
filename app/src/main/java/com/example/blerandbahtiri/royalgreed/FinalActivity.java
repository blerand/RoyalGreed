package com.example.blerandbahtiri.royalgreed;
/**
 * ROYAL DICE . BLERAND BAHTIRI
 * Created by blerandbahtiri on 16-02-06.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class FinalActivity extends AppCompatActivity {
    private static final String TAG = "FinalActivity";
    Context mContext;
    private TextView mtxtName;
    private TextView mThrows;
    private TextView mtxtThrow;
    private TextView mPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        mContext= this;
        mtxtName = (TextView) findViewById(R.id.textSingelPlayer);
        mtxtThrow = (TextView) findViewById(R.id.textScore);
        mPoints   = (TextView) findViewById(R.id.points);
        mThrows   = (TextView) findViewById(R.id.throows);

        mtxtName.setText(PlayerData.getUsername(mContext, "username_key"));
        mThrows.setText(String.valueOf(PlayerData.getScore(mContext, "throw_key")));
        mPoints.setText(String.valueOf(PlayerData.getScore(mContext, "score_key")));
        mtxtName.setText("Congratulations " + PlayerData.getUsername(mContext, "username_key"));


    }

}

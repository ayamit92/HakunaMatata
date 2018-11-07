package com.balli.kbcreturns;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MotivationalNonCloudActivity extends AppCompatActivity {

//    MediaPlayer mp;
//    private InterstitialAd mInterstitialAd;
//    private static boolean interstitialad;
//    private AdView mAdView;
//
//    public void playmusic(View view) {
//        Log.i("TAG123", String.valueOf(mInterstitialAd.isLoaded()));
//        if (interstitialad == false) {
//
//            if (mInterstitialAd.isLoaded()) {
//                mInterstitialAd.show();
//                interstitialad = true;
//            } else {
//                Log.i("TAG", "The interstitial wasn't loaded yet.");
//            }
//        }
//
//        switch (view.getId()) {
//
//            case R.id.buttonTuChal:
//                stopPlaying();
//                mp = MediaPlayer.create(MotivationalNonCloudActivity.this, R.raw.tuchal);
//                mp.start();
//                Toast.makeText(getApplicationContext(), "Starting audio", Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.buttonKoshish:
//                stopPlaying();
//                mp = MediaPlayer.create(MotivationalNonCloudActivity.this, R.raw.koshish);
//                mp.start();
//                Toast.makeText(getApplicationContext(), "Starting audio", Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.buttonBelieve:
//                stopPlaying();
//                mp = MediaPlayer.create(MotivationalNonCloudActivity.this, R.raw.believe);
//                mp.start();
//                Toast.makeText(getApplicationContext(), "Starting audio", Toast.LENGTH_SHORT).show();
//                break;
//
//            default:
//                break;
//
//        }
//    }
//
//    private void stopPlaying() {
//        if (mp != null) {
//            mp.stop();
//            mp.release();
//            mp = null;
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_motivational_non_cloud);
//        interstitialad = false;
//
//        mAdView = (AdView) findViewById(R.id.adView4);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
////        production ad
////        ads:adUnitId="ca-app-pub-9621990942730139/9106367165"
////        test ad
////        ads:adUnitId="ca-app-pub-3940256099942544/6300978111"
//
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());
////        production ad
////        mInterstitialAd.setAdUnitId("ca-app-pub-9621990942730139/9281217864");
////        test add
////        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
//    }
//
//    @Override
//    public void onBackPressed() {
//        stopPlaying();
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(intent);
//        return;
//    }
}

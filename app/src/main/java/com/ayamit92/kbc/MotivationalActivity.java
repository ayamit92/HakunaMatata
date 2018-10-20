package com.ayamit92.kbc;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class MotivationalActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

//    private MediaPlayer mMediaplayer;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_motivational);
//        mMediaplayer = new MediaPlayer();
//        mMediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        fetchAudioUrlFromFirebase();
//    }
//
//    private void fetchAudioUrlFromFirebase() {
//        final FirebaseStorage storage = FirebaseStorage.getInstance();
//        // Create a storage reference from our app
//        StorageReference storageRef = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/kbc2018-360a4.appspot.com/o/amitabh_bachchans_best_motivational_shayari_tu_chal.mp3?alt=media&token=c94d23f4-8c27-4e9a-947d-898bf7466b1e");
//        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                try {
//                    // Download url of file
//                    final String url = uri.toString();
//                    mMediaplayer.setDataSource(url);
//                    // wait for media player to get prepare
//                    mMediaplayer.setOnPreparedListener(MotivationalActivity.this);
//                    mMediaplayer.prepareAsync();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.i("TAG", e.getMessage());
//                    }
//                });
//
//    }
//
    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}

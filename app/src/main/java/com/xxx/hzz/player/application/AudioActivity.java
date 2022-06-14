package com.xxx.hzz.player.application;

import android.Manifest;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class AudioActivity extends AppCompatActivity {
    private static String TAG = "AudioActivity";
    private Button recordBtn, stopRecordBtn, playBtn;
    private static final int REQUEST_AUDIORECORDER = 100;
    private Uri audioUri = null;
    private MediaRecorderImp mMediaRecorderImp = null;

    String[] permission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.RECORD_AUDIO};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        recordBtn = findViewById(R.id.record_id);
        recordBtn.setOnClickListener(listener);
        playBtn = findViewById(R.id.play_id);
        playBtn.setOnClickListener(listener);
        stopRecordBtn = findViewById(R.id.stop_record);
        stopRecordBtn.setOnClickListener(listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.i(TAG, "onClick ");
            switch (view.getId()) {
                case R.id.record_id:
                    startRecord();
//                    startMediaRecorder();
                    break;
                case R.id.stop_record:
                    stoptMediaRecorder();
                    break;
                case R.id.play_id:
                    playAudio();
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult requestCode:" + requestCode + ", requestCode:" + requestCode);
        if (requestCode == RESULT_OK && requestCode == REQUEST_AUDIORECORDER) {
            audioUri = data.getData();
        }
    }

    private void startRecord() {
        Log.i(TAG, "startRecord");
        Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult(intent, REQUEST_AUDIORECORDER);
    }

    private void startMediaRecorder() {
        if (mMediaRecorderImp == null) {
            mMediaRecorderImp = new MediaRecorderImp();
        }
        mMediaRecorderImp.startMediaRecorder();
    }

    private void stoptMediaRecorder() {
        if (mMediaRecorderImp == null) {
            return;
        }
        mMediaRecorderImp.stoptMediaRecorder();
    }

    private void playAudio() {
        Log.i(TAG, "playAudio audioUri:" + audioUri);
        if (audioUri == null) {
            return;
        }
        MediaPlayer player = new MediaPlayer();
        try {
            player.reset();
            player.setDataSource(AudioActivity.this, audioUri);
            player.prepare();
            player.start();
        } catch (Exception e) {

        }
    }

    private boolean checkPermission() {
        Log.i(TAG, "checkPermission :");
//        return (PackageManager.PERMISSION_GRANTED == ContextCompat.
//
//                checkSelfPermission(AudioActivity.this.getApplicationContext(), android.Manifest.permission.RECORD_AUDIO));
        for (String p : permission) {
            Log.i(TAG, "checkPermission :" + p);
            if (checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permission, 300);
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 300) {
            Log.i(TAG, "--------------requestCode == 300->" + requestCode + "," + permissions.length + "," + grantResults.length);
        } else {
            Log.i(TAG, "--------------requestCode != 300->" + requestCode + "," + permissions + "," + grantResults);
        }
    }
}
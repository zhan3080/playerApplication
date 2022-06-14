package com.xxx.hzz.player.application;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

public class MediaRecorderImp {
    private static final String TAG = "MediaRecorderImp";

    private MediaPlayer mPlayer;
    MediaRecorder mMediaRecorder = null;
    File audioFile;
    boolean isRecording = true;

    public void startMediaRecorder(){
        Log.i(TAG,"startMediaRecorder");
        if(mMediaRecorder == null){
            mMediaRecorder = new MediaRecorder();
        }
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        path.mkdirs();
        deleteFolderFile(path.getAbsolutePath(),false);
        try {
            audioFile = File.createTempFile("recording",".3gp",path);
            Log.i(TAG,"startMediaRecorder audioFile:" + audioFile);
            mMediaRecorder.setOutputFile(audioFile.getAbsolutePath());
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            isRecording = true;
        }catch (Exception e){

        }
    }

    public void stoptMediaRecorder(){
        if(mMediaRecorder != null){
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
        isRecording = false;
        playMediaRecorderSource();
    }

    private void deleteFolderFile(String filePath, boolean deleteThisPath){
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFolderFile(files[i].getAbsolutePath(), true);
                }
            }
            if (deleteThisPath) {
                if (!file.isDirectory()) {
                    file.delete();
                }else {
                    if (file.listFiles().length == 0) {
                        file.delete();
                    }
                }
            }
        }
    }



    private void playMediaRecorderSource(){
        if(audioFile == null){
            return;
        }
        MediaPlayer player = new MediaPlayer();
        try {
            player.setDataSource(audioFile.getAbsolutePath());
            player.prepare();
            player.start();
        }catch (Exception e){

        }
    }
}

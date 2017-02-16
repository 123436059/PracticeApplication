package com.practice.videorecord.inter;

/**
 * Created by Taxi on 2017/2/14.
 */

public interface IVideoRecord {

    void onPlay();

    void onStop();

     boolean isRecording();
}

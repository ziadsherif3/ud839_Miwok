package com.example.android.miwok;

import android.content.Context;
import android.media.MediaPlayer;

public class Word {

    private String mMiwokTrans;
    private String mDefaultTrans;
    private int mImageRes;
    private boolean imagePresent;
    private int mAudio;

    public Word(String miwokTrans, String defaultTrans, int imageRes, int audio) {
        this.mMiwokTrans = miwokTrans;
        this.mDefaultTrans = defaultTrans;
        this.mImageRes = imageRes;
        this.imagePresent = true;
        this.mAudio = audio;
    }

    public Word(String miwokTrans, String defaultTrans, int audio) {
        this.mMiwokTrans = miwokTrans;
        this.mDefaultTrans = defaultTrans;
        this.imagePresent = false;
        this.mAudio = audio;
    }

    public String getDefaultTrans() {
        return this.mDefaultTrans;
    }

    public String getMiwokTrans() {
        return this.mMiwokTrans;
    }

    public int getmImageRes() {
        return this.mImageRes;
    }

    public boolean isImagePresent() {
        return this.imagePresent;
    }

    public int getmAudio() {
        return this.mAudio;
    }
}

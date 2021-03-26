/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    private ArrayList<Word> words = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPLayer();
        }
    };
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener afChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        words.add(new Word("әpә", "Father", R.drawable.family_father, R.raw.family_father));
        words.add(new Word("әṭa", "Mother", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("angsi", "Son", R.drawable.family_son, R.raw.family_son));
        words.add(new Word("tune", "Daughter", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("taachi", "Older Brother", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word("chalitti", "Younger Brother", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word("teṭe", "Older Sister", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word("kolliti", "Younger Sister", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word("ama", "Grandmother", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("paapa", "Grandfather", R.drawable.family_grandfather, R.raw.family_grandfather));

        WordAdapter familyAdapt = new WordAdapter(this, this.words, R.color.category_family);

        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int i) {
                switch (i) {
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                        releaseMediaPLayer();
                        break;
                    case AudioManager.AUDIOFOCUS_GAIN:
                        mediaPlayer.start();
                        break;
                }
            }
        };

        ListView listView = findViewById(R.id.rootView);
        listView.setAdapter(familyAdapt);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPLayer();
                int res = audioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(FamilyActivity.this, words.get(i).getmAudio());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        releaseMediaPLayer();
        super.onStop();
    }

    private void releaseMediaPLayer () {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(afChangeListener);
        }
    }

}

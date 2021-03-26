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

public class NumbersActivity extends AppCompatActivity {

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

        words.add(new Word("lutti", "One", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("otiiko", "Two", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("tolookosu", "Three", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("oyyisa", "Four", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("massokka", "Five", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("temmokka", "Six", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("kenekaku", "Seven", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("kawinta", "Eight", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("wo’e", "Nine", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("na’aacha", "Ten", R.drawable.number_ten, R.raw.number_ten));

        WordAdapter numbersAdapt = new WordAdapter(this, this.words, R.color.category_numbers);

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
        listView.setAdapter(numbersAdapt);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPLayer();
                int res = audioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, words.get(i).getmAudio());
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

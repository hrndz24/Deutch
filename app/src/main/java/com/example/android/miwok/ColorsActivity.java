package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    //Creating a media player for a sound
    private MediaPlayer mMediaPlayer;

    //Creating an Audio manager
    private AudioManager mAudioManager;

    /**Creating a change listener that checks the state of the focus */
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                        //If the focus is lost temporarily (notification, etc.) or the sound
                        //still can be played, but at a lower volume(ducking), then we should
                        //pause the sound and reset it to the beginning
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                        //The focus is on our app and isn't interrupted by any
                        //other apps or sounds we can play the sound
                        mMediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                        //Another app is playing an audio so we should clean the media player
                        releaseMediaPlayer();
                    }
                }
            };

    /**Setting a completion listener that cleans the media player when the sound is finished */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);

        //Creating an audio manager for music (song, sounds, etc.)
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Creating a list of colors

        final ArrayList<Word> words= new ArrayList<Word>();
        words.add(new Word("white", "weiß", R.color.white, R.raw.white));
        words.add(new Word("black", "schwarz", R.color.black, R.raw.black));
        words.add(new Word("red","rot", R.color.red, R.raw.red));
        words.add(new Word("green", "grün", R.color.green, R.raw.green));
        words.add(new Word("blue", "blau", R.color.blue, R.raw.blue));
        words.add(new Word("sky blue", "himmelblau", R.color.sky_blue, R.raw.schone_frauen));
        words.add(new Word("yellow", "gelb", R.color.yellow, R.raw.yellow));
        words.add(new Word("gray", "grau", R.color.gray, R.raw.gray));
        words.add(new Word("brown", "braun", R.color.primary_color, R.raw.brown));
        words.add(new Word("pink", "rosa", R.color.pink, R.raw.pink));
        words.add(new Word("purple", "lila", R.color.purple, R.raw.purple));


        //Creating an adapter of Word class
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_colors);

        //Making a ListView in ColorsActivity
        ListView listView = (ListView) findViewById(R.id.words_list);

        //Setting the adapter on ColorsActivity ListView
        listView.setAdapter(adapter);

        // Set a click listener to play the audio when the list item is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Get the {@link Word} object at the given position the user clicked on
                Word word = words.get(position);
                // Cleans the media player
                releaseMediaPlayer();

                //Somehow requests audio focus
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                //Somehow tells us whether we can play a sound, if not it doesn't do anything
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //Fills the media player with a sound
                    mMediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getAudioResourceId());
                    // Start the audio file
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

    }

    /**When the app is not active the sound stops playing */
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player (stop playing the sound) by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            //For some reasons we should leave it
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}

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


public class NumbersActivity extends AppCompatActivity {

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

        //Creating a list of numbers

        final ArrayList<Word> words= new ArrayList<Word>();
        words.add(new Word("zero", "null", R.color.tan_background, R.raw.zero));
        words.add(new Word("one", "eins", R.drawable.number_one, R.raw.one));
        words.add(new Word("two", "zwei", R.drawable.number_two, R.raw.two));
        words.add(new Word("three","drei", R.drawable.number_three,R.raw.three));
        words.add(new Word("four", "fier", R.drawable.number_four, R.raw.four));
        words.add(new Word("five", "fünf", R.drawable.number_five, R.raw.five));
        words.add(new Word("six", "sechs", R.drawable.number_six, R.raw.six));
        words.add(new Word("seven", "sieben", R.drawable.number_seven, R.raw.seven));
        words.add(new Word("eight", "acht", R.drawable.number_eight, R.raw.eight));
        words.add(new Word("nine", "neun", R.drawable.number_nine, R.raw.nine));
        words.add(new Word("ten", "zehn", R.drawable.number_ten, R.raw.ten));


        //Creating an adapter of Word class
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);

        //Making a ListView in NumbersActivity
        ListView listView = (ListView) findViewById(R.id.words_list);

        //Setting the adapter on NumbersActivity ListView
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
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getAudioResourceId());
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
     * Clean up the media player by releasing its resources.
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

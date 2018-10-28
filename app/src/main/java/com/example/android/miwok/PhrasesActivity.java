package com.example.android.miwok;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);

        //Creating a list of phrases

        ArrayList<Word> words= new ArrayList<Word>();

        words.add(new Word("Hello", "Hallo"));
        words.add(new Word("Goodbye", "Auf Wiedersehen"));
        words.add(new Word("Good afternoon","Guten Tag"));
        words.add(new Word("Good morning", "Guten Morgen"));
        words.add(new Word("How are you?", "Wie geht's?"));
        words.add(new Word("I'm good", "Gut"));
        words.add(new Word("My name is ", "Ich heisse"));
        words.add(new Word("Excuse me", "Entschuldigung"));
        words.add(new Word("Where is the bathroom?", "Wo ist die Toilette?"));
        words.add(new Word("Thank you", "Dankeshön"));
        words.add(new Word("Life is pain", "Das Leben ist Schmerz"));
        words.add(new Word("One beer please", "Ein Bier bitte"));


        //Creating an adapter of Word class
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_phrases);

        //Making a ListView in PhrasesActivity
        ListView listView = (ListView) findViewById(R.id.words_list);

        //Setting the adapter on PhrasesActivity ListView
        listView.setAdapter(adapter);

    }
}

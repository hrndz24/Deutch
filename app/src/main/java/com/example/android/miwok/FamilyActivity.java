package com.example.android.miwok;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);

        //Creating a list of family members

        ArrayList<Word> words= new ArrayList<Word>();
        words.add(new Word("mother", "Mutter", R.drawable.family_mother, R.raw.not_today));
        words.add(new Word("father", "Vater", R.drawable.family_father , R.raw.not_today));
        words.add(new Word("sister","Schwester", R.drawable.family_daughter, R.raw.not_today));
        words.add(new Word("brother", "Bruder", R.drawable.family_younger_brother, R.raw.not_today));
        words.add(new Word("son", "Sohn", R.drawable.family_son, R.raw.not_today));
        words.add(new Word("daughter", "Tochter", R.drawable.family_younger_sister, R.raw.not_today));
        words.add(new Word("aunt", "Tante", R.drawable.family_mother, R.raw.not_today));
        words.add(new Word("uncle", "Onkel", R.drawable.family_father, R.raw.not_today));
        words.add(new Word("nephew", "Neffe", R.drawable.family_son, R.raw.not_today));
        words.add(new Word("niece", "Nichte", R.drawable.family_younger_sister, R.raw.not_today));
        words.add(new Word("parent", "Elter", R.drawable.family_mother, R.raw.not_today));
        words.add(new Word("child", "Kind", R.drawable.family_older_sister, R.raw.not_today));
        words.add(new Word("husband", "Mann", R.drawable.family_father, R.raw.not_today));
        words.add(new Word("wife", "Frau", R.drawable.family_mother, R.raw.not_today));


        //Creating an adapter of Word class
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_family);

        //Making a ListView in FamilyActivity
        ListView listView = (ListView) findViewById(R.id.words_list);

        //Setting the adapter on FamilyActivity ListView
        listView.setAdapter(adapter);

    }


}

package com.example.android.miwok;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourceId;

    public WordAdapter(Activity context, ArrayList<Word> words, int colorResourceId) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
        mColorResourceId = colorResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        //trying to make an item rounded
        // listItemView.setBackgroundResource(R.drawable.shape_item);

        // Get the Word object located at this position in the list
        Word currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the deutch word
        TextView deutchTextView = (TextView) listItemView.findViewById(R.id.deutch_text_view);
        // Get the deutch translation from the current Word object and
        // set this text on the deutch TextView
        deutchTextView.setText(currentWord.getDeutchTranslation());

        // Find the TextView in the list_item.xml layout with the english word
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        // Get the English translation from the current Word object and
        // set this text on the English TextView
        defaultTextView.setText(currentWord.getDefaultTranslation());

        /**
           Only in case of having an image
         */
        //Find the ImageView in the list_item.xml
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image_view);
        //Set the associated picture on this ImageView if there is one
        if (currentWord.hasImage()) {
            imageView.setImageResource(currentWord.getImageResourceId());
            imageView.setVisibility(View.VISIBLE);
        }
        else {
            imageView.setVisibility(View.GONE);
        }

        /**
         * In case of making a list item colored
         */

        View textContainer = listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        textContainer.setBackgroundColor(color);



        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }
}


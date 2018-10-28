package com.example.android.miwok;

//The class for both english word and deutch translation

public class Word {

    //English translation for the word
    private String mDefaultTranslation;

    //Deutch translation for the word
    private String mDeutchTranslation;

    private int mAudioResourceId;

    //ID of the image arranged to -1
    private int mImageResourceId = NO_IMAGE;

    //Placeholder in case of no image provided
    private static final int NO_IMAGE = -1;


    public Word (String defaultTranslation, String deutchTranslation){
        mDefaultTranslation = defaultTranslation;
        mDeutchTranslation = deutchTranslation;

    }

    public Word (String defaultTranslation, String deutchTranslation, int imageResourceId, int audioResourceId){
        mImageResourceId = imageResourceId;
        mDefaultTranslation = defaultTranslation;
        mDeutchTranslation = deutchTranslation;
        mAudioResourceId = audioResourceId;
    }


    //This method returns English translation
    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }
    //This method returns Deutch translation
    public String getDeutchTranslation(){
        return mDeutchTranslation;
    }
    //This method returns image ID
    public int getImageResourceId() {return mImageResourceId;}
    //This method checks whether there is an image and return true if it has
    public boolean hasImage(){
        return mImageResourceId != NO_IMAGE;
    }

    public int getAudioResourceId() {return mAudioResourceId;}
}

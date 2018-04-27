package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public final class JsonUtils {

    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        //create a null object
        Sandwich mSandwich = null;

        try {
            // Initialize JSONObject from JSON String
            JSONObject mSandwichJsonObject = new JSONObject(json);

            // Name into a JSONObject
            JSONObject mName = mSandwichJsonObject.getJSONObject(NAME);

            // get the data from json and assign to variables
            String mMainName = mName.getString(MAIN_NAME);

            JSONArray mAKAJsonArray = mName.getJSONArray(ALSO_KNOWN_AS);
            List<String> mAKA = jsonArrayToStringListConverter(mAKAJsonArray);

            String mPlaceOfOrigin = mSandwichJsonObject.getString(PLACE_OF_ORIGIN);
            String mDescription = mSandwichJsonObject.getString(DESCRIPTION);
            String mImage = mSandwichJsonObject.getString(IMAGE);

            JSONArray mIngredientsJsonArray = mSandwichJsonObject.getJSONArray(INGREDIENTS);
            List<String> mIngredients = jsonArrayToStringListConverter(mIngredientsJsonArray);

            // Assign variables to mSandwich
            mSandwich = new Sandwich(mMainName, mAKA, mPlaceOfOrigin, mDescription, mImage, mIngredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mSandwich;

    }


    private static List<String> jsonArrayToStringListConverter (JSONArray mJsonArray) throws JSONException {
        // method takes a jsonArray and returns a List
        int mArrayLength = mJsonArray.length();
        List<String> mResult = new ArrayList<>();
        for (int i = 0; i < mArrayLength; i++) {
            mResult.add(mJsonArray.getString(i));
        }
        return mResult;
    }
}

package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public final class JsonUtils {


    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        // method variables
        List<String> mAKAList = new ArrayList<String>();
        List<String> mIngredientsList = new ArrayList<String>();
        Sandwich mSandwich;

        try {

            // initialize Json object from json String
            JSONObject jsonObject = new JSONObject(json);

            // get the name into a JSONObject
            JSONObject name = jsonObject.getJSONObject("");

            //get main name
            String mainName = name.getString(MAIN_NAME);

            // get alsoKnownAs array
            JSONArray aKaJasonArray = name.getJSONArray(ALSO_KNOWN_AS);
            for (int i = 0; i < aKaJasonArray.length(); i++) {
                String akaName = aKaJasonArray.optString(i);
                mAKAList.add(akaName);
            }

            // get place of origin, description, image URL
            String placeOfOrigin = name.getString(PLACE_OF_ORIGIN);
            String description = name.getString(DESCRIPTION);
            String imageURL = name.getString(IMAGE);

            // get ingredients array
            JSONArray ingredientsArray = name.getJSONArray(INGREDIENTS);
            for (int i = 0; i < ingredientsArray.length(); i++) {
                String ingredient = ingredientsArray.optString(i);
                mIngredientsList.add(ingredient);
            }

            mSandwich = new Sandwich(mainName, mAKAList, placeOfOrigin, description, imageURL, mIngredientsList);

            return mSandwich;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

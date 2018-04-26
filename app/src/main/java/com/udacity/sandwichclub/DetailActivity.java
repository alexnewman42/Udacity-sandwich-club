package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    // class variables
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        // get references to TextViews
        TextView alsoKnownAsTV = findViewById(R.id.also_known_tv);
        TextView placeOfOriginTV = findViewById(R.id.origin_tv);
        TextView ingredientsTV = findViewById(R.id.ingredients_tv);
        TextView descriptionTV = findViewById(R.id.description_tv);

        // method variables
        String mAlsoKnownAs;
        List<String> mAlsoKnownAsList = sandwich.getAlsoKnownAs();
        String mPlaceOfOrigin = sandwich.getPlaceOfOrigin();
        String mIngredient;
        List<String> mIngredientsList = sandwich.getIngredients();
        String mDescription = sandwich.getDescription();
        StringBuilder mAKABuilder = new StringBuilder();
        StringBuilder mIngredientsBuilder = new StringBuilder();

        // Assign aka to TextView
        // This adds a bullet point to each aka and a new line to each except the last.
        for (int i = 0; i < alsoKnownAsTV.length(); i++) {
            mAKABuilder.append("\u2022 ").append(mAlsoKnownAsList.get(i));
            if (i != mAlsoKnownAsList.size() -1) {
                mAKABuilder.append('\n');
            }
        }

        mAlsoKnownAs = mAKABuilder.toString();
        alsoKnownAsTV.setText(mAlsoKnownAs);


        // Assign Place of Origin to TextView
        placeOfOriginTV.setText(mPlaceOfOrigin);


        // Assign ingredients to TextView
        // This adds a bullet point to each ingredient and a new line to each except the last.
        for (int i = 0; i < mIngredientsList.size(); i++) {
            mIngredientsBuilder.append("\u2022 ").append(mIngredientsList.get(i));
            if (i != mIngredientsList.size() -1) {
                mIngredientsBuilder.append('\n');
            }
        }

        mIngredient = mIngredientsBuilder.toString();
        ingredientsTV.setText(mIngredient);


        // Assign description to the textview
        descriptionTV.setText(mDescription);

    }
}

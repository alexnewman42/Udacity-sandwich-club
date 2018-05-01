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

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    // class variables
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.image_iv) ImageView ingredientsIv;
    @BindView(R.id.also_known_tv) TextView alsoKnownAsTV;
    @BindView(R.id.origin_tv) TextView placeOfOriginTV;
    @BindView(R.id.ingredients_tv) TextView ingredientsTV;
    @BindView(R.id.description_tv) TextView descriptionTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        //ImageView ingredientsIv = findViewById(R.id.image_iv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
            return;
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

    private void populateUI(Sandwich mSandwich) {

        // get references to TextViews (old)
        //TextView alsoKnownAsTV = findViewById(R.id.also_known_tv);
        //TextView placeOfOriginTV = findViewById(R.id.origin_tv);
        //TextView ingredientsTV = findViewById(R.id.ingredients_tv);
        //TextView descriptionTV = findViewById(R.id.description_tv);

        // method variables
        String mAlsoKnownAs;
        List<String> mAlsoKnownAsList = mSandwich.getAlsoKnownAs();
        String mPlaceOfOrigin = mSandwich.getPlaceOfOrigin();
        String mIngredient;
        List<String> mIngredientsList = mSandwich.getIngredients();
        String mDescription = mSandwich.getDescription();
        StringBuilder mAKABuilder = new StringBuilder();
        StringBuilder mIngredientsBuilder = new StringBuilder();

        // Check for null and then assign aka to TextView
        if (mAlsoKnownAsList == null || mAlsoKnownAsList.isEmpty()) {
            mAlsoKnownAs = getString(R.string.no_aliases);
        } else {
            // Adds each aka and a new line to each except the last.
            for (int i = 0; i < mAlsoKnownAsList.size(); i++) {
                mAKABuilder.append(mAlsoKnownAsList.get(i));
                if (i != mAlsoKnownAsList.size() - 1) {
                    mAKABuilder.append('\n');
                }
            }
            mAlsoKnownAs = mAKABuilder.toString();
        }
        alsoKnownAsTV.setText(mAlsoKnownAs);


        // Check for null or empty variable and then assign mPlaceOfOrigin to TextView
        if (mPlaceOfOrigin == null || mPlaceOfOrigin.equals("")) {
            mPlaceOfOrigin = getString(R.string.unknown_origin);
        }
        placeOfOriginTV.setText(mPlaceOfOrigin);


        // Check for null and then assign ingredients to TextView
        if (mIngredientsList == null || mIngredientsList.isEmpty()) {
            mIngredient = getString(R.string.unknown_ingredients);
        } else {
        // This adds each ingredient and a new line to each except the last.
            for (int i = 0; i < mIngredientsList.size(); i++) {
                mIngredientsBuilder.append(mIngredientsList.get(i));
                if (i != mIngredientsList.size() - 1) {
                    mIngredientsBuilder.append('\n');
                }
            }
            mIngredient = mIngredientsBuilder.toString();
        }
        ingredientsTV.setText(mIngredient);


        // Assign description to the textview
        if (mDescription == null || mDescription.equals("")) {
            mDescription = getString(R.string.no_description);
        }
        descriptionTV.setText(mDescription);

    }
}

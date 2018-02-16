package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mDescriptionLabel, mIngredientsLabel, mOriginLabel, mAlsoKnownAsLabel;
    private TextView mDescription, mIngredients, mOrigin, mAlsoKnownAs;

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
        mDescriptionLabel = (TextView) findViewById(R.id.description_label);
        mIngredientsLabel = (TextView) findViewById(R.id.ingredients_label);
        mOriginLabel = (TextView) findViewById(R.id.place_of_origin_label);
        mAlsoKnownAsLabel = (TextView) findViewById(R.id.also_known_as_label);
        mDescription = (TextView) findViewById(R.id.description_tv);
        mIngredients = (TextView) findViewById(R.id.ingredients_tv);
        mOrigin = (TextView) findViewById(R.id.origin_tv);
        mAlsoKnownAs = (TextView) findViewById(R.id.also_known_tv);

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
        setTitle(sandwich.getMainName());
        bindTextView(mDescriptionLabel, mDescription, sandwich.getDescription());
        bindTextView(mIngredientsLabel, mIngredients, getListAsString(sandwich.getIngredients()));
        bindTextView(mOriginLabel, mOrigin, sandwich.getPlaceOfOrigin());
        bindTextView(mAlsoKnownAsLabel, mAlsoKnownAs, getListAsString(sandwich.getAlsoKnownAs()));
    }

    private String getListAsString(List<String> list) {
        if(list != null) {
            if(list.isEmpty())
                return null;
            String string = list.toString();
            return string.substring(1, string.length() - 1);
        } else {
            return null;
        }
    }

    private void bindTextView(TextView label, TextView value, String text) {
        if(text != null && !text.equals("")) {
            value.setText(text);
        } else {
            label.setVisibility(View.GONE);
            value.setVisibility(View.GONE);
        }
    }
}

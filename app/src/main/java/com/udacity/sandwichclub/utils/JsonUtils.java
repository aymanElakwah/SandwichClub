package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static List<String> getListString(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<String>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject tmp = jsonObject.getJSONObject("name");
            String mainName = tmp.getString("mainName");
            List<String> alsoKnownAs = getListString(tmp.getJSONArray("alsoKnownAs"));
            String placeOfOrigin = jsonObject.getString("placeOfOrigin");
            String description = jsonObject.getString("description");
            String image = jsonObject.getString("image");
            List<String> ingredients = getListString(jsonObject.getJSONArray("ingredients"));
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException ex) {
            return null;
        }
    }
}

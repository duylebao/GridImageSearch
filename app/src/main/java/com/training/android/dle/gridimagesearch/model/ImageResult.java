package com.training.android.dle.gridimagesearch.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImageResult implements Serializable{
    public String url;
    public int width;
    public int height;
    public int tbWidth;
    public int tbHeight;
    public String title;
    public String tbUrl;

    public ImageResult(JSONObject imageJson) throws JSONException{
        height = imageJson.getInt("height");
        width = imageJson.getInt("width");
        tbHeight = imageJson.getInt("tbHeight");
        tbWidth = imageJson.getInt("tbWidth");
        title = imageJson.getString("title");
        url = imageJson.getString("url");
        tbUrl = imageJson.getString("tbUrl");
    }

    public static List<ImageResult> toImageResult(JSONArray imageJsonArray) throws JSONException{
        List<ImageResult> result = new ArrayList<ImageResult>();
        for (int i = 0; i < imageJsonArray.length(); i++) {
            result.add( new ImageResult( imageJsonArray.getJSONObject(i)));
        }
        return result;
    }
}

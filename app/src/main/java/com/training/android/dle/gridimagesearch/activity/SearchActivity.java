package com.training.android.dle.gridimagesearch.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.training.android.dle.gridimagesearch.R;
import com.training.android.dle.gridimagesearch.adapter.ImageResultAdapter;
import com.training.android.dle.gridimagesearch.model.ImageResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {
    private EditText etSearch;
    private List<ImageResult> images;
    private ImageResultAdapter imageResultAdapter;
    private GridView gvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        etSearch = (EditText)findViewById(R.id.etSearch);
        images = new ArrayList<ImageResult>();
        imageResultAdapter = new ImageResultAdapter(this, images);
        gvResult = (GridView)findViewById(R.id.gvResult);
        gvResult.setAdapter(imageResultAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSearch(View view) {
        String text = etSearch.getText().toString();
        String url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q="+text;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray imageJsonArray = null;
                try {
                    imageJsonArray = response.getJSONObject("responseData").getJSONArray("results");
                    images.clear();
                    images.addAll(ImageResult.toImageResult(imageJsonArray));
                    imageResultAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

package com.training.android.dle.gridimagesearch.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.training.android.dle.gridimagesearch.R;
import com.training.android.dle.gridimagesearch.adapter.ImageResultAdapter;
import com.training.android.dle.gridimagesearch.listener.ImageResultScrollListener;
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
    private static final int VISIBLE_THRESHOLD = 8;
    private AsyncHttpClient client;
    private boolean stopLoading = false;
    private ImageResultScrollListener scrollListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        client = new AsyncHttpClient();
        etSearch = (EditText)findViewById(R.id.etSearch);
        images = new ArrayList<ImageResult>();
        imageResultAdapter = new ImageResultAdapter(this, images);
        gvResult = (GridView)findViewById(R.id.gvResult);
        gvResult.setAdapter(imageResultAdapter);
        scrollListener = new ImageResultScrollListener(VISIBLE_THRESHOLD, 0) {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                if (stopLoading){
                    return false;
                }
                loadImages(page);

                return true;
            }
        };

        gvResult.setOnScrollListener(scrollListener);
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
        // reset the variable for scroll listener on a new search
        scrollListener.reset();
        loadImages(0);
    }

    private void loadImages(final int page){
        String text = etSearch.getText().toString();
        int start = page * VISIBLE_THRESHOLD;
        String url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz="+VISIBLE_THRESHOLD+"&q="+text+"&start="+start;
        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray imageJsonArray = null;
                try {
                    stopLoading = false;
                    imageJsonArray = response.getJSONObject("responseData").getJSONArray("results");
                    if (page == 0){
                        imageResultAdapter.clear();
                    }
                    imageResultAdapter.addAll(ImageResult.toImageResult(imageJsonArray));
                } catch (JSONException e) {
                    stopLoading = true;
                }
            }
        });
    }
}

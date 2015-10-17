package com.training.android.dle.gridimagesearch.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.training.android.dle.gridimagesearch.R;
import com.training.android.dle.gridimagesearch.model.SearchSetting;

public class SearchSettingActivity extends AppCompatActivity {
    private Spinner spSize;
    private Spinner spColor;
    private Spinner spType;
    private EditText etSite;
    private SearchSetting setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_setting);
        getSupportActionBar().hide();
        Intent i = getIntent();
        setting = (SearchSetting)i.getSerializableExtra("settings");

        spSize = (Spinner)findViewById(R.id.spSize);
        spColor = (Spinner)findViewById(R.id.spColor);
        spType = (Spinner)findViewById(R.id.spType);
        etSite = (EditText)findViewById(R.id.etSite);
        // size adaptor
        ArrayAdapter<CharSequence> adSize = ArrayAdapter.createFromResource(this,
                R.array.size_choice, android.R.layout.simple_spinner_item);
        adSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSize.setAdapter(adSize);

        // color adaptor
        ArrayAdapter<CharSequence> adColor = ArrayAdapter.createFromResource(this,
                R.array.color_choice, android.R.layout.simple_spinner_item);
        adColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spColor.setAdapter(adColor);

        // type adaptor
        // color adaptor
        ArrayAdapter<CharSequence> adType = ArrayAdapter.createFromResource(this,
                R.array.type_choice, android.R.layout.simple_spinner_item);
        adType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(adType);

        if (setting.size != null) {
            int index = adSize.getPosition(setting.size);
            spSize.setSelection(index);
        }
        if (setting.color != null) {
            int index = adColor.getPosition(setting.color);
            spColor.setSelection(index);
        }
        if (setting.type != null) {
            int index = adType.getPosition(setting.type);
            spType.setSelection(index);
        }
        if (setting.site != null) {
            etSite.setText(setting.site);
        }

        Button btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting.size = (String)spSize.getSelectedItem();
                setting.color = (String)spColor.getSelectedItem();
                setting.type = (String)spType.getSelectedItem();
                setting.site = etSite.getText().toString();
                Intent intent = SearchSettingActivity.this.getIntent();
                intent.putExtra("settings", setting);
                SearchSettingActivity.this.setResult(RESULT_OK, intent);
                SearchSettingActivity.this.finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_setting, menu);
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
}

package com.example.td1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

public class BreachActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breach);
        showBreachDetails();
    }

    public void showBreachDetails() {
        ImageView breachLogoView = (ImageView) findViewById(R.id.imageViewBreachLogo);
        TextView breachTitleView = (TextView) findViewById(R.id.textViewBreachTitle);

        String json = getIntent().getStringExtra(Constants.currentBreachIntentKey);
        Gson gson = new Gson();
        //Breaches breach = gson.fromJson(json);
    }
}

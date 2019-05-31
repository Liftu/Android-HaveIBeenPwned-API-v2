package com.example.td1;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class BreachActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breach);
        showBreachDetails();
    }

    public void showBreachDetails() {
        ImageView breachLogoView =              (ImageView) findViewById(R.id.imageViewBreachLogo);
        TextView breachTitleView =              (TextView) findViewById(R.id.textViewBreachTitle);
        TextView breachDomainValueView =        (TextView) findViewById(R.id.textViewDomainValue);
        TextView breachPwnCountValueView =      (TextView) findViewById(R.id.textViewPwnCountValue);
        TextView breachDateValueView =          (TextView) findViewById(R.id.textViewDateValue);
        ListView breachDataTypesValuesView =    (ListView) findViewById(R.id.ListViewDataTypesValues);

        Breaches breach = new Gson().fromJson(getIntent().getStringExtra(Constants.currentBreachIntentKey), Breaches.class);

        Picasso.with(getBaseContext()).load(breach.getLogoPath()).into(breachLogoView);
        breachTitleView.setText(breach.getTitle());
        breachDomainValueView.setText(breach.getDomain());
        breachPwnCountValueView.setText(String.format("%,d", breach.getPwnCount()));
        breachDateValueView.setText(breach.getBreachDate());

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getBaseContext(), android.R.layout.simple_list_item_1, breach.getDataClasses());
        breachDataTypesValuesView.setAdapter(adapter);
    }
}

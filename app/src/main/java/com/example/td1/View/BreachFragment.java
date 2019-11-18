package com.example.td1.View;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.td1.Controller.Constants;
import com.example.td1.Model.Breaches;
import com.example.td1.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class BreachFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_breach, container, false);

//        overridePendingTransition(R.xml.slide_in, R.xml.slide_out);
//        overridePendingTransition(R.xml.slide_in, R.xml.slide_out);
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_breach);
        showBreachDetails();

        return view;
    }

    public void showBreachDetails() {
        ImageView breachLogoView =              (ImageView) view.findViewById(R.id.imageViewBreachLogo);
        TextView breachTitleView =              (TextView) view.findViewById(R.id.textViewBreachTitle);
        TextView breachDomainValueView =        (TextView) view.findViewById(R.id.textViewDomainValue);
        TextView breachPwnCountValueView =      (TextView) view.findViewById(R.id.textViewPwnCountValue);
        TextView breachDateValueView =          (TextView) view.findViewById(R.id.textViewDateValue);
        ListView breachDataTypesValuesView =    (ListView) view.findViewById(R.id.ListViewDataTypesValues);

        Breaches breach = new Gson().fromJson(this.getArguments().getString(Constants.current_breach_intent_key), Breaches.class);

        Picasso.with(getContext()).load(breach.getLogoPath()).into(breachLogoView);
        breachTitleView.setText(breach.getTitle());
        breachDomainValueView.setText(breach.getDomain());
        breachPwnCountValueView.setText(String.format("%,d", breach.getPwnCount()));
        breachDateValueView.setText(breach.getBreachDate());

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, breach.getDataClasses());
        breachDataTypesValuesView.setAdapter(adapter);
    }
}

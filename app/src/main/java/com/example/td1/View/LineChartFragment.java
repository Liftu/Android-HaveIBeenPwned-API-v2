package com.example.td1.View;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.td1.Controller.Constants;
import com.example.td1.Controller.BarChartController;
import com.example.td1.Controller.LineChartController;
import com.example.td1.Model.Breaches;
import com.example.td1.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class LineChartFragment extends Fragment implements OnChartValueSelectedListener {
    private ProgressBar progressBar;
    private LineChart lineChart;
    private List<Breaches> sortedBreachesList;
    private Entry lastEntryHighlighted;

    private LineChartController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_line_chart, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.graphProgressBar);
        lineChart = (LineChart) view.findViewById(R.id.lineChart);
        lineChart.setOnChartValueSelectedListener(this);
        lineChart.getDescription().setEnabled(false);

        controller = new LineChartController(this, getContext().getSharedPreferences(Constants.user_sharedpreferences, MODE_PRIVATE));
        controller.start();

        return view;
    }

    public void updateGraph(List<Breaches> breachesList) {
        if (breachesList != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                breachesList.sort(Comparator.comparing(Breaches::getBreachLocalDate, (d1, d2) -> {
                    return d1.compareTo(d2);
                }));
                sortedBreachesList = breachesList;
            }

            List<Entry> lineEntries = new ArrayList<Entry>();
            int i = 0;
            for (Breaches breach : breachesList) {
                i++;
                lineEntries.add(new Entry(i, breach.getPwnCount()));
            }

            LineDataSet set = new LineDataSet(lineEntries, "Breaches over time");
            set.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary100));
            set.setCircleColor(Color.WHITE);
            set.setCircleRadius(1.8f);

            LineData data = new LineData(set);
            data.setHighlightEnabled(true);
            lineChart.setData(data);
            lineChart.invalidate();
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;

        displayToast(sortedBreachesList.get((int)e.getX()-1).getBreachDate() + " : " + sortedBreachesList.get((int)e.getX()-1).getTitle());
        lastEntryHighlighted = e;
    }

    @Override
    public void onNothingSelected() {
        // Si l'on clic une deuxieme fois sur l'element.
        controller.onItemClick((int)lastEntryHighlighted.getX()-1);
    }

    public void navigateToDetail(String json) {
        MainActivity mainActivity = (MainActivity) getActivity();
        BreachFragment breachFragment = new BreachFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.current_breach_intent_key, json);
        breachFragment.setArguments(bundle);
        mainActivity.goToWithBackStack(breachFragment);
    }

    public void displayToast(String message)
    {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }

    public void showProgressBar()
    {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar()
    {
        progressBar.setVisibility(View.GONE);
    }

    public void showLineChart() {
        lineChart.setVisibility(View.VISIBLE);
    }

    public void hideLineChart() {
        lineChart.setVisibility(View.GONE);
    }
}

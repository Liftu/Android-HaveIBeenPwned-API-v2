package com.example.td1.View;

import android.graphics.Color;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.td1.Controller.Constants;
import com.example.td1.Controller.GraphController;
import com.example.td1.Controller.MainController;
import com.example.td1.Model.Breaches;
import com.example.td1.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.RequiresApi;

import static android.content.Context.MODE_PRIVATE;

public class GraphFragment extends Fragment implements OnChartValueSelectedListener {
    private ProgressBar progressBar;
    private BarChart barChart;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Breaches> sortedBreachesList;

    private GraphController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.graphProgressBar);
        barChart = (BarChart) view.findViewById(R.id.barChart);
        barChart.setOnChartValueSelectedListener(this);

        controller = new GraphController(this, getContext().getSharedPreferences(Constants.user_sharedpreferences, MODE_PRIVATE));
        controller.start();

        return view;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                controller.onFilter(newText);
//                return true;
//            }
//        });
//    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        searchBar.setOnQueryTextListener(null);
//    }

    public void updateGraph(List<Breaches> breachesList) {
        if (breachesList != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                breachesList.sort(Comparator.comparing(Breaches::getBreachLocalDate, (d1, d2) -> {
                    return d1.compareTo(d2);
                }));
            }

            List<BarEntry> barEntries = new ArrayList<BarEntry>();
            int i = 0;
            for (Breaches breach : breachesList) {
                i++;
                barEntries.add(new BarEntry(i, breach.getPwnCount()));
            }

            BarDataSet set = new BarDataSet(barEntries, "BarDataSet");
            set.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary700));
            BarData data = new BarData(set);
            data.setHighlightEnabled(true);
            //data.setBarWidth(0.9f);
            barChart.setData(data);
            barChart.setFitBars(true);
            barChart.invalidate();
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        controller.onItemClick((int)e.getX()-1);
    }

    @Override
    public void onNothingSelected() {

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

    public void showBarChart() {
        barChart.setVisibility(View.VISIBLE);
    }

    public void hideBarChart() {
        barChart.setVisibility(View.GONE);
    }
}

package com.example.td1.View;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class GraphFragment extends Fragment {
    private ProgressBar progressBar;
    private LineChart lineChart;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private GraphController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.graphProgressBar);
        lineChart = (LineChart) view.findViewById(R.id.lineChart);
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
//
//    public void showList(List<Breaches> breachesList) {
//        if (breachesList != null) {
//            recyclerView.setHasFixedSize(true);
//            layoutManager = new LinearLayoutManager(getContext());
//            recyclerView.setLayoutManager(layoutManager);
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                breachesList.sort(Comparator.comparingInt(Breaches::getPwnCount).reversed());
//            }
//            mAdapter = new MyAdapter(breachesList, getContext(), getListener());
//            recyclerView.setAdapter(mAdapter);
//        }
//    }

//
//    private MyAdapter.OnItemClickListener getListener() {
//        return new MyAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Breaches item) {
//                controller.onItemClick(item);
//            }
//        };
//    }


//    public void navigateToDetail(String json) {
//        MainActivity mainActivity = (MainActivity) getActivity();
//        BreachFragment breachFragment = new BreachFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString(Constants.current_breach_intent_key, json);
//        breachFragment.setArguments(bundle);
//        mainActivity.goToWithBackStack(breachFragment);
//    }

    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * (range + 1)) + 20;
            values.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.WHITE);
            set1.setFillColor(Color.WHITE);
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return lineChart.getAxisLeft().getAxisMinimum();
                }
            });

            // create a data object with the data sets
            LineData data = new LineData(set1);
            //data.setValueTypeface(tfLight);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            lineChart.setData(data);
        }
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

}

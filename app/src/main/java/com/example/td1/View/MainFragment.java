package com.example.td1.View;

import android.content.Intent;
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
import com.example.td1.Controller.Controller;
import com.example.td1.Model.Breaches;
import com.example.td1.R;

import java.util.Comparator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MainFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SearchView searchBar;
    private ProgressBar progressBar;

    private Controller controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        searchBar = (SearchView) view.findViewById(R.id.searchViewBar);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //controller.onFilter(newText);
                return true;
            }
        });

        controller = new Controller(this, getContext().getSharedPreferences(Constants.user_sharedpreferences, MODE_PRIVATE));
        controller.start();

        return view;
    }

//    public void updateDesignWhenUserClickedBottomView(String request){
//        this.refreshProjects(request);
//    }

    public void showList(List<Breaches> breachesList) {
        if (breachesList != null) {
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                breachesList.sort(Comparator.comparingInt(Breaches::getPwnCount).reversed());
            }
            mAdapter = new MyAdapter(breachesList, getContext(), getListener());
            recyclerView.setAdapter(mAdapter);
        }
    }


    private MyAdapter.OnItemClickListener getListener() {
        return new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Breaches item) {
                controller.onItemClick(item);
            }
        };
    }


    public void navigateToDetail(String json) {
        MainActivity mainActivity = (MainActivity) getActivity();
//        mainActivity.goTo();
//        Intent breachIntent = new Intent(this.getContext(), BreachFragment.class);
//        breachIntent.putExtra(Constants.current_breach_intent_key, json);
//        startActivity(breachIntent);
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

}

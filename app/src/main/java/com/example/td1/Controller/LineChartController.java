package com.example.td1.Controller;

import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;

import com.example.td1.Model.Breaches;
import com.example.td1.Model.HibpRestAPI;
import com.example.td1.View.BarChartFragment;
import com.example.td1.View.LineChartFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LineChartController {

    private static final String BASE_URL = "https://haveibeenpwned.com/api/v3/";
    private LineChartFragment fragment;
    private SharedPreferences sharedPreferences;
    private  List<Breaches> breachesList;


    public LineChartController(LineChartFragment fragment, SharedPreferences sharedPreferences) {
        this.fragment = fragment;
        this.sharedPreferences = sharedPreferences;
    }

    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        HibpRestAPI gerritAPI = retrofit.create(HibpRestAPI.class);

        fragment.showProgressBar();
        fragment.hideBarChart();
        Call<List<Breaches>> call = gerritAPI.getBreachesList();
        call.enqueue(new Callback<List<Breaches>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<Breaches>> call, Response<List<Breaches>> response) {
                if(response.isSuccessful()) {
//                    fragment.displayToast("Connection successfull");
                    breachesList = response.body();
                    storeDataToCache();
                } else {
                    fragment.displayToast("Connection failure");
                    System.out.println(response.errorBody());
                    getDataFromCache();
                }

                if (breachesList != null)
                    for (Breaches breach : breachesList)
                        breach.setBreachLocalDate(LocalDate.parse(breach.getBreachDate()));

                fragment.updateGraph(breachesList);
                fragment.hideProgressBar();
                fragment.showBarChart();
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onFailure(Call<List<Breaches>> call, Throwable t) {
                fragment.displayToast("Connection failure");
                getDataFromCache();
                if (breachesList != null)
                    for (Breaches breach : breachesList)
                        breach.setBreachLocalDate(LocalDate.parse(breach.getBreachDate()));
                fragment.updateGraph(breachesList);
                fragment.hideProgressBar();
                fragment.showBarChart();
            }
        });
    }


    private void storeDataToCache() {
        String breachesListJsonString = new Gson().toJson(breachesList);
        sharedPreferences.edit().putString(Constants.breaches_list_json_string, breachesListJsonString).apply();
    }

    private void getDataFromCache() {
        String breachesListJsonString = sharedPreferences.getString(Constants.breaches_list_json_string, null);
        if (breachesListJsonString != null && !TextUtils.isEmpty(breachesListJsonString)) {
            breachesList = new Gson().fromJson(breachesListJsonString, new TypeToken<List<Breaches>>(){}.getType());
        }
        else
            breachesList = new ArrayList<Breaches>();
    }

    public void onItemClick(int item) {
        Gson gson = new Gson();
        String json = gson.toJson(breachesList.get(item));
        fragment.navigateToDetail(json);
    }

//    public void onFilter(String filtre) {
//        List<Breaches> filteredBreachesList = new ArrayList<>();
//        for (Breaches breach : breachesList)
//        {
//            if (breach.getTitle().toLowerCase().contains(filtre.toLowerCase()) || breach.getName().toLowerCase().contains(filtre.toLowerCase()) || breach.getDomain().toLowerCase().contains(filtre.toLowerCase()))
//                filteredBreachesList.add(breach);
//        }
//        fragment.showList(filteredBreachesList);
//        fragment.updateGraph(breachList);
//    }
}
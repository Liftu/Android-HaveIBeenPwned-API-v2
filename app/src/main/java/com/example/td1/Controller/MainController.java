package com.example.td1.Controller;

import android.content.SharedPreferences;

import com.example.td1.Model.Breaches;
import com.example.td1.Model.HibpRestAPI;
import com.example.td1.View.MainActivity;
import com.example.td1.View.MainFragment;
import com.google.gson.reflect.TypeToken;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainController {

    private static final String BASE_URL = "https://haveibeenpwned.com/api/v3/";
    private MainFragment fragment;
    private SharedPreferences sharedPreferences;
    private List<Breaches> breachesList;


    public MainController(MainFragment fragment, SharedPreferences sharedPreferences) {
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
        Call<List<Breaches>> call = gerritAPI.getBreachesList();
        call.enqueue(new Callback<List<Breaches>>() {
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
                fragment.hideProgressBar();
                fragment.showList(breachesList);
            }

            @Override
            public void onFailure(Call<List<Breaches>> call, Throwable t) {
                fragment.displayToast("Connection failure");
                getDataFromCache();
                fragment.hideProgressBar();
                fragment.showList(breachesList);
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


    public void onFilter(String filtre) {
        List<Breaches> filteredBreachesList = new ArrayList<>();
        for (Breaches breach : breachesList)
        {
            if (breach.getTitle().toLowerCase().contains(filtre.toLowerCase()) || breach.getName().toLowerCase().contains(filtre.toLowerCase()) || breach.getDomain().toLowerCase().contains(filtre.toLowerCase()))
                filteredBreachesList.add(breach);
        }
        fragment.showList(filteredBreachesList);
    }


    public void onItemClick(Breaches item) {
        Gson gson = new Gson();
        String json = gson.toJson(item);
        fragment.navigateToDetail(json);
    }
}
package com.example.td1;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller {

    static final String BASE_URL = "https://haveibeenpwned.com/api/v2/";

    private MainActivity view;

    private  List<Breaches> breachesList;

    public Controller(MainActivity view) {
        this.view = view;
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

        Call<List<Breaches>> call = gerritAPI.getBreachesList();
        call.enqueue(new Callback<List<Breaches>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Breaches>> call, Response<List<Breaches>> response) {
                if(response.isSuccessful()) {
                    breachesList = response.body();
                    view.showList(breachesList);
                } else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Breaches>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClickFilter(String filtre) {
        // TODO
        List<Breaches> filteredBreachesList = new ArrayList<>();
        for (Breaches breach : breachesList)
        {
            if (breach.getTitle().toLowerCase().indexOf(filtre.toLowerCase()) != -1)
                filteredBreachesList.add(breach);
        }
        view.showList(filteredBreachesList);
    }


    public void onItemClick(Breaches item) {
        Gson gson = new Gson();
        String json = gson.toJson(item);
        view.navigateToDetail(json);
    }
}
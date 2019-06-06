package com.example.td1.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HibpRestAPI {

    @GET("breaches/")
    Call<List<Breaches>> getBreachesList();
}
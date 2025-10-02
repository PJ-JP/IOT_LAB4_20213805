package com.example.labiot.retrofitHelpers;

import com.example.labiot.entity.Deporte;
import com.example.labiot.entity.Locacion;
import com.example.labiot.entity.Pronostico;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocacionesService {
    //http://api.weatherapi.com/v1/  +  search.json  +  ?key=ec24b1c6dd8a4d528c1205500250305&q={location}
    @GET("search.json")
    Call<List<Locacion>> buscarLocacion(
            @Query("key") String apiKey,
            @Query("q") String locacion
    );

    @GET("forecast.json")
    Call<Pronostico> buscarPronostico(
            @Query("key") String apiKey,
            @Query("q") String locacion,
            @Query("days") int dias
    );

    @GET("sports.json")
    Call<Deporte> buscarDeporte(
            @Query("key") String apiKey,
            @Query("q") String locacion
    );
}

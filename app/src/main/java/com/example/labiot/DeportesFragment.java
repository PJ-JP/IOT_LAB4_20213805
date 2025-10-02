package com.example.labiot;

import static com.example.labiot.entity.APIKey.API_KEY;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.labiot.databinding.FragmentDeportesBinding;
import com.example.labiot.databinding.FragmentLocationBinding;
import com.example.labiot.entity.Deporte;
import com.example.labiot.entity.Locacion;
import com.example.labiot.retrofitHelpers.LocacionesService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DeportesFragment extends Fragment {

    FragmentDeportesBinding binding;
    LocacionesService locacionesService;

    private static String TAG = "msg";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDeportesBinding.inflate(inflater, container, false);
        binding.button.setOnClickListener( view -> {
            buscarDeportes();
        });
        binding.buttonToLocation.setOnClickListener(view -> {
            NavController navController = NavHostFragment.findNavController(DeportesFragment.this);
            //navController.navigate(LocationFragmentDirections.actionLocationFragmentToDeportesFragment());
            navController.navigate(R.id.action_deportesFragment_to_locationFragment);
        });
        binding.buttonToPronosticos.setOnClickListener(view -> {
            NavController navController = NavHostFragment.findNavController(DeportesFragment.this);
            navController.navigate(DeportesFragmentDirections.actionDeportesFragmentToPronosticosFragment());
        });


        createRetrofitService();
        return binding.getRoot();
    }


    public void createRetrofitService() {
        locacionesService = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LocacionesService.class);
    }

    public void buscarDeportes() {
        String s = binding.textInputLayout.getEditText().getText().toString();
        cargarListaWebService(s);
    }
    public void cargarListaWebService(String s) {
        locacionesService.buscarDeporte(API_KEY, s).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Deporte> call, Response<Deporte> response) {
                if (response.isSuccessful()) {
                    Deporte deporte = response.body();
                    DeporteAdapter deporteAdapter = new DeporteAdapter();
                    deporteAdapter.setListaJuegos(deporte.getFootball());
                    deporteAdapter.notifyDataSetChanged();

                    deporteAdapter.setContext(getContext());
                    binding.recyclerView.setAdapter(deporteAdapter);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                } else {
                    Log.d(TAG, "response unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<Deporte> call, Throwable t) {
                Log.d(TAG, "algo pasó!!!");
                Log.d(TAG, t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
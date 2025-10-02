package com.example.labiot;

import static com.example.labiot.entity.APIKey.API_KEY;

import android.content.Context;
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
import android.widget.EditText;

import com.example.labiot.databinding.FragmentLocationBinding;
import com.example.labiot.entity.Locacion;
import com.example.labiot.entity.LocacionDTO;
import com.example.labiot.retrofitHelpers.LocacionesService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LocationFragment extends Fragment {

    FragmentLocationBinding binding;
    LocacionesService locacionesService;

    private static String TAG = "msg";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLocationBinding.inflate(inflater, container, false);
        binding.button.setOnClickListener( view -> {
            buscarLocacion();
        });
        binding.buttonToDeportes.setOnClickListener(view -> {
            NavController navController = NavHostFragment.findNavController(LocationFragment.this);
            //navController.navigate(LocationFragmentDirections.actionLocationFragmentToDeportesFragment());
            navController.navigate(R.id.action_pronosticosFragment_to_locationFragment);
        });
        binding.buttonToPronosticos.setOnClickListener(view -> {
            NavController navController = NavHostFragment.findNavController(LocationFragment.this);
            navController.navigate(LocationFragmentDirections.actionLocationFragmentToPronosticosFragment());
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

    public void buscarLocacion() {
        String s = binding.textInputLayout.getEditText().getText().toString();
        cargarListaWebService(s);
    }
    public void cargarListaWebService(String s) {
        locacionesService.buscarLocacion(API_KEY, s).enqueue(new Callback<List<Locacion>>() {
            @Override
            public void onResponse(Call<List<Locacion>> call, Response<List<Locacion>> response) {
                if (response.isSuccessful()) {
                    List<Locacion> locacionList = response.body();
                    LocacionAdapter locacionAdapter = new LocacionAdapter();
                    locacionAdapter.setListaLocaciones(locacionList);
                    locacionAdapter.notifyDataSetChanged();

                    locacionAdapter.setContext(getContext());
                    binding.recyclerView.setAdapter(locacionAdapter);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                } else {
                    Log.d(TAG, "response unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<List<Locacion>> call, Throwable t) {
                Log.d(TAG, "algo pasó!!!");
                Log.d(TAG, t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
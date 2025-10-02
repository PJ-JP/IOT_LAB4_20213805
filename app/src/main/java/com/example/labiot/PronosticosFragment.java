package com.example.labiot;

import static com.example.labiot.entity.APIKey.API_KEY;

import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.labiot.databinding.FragmentPronosticosBinding;
import com.example.labiot.entity.Pronostico;
import com.example.labiot.retrofitHelpers.LocacionesService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PronosticosFragment extends Fragment implements SensorEventListener {

    FragmentPronosticosBinding binding;
    LocacionesService locacionesService;
    private SensorManager sensorManager;
    private String id;
    private int days;

    private Sensor accelerometer;
    private static String TAG = "msg-p";
    boolean isShowing=false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentPronosticosBinding.inflate(inflater, container, false);
        binding.button.setOnClickListener( view -> {
            buscarPronosticos();
        });
        binding.buttonToDeportes.setOnClickListener(view -> {
            NavController navController = NavHostFragment.findNavController(PronosticosFragment.this);
            navController.navigate(R.id.action_pronosticosFragment_to_deportesFragment);
        });
        binding.buttonToLocation.setOnClickListener(view -> {
            NavController navController = NavHostFragment.findNavController(PronosticosFragment.this);
            navController.navigate(PronosticosFragmentDirections.actionPronosticosFragmentToLocationFragment());
        });

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        createRetrofitService();
        if (getArguments().getInt("days")==3) {
            //Log.d(TAG, getArguments().toString());
            id = getArguments().getString("id");
            //Log.d(TAG, id);
            days = getArguments().getInt("days");
            //Log.d(TAG, String.valueOf(days));
            binding.textInputLayout.getEditText().setText(id);
            binding.textInputLayout2.getEditText().setText(String.valueOf(days));
            cargarListaWebService("id:"+id,days);
        }
        return binding.getRoot();
    }


    public void createRetrofitService() {
        locacionesService = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LocacionesService.class);
    }

    public void buscarPronosticos() {
        String s1 = "id:"+binding.textInputLayout.getEditText().getText().toString();
        Integer s2 = Integer.parseInt(binding.textInputLayout2.getEditText().getText().toString());
        Log.d(TAG, s1);
        Log.d(TAG, s2.toString());
        if(s1 != null && s2 != null){
            if(s2 > 0 && s2 < 15)
                cargarListaWebService(s1,s2);
        }
    }
    public void cargarListaWebService(String s1, int s2) {
        locacionesService.buscarPronostico(API_KEY,s1,s2).enqueue(new Callback<Pronostico>() {

            @Override
            public void onResponse(Call<Pronostico> call, Response<Pronostico> response) {
                if (response.isSuccessful()) {
                    Pronostico pronostico = response.body();

                    PronosticoAdapter pronosticoAdapter = new PronosticoAdapter();
                    pronosticoAdapter.setListaPronosticos(pronostico.getForecast().getForecastday());
                    pronosticoAdapter.notifyDataSetChanged();

                    pronosticoAdapter.setContext(getContext());
                    binding.recyclerView.setAdapter(pronosticoAdapter);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                } else {
                    Log.d(TAG, "response unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<Pronostico> call, Throwable t) {
                Log.d(TAG, "algo pasó!!!");
                Log.d(TAG, t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onStop(){
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            float magnitude = (float) Math.sqrt(x * x + y * y + z * z);
            if ( magnitude>= 20) {
                abrirDialog();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
    public void abrirDialog(){
        if (isShowing) return;
        isShowing = true;
        new AlertDialog.Builder(getContext())
                .setTitle("Limpiar")
                .setMessage("¿Desea limpiar la lista?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    binding.recyclerView.setAdapter(null);
                })
                .setNegativeButton("No", (dialog, which) -> {
                    isShowing = false;
                    dialog.dismiss();
                })
                .show();
    }
}




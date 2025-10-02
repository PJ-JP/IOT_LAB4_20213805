package com.example.labiot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;


import com.example.labiot.databinding.IrvLocacionBinding;
import com.example.labiot.entity.Locacion;

import java.util.List;

public class LocacionAdapter extends RecyclerView.Adapter<LocacionAdapter.LocacionViewHolder> {

    private List<Locacion> listaLocaciones;
    private Context context;
    private static String TAG = "msg-fffffffff";

    @NonNull
    @Override
    public LocacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        IrvLocacionBinding binding = IrvLocacionBinding.inflate(inflater, parent, false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.irv_locacion, parent, false);
        return new LocacionViewHolder(binding);
        //return new LocacionViewHolder(IrvLocacionBinding.bind(view));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LocacionViewHolder holder, int position) {
        Locacion locacion = listaLocaciones.get(position);
        holder.locacion = locacion;
        ViewGroup viewGroup = holder.binding.getRoot();
        viewGroup.setOnClickListener(v -> {
            PronosticosFragment fragment = new PronosticosFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(locacion.getId()));
            bundle.putInt("days", 3);
            fragment.setArguments(bundle);
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .addToBackStack(null)
                    .commit();
        });
        TextView textView1 = holder.binding.textView1;
        textView1.setText("ID:       ");
        TextView textView2 = holder.binding.textView2;
        textView2.setText("Nombre:   ");
        TextView textView3 = holder.binding.textView3;
        textView3.setText("Región:   ");
        TextView textView4 = holder.binding.textView4;
        textView4.setText("País:     ");
        TextView textView5 = holder.binding.textView5;
        textView5.setText("Latitud:  ");
        TextView textView6 = holder.binding.textView6;
        textView6.setText("Longitud: ");
        TextView textView7 = holder.binding.textView7;
        textView7.setText("URL:      ");
        TextView textView8 = holder.binding.textView8;
        textView8.setText(String.valueOf(locacion.getId()));
        TextView textView9 = holder.binding.textView9;
        textView9.setText(locacion.getName());
        TextView textView10 = holder.binding.textView10;
        textView10.setText(locacion.getRegion()+"-");
        TextView textView11 = holder.binding.textView11;
        textView11.setText(locacion.getCountry());
        TextView textView12 = holder.binding.textView12;
        textView12.setText(String.valueOf(locacion.getLat()));
        TextView textView13 = holder.binding.textView13;
        textView13.setText(String.valueOf(locacion.getLon()));
        TextView textView14 = holder.binding.textView14;
        textView14.setText(locacion.getUrl());
    }

    public static class LocacionViewHolder extends RecyclerView.ViewHolder {

        IrvLocacionBinding binding;
        Locacion locacion;

        public LocacionViewHolder(IrvLocacionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public int getItemCount() {
        return listaLocaciones.size();
    }
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Locacion> getListaEmpleados() {
        return listaLocaciones;
    }
    public void setListaLocaciones(List<Locacion> listaLocaciones) {
        this.listaLocaciones = listaLocaciones;
    }
}

package com.example.labiot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.labiot.entity.Pronostico;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.labiot.databinding.IrvLocacionBinding;
import com.example.labiot.entity.Locacion;

import java.util.List;

public class PronosticoAdapter extends RecyclerView.Adapter<PronosticoAdapter.PronosticoViewHolder> {

    private List<Pronostico.ForecastDay> listaPronosticos;
    private Context context;

    @NonNull
    @Override
    public PronosticoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        IrvLocacionBinding binding = IrvLocacionBinding.inflate(inflater, parent, false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.irv_locacion, parent, false);
        return new PronosticoViewHolder(binding);
        //return new LocacionViewHolder(IrvLocacionBinding.bind(view));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PronosticoViewHolder holder, int position) {
        Pronostico.ForecastDay pronostico = listaPronosticos.get(position);
        holder.pronostico = pronostico;
        TextView textView1 = holder.binding.textView1;
        textView1.setText("Atributo");
        TextView textView2 = holder.binding.textView2;
        textView2.setText("Fecha:");
        TextView textView3 = holder.binding.textView3;
        textView3.setText("°T máxima:");
        TextView textView4 = holder.binding.textView4;
        textView4.setText("°T mínima:");
        TextView textView5 = holder.binding.textView5;
        textView5.setText("°T promedio:");
        TextView textView6 = holder.binding.textView6;
        textView6.setText("Humedad promedio:");
        TextView textView7 = holder.binding.textView7;
        textView7.setText("Condición del clima:");
        TextView textView8 = holder.binding.textView8;
        textView8.setText("Valor");
        TextView textView9 = holder.binding.textView9;
        textView9.setText(pronostico.getDate());
        TextView textView10 = holder.binding.textView10;
        textView10.setText(pronostico.getDay().getMaxtemp_c()+" °C");
        TextView textView11 = holder.binding.textView11;
        textView11.setText(pronostico.getDay().getMintemp_c()+" °C");
        TextView textView12 = holder.binding.textView12;
        textView12.setText(pronostico.getDay().getAvgtemp_c()+" °C");
        TextView textView13 = holder.binding.textView13;
        textView13.setText(String.valueOf(pronostico.getDay().getAvghumidity()));
        TextView textView14 = holder.binding.textView14;
        textView14.setText(pronostico.getDay().getCondition().getText());



    }

    public static class PronosticoViewHolder extends RecyclerView.ViewHolder {

        IrvLocacionBinding binding;
        Pronostico.ForecastDay pronostico;

        public PronosticoViewHolder(IrvLocacionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public int getItemCount() {
        return listaPronosticos.size();
    }
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Pronostico.ForecastDay> getListaPronosticos() {
        return listaPronosticos;
    }
    public void setListaPronosticos(List<Pronostico.ForecastDay> listaPronosticos) {
        this.listaPronosticos= listaPronosticos;
    }
}

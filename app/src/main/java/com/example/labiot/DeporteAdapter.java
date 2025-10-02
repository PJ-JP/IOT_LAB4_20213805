package com.example.labiot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.labiot.databinding.IrvLocacionBinding;
import com.example.labiot.entity.Deporte;
import com.example.labiot.entity.Locacion;

import java.util.List;

public class DeporteAdapter extends RecyclerView.Adapter<DeporteAdapter.DeporteViewHolder> {

    private List<Deporte.FootballMatch> listaJuegos;
    private Context context;

    @NonNull
    @Override
    public DeporteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        IrvLocacionBinding binding = IrvLocacionBinding.inflate(inflater, parent, false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.irv_locacion, parent, false);
        return new DeporteViewHolder(binding);
        //return new LocacionViewHolder(IrvLocacionBinding.bind(view));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DeporteViewHolder holder, int position) {
        Deporte.FootballMatch footballMatch = listaJuegos.get(position);
        holder.footballMatch = footballMatch;

        TextView textView1 = holder.binding.textView1;
        textView1.setText("Football:       ");
        TextView textView2 = holder.binding.textView2;
        textView2.setText("Estadio:  ");
        TextView textView3 = holder.binding.textView3;
        textView3.setText("País:   ");
        TextView textView4 = holder.binding.textView4;
        textView4.setText("Región:     ");
        TextView textView5 = holder.binding.textView5;
        textView5.setText("Torneo:  ");
        TextView textView6 = holder.binding.textView6;
        textView6.setText("Inicio: ");
        TextView textView7 = holder.binding.textView7;
        textView7.setText("Juegan:      ");
        TextView textView8 = holder.binding.textView8;
        textView8.setText(".");
        TextView textView9 = holder.binding.textView9;
        textView9.setText(footballMatch.getStadium());
        TextView textView10 = holder.binding.textView10;
        textView10.setText(footballMatch.getCountry());
        TextView textView11 = holder.binding.textView11;
        textView11.setText(footballMatch.getRegion());
        TextView textView12 = holder.binding.textView12;
        textView12.setText(footballMatch.getTournament());
        TextView textView13 = holder.binding.textView13;
        textView13.setText(footballMatch.getStart());
        TextView textView14 = holder.binding.textView14;
        textView14.setText(footballMatch.getMatch());

    }

    public static class DeporteViewHolder extends RecyclerView.ViewHolder {

        IrvLocacionBinding binding;
        Deporte.FootballMatch footballMatch;

        public DeporteViewHolder(IrvLocacionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public int getItemCount() {
        return listaJuegos.size();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Deporte.FootballMatch> getListaJuegos() {
        return listaJuegos;
    }

    public void setListaJuegos(List<Deporte.FootballMatch> listaJuegos) {
        this.listaJuegos = listaJuegos;
    }
}
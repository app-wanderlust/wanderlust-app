package br.ufrpe.wanderlustapp.pratoTipico.gui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;

public class AdapterPratoTipico extends RecyclerView.Adapter<AdapterPratoTipico.MyViewHolder> {
    @NonNull
    @Override
    public AdapterPratoTipico.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista_prato, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPratoTipico.MyViewHolder holder, int position) {
        PratoTipico pratoTipico =

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

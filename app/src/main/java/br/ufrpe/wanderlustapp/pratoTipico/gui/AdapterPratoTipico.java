package br.ufrpe.wanderlustapp.pratoTipico.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.zip.Inflater;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.pratoTipico.persistencia.PratoTipicoDAO;

public class AdapterPratoTipico extends RecyclerView.Adapter<AdapterPratoTipico.MyViewHolder> {

    private List<PratoTipico> pratoTipicoList;
    private Context context;
    //private LayoutInflater inflater;
    private PratoTipicoDAO pratoTipicoDAO;

    public AdapterPratoTipico(List<PratoTipico> pratoTipicoList) {
        pratoTipicoList = pratoTipicoDAO.getListPrato();
        this.pratoTipicoList = pratoTipicoList;
    }

    @NonNull
    @Override
    public AdapterPratoTipico.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista_prato, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPratoTipico.MyViewHolder holder, int position) {
        PratoTipico pratoTipico = pratoTipicoList.get(position);
        holder.prato.setText(pratoTipico.getNome());
        holder.pais.setText(pratoTipico.getCidade().getPais().getNome());
        holder.cidade.setText(pratoTipico.getCidade().getNome());
    }

    @Override
    public int getItemCount() {
        return pratoTipicoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView prato;
        TextView pais;
        TextView cidade;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            prato = itemView.findViewById(R.id.textNomePratoId);
            pais = itemView.findViewById(R.id.textPaisId);
            cidade = itemView.findViewById(R.id.textCidadeId);
        }
    }
}

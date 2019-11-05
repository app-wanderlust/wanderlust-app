package br.ufrpe.wanderlustapp.pratoTipico.gui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import br.ufrpe.wanderlustapp.R;

public class CadastrarPratosFragment extends Fragment {

    public static CadastrarPratosFragment newInstance(){
        return new CadastrarPratosFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lista_pratos,container,false);
        return view;
    }


}

package br.ufrpe.wanderlustapp.conversa.gui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import br.ufrpe.wanderlustapp.R;
import br.ufrpe.wanderlustapp.usuario.gui.HomeFragment;

public class ChatBotFragment extends Fragment {

    public static ChatBotFragment newInstance(){
        return new ChatBotFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chatbot,container,false);
        return view;
    }


}

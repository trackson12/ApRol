package com.example.aprol.ui.Usuario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aprol.R;

public class Fragment_Registro extends Fragment {
    private View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.registro, container, false);



        return root;
    }
    public static Fragment_Registro newInstance(String tipo) {

        Bundle b = new Bundle();
        b.putString("tipo", tipo);

        Fragment_Registro f= new Fragment_Registro();
        f.setArguments(b);



        return f;
    }
}

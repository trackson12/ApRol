package com.example.aprol.ui.Juegos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.aprol.R;

public class Fragment_Juegos extends Fragment {
    private View root;
    ConstraintLayout constr;
    private LinearLayout linla;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.fragment_lista_juegos, container, false);
        constr = (ConstraintLayout) root.findViewById(R.id.rvFragmentJuego);
        linla = (LinearLayout) root.findViewById(R.id.LinLjueg);
        return root;
    }
}




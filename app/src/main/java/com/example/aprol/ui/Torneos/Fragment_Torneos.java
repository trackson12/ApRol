package com.example.aprol.ui.Torneos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.aprol.R;

public class Fragment_Torneos extends Fragment {
    private View root;
    ListView torneoView;
    ConstraintLayout constr;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.fragment_lista_torneos, container, false);
        constr = (ConstraintLayout) root.findViewById(R.id.rvFragmentJuego);
        torneoView = (ListView) root.findViewById(R.id.tournament_list);
        return root;
    }
}

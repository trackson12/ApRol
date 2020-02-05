package com.example.aprol.ui.Torneos;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.aprol.R;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class Fragment_Torneos extends Fragment {
    private View root;
    ListView torneoView;
    ConstraintLayout constr;
    private TextView mEntradaTexto;
    private static final int captura_voz=100;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.fragment_lista_torneos, container, false);
        constr = (ConstraintLayout) root.findViewById(R.id.rvFragmentJuego);
        torneoView = (ListView) root.findViewById(R.id.tournament_list);


        iniciarEntradaVoz();




        return root;
    }

    private void iniciarEntradaVoz() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hablame papu");

        try{
            startActivityForResult(intent,captura_voz);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case captura_voz:{
                if (requestCode==RESULT_OK && null != data){
                    ArrayList<String> resul = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mEntradaTexto.setText(resul.get(0));
                    Toast.makeText(getActivity(), mEntradaTexto.getText(), Toast.LENGTH_SHORT).show();

                }
                break;
            }


        }

    }

}

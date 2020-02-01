package com.example.aprol.ui.Usuario;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.aprol.R;

public class Fragment_Usuario extends Fragment {
    private View root;
    private Fragment_Registro f2;
    private Fragment_Usuario f1;
    Button b1;
    private Context context;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.login, container, false);
        b1 = (Button) root.findViewById(R.id.btnRegistrar);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Fragment_Registro dj= new Fragment_Registro();
                FragmentTransaction transaction = getFragmentManager().beginTransaction().replace(R.id.loginlayout,dj);
                transaction.addToBackStack(null);
                transaction.commit();

                getFragmentManager().beginTransaction().hide(dj);


                Fragment_Usuario fu= new Fragment_Usuario();
                FragmentTransaction tra = getFragmentManager().beginTransaction().replace(R.id.registrolayout,fu);
                tra.addToBackStack(null);
                tra.commit();

                getFragmentManager().beginTransaction().hide(fu);

                /*android.app.Fragment fragment = getActivity().getFragmentManager().findFragmentByTag("YOUR_FRAGMENT_TAG");
                getActivity().getFragmentManager().beginTransaction().hide(fragment);

                getView().setVisibility(View.GONE);

                YourFragmentClass fragment =    (YourFragmentClass)fm.findFragmentById(R.id.your_fragment_id);
                fragment.yourPublicMethod();
                Fragment_Usuario fu= new Fragment_Usuario();
                FragmentTransaction tra = getFragmentManager().beginTransaction().replace(R.id.registrolayout,fu);
                tra.addToBackStack(null);
                tra.commit();


                getView().setVisibility(View.VISIBLE);*/

            }


        });
        return root;
    }

    private FragmentManager getSupportFragmentManager() {

        return null;
    }


}




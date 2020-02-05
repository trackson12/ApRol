package com.example.aprol.ui.Usuario;

import android.content.Context;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.aprol.R;
import com.example.aprol.objeto.Cliente;
import com.example.aprol.rest.APIUtils;
import com.example.aprol.rest.RestCliente;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Usuario extends Fragment {
    private View root;
    private Fragment_Registro f2;
    private Fragment_Usuario f1;
    Button b1;
    Button aceptar;
    EditText usuario;
    EditText pass;
    private Context context;
    RestCliente clienteRest;
    APIUtils util;
    private SensorManager sensorManager;

    Calendar cal = Calendar.getInstance();

    Date date = cal.getTime();
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    String date1 = format1.format(date);
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.login, container, false);
        b1 = (Button) root.findViewById(R.id.btnRegistrar);
        aceptar = (Button) root.findViewById(R.id.btnAceptar);
        usuario = (EditText) root.findViewById(R.id.etUsuLogin);
        pass = (EditText) root.findViewById(R.id.etPwdLogin);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

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

        if(isNetworkAvailable()) {
            clienteRest = APIUtils.getService();

            aceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Call<Cliente> call = clienteRest.findById(usuario.getText().toString());
                    call.enqueue(new Callback<Cliente>() {
                        @Override
                        public void onResponse(Call<Cliente> call, Response<Cliente> response) {

                            if (response.isSuccessful()){
                                Toast.makeText(getContext(), "Existe el cliente", Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(getContext(), "8------D", Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<Cliente> call, Throwable t) {

                        }
                    });
                }
            });
        }else{
            Toast.makeText(getContext(), "Es necesaria una conexión a internet", Toast.LENGTH_SHORT).show();
        }


        return root;
    }

    private FragmentManager getSupportFragmentManager() {

        return null;
    }

    private boolean isNetworkAvailable() {
        /*
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

         */
        ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}




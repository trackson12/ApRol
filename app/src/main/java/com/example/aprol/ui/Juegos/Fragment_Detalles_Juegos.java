package com.example.aprol.ui.Juegos;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aprol.R;
import com.example.aprol.objeto.Cliente;
import com.example.aprol.objeto.Juego;
import com.example.aprol.rest.APIUtils;
import com.example.aprol.rest.RestJuego;
import com.example.aprol.ui.Usuario.RegistroActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Detalles_Juegos extends Fragment {
    private View root;
    TextView tvNombreJuegosDetalle,tvNumeroJugadoresDetalle,tvDescripcionJuegosDetalle;
    ImageView ivFotoJuegosDetalle;
    RestJuego juegoRest;
    Button añadirJuego,añadirFoto;
    private String imagen;

    private static final int GALERIA = 1 ;
    private static final int CAMARA = 2 ;
    private String imagenFinal;
    private Juego juego;

    public static Fragment_Detalles_Juegos newInstance(String tipo) {

        Bundle b = new Bundle();
        b.putString("tipo", tipo);

        Fragment_Detalles_Juegos f= new Fragment_Detalles_Juegos();
        f.setArguments(b);



        return f;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        switch (id){
            case R.id.it_detallesJuegos:
                getActivity().onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    public static Fragment_Detalles_Juegos newInstance(String tipo, Juego j) {

        Bundle b = new Bundle();
        b.putString("tipo", tipo);
        b.putSerializable("Juego", j);

        Fragment_Detalles_Juegos f= new Fragment_Detalles_Juegos();
        f.setArguments(b);



        return f;
    }

    /*public View onCreate(@NonNull LayoutInflater inflater, @Nullable ViewGroup container){
        root=inflater.inflate(R.layout.fragment_detalle_juego, container, false);
        tvNombreJuegosDetalle=(TextView)root.findViewById(R.id.tvNombreDetJuego);
        tvNumeroJugadoresDetalle=(TextView) root.findViewById(R.id.tvNjugDetJuego);
        tvDescripcionJuegosDetalle=(TextView) root.findViewById(R.id.tvDescripcionDetJuego);
        ivFotoJuegosDetalle=(ImageView)root.findViewById(R.id.ivImgDetJuego);


        Bundle b = getArguments();
         Juego juego = (Juego) b.getSerializable("Juego");


        tvNombreJuegosDetalle.setText(juego.getNombre());
        tvNumeroJugadoresDetalle.setText(juego.getN_jugadores());
        tvDescripcionJuegosDetalle.setText(juego.getDescripcion());

        //Picasso.with(this).load(juegos.getImagen()).into(ivFotoJuegosDetalle);

        return root;
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        root=inflater.inflate(R.layout.fragment_detalle_juego, container, false);
        tvNombreJuegosDetalle=(TextView)root.findViewById(R.id.tvNombreDetJuego);
        tvNumeroJugadoresDetalle=(TextView) root.findViewById(R.id.tvNumeroDetJuego);
        tvDescripcionJuegosDetalle=(TextView) root.findViewById(R.id.tvDescripcionDetJuego);
        ivFotoJuegosDetalle=(ImageView)root.findViewById(R.id.ivImgDetJuego);
        añadirFoto=(Button)root.findViewById(R.id.btnAñadirFotoJuego) ;
        añadirJuego=(Button)root.findViewById(R.id.btnInsertarJuego);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        pedirMultiplesPermisos();
        añadirFoto.setOnClickListener(v -> mostrarDialogoFoto());





        Bundle b = getArguments();
       if(b.getString("tipo").equals("crear")){
            añadirJuego.setVisibility(View.VISIBLE);
            añadirFoto.setVisibility(View.VISIBLE);
       }
       if(b.getString("tipo").equals("ver")){
           Juego juego = (Juego) b.getSerializable("Juego");
           if(juego ==null){
               Log.e("joder","aaaaa");
           }

           tvNombreJuegosDetalle.setText(juego.getNombre());
           tvNumeroJugadoresDetalle.setText(juego.getN_jugadores());
           tvDescripcionJuegosDetalle.setText(juego.getDescripcion());
           //Picasso.with(getContext()).load(imagen).into(ivFotoJuegosDetalle);
           añadirJuego.setVisibility(View.GONE);
           añadirFoto.setVisibility(View.GONE);
       }



        if(isNetworkAvailable()) {
            juegoRest = APIUtils.getServiceJuego();
            //getActivity().onBackPressed();
            añadirJuego.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Juego j = new Juego(tvNombreJuegosDetalle.getText().toString(), imagenFinal,tvNumeroJugadoresDetalle.getText().toString(),tvDescripcionJuegosDetalle.getText().toString());
                    Call<Juego> call = juegoRest.create(j);
                    call.enqueue(new Callback<Juego>() {
                        // Si todo ok
                        @Override
                        public void onResponse(Call<Juego> call, Response<Juego> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(getContext(), "Juego guardado", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getContext(), "Error al guardar Juego", Toast.LENGTH_SHORT).show();

                            }

                        }

                        // Si error
                        @Override
                        public void onFailure(Call<Juego> call, Throwable t) {
                            Log.e("ERROR: ", t.getMessage());
                        }
                    });
                }
            });

        }else{
            Toast.makeText(getContext(), "Es necesaria una conexión a internet", Toast.LENGTH_SHORT).show();

        }
        return  root;
    }
    private void mostrarDialogoFoto(){
        AlertDialog.Builder fotoDialogo= new AlertDialog.Builder(getContext());
        fotoDialogo.setTitle("Seleccionar Acción");
        String[] fotoDialogoItems = {
                "Seleccionar fotografía de galería",
                "Capturar fotografía desde la cámara" };
        fotoDialogo.setItems(fotoDialogoItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                elegirFotoGaleria();
                                break;
                            case 1:
                                tomarFotoCamara();
                                break;
                        }
                    }
                });
        fotoDialogo.show();
    }

    public void elegirFotoGaleria() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALERIA);
    }

    //Llamamos al intent de la camara
    // https://developer.android.com/training/camera/photobasics.html#TaskPath
    private void tomarFotoCamara() {
        // Si queremos hacer uso de fotos en aklta calidad
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        // Eso para alta o baja
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        // Esto para alta calidad
        //photoURI = Uri.fromFile(this.crearFichero());
        //intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoURI);

        // Esto para alta y baja
        startActivityForResult(intent, CAMARA);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("FOTO", "Opción::--->" + requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }

        if (requestCode == GALERIA) {
            Log.d("FOTO", "Go to Galeria");
            if (data != null) {
                // Obtenemos su URI con su dirección temporal
                Uri contentURI = data.getData();
                try {
                    // Obtenemos el bitmap de su almacenamiento externo
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), contentURI);
                    imagenFinal=bitmapToBase64(bitmap);
                    //path = salvarImagen(bitmap);
                    Toast.makeText(getActivity(), "¡Foto salvada!", Toast.LENGTH_SHORT).show();
                    this.ivFotoJuegosDetalle.setImageBitmap(bitmap);
                    imagen=bitmapToBase64(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "¡Fallo Galeria!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMARA) {
            Log.d("FOTO", "Entramos en Camara");
            // Cogemos la imagen, pero podemos coger la imagen o su modo en baja calidad (thumbnail
            Bitmap thumbnail = null;
            try {
                // Esta línea para baja
                thumbnail = (Bitmap) data.getExtras().get("data");
                // Esto para alta
                // thumbnail = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), photoURI);
                imagenFinal=bitmapToBase64(thumbnail);
                // salvamos
                // path = salvarImagen(thumbnail); //  photoURI.getPath(); Podríamos poner esto, pero vamos a salvarla comprimida y borramos la no comprimida (por gusto)

                this.ivFotoJuegosDetalle.setImageBitmap(thumbnail);
                imagen=bitmapToBase64(thumbnail);


                // Borramos el fichero de la URI
                //borrarFichero(photoURI.getPath());

                Toast.makeText(getActivity(), "¡Foto Salvada!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "¡Fallo Camara!", Toast.LENGTH_SHORT).show();
            }

        }


    }



    private ByteArrayOutputStream comprimirImagen(Bitmap myBitmap) {
        // Stream de binario
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        // Seleccionamos la calidad y la trasformamos y comprimimos
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 20, bytes);
        return bytes;
    }

    private void pedirMultiplesPermisos(){
        // Indicamos el permisos y el manejador de eventos de los mismos
        Dexter.withActivity(this.getActivity())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // ccomprbamos si tenemos los permisos de todos ellos
                        if (report.areAllPermissionsGranted()) {
                            // Toast.makeText(getContext(), "¡Todos los permisos concedidos!", Toast.LENGTH_SHORT).show();
                        }

                        // comprobamos si hay un permiso que no tenemos concedido ya sea temporal o permanentemente
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // abrimos un diálogo a los permisos
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        //Toast.makeText(getContext(), "Existe errores! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }



    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
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

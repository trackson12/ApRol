package com.example.aprol.ui;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBController extends SQLiteOpenHelper {

    public DBController(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    public  static void insertarData(Context c, String token) {
        //Abrimos la base de datos  en modo escritura
        DBController Login = new DBController(c, "rolstation", null, 1);
        SQLiteDatabase bd = Login.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        if (bd != null) {
            try {
                bd.execSQL("DELETE FROM registro");

                //insertamos el juego recibido con su id
                bd.execSQL("INSERT INTO registro (token) " +
                        "VALUES ('"+token+")");

                //Cerramos la base de datos
                bd.close();
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static  int selectIdUser(Context c) {
        int id = 0;
        DBController Login = new DBController(c, "rolstation", null, 1);
        SQLiteDatabase bd = Login.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        if (bd != null) {
            //Seleccionamos todos
            Cursor cur = bd.rawQuery(" SELECT token FROM registro", null);
            //Nos aseguramos de que existe al menos un registro
            if (cur.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros y se llena la lista de juegos
                id = cur.getInt(0);

            }
            //Cerramos la base de datos
            bd.close();
        }

        return id;
    }

    public static  String  selectToken(Context c) {
        String token="";
        DBController Login = new DBController(c, "rolstation", null, 1);
        SQLiteDatabase bd = Login.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        if (bd != null) {
            //Seleccionamos todos
            Cursor cur = bd.rawQuery(" SELECT nombre FROM registro", null);
            //Nos aseguramos de que existe al menos un registro
            if (cur.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros y se llena la lista de juegos
                token = cur.getString(0);

            }
            //Cerramos la base de datos
            bd.close();
        }

        return token;
    }
}

package com.example.tiendadeportiva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //Funcion para cargar el activity para eliminar datos
    public void ActivityEliminar(View view){
        //Validacion para funcionamiento del codigo
        try {
            //Declaracion del intent con el activity de destino y su inicializacion
            Intent intent = new Intent(this, EliminacionProductos.class);
            startActivity(intent);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void ActivityEditar(View view){
        //Validacion para funcionamiento del codigo
        try {
            //Declaracion del intent con el activity de destino y su inicializacion
            Intent intent = new Intent(this, EditarProductos.class);
            startActivity(intent);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //Funcion para cargar el activity para mostrar los datos
    public void ActivityMostrar(View view){
        //Validacion para funcionamiento del codigo
        try {
            //Declaracion del intent con el activity de destino y su inicializacion
            Intent intent = new Intent(this, TablaProductos.class);
            startActivity(intent);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void ActivityCrear(View view){
        //Validacion para funcionamiento del codigo
        try {
            //Declaracion del intent con el activity de destino y su inicializacion
            Intent intent = new Intent(this, CreacionProductos.class);
            startActivity(intent);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
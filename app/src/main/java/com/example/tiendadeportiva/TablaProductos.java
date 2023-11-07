package com.example.tiendadeportiva;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TablaProductos extends AppCompatActivity {

    //Declaracion de variables
    private ListView ListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla_productos);

        //Asignacion de variables
        ListView = findViewById(R.id.lvListProductos);

        ListView.setBackgroundColor(1);

        //Inicializacion de funcion CargarProductos()
        CargarProductos();
    }

    //Funcion que carga los datos que se obtienen de la base de datos en un ListView
    private void CargarProductos(){
        //Validacion para funcionamiento del codigo
        try {
            //Declaracion de AdminSQLiteOpenHelper con la base de datos destinada
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Productos", null, 1);

            //Declaracion de SQLiteDatabase en modo de lectura
            SQLiteDatabase DataBase = admin.getReadableDatabase();

            //Declaracion de Arraylist y string para proximo uso
            ArrayList<String> Lista = new ArrayList<>();
            String datos = "";

            //Cursor utilizado para traer los datos de la base de datos segun los datos necesarios
            Cursor fila = DataBase.rawQuery("Select IDProducto, NombreProducto, MarcaProducto, DescripcionProducto, ValorUnidad from Productos", null);

            //Validacion para asegurar la existencia de datos
            if (fila.moveToFirst()) {
                //Ciclo para obtener los datos, agregarlos a un string, y añadirlo al arraylist
                do{
                    //String con los datos
                    datos = "ID del producto: "+fila.getString(0)+"\n"+
                            "Nombre del producto: "+fila.getString(1)+"\n"+
                            "Marca del producto: "+fila.getString(2)+"\n"+
                            "Descripcion del producto: "+fila.getString(3)+"\n"+
                            "Valor C/U: $"+fila.getString(4);
                    //Adiccion de string al arraylist
                    Lista.add(datos);
                }while(fila.moveToNext());
                //Cierre de la base de datos
                DataBase.close();
            } else {
                Alerta("No hay datos en el sistema");
            }

            //Declaracion de un array adapter para llenar el ListView con los datos recolectados del ArrayList
            ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Lista);
            ListView.setAdapter(ad);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void Alerta(String mensaje){
        //Validacion para funcionamiento del codigo
        try{
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
package com.example.tiendadeportiva;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EliminacionProductos extends AppCompatActivity {

    //Declaracion de variables
    private EditText ID;
    private ListView ListView2;

    private Button EliminarOne, EliminarAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminacion_productos);

        //Asignacion de variables
        ID = findViewById(R.id.txtIDProducto);
        ListView2 = findViewById(R.id.LvListaEliminar);
        EliminarOne = findViewById(R.id.btnElminarUno);
        EliminarAll = findViewById(R.id.btnEliminarTodo);

        ListView2.setBackgroundColor(1);

        //Se ocupa la funcion OnClick en el boton
        EliminarOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EliminarDato();
            }
        });

        //Se ocupa la funcion OnClick en el boton
        EliminarAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EliminarTodo();
            }
        });

        //Inicializacion de la funcion CargarProductos
        CargarProductos();
    }

    //Funcion para cargar los datos en el ListView
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
            Cursor fila = DataBase.rawQuery("Select IDProducto, NombreProducto, DescripcionProducto from Productos", null);

            //Validacion para asegurar la existencia de datos
            if (fila.moveToFirst()) {
                //Ciclo para obtener los datos, agregarlos a un string, y añadirlo al arraylist
                do{
                    //String con los datos
                    datos = "ID del producto: "+fila.getString(0)+"\n"+
                            "Nombre del producto: "+fila.getString(1)+"\n"+
                            "Descripcion del producto: "+fila.getString(2);
                    //Adiccion de string al arraylist
                    Lista.add(datos);
                }while(fila.moveToNext());
                //Cierre de la base de datos
                DataBase.close();
            } else {
                Alerta("Datos no encontrados");
            }

            //Declaracion de un array adapter para llenar el ListView con los datos recolectados del ArrayList
            ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Lista);
            ListView2.setAdapter(ad);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //Funcion para eliminar un dato especifico
    private void EliminarDato(){
        //Validacion para funcionamiento del codigo
        try{
            //Declaracion de AdminSQLiteOpenHelper con la base de datos destinada
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Productos", null, 1);

            //Declaracion de SQLiteDatabase en modo de escritura
            SQLiteDatabase DataBase = admin.getWritableDatabase();

            //Declaracion de variables
            String IDProductoI = ID.getText().toString();

            //Validacion para campos vacios
            if(IDProductoI.isEmpty()){
                Alerta("No puede quedar campos vacios");
            }else {
                //Conversion de datos
                int IDProductoF = Integer.parseInt(IDProductoI);

                //Se crea la alerta para el boton
                AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

                //Se le ingresa un titulo
                dialogo.setTitle("Pregunta");

                //Se le ingresa un mensaje
                dialogo.setMessage("¿Desea eliminar el elemento?");

                //Se le asigna codigo en el caso de que el usuario decida seleccionar si
                dialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Declaracion de variable para obtener la respuesta de eliminacion
                        int Eliminar = DataBase.delete("Productos", "IDProducto =" + IDProductoF, null);

                        //Cierre de base de datos
                        DataBase.close();

                        //Validacion para comprobar si la eliminacion fue existosa o fallida
                        if (Eliminar < 1) {
                            Alerta("El ID seleccionado no existe o no fue eliminado");
                        } else {
                            //Vaciar campos una vez realizada la eliminacion
                            ID.setText("");
                            Alerta("Dato eliminado exitosamente");
                            CargarProductos();
                        }
                    }
                });

                //Se le asigna codigo en el caso de que el usuario decida seleccionar no
                dialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Alerta("Eliminación cancelada");
                    }
                });

                //Se muestra el dialogo ya configurado
                dialogo.show();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //Funcion para eliminar todos los datos
    private void EliminarTodo(){
        //Validacion para funcionamiento del codigo
        try{
            //Declaracion de AdminSQLiteOpenHelper con la base de datos destinada
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Productos", null, 1);

            //Declaracion de SQLiteDatabase en modo de escritura
            SQLiteDatabase DataBase = admin.getWritableDatabase();

            //Se crea la alerta para el boton
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

            //Se le ingresa un titulo
            dialogo.setTitle("Pregunta");

            //Se le ingresa un mensaje
            dialogo.setMessage("¿Desea eliminar todos los elementos?");

            //Se le asigna codigo en el caso de que el usuario decida seleccionar si
            dialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Declaracion de variable para obtener la respuesta de eliminacion
                    int Eliminar = DataBase.delete("Productos",null,null);

                    //Cierre de base de datos
                    DataBase.close();

                    //Validacion para comprobar si la eliminacion fue existosa o fallida
                    if(Eliminar < 1){
                        Alerta("El ID seleccionado no existe o no fue eliminado");
                    }else{
                        Alerta("Datos eliminados exitosamente");
                        CargarProductos();
                    }
                }
            });

            //Se le asigna codigo en el caso de que el usuario decida seleccionar no
            dialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Alerta("Eliminación cancelada");
                }
            });

            //Se muestra el dialogo ya configurado
            dialogo.show();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void Alerta(String mensaje){
        //Validacion para funcionamiento del codigo
        try {
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
package com.example.tiendadeportiva;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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

public class EditarProductos extends AppCompatActivity {

    //Declaracion de variables
    private ListView ListView3;
    private EditText IDProducto, NombreProducto, MarcaProducto, DescripcionProducto, ValorProducto;

    private Button Editar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_productos);

        //Asignacion de variables
        ListView3 = findViewById(R.id.LvListaEditar);
        IDProducto = findViewById(R.id.txtIDProducto2);
        NombreProducto = findViewById(R.id.txtNombreProducto);
        MarcaProducto = findViewById(R.id.txtMarcaProducto);
        DescripcionProducto = findViewById(R.id.txtDescripcionProducto);
        ValorProducto = findViewById(R.id.txtValorProducto);
        Editar = findViewById(R.id.btnEditar2);

        ListView3.setBackgroundColor(1);

        //Se ocupa la funcion OnClick en el boton
        Editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditarDatos();
            }
        });

        //Se inicializa la funcion de CargarProductos()
        CargarProductos();
    }

    //Funcion para cargar los productos en el list view
    private void CargarProductos() {
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
                    datos = "ID del producto: " + fila.getString(0) + "\n" +
                            "Nombre del producto: " + fila.getString(1) + "\n" +
                            "Marca del producto: " + fila.getString(2) + "\n" +
                            "Descripcion del producto: " + fila.getString(3) + "\n" +
                            "Valor C/U: $" + fila.getString(4);
                    //Adiccion de string al arraylist
                    Lista.add(datos);
                }while (fila.moveToNext());
                //Cierre de la base de datos
                DataBase.close();
            } else {
                Alerta("Datos no encontrados");
            }

            //Declaracion de un array adapter para llenar el ListView con los datos recolectados del ArrayList
            ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Lista);
            ListView3.setAdapter(ad);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    //Funcion para editar datos a traves del ID
    private void EditarDatos(){
        //Validacion para funcionamiento del codigo
        try {
            //Declaracion de AdminSQLiteOpenHelper con la base de datos destinada
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Productos", null, 1);

            //Declaracion de SQLiteDatabase en modo de escritura
            SQLiteDatabase basedatos = admin.getWritableDatabase();

            //Declaracion de variables
            String IDUsuarioI = IDProducto.getText().toString();
            String Nombre = NombreProducto.getText().toString();
            String Marca = MarcaProducto.getText().toString();
            String Descripcion = DescripcionProducto.getText().toString();
            String ValorI = ValorProducto.getText().toString();

            //Validacion de campos vacios
            if (IDUsuarioI.isEmpty() || Nombre.isEmpty() || Marca.isEmpty() || Descripcion.isEmpty() || ValorI.isEmpty()) {
                Alerta("No pueden quedar campos vacios");
            } else {
                //Conversion de datos
                int ValorF = Integer.parseInt(ValorI);
                int IDUsuarioF = Integer.parseInt(IDUsuarioI);

                //Declaracion de contentValues para editar campos
                ContentValues Datos = new ContentValues();
                Datos.put("NombreProducto", Nombre);
                Datos.put("MarcaProducto", Marca);
                Datos.put("DescripcionProducto", Descripcion);
                Datos.put("ValorUnidad", ValorF);

                //Se crea la alerta para el boton
                AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

                //Se le ingresa un titulo
                dialogo.setTitle("Pregunta");

                //Se le ingresa un mensaje
                dialogo.setMessage("¿Seguro que desea editar el registro?");

                //Se le asigna codigo en el caso de que el usuario decida seleccionar si
                dialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Ejecucion de la consulta
                        int Cantidad = basedatos.update("Productos", Datos, "IDProducto =" + IDUsuarioF, null);

                        //Cierre de la base de datos
                        basedatos.close();

                        //Validacion de respuesta de la consulta
                        if (Cantidad < 1) {
                            Alerta("No se encontro el ID");
                        } else {
                            Alerta("La actualizacion se realizo correctamente");
                            IDProducto.setText("");
                            NombreProducto.setText("");
                            MarcaProducto.setText("");
                            DescripcionProducto.setText("");
                            ValorProducto.setText("");
                            CargarProductos();
                        }
                    }
                });

                //Se le asigna codigo en el caso de que el usuario decida seleccionar no
                dialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Alerta("Edición cancelada");
                    }
                });

                //Se muestra el dialogo ya configurado
                dialogo.show();

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    //Funcion para alertar al usuario de una accion realizada
    private void Alerta (String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
}
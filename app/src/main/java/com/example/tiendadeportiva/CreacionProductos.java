package com.example.tiendadeportiva;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreacionProductos extends AppCompatActivity {

    //Declaracion de variables
    private EditText Nombre, Marca, Descripcion, Valor;
    private Button Crear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_productos);

        //Asignacion de variables
        Nombre = findViewById(R.id.txtNombre);
        Marca = findViewById(R.id.txtMarca);
        Descripcion = findViewById(R.id.txtDescripcion);
        Valor = findViewById(R.id.txtValorUnidad);
        Crear = findViewById(R.id.btnCrear2);

        Crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearProducto();
            }
        });
    }

    //Funcion para añadir productos a la base de datos
    private void CrearProducto(){
        //Validacion para funcionamiento del codigo
        try {
            //Declaracion de AdminSQLiteOpenHelper con la base de datos destinada
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Productos", null, 1);

            //Declaracion de SQLiteDatabase en modo de escritura
            SQLiteDatabase DataBase = admin.getWritableDatabase();

            //Declaracion de variables en la funcion CrearProduct()
            String NombreProducto = Nombre.getText().toString();
            String MarcaProducto = Marca.getText().toString();
            String DescripcionProducto = Descripcion.getText().toString();
            String ValorProductoI = Valor.getText().toString();

            //Validacion de campos vacios
            if(NombreProducto.isEmpty() || MarcaProducto.isEmpty() || DescripcionProducto.isEmpty() || ValorProductoI.isEmpty()){
                Alerta("No pueden quedar campos vacios");
            }else {
                //Validaciones de campos de texto en caso de empezar caracteres especiales o numeros
                if(!ValidacionCaracteres(NombreProducto)){
                    Alerta("El nombre no puede iniciar con números ni caracteres especiales");
                }else if(!ValidacionCaracteres(MarcaProducto)){
                    Alerta("La marca no puede iniciar con números ni caracteres especiales");
                }else if(!ValidacionCaracteres(DescripcionProducto)){
                    Alerta("La descripción no puede iniciar con números ni caracteres especiales");
                } else{
                    //Conversion de valor string a numerico
                    int ValorProductoF = Integer.parseInt(ValorProductoI);

                    //Inserccion a la base de datos
                    ContentValues Datos = new ContentValues();
                    Datos.put("NombreProducto", NombreProducto);
                    Datos.put("MarcaProducto", MarcaProducto);
                    Datos.put("DescripcionProducto", DescripcionProducto);
                    Datos.put("ValorUnidad", ValorProductoF);

                    //Se crea la alerta para el boton
                    AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

                    //Se le ingresa un titulo
                    dialogo.setTitle("Pregunta");

                    //Se le ingresa un mensaje
                    dialogo.setMessage("¿Seguro que desea ingresar el registro al sistema?");

                    //Se le asigna codigo en el caso de que el usuario decida seleccionar si
                    dialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataBase.insert("Productos", null, Datos);

                            //Cierre de base de datos
                            DataBase.close();

                            //Vaciar campos al realizar correctamente la adiccion a la base de datos
                            Nombre.setText("");
                            Marca.setText("");
                            Descripcion.setText("");
                            Valor.setText("");

                            Alerta("Datos ingresados correctamente");
                        }
                    });

                    //Se le asigna codigo en el caso de que el usuario decida seleccionar no
                    dialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Alerta("Ingreso cancelado");
                        }
                    });

                    //Se muestra el dialogo ya configurado
                    dialogo.show();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //Funcion para utilizar el Toast y mostrar mensajes en pantalla
    private void Alerta(String mensaje){
        //Validacion para funcionamiento del codigo
        try {
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //Funcion que valida si el campo ingresado comienza con numeros o caracteres especiales
    private boolean ValidacionCaracteres(String texto){
        String caracteres = "^[a-zA-Z].*";
        boolean validacion = false;
        if(texto.matches(caracteres)){
            validacion = true;
        }
        return validacion;
    }
}
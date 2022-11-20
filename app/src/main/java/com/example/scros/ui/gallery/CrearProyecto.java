package com.example.scros.ui.gallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scros.Inter;
import com.example.scros.Login;
import com.example.scros.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;


public class CrearProyecto extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://scros-bce88-default-rtdb.firebaseio.com/");

    FirebaseAuth auth;
    String nombreUsuario;
    EditText nombreProyecto, descripcion;
    Button botonCreaproyecto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_proyecto);

        nombreProyecto = findViewById(R.id.txt_NombreProyecto);
        descripcion = findViewById(R.id.txt_DescripcionProyecto);
        botonCreaproyecto = findViewById(R.id.btnCrearProyecto);

        botonCreaproyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObtenerDatos();
            }
        });

    }
    public void ObtenerDatos(){
        nombreUsuario = databaseReference.getKey();
        Toast.makeText(CrearProyecto.this, nombreUsuario, Toast.LENGTH_SHORT).show();
    }
}
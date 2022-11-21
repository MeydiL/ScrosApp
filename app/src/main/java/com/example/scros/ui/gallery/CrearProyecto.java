package com.example.scros.ui.gallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scros.Inter;
import com.example.scros.Login;
import com.example.scros.R;
import com.example.scros.Registro;
import com.example.scros.item.Item_Proyecto;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Random;


public class CrearProyecto extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://scros-bce88-default-rtdb.firebaseio.com/");

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    String uidUser, nombreProyecto, descripcion;
    EditText nombreProyectoEt, descripcionEt;
    Button botonCreaproyecto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_proyecto);

        nombreProyectoEt = findViewById(R.id.txt_NombreProyecto);
        descripcionEt = findViewById(R.id.txt_DescripcionProyecto);
        botonCreaproyecto = findViewById(R.id.btnCrearProyecto);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(CrearProyecto.this);
        progressDialog.setTitle("Espere por favor.");
        progressDialog.setCanceledOnTouchOutside(false);

        botonCreaproyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombreProyecto = nombreProyectoEt.getText().toString();
                descripcion = descripcionEt.getText().toString();
                CrearYAsignarProyecto();
            }
        });
    }

    private void CrearYAsignarProyecto() {
        progressDialog.setMessage("Creando proyecto.");
        progressDialog.show();
        uidUser = firebaseAuth.getUid();

        GuardarInfo();
    }

    private void GuardarInfo() {

        Item_Proyecto itemP = new Item_Proyecto(nombreProyecto + descripcion, uidUser, nombreProyecto, descripcion);

        String proyectoUsuario = databaseReference.push().getKey();
        databaseReference.child("Proyectos").child(proyectoUsuario).setValue(itemP)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressDialog.dismiss();
                                Toast.makeText(CrearProyecto.this, "Proyecto creado correctamente.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CrearProyecto.this, " "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //Toast.makeText(this, "El proyecto se agregó correctamente.", Toast.LENGTH_SHORT).show();
        //OnbackPressed();
        /*HashMap<String, String> Datos = new HashMap<>();
        Datos.put("descripcion", descripcion);
        Datos.put("uid", uidUser);


        databaseReference = FirebaseDatabase.getInstance().getReference("Proyectos");
        databaseReference.child(nombreProyecto)
                .setValue(Datos)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(CrearProyecto.this, "Proyecto creado correctamente.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });*/
    }
}
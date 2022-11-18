package com.example.scros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://scros-bce88-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usuario = findViewById(R.id.usuario);
        final EditText contraseña = findViewById(R.id.contrasena);
        final Button btnlogin= findViewById(R.id.btnlogin);
        final TextView registrarAhora =findViewById(R.id.txtRegistro);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String txtUsuario = usuario.getText().toString();
                final String txtContraseña = contraseña.getText().toString();

                if (txtUsuario.isEmpty() || txtContraseña.isEmpty()){
                    Toast.makeText(Login.this, "Por favor, ingrese su usuario o contraseña.", Toast.LENGTH_SHORT).show();
                }else{

                    databaseReference.child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //Verificar si el usuario existe en la base de datos
                            if (snapshot.hasChild(txtUsuario)){
                                //El usuario existe por lo tanto, obtenemos la contraseña de la DB.

                                final String getContraseña = snapshot.child(txtUsuario).child("contraseña").getValue(String.class);

                                if(getContraseña.equals(txtContraseña)){
                                    Toast.makeText(Login.this, "Ha iniciado sesión exitosamente.", Toast.LENGTH_SHORT).show();

                                    //Abrimos la pantalla de menu principal
                                    startActivity(new Intent(Login.this, Menu.class));
                                    finish();
                                }else{
                                    Toast.makeText(Login.this, "Error, contraseña incorrecta.", Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                Toast.makeText(Login.this, "Error, usuario incorrecto.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        registrarAhora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Abre la actividad de registro
                startActivity(new Intent(Login.this, Registro.class));
            }
        });
    }
}
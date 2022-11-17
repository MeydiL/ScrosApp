package com.example.scros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Registro extends AppCompatActivity {
    //Crear objeto de la referencia de la base de datos para acceder a Firebase
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://scros-bce88-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        final EditText usuario =findViewById(R.id.usuario);
        final EditText nombre = findViewById(R.id.nombre);
        final EditText apellidoPaterno = findViewById(R.id.apellidoP);
        final EditText apellidoMaterno = findViewById(R.id.apellidoM);
        final EditText contraseña = findViewById(R.id.contrasena);
        final EditText confirmContraseña = findViewById(R.id.confirmContra);

        final Button btnRegistrar = findViewById(R.id.btnregistrar);
        final TextView txtIniciar = findViewById(R.id.txtLogin);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Obtenemos los datos de registro
                final String txtUsuario = usuario.getText().toString();
                final String txtNombre = nombre.getText().toString();
                final String txtApellidoPat = apellidoPaterno.getText().toString();
                final String txtApellidoMat = apellidoMaterno.getText().toString();
                final String txtContraseña = contraseña.getText().toString();
                final String txtConfirmContra = confirmContraseña.getText().toString();

                //Verificamos que todos los campos esten llenos
                if(txtUsuario.isEmpty() || txtNombre.isEmpty() || txtApellidoPat.isEmpty() || txtApellidoMat.isEmpty() ||txtContraseña.isEmpty() || txtConfirmContra.isEmpty()){
                    Toast.makeText(Registro.this, "Error, falta datos por llenar.", Toast.LENGTH_SHORT).show();
                }

                //Verificar la confirmacion de contraseñas.
                else if (!txtContraseña.equals(txtConfirmContra)){
                    Toast.makeText(Registro.this, "Error, las contraseñas no son iguales.", Toast.LENGTH_SHORT).show();
                }
                else{

                    databaseReference.child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //VERIFICAMOS SI EL USUARIO YA ESTA REGISTRADO EN LA BD
                            if (snapshot.hasChild(txtUsuario)){
                                Toast.makeText(Registro.this, "Este usuario ya está registrado.", Toast.LENGTH_SHORT).show();

                            }else{
                                // Enviar los datos a la BD, el nombre usuario sera la identidad unica por lo que todos lo datos de
                                // detalle del usuario debe  de comenzar con el usuario.

                                databaseReference.child("usuarios").child(txtUsuario).child("nombre").setValue(txtNombre);
                                databaseReference.child("usuarios").child(txtUsuario).child("apellidoPat").setValue(txtApellidoPat);
                                databaseReference.child("usuarios").child(txtUsuario).child("apellidoMat").setValue(txtApellidoMat);
                                databaseReference.child("usuarios").child(txtUsuario).child("contraseña").setValue(txtContraseña);

                                Toast.makeText(Registro.this, "Usuario registrado correctamente.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });


        txtIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
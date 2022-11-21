package com.example.scros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://scros-bce88-default-rtdb.firebaseio.com/");

    EditText correoUsuario, contraUsuario;
    Button btnLogin;
    TextView registrarAhora;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    //Validar los datos
    String correo="", contrasena="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correoUsuario = findViewById(R.id.usuario);
        contraUsuario = findViewById(R.id.contrasena);
        btnLogin= findViewById(R.id.btnlogin);
        registrarAhora =findViewById(R.id.txtRegistro);

        firebaseAuth =FirebaseAuth.getInstance(); //Inicializar FIREBASE AUTHENTICATION

        progressDialog = new ProgressDialog(Login.this); //Inicializar instancia de progressdialog
        progressDialog.setTitle("Espere por favor.");
        progressDialog.setCanceledOnTouchOutside(false);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //inter.nombreUsuario = usuario.getText().toString();
                correo = correoUsuario.getText().toString();
                contrasena = contraUsuario.getText().toString();

                if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
                    Toast.makeText(Login.this, "Correo invalido.", Toast.LENGTH_SHORT).show();
                }
                else if (correo.isEmpty() || contrasena.isEmpty()){
                    Toast.makeText(Login.this, "Por favor, ingrese su usuario o contraseña.", Toast.LENGTH_SHORT).show();
                }else{
                    LoginDeUsuario();
                    /*databaseReference.child("Usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //Verificar si el usuario existe en la base de datos
                            if (snapshot.exists() ){
                                //El usuario existe por lo tanto, obtenemos la contraseña de la DB.

                                final String getContraseña = snapshot.child(correo).child("contraseña").getValue(String.class);

                                if(getContraseña.equals(contrasena)){
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
                    });*/
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

    private void LoginDeUsuario(){
        progressDialog.setMessage("Iniciando sesión...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(correo,contrasena)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //verificar si la tarea se completo exitosamente
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            FirebaseUser user =firebaseAuth.getCurrentUser();

                            //Abrimos la pantalla de menu principal
                            startActivity(new Intent(Login.this, Menu.class));

                            Toast.makeText(Login.this, "Bienvenido(a): "+user.getEmail(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "Error, verifique si los datos son correctos.", Toast.LENGTH_SHORT).show();
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
        });
    }
}
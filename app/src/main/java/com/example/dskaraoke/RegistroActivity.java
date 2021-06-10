package com.example.dskaraoke;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistroActivity extends AppCompatActivity {

    private EditText etEmail, etPass, etNombre;
    private Button btnRegistro;
    private TextView tvLogin;
    private ProgressBar pbRegistro;
    private FirebaseAuth fbAuth;

    private void Ini() {
        this.etNombre = findViewById(R.id.etNombre);
        this.etEmail = findViewById(R.id.etEmail);
        this.etPass = findViewById(R.id.etContraseña);
        this.btnRegistro = findViewById(R.id.btnRegistro);
        this.tvLogin = findViewById(R.id.tvLogin);
        this.pbRegistro = findViewById(R.id.pbRegistro);
    }

    private void Logeo() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Ini();

        fbAuth = FirebaseAuth.getInstance();
        if (fbAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = etEmail.getText().toString().trim();
                String password = etPass.getText().toString().trim();
                if (TextUtils.isEmpty(mail)) {
                    etEmail.setError("Email es requerido");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    etPass.setError("Password es requerido");
                    return;
                }

                if (password.length() < 6) {
                    etPass.setError("La contraseña debe ser mayor o igual a 6 caracteres.");
                    return;
                }
                pbRegistro.setVisibility(view.VISIBLE);


                // enviar datos a firebase como email, contraseña - oyente que nos confirma si el registro se realizo correctamente

                fbAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistroActivity.this, "Usuario creado exitosamente", Toast.LENGTH_LONG);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(RegistroActivity.this, "Se produjo un error, usuario no creado" + task.getException().getMessage(), Toast.LENGTH_LONG);
                            pbRegistro.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        tvLogin.setOnClickListener(e -> Logeo());
    }
}
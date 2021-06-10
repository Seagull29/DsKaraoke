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

public class MainActivity extends AppCompatActivity {

    private EditText etEmail, etContra;
    private TextView tvRegistro;
    private Button btnLogin;
    private ProgressBar pbLogin;
    private FirebaseAuth fAuth;

    private void Ini() {
        this.etEmail = findViewById(R.id.etEmail);
        this.etContra = findViewById(R.id.etContraseña);
        this.tvRegistro = findViewById(R.id.tvRegistro);
        this.btnLogin = findViewById(R.id.btnLogin);
        this.pbLogin = findViewById(R.id.pbLogin);

    }

    private void Registro() {
        startActivity(new Intent(getApplicationContext(), RegistroActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Ini();
        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = etEmail.getText().toString().trim();
                String password = etContra.getText().toString().trim();
                if (TextUtils.isEmpty(mail)) {
                    etEmail.setError("Email es requerido");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    etContra.setError("Password es requerido");
                    return;
                }


                pbLogin.setVisibility(view.VISIBLE);


                // enviar datos a firebase como email, contraseña - oyente que nos confirma si el registro se realizo correctamente

                fAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Inicio de sesion en proceso", Toast.LENGTH_LONG);
                            startActivity(new Intent(getApplicationContext(), LivingActivity.class));
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "No se pudo iniciar sesio" + task.getException().getMessage(), Toast.LENGTH_LONG);
                            pbLogin.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        tvRegistro.setOnClickListener(e -> Registro());
    }
}
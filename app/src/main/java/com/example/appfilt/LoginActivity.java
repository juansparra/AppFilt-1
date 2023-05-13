package com.example.appfilt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializa los elementos de la interfaz de usuario
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtén los valores del nombre de usuario y la contraseña ingresados por el usuario
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Verifica si el nombre de usuario y la contraseña son válidos (cambia esto para que se adapte a tu lógica de inicio de sesión)
                if (isValidCredentials(username, password)) {
                    // Si las credenciales son válidas, inicia sesión y navega a la actividad principal
                    login();
                } else {
                    // Si las credenciales no son válidas, muestra un mensaje de error al usuario
                    Toast.makeText(LoginActivity.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método para verificar si las credenciales ingresadas son válidas (cambia esto para que se adapte a tu lógica de inicio de sesión)
    private boolean isValidCredentials(String username, String password) {
        return username.equals("admin") && password.equals("123");
    }

    // Método para iniciar sesión (cambia esto para que se adapte a tu lógica de inicio de sesión)
    private void login() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.apply();
        Intent intent = new Intent(LoginActivity.this, menu.class);
        startActivity(intent);
        finish();
    }
}
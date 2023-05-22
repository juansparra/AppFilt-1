package com.example.appfilt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appfilt.databinding.ActivityRegisterUserBinding;

public class RegisterUserActivity extends AppCompatActivity {

    ActivityRegisterUserBinding binding;
    DataBaseHelperLoginAndRegister dataBaseHelperLoginAndRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataBaseHelperLoginAndRegister = new DataBaseHelperLoginAndRegister(this);

        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.editTextUsernameRegister.getText().toString();
                String password = binding.editTextPasswordRegister.getText().toString();

                if(email.equals("") || password.equals("")){
                    Toast.makeText(RegisterUserActivity.this,"Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkUserEmail = dataBaseHelperLoginAndRegister.checkEmail(email);

                    if(checkUserEmail == false){
                        Boolean insert = dataBaseHelperLoginAndRegister.insertData(email, password);

                        if(insert == true){
                            Toast.makeText(RegisterUserActivity.this,"Signup Successfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterUserActivity.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterUserActivity.this, "Usuario ya existe", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
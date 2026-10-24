package com.example.campusstore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etName;
    EditText etPassword;

    Button btnLogin;
    Button btnRegister;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        dbHelper = new DBHelper(this);

        btnLogin.setOnClickListener(v -> loginUser());

        btnRegister.setOnClickListener(v -> {

            Intent intent = new Intent(
                    LoginActivity.this,
                    RegisterActivity.class
            );

            startActivity(intent);
        });
    }

    private void loginUser() {

        String name = etName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (name.isEmpty() || password.isEmpty()) {

            Toast.makeText(
                    LoginActivity.this,
                    "Enter name and password",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // Admin login
        if (name.equals("admin") && password.equals("admin")) {

            Intent intent = new Intent(
                    LoginActivity.this,
                    AdminActivity.class
            );

            startActivity(intent);

            finish();

            return;
        }

        // Normal user login
        boolean valid = dbHelper.checkUser(name, password);

        if (valid) {

            Toast.makeText(
                    LoginActivity.this,
                    "Login successful",
                    Toast.LENGTH_SHORT
            ).show();

            Intent intent = new Intent(
                    LoginActivity.this,
                    MainActivity.class
            );
            intent.putExtra(
                    "customer_name",
                    name
            );

            startActivity(intent);

            finish();

        } else {

            Toast.makeText(
                    LoginActivity.this,
                    "Invalid name or password",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
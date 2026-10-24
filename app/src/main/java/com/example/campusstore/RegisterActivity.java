package com.example.campusstore;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText etName;
    EditText etPassword;
    EditText etConfirmPassword;

    Button btnRegister;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnRegister = findViewById(R.id.btnRegister);

        dbHelper = new DBHelper(this);

        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {

        String name = etName.getText().toString().trim();

        String password =
                etPassword.getText().toString().trim();

        String confirmPassword =
                etConfirmPassword.getText().toString().trim();

        // Check empty fields
        if (name.isEmpty() ||
                password.isEmpty() ||
                confirmPassword.isEmpty()) {

            Toast.makeText(
                    RegisterActivity.this,
                    "Fill all fields",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // Check password
        if (!password.equals(confirmPassword)) {

            Toast.makeText(
                    RegisterActivity.this,
                    "Passwords do not match",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // Admin name is reserved
        if (name.equalsIgnoreCase("admin")) {

            Toast.makeText(
                    RegisterActivity.this,
                    "This name is reserved",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // Register user
        boolean result =
                dbHelper.registerUser(name, password);

        if (result) {

            Toast.makeText(
                    RegisterActivity.this,
                    "Registration successful",
                    Toast.LENGTH_SHORT
            ).show();

            // Return to Login page
            finish();

        } else {

            Toast.makeText(
                    RegisterActivity.this,
                    "Name already exists",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
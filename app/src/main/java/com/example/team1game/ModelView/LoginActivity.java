package com.example.team1game.ModelView;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.example.team1game.R;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText emailField, passwordField;
    private Button loginButton, createUserButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        loginButton = findViewById(R.id.loginButton);
        createUserButton = findViewById(R.id.createUserButton);

        loginButton.setOnClickListener(view -> {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(email, password);
            }
        });


        createUserButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, CreateUserActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("Login", "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intent = new Intent(LoginActivity.this, InstructionsScreen.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.w("Login", "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

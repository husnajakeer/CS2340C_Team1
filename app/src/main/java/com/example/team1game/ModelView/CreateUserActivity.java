package com.example.team1game.ModelView;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.example.team1game.R;
import com.google.firebase.auth.FirebaseUser;

public class CreateUserActivity extends AppCompatActivity {

    private EditText emailField, passwordField;
    private Button createAccountButton, backToLoginButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        mAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        createAccountButton = findViewById(R.id.createAccountButton);
        backToLoginButton = findViewById(R.id.backToLoginButton);

        createAccountButton.setOnClickListener(view -> {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();
            createUser(email, password);
        });

        backToLoginButton.setOnClickListener(view -> {
            Intent intent = new Intent(CreateUserActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("CreateUser", "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intent = new Intent(CreateUserActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        if (task.getException() != null) {
                            Log.w("CreateUser", "createUserWithEmail:failure", task.getException());
                            String errorMessage = getErrorMessage(task.getException());
                            Toast.makeText(CreateUserActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(CreateUserActivity.this, "Registration failed. Unknown error.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private String getErrorMessage(Exception exception) {
        if (exception instanceof FirebaseAuthWeakPasswordException) {
            return "Error: Weak Password!";
        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            return "Error: Invalid Credentials!";
        } else if (exception instanceof FirebaseAuthUserCollisionException) {
            return "Error: User Already Exists!";
        }
        // Generic error message for other exceptions
        return "Error: " + exception.getLocalizedMessage();
    }
}

package com.example.team1game.ModelView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.team1game.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InitialConfigScreen extends AppCompatActivity {
    private EditText playerNameEditText;
    private RadioGroup difficultyRadioGroup;
    private RadioButton easyDifficulty;
    private RadioButton mediumDifficulty;
    private RadioButton hardDifficulty;
    private RadioButton radioButtonSprite1;
    private RadioButton radioButtonSprite2;
    private RadioButton radioButtonSprite3;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_config_screen);

        playerNameEditText = findViewById(R.id.playerNameEditText);
        difficultyRadioGroup = findViewById(R.id.difficultyRadioGroup);
        easyDifficulty = findViewById(R.id.easyDifficulty);
        mediumDifficulty = findViewById(R.id.mediumDifficulty);
        hardDifficulty = findViewById(R.id.hardDifficulty);
        radioButtonSprite1 = findViewById(R.id.radioButtonSprite1);
        radioButtonSprite2 = findViewById(R.id.radioButtonSprite2);
        radioButtonSprite3 = findViewById(R.id.radioButtonSprite3);
        continueButton = findViewById(R.id.continueButton);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String name = dataSnapshot.child("name").getValue(String.class);
                        String difficulty = dataSnapshot.child("difficulty").getValue(String.class);
                        String sprite = dataSnapshot.child("sprite").getValue(String.class);

                        if (name != null) {
                            playerNameEditText.setText(name);
                        }
                        if (difficulty != null) {
                            setDifficultyRadioButton(difficulty);
                        }
                        if (sprite != null) {
                            setSpriteRadioButton(sprite);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("InitialConfigScreen", "loadUser:onCancelled", databaseError.toException());
                }
            });
        }

        continueButton.setOnClickListener(view -> {
            String playerName = playerNameEditText.getText().toString().trim();
            String difficulty = getSelectedDifficulty();
            String sprite = getSelectedSprite();

            if (playerName.isEmpty()) {
                playerNameEditText.setError("Name cannot be empty");
                return;
            }

            if (difficulty == null || sprite == null) {
                Toast.makeText(this, "Please select difficulty and sprite", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
            userRef.child("name").setValue(playerName)
                    .addOnSuccessListener(aVoid -> showSaveSuccessToast())
                    .addOnFailureListener(e -> showSaveFailureToast());
            userRef.child("difficulty").setValue(difficulty)
                    .addOnSuccessListener(aVoid -> showSaveSuccessToast())
                    .addOnFailureListener(e -> showSaveFailureToast());
            userRef.child("sprite").setValue(sprite)
                    .addOnSuccessListener(aVoid -> showSaveSuccessToast())
                    .addOnFailureListener(e -> showSaveFailureToast());

            Intent intent = new Intent(InitialConfigScreen.this, GameScreen.class);
            intent.putExtra("playerName", playerName);
            intent.putExtra("difficulty", difficulty);
            intent.putExtra("sprite", sprite);
            startActivity(intent);
        });
    }

    private void showSaveSuccessToast() {
        Log.d("InitialConfigScreen", "Save successful");
    }

    private void showSaveFailureToast() {
        Log.e("InitialConfigScreen", "Save failed");
    }

    private String getSelectedDifficulty() {
        if (easyDifficulty.isChecked()) return "Easy";
        if (mediumDifficulty.isChecked()) return "Medium";
        if (hardDifficulty.isChecked()) return "Hard";
        return null;
    }

    private String getSelectedSprite() {
        if (radioButtonSprite1.isChecked()) return "eva_idle";
        if (radioButtonSprite2.isChecked()) return "kaya_idle";
        if (radioButtonSprite3.isChecked()) return "rika_idle";
        return null;
    }

    private void setDifficultyRadioButton(String difficulty) {
        if ("Easy".equals(difficulty)) easyDifficulty.setChecked(true);
        else if ("Medium".equals(difficulty)) mediumDifficulty.setChecked(true);
        else if ("Hard".equals(difficulty)) hardDifficulty.setChecked(true);
    }

    private void setSpriteRadioButton(String sprite) {
        switch (sprite) {
            case "eva_idle":
                radioButtonSprite1.setChecked(true);
                break;
            case "kaya_idle":
                radioButtonSprite2.setChecked(true);
                break;
            case "rika_idle":
                radioButtonSprite3.setChecked(true);
                break;
        }
    }
}

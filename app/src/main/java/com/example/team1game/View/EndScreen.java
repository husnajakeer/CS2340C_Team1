package com.example.team1game.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.team1game.Model.Attempt;
import com.example.team1game.Model.Leaderboard;
import com.example.team1game.Model.Player;
import com.example.team1game.ModelView.MainActivity;
import com.example.team1game.R;

// firestore
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class EndScreen extends AppCompatActivity {

    private Button restartButton;
    private Button quitButton;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        player = Player.getPlayer();
        setContentView(R.layout.activity_end_screen);

        setLeaderboard();
        setCurrentAttempt();
        saveInBackend();
        fetchTopScoresFromFirestore();

        restartButton = findViewById(R.id.restartButton);
        quitButton = findViewById(R.id.quitButton);

        restartButton.setOnClickListener(view -> {
            Intent intent = new Intent(EndScreen.this, MainActivity.class);
            startActivity(intent);
        });

        quitButton.setOnClickListener(view -> {
            finishAffinity();  // This will close the entire app
        });
    }

    private void setLeaderboard() {
        Leaderboard leaderboard = Leaderboard.getInstance();
        TableLayout leaderboardTable = findViewById(R.id.leaderboardTable);

        for (Attempt attempt : leaderboard.getAttempts()) {
            TableRow row = new TableRow(this);
            TextView playerName = new TextView(this);
            TextView score = new TextView(this);
            TextView attemptTime = new TextView(this);

            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.MATCH_PARENT, 1);

            playerName.setLayoutParams(layoutParams);
            score.setLayoutParams(layoutParams);
            attemptTime.setLayoutParams(layoutParams);

            playerName.setGravity(Gravity.CENTER);
            score.setGravity(Gravity.CENTER);
            attemptTime.setGravity(Gravity.CENTER);

            playerName.setPadding(8, 8, 8, 8);
            score.setPadding(8, 8, 8, 8);
            attemptTime.setPadding(8, 8, 8, 8);

            playerName.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
            score.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
            attemptTime.setBackground(ContextCompat.getDrawable(this, R.drawable.border));

            playerName.setText(attempt.getPlayerName());
            score.setText(String.valueOf(attempt.getScore()));
            attemptTime.setText(attempt.getAttemptTime().toString());

            row.addView(playerName);
            row.addView(score);
            row.addView(attemptTime);

            leaderboardTable.addView(row);
        }
    }

    private void setCurrentAttempt() {
        Leaderboard leaderboard = Leaderboard.getInstance();
        TableLayout mostRecentAttemptTable = findViewById(R.id.mostRecentAttemptTable);
        Attempt recentAttempt = leaderboard.getMostRecentAttempt();

        if (recentAttempt != null) {
            TableRow row = new TableRow(this);
            TextView playerName = new TextView(this);
            TextView score = new TextView(this);
            TextView attemptTime = new TextView(this);

            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.MATCH_PARENT, 1);

            playerName.setLayoutParams(layoutParams);
            score.setLayoutParams(layoutParams);
            attemptTime.setLayoutParams(layoutParams);

            playerName.setGravity(Gravity.CENTER);
            score.setGravity(Gravity.CENTER);
            attemptTime.setGravity(Gravity.CENTER);

            playerName.setPadding(8, 8, 8, 8);
            score.setPadding(8, 8, 8, 8);
            attemptTime.setPadding(8, 8, 8, 8);

            playerName.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
            score.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
            attemptTime.setBackground(ContextCompat.getDrawable(this, R.drawable.border));

            playerName.setText(recentAttempt.getPlayerName());
            score.setText(String.valueOf(recentAttempt.getScore()));
            attemptTime.setText(recentAttempt.getAttemptTime().toString());

            row.addView(playerName);
            row.addView(score);
            row.addView(attemptTime);

            mostRecentAttemptTable.addView(row);
        }
    }
    private void saveInBackend() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("name", player.getName());
        user.put("difficulty", player.getDifficulty());
        user.put("score", player.getScore());
        //user.put("attemptTime", attempt.getAttemptTime().toString());
        System.out.println("saved in firestore");

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener(documentReference ->
                    System.out.println("DocumentSnapshot added with ID: "
                            + documentReference.getId()))
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println( "Error adding document" + e);
                }
            });
        db.collection("users")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            System.out.println( " => " + document.getData());
                        }
                    } else {
                        System.out.println("Error getting documents."+ task.getException());
                    }
                }
            });
    }
    private void fetchTopScoresFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .orderBy("score", Query.Direction.DESCENDING)
                .limit(5)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        System.out.println("Top 5 Scores:");
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            long playerScore = document.getLong("score");
                            System.out.println("Score: " + playerScore);
                        }
                    } else {
                        // Handle errors while fetching data
                        System.out.println("Error getting documents: " + task.getException());
                    }
                });
    }


}

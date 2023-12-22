package com.example.team1game.Model.Backend;
//
//import androidx.annotation.NonNull;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.util.HashMap;
//import java.util.Map;
///*
//not used for now
// */
//public class FirestoreHelper {
//
//    private final FirebaseFirestore db;
//
//    public FirestoreHelper() {
//        db = FirebaseFirestore.getInstance();
//    }
//
//    public void addUser(Map<String, Object> userData,
//                        OnCompleteListener<DocumentReference>
//                        successListener, OnFailureListener failureListener) {
//        db.collection("users")
//                .add(userData)
//                .addOnCompleteListener(successListener)
//                .addOnFailureListener(failureListener);
//    }
//
//    public void getAllUsers(OnCompleteListener<QuerySnapshot> onCompleteListener) {
//        db.collection("users")
//                .get()
//                .addOnCompleteListener(onCompleteListener);
//     }
//}
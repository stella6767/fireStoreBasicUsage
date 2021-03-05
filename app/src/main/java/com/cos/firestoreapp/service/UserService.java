package com.cos.firestoreapp.service;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cos.firestoreapp.model.Board;
import com.cos.firestoreapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {

    private static final String TAG = "UserService";
    private FirebaseFirestore db;
    private TextView tv;


    public UserService() {
            db = FirebaseFirestore.getInstance();
    }

    public void userreadTest(){
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        //List<User> users = task.getResult().toObjects(User.class);

                        List<User> users = new ArrayList<>();

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                user.setId(document.getId());
                                users.add(user);
                            }
                            Log.d(TAG, "onComplete: user : "+users);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    public void userReadAll(MyCallback myCallback){
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<User> users = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                user.setId(document.getId());
                                users.add(user);
                            }
                            Log.d(TAG, "onComplete: users : "+users);
                            myCallback.back(users);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void userDelete(){
        db.collection("users").document("DC")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }



    public void userSave(MyCallback myCallback) {
        String collectionPath = "users";
        User user = new User(null,"qqqq", "1234", "0102222");
        // Add a new document with a generated ID
        db.collection(collectionPath)
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            idInit(documentReference, collectionPath, myCallback);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }


    public void idInit(DocumentReference documentReference, String collectionPath, MyCallback myCallback){
        DocumentReference washingtonRef =
                db.collection(collectionPath).document(documentReference.getId());

        washingtonRef
                .update("id", documentReference.getId()) //auto 생성된 문서id를 필드 id에 덮어씌움
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "successfully id created!");

                        //////////////////////////////////////////////////////////////////////////////// 새로운 영역
                        DocumentReference docRef = db.collection(collectionPath).document(documentReference.getId());
                        docRef.get().addOnSuccessListener(doc-> {
                                User user = doc.toObject(User.class);
                                myCallback.back(user);
                        });
                        ////////////////////////////////////////////////////////////////////////////////

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }

}

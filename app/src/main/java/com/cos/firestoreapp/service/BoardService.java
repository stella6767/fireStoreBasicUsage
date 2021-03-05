package com.cos.firestoreapp.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.cos.firestoreapp.model.Board;
import com.cos.firestoreapp.model.BoardDetailDto;
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

public class BoardService {

    private static final String TAG = "BoardService";

    private FirebaseFirestore db;

    public BoardService() {
        db = FirebaseFirestore.getInstance();
    }



    public void boardDetail(MyCallback myCallback){

        BoardDetailDto dto = new BoardDetailDto();
        DocumentReference docRef = db.collection("board").document("uAISMBVwaomO4b5GdM3p");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Board board = task.getResult().toObject(Board.class);
                        String userId = board.getUserId();

                        dto.setBoard(board);

                        /////////////////////////////////////////////////////////////////////
                        DocumentReference docRef = db.collection("users").document(userId);
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        User user = task.getResult().toObject(User.class);

                                        dto.setUser(user);
                                        myCallback.back(dto);

                                        //Log.d(TAG, "dto " + dto);
                                    } else {
                                        //Log.d(TAG, "No such document");
                                    }
                                } else {
                                    //Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });
                        /////////////////////////////////////////////////////////////////////

                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public void boardAll(){
        db.collection("board")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        List<Board> boards = new ArrayList<>();

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Board board = document.toObject(Board.class);
                                board.setId(document.getId());
                                boards.add(board);
                            }
                            Log.d(TAG, "onComplete: board : "+boards);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }




}

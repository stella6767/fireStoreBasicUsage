package com.cos.firestoreapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.cos.firestoreapp.model.BoardDetailDto;
import com.cos.firestoreapp.model.User;
import com.cos.firestoreapp.service.BoardService;
import com.cos.firestoreapp.service.MyCallback;
import com.cos.firestoreapp.service.UserService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity2";
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();


        //dbSave();
        //dbReadAll();
        //dbSaveOrUpdate();
        //dbUpdate(); //한 필드만 부분변경
        //readTest();
        // 화면에 뿌릴예정
        //boardAll();


        BoardService boardService = new BoardService();
        UserService userService = new UserService();

//        boardService.boardDetail(new MyCallback<BoardDetailDto>() {
//            @Override
//            public void back(BoardDetailDto dto) {
//                Log.d(TAG, "back: dto :" + dto);
//                //어댑터에 던지기
//            }
//        });


        userService.userSave(user -> {
            Log.d(TAG, "onCreate: user: "+user);
        });


//        userService.userReadAll(v->{
//            Log.d(TAG, "onCreate: useres:  "+v);
//        });
    }


}
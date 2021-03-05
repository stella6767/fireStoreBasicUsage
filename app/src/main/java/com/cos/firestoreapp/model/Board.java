package com.cos.firestoreapp.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Board {
    private String id;
    private String title;
    private String content;
    private String userId;
}

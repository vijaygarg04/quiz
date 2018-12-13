package com.example.vijaygarg.quiz;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
EditText quizcode;
Button searchquizbtn;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quizcode=findViewById(R.id.etquizcode);
        searchquizbtn=findViewById(R.id.btnsearchquiz);
        firebaseDatabase=FirebaseDatabase.getInstance();
        searchquizbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchquiz();
            }
        });

    }

    public void searchquiz(){
        final String squizcode=quizcode.getText().toString();
        if(squizcode.length()<=0){
            return;
        }
    databaseReference=firebaseDatabase.getReference().child("quiz");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isfound=false;
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    if(dataSnapshot1.getKey().equals(squizcode)){
                        startNextActivity(squizcode);
                        isfound=true;
                        break;
                    }
                }
                if(isfound==false) {
                    quizcode.setError("Wrong Quiz Code");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void startNextActivity(String quizcode) {
        Intent i = new Intent(MainActivity.this,Quiz.class);
        i.putExtra("quizcode",quizcode);
        startActivity(i);
    }
}

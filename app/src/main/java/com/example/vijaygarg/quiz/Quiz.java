package com.example.vijaygarg.quiz;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Quiz extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
TextView settime;
Button startquiz;
String quizcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        settime=findViewById(R.id.tvsettime);
        startquiz=findViewById(R.id.btnstartquiz);
        firebaseDatabase=FirebaseDatabase.getInstance();
        Intent i=getIntent();
        Bundle b=i.getExtras();
        quizcode=b.getString("quizcode");

        databaseReference=firebaseDatabase.getReference().child("quiz").child(quizcode).child("time");
        final int timeinmins[]=new int[1];
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                timeinmins[0]=dataSnapshot.getValue(Integer.class);
                String hour =timeinmins[0]/60+"";
                if(hour.length()<2){
                    hour="0"+hour;
                }

                timeinmins[0]=timeinmins[0]%60;
                String mins=timeinmins[0]+"";
                if(mins.length()<2){
                    mins="0"+mins;
                }

                String time=hour+" : "+ mins+" : 00";
                settime.setText(time);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    startquiz.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=new Intent(Quiz.this,StartQuiz.class);
            i.putExtra("time",timeinmins[0]);
            i.putExtra("quizcode",quizcode);
            startActivity(i);
        }
    });
    }
}

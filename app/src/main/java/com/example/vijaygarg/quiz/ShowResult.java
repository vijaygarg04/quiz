package com.example.vijaygarg.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.ArrayList;

public class ShowResult extends AppCompatActivity {
ArrayList<Question> questionslist;
RecyclerView recyclerView;
Button uploadresult;
TextView scoretv;
int score;
DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        Intent i=getIntent();
        Bundle b=i.getBundleExtra("questionlist");
        questionslist= (ArrayList<Question>) b.getSerializable("bundle");
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        QuestionAnswerAdapter questionAnswerAdapter=new QuestionAnswerAdapter(this,questionslist);
        recyclerView.setAdapter(questionAnswerAdapter);
        scoretv=findViewById(R.id.score);

        calculatescore();
        uploadresult=findViewById(R.id.uploadbtn);
        uploadresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //todo update score on firebase
            }
        });
    }

    private void calculatescore() {

        for(Question question:questionslist){
            if(question.getMyanswer()!=null && question.getAnswer().equals(question.getMyanswer())){
                score++;
            }
        }
        int totalquestion=questionslist.size();
        scoretv.setText("Your Score: "+score+"/"+totalquestion);

    }
}

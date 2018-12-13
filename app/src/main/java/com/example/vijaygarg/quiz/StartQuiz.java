package com.example.vijaygarg.quiz;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class StartQuiz extends AppCompatActivity {
int mins;
ArrayList<Question> questionList;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
int questionnumber=0;
String optionselected;
TextView question,option1,option2,option3,option4;
CountDownTimerView timerView;
Button submitanswer;
String quizcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);
        firebaseDatabase=FirebaseDatabase.getInstance();
        Intent i =getIntent();
        Bundle bundle=i.getExtras();
        mins=bundle.getInt("time");
        quizcode=bundle.getString("quizcode");

        questionList=new ArrayList<>();
        databaseReference=firebaseDatabase.getReference().child("quiz").child(quizcode).child("questions");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        Question nextQuestion = dataSnapshot1.getValue(Question.class);
                        questionList.add(nextQuestion);
                }
                Collections.shuffle(questionList);
                startTimer(mins);
                displayquiz(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        question=findViewById(R.id.tvquestion);
        option1=findViewById(R.id.option1);
        option2=findViewById(R.id.option2);
        option3=findViewById(R.id.option3);
        option4=findViewById(R.id.option4);
        submitanswer=findViewById(R.id.btnsubmitanswer);




        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionselected!=null){
                    changeColor(optionselected,"1");
                }else{
                    setColor("1",Color.GREEN);
                }
                optionselected="1";
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionselected!=null){
                    changeColor(optionselected,"2");
                }else{
                    setColor("2",Color.GREEN);
                }
                optionselected="2";
            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionselected!=null){
                    changeColor(optionselected,"3");
                }else{
                    setColor("3",Color.GREEN);
                }
                optionselected="3";
            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionselected!=null){
                    changeColor(optionselected,"4");
                }else{
                    setColor("4",Color.GREEN);
                }
                optionselected="4";
            }
        });
        submitanswer.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(optionselected==null){
                //select option please
                return;
            }else{
                questionList.get(questionnumber).setMyanswer(optionselected);
            }
            if(questionList.size()==questionnumber+1){
                finishquiz();

                //showresultpage
            }else {
                questionnumber++;
                displayquiz(questionnumber);
                optionselected = null;
            }
        }
        });


    }

    private void setColor(String option,  int colorcode) {
        switch (option){
            case "1":

                option1.setBackgroundColor(colorcode);
                break;

            case "2":
                option2.setBackgroundColor(colorcode);
                break;

            case "3":
                option3.setBackgroundColor(colorcode);
                break;

            case "4":
                option4.setBackgroundColor(colorcode);
                break;

                default:
                    Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_LONG).show();
        }

    }

    private void changeColor(String optionselected, String newoption) {
        switch (optionselected){
            case "1":

                option1.setBackgroundColor(Color.WHITE);
                setColor(newoption,Color.GREEN);
                break;

            case "2":
                option2.setBackgroundColor(Color.WHITE);
                setColor(newoption,Color.GREEN);
                break;

            case "3":
                option3.setBackgroundColor(Color.WHITE);
                setColor(newoption,Color.GREEN);
                break;

            case "4":
                option4.setBackgroundColor(Color.WHITE);
                setColor(newoption,Color.GREEN);
                break;

            default:
                Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_LONG).show();
        }


    }


    private void displayquiz(int i) {
    question.setText(questionList.get(i).getQuestion());
    option1.setText(questionList.get(i).getOption1());
    option2.setText(questionList.get(i).getOption2());
    option3.setText(questionList.get(i).getOption3());
    option4.setText(questionList.get(i).getOption4());
    if(optionselected!=null) {
        setColor(optionselected,Color.WHITE);
    }
    }


    public void startTimer(int timeinmins){
timerView=(CountDownTimerView)findViewById(R.id.short_timer_view);
int timeinmillisec=timeinmins*60*1000;

timerView.setTime(timeinmillisec);
        timerView.setOnTimerListener(new CountDownTimerView.TimerListener() {
            @Override
            public void onTick(long millisUntilFinished) {
                timerView.setTime(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                timerView.setText("Time up!");
                finishquiz();
                timerView.stopCountDown();
            }
        });
        timerView.startCountDown();
    }

    private void finishquiz() {
        timerView.stopCountDown();
        Intent i=new Intent(StartQuiz.this,ShowResult.class);
        Bundle b=new Bundle();
        b.putSerializable("bundle",(Serializable) questionList);
        i.putExtra("questionlist",b);
        startActivity(i);

        int score=0;
        for(Question question:questionList){
            if(question.getAnswer().equals(question.getMyanswer())){
                score++;
            }
        }

        Toast.makeText(this,"Score :"+score,Toast.LENGTH_LONG).show();
        finish();
    }


}


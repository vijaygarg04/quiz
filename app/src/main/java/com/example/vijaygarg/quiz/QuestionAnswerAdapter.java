package com.example.vijaygarg.quiz;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



import java.util.ArrayList;

/**
 * Created by vijaygarg on 18/03/18.
 */

public class QuestionAnswerAdapter extends RecyclerView.Adapter<QuestionAnswerAdapter.MyViewHolder> {
    Context context;
    ArrayList<Question>arr;


    public QuestionAnswerAdapter(Context context, ArrayList<Question> arr) {
        this.context = context;
        this.arr = arr;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.view_questionanswer,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.question.setText(arr.get(position).getQuestion());

        if(arr.get(position).getMyanswer()!=null && arr.get(position).getMyanswer().length()>0) {
            holder.youranswer.setText(showoption(arr.get(position).getMyanswer(),position));
        }else{
            holder.youranswer.setText("NO OPTION SELECTED");
        }
        holder.correctanswer.setText(showoption(arr.get(position).getAnswer(),position));
        if (arr.get(position).getAnswer()!=null && arr.get(position).getAnswer().equals(arr.get(position).getMyanswer())) {

            holder.youranswer.setBackgroundColor(Color.GREEN);
        }else{
            holder.youranswer.setBackgroundColor(Color.RED);
        }
    }

    private String showoption(String option, int position) {
        switch (option){
            case "1":

                return arr.get(position).getOption1();

            case "2":
                return arr.get(position).getOption2();

            case "3":
                return arr.get(position).getOption3();

            case "4":
                return arr.get(position).getOption4();

                default:
                    return "wrong attempt";

        }
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView question, correctanswer, youranswer;
        public MyViewHolder(View itemView) {
            super(itemView);
            question=itemView.findViewById(R.id.question);
            correctanswer=itemView.findViewById(R.id.correctresponse);
            youranswer=itemView.findViewById(R.id.yourresponse);


        }
    }
}

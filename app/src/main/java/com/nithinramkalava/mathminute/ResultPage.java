package com.nithinramkalava.mathminute;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ResultPage extends AppCompatActivity {

    int score, questionCount;
    ArrayList<Double> timeTakenForEachQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);

        TextView scoreTextView = findViewById(R.id.scoreTextViewResult);
        TextView averageTimeTextView = findViewById(R.id.averageResponseTimeTextView);
        TextView accuracyTextView = findViewById(R.id.accuracyTextView);


        score = getIntent().getIntExtra("score", 0);
        questionCount = getIntent().getIntExtra("questionCount", 0);

        try {
            timeTakenForEachQuestion = (ArrayList<Double>) ObjectSerializer.deserialize(getIntent().getStringExtra("timeTakenForEachQuestion"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        double averageTime = 0;

        for (int i = 0; i < timeTakenForEachQuestion.size(); i++) {
            averageTime += timeTakenForEachQuestion.get(i);
        }
        averageTime /= timeTakenForEachQuestion.size();

        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);

        scoreTextView.setText("Score: " + score + "/" + questionCount);
        averageTimeTextView.setText("Average time taken per question: " + df.format(averageTime) + " seconds");
        accuracyTextView.setText("Accuracy: " + df.format((score*100)/questionCount) + "%");

    }
}
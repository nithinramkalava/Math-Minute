package com.nithinramkalava.mathminute;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ResultPage extends AppCompatActivity {

    int score, questionCount;
    double averageTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);

        TextView scoreTextView = findViewById(R.id.scoreTextViewResult);
        TextView averageTimeTextView = findViewById(R.id.averageResponseTimeTextView);
        TextView accuracyTextView = findViewById(R.id.accuracyTextView);


        score = getIntent().getIntExtra("score", 0);
        questionCount = getIntent().getIntExtra("questionCount", 0);
        averageTime = getIntent().getDoubleExtra("averageTime", 0);

        scoreTextView.setText("Score: " + score + "/" + questionCount);
        averageTimeTextView.setText("Average Response time per question:\n\n" + averageTime + " seconds");
        accuracyTextView.setText("Accuracy: " + (score * 100) / questionCount + "%");


    }
}
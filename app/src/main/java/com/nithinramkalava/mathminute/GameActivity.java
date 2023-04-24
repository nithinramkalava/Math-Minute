package com.nithinramkalava.mathminute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    Random random;
    Button option1, option2, option3, option0;
    TextView questionTextView, scoreTextView, timerTextView;
    int score = 0, questionCount = 0;
    CountDownTimer countDownTimer;
    boolean TimerRunning = true;
    Question question;
    ArrayList<Double> timeTakenForEachQuestion;
    Double startTime, endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        random = new Random();
        option0 = findViewById(R.id.option0);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        questionTextView = findViewById(R.id.questionTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);

        timeTakenForEachQuestion = new ArrayList<>();

        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished/1000) + "s");
            }

            @Override
            public void onFinish() {
                Toast.makeText(GameActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();
                TimerRunning = false;
                Intent intent = new Intent(getApplicationContext(), ResultPage.class);


//                SharedPreferences sharedPreferences = getSharedPreferences("com.nithinramkalava.mathminute", Context.MODE_PRIVATE);
//                sharedPreferences.edit().putInt("score", score).apply();
//                sharedPreferences.edit().putInt("questionCount", questionCount).apply();

                intent.putExtra("score", score);
                intent.putExtra("questionCount", questionCount);
                try {
                    intent.putExtra("timeTakenForEachQuestion", ObjectSerializer.serialize(timeTakenForEachQuestion));
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                try {
//                    sharedPreferences.edit().putString("timeTakenForEachQuestion",ObjectSerializer.serialize(timeTakenForEachQuestion)).apply();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                Bundle bundle = new Bundle();
//                bundle.putStringArrayList("timeTakenForEachQuestion", timeTakenForEachQuestion);
                startActivity(intent);
            }
        }.start();

        question = questionGenerator();
        updateScreen(question);

    }

    public void answered(View view) {
        endTime = (double) System.currentTimeMillis();
        timeTakenForEachQuestion.add((endTime - startTime)/1000);
        Button button = (Button) view;
        int tag = Integer.parseInt(button.getTag().toString());
        Log.i("Answer", String.valueOf(question.answerIndex));
        Log.i("Tag", String.valueOf(tag));

        if (tag == question.answerIndex) {
            score++;
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        }

        if (TimerRunning) {
            question = questionGenerator();
            updateScreen(question);
        }
        else Toast.makeText(this, "Time's up!", Toast.LENGTH_SHORT).show();
    }

    public void updateScreen(Question question) {

        scoreTextView.setText("score: " + score + "/" + questionCount);
        questionCount++;
        questionTextView.setText(question.question);
        option0.setText(String.valueOf(question.options[0]));
        option1.setText(String.valueOf(question.options[1]));
        option2.setText(String.valueOf(question.options[2]));
        option3.setText(String.valueOf(question.options[3]));

        startTime = (double) System.currentTimeMillis();
    }

    public Question questionGenerator() {
        String[] operators = {"+","-"};
        int a,b;
        int[] options = new int[4];

        a = random.nextInt(100);
        b = random.nextInt(100);
        String operation = operators[random.nextInt(2)];

        int answerIndex = random.nextInt(4);

        for (int i = 0; i < 4; i++) {
            if (i != answerIndex) options[i] = random.nextInt(2*(a+b)-2*(a-b)) + 2*(a-b);
            else {
                if (operation.equals("+")) options[i] = a + b;
                else options[i] = a - b;
            }
        }
        return new Question(a + " " + operation + " " + b + " = ", options, answerIndex);
    }
}
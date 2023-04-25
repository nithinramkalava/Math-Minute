package com.nithinramkalava.mathminute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class GameHistory extends AppCompatActivity {

    ListView playedHistoryListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);

        playedHistoryListView = findViewById(R.id.playedHistoryListView);

        ArrayList<String> playedHistory = new ArrayList<>();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.list_view_text_style, playedHistory);

        playedHistoryListView.setOnItemClickListener((parent, view, position, id) -> {

            String data = (String) playedHistoryListView.getItemAtPosition(position);
            String[] dataSplit = data.split("\n");
            String score = dataSplit[1].split(": ")[1].split("/")[0];
            String questionCount = dataSplit[1].split(": ")[1].split("/")[1];
            String averageTimeTakenForEachQuestion = dataSplit[2].split(": ")[1].split(" ")[0];

            int scoreInt = Integer.parseInt(score);
            int questionCountInt = Integer.parseInt(questionCount);
            double averageTimeTakenForEachQuestionDouble = Double.parseDouble(averageTimeTakenForEachQuestion);

            Intent intent = new Intent(getApplicationContext(), ResultPage.class);
            intent.putExtra("history", true);
            intent.putExtra("score", scoreInt);
            intent.putExtra("questionCount", questionCountInt);
            intent.putExtra("averageTime", averageTimeTakenForEachQuestionDouble);
            startActivity(intent);
        });


        try {
            SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("MathMinute", MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM playedHistory", null);

            int dateIndex = cursor.getColumnIndex("date");
            int scoreIndex = cursor.getColumnIndex("score");
            int questionCountIndex = cursor.getColumnIndex("questionCount");
            int averageTimeTakenForEachQuestionIndex = cursor.getColumnIndex("averageTimeTakenForEachQuestion");

            while (cursor.moveToNext()) {
                playedHistory.add("Date: " + cursor.getString(dateIndex) + "\nScore: " + cursor.getInt(scoreIndex) + "/" + cursor.getInt(questionCountIndex) + "\nAverage Time Taken: " + cursor.getString(averageTimeTakenForEachQuestionIndex) + " seconds");
            }

            cursor.close();

            Collections.reverse(playedHistory);
            playedHistoryListView.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
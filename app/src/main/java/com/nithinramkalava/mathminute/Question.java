package com.nithinramkalava.mathminute;

public class Question {
    String question;
    int[] options;
    int answerIndex;

    public Question(String question, int[] options, int answerIndex) {
        this.question = question;
        this.options = options;
        this.answerIndex = answerIndex;
    }
}

package com.ita.filmquiz.pojos;

import java.io.Serializable;

public class Question implements Serializable {
    //region Propriete
    //Propriete ID
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    //Propriete TEXT de question
    private String text;
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    //Propriete ANSWER true ou false
    private boolean answer;
    public boolean getAnswer() {
        return answer;
    }
    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
    //endregion

    //region Constructeur
    public Question() {
    }

    public Question(String text, boolean answer) {
        this.text = text;
        this.answer = answer;
    }
    //endregion

    //region Overrides
    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", answer=" + answer +
                '}';
    }
    //endregion
}

package com.ita.filmquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ita.filmquiz.pojos.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnTrue;
    private Button btnFalse;
    private TextView textQuestion;
    private TextView textScore;
    private Button btnRestart;
    private Toolbar toolbar;
    private List<Question> questions = new ArrayList<>();
    private int index = 0;
    private int score = 0;
    private Question question;
    private final String TAG = "QuizActivity";
    public static final String KEY_INDEX = "index";
    public static final String KEY_SCORE = "score";
    public static final String KEY_BTN_RESTART = "visibilityBtnRestart";
    public int btnRestartVisibility = View.INVISIBLE;
    public static final String KEY_QUESTION = "question";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //ajouter log
        Log.d(TAG, "onCreate() called");

        //recuperer les elements
        btnTrue = findViewById(R.id.btnTrue);
        btnFalse = findViewById(R.id.btnFalse);
        btnRestart = findViewById(R.id.btnRestart);
        textQuestion = findViewById(R.id.textQuestion);
        textScore = findViewById(R.id.textScore);
        toolbar = findViewById(R.id.toolbar);

        //affecter le toolbar comme ActionBar
        setSupportActionBar(toolbar);

        //test si bundle existe
        if (savedInstanceState != null) {
            //recupere les donnees misent dans le bundle
            index = savedInstanceState.getInt(KEY_INDEX);
            score = savedInstanceState.getInt(KEY_SCORE);
            btnRestartVisibility = savedInstanceState.getInt(KEY_BTN_RESTART);
            //ajouster la visibilite des boutons
            if (btnRestartVisibility == View.VISIBLE) {
                btnTrue.setVisibility(View.GONE);
                btnFalse.setVisibility(View.GONE);
                btnRestart.setVisibility(View.VISIBLE);
            } else {
                btnTrue.setVisibility(View.VISIBLE);
                btnFalse.setVisibility(View.VISIBLE);
                btnRestart.setVisibility(View.GONE);
            }
        };


        //creer et ajouter les questions a la liste
        questions.add(new Question(getString(R.string.question_ai), true));
        questions.add(new Question(getString(R.string.question_taxi_driver), true));
        questions.add(new Question(getString(R.string.question_2001), false));
        questions.add(new Question(getString(R.string.question_reservoir_dogs), true));
        questions.add(new Question(getString(R.string.question_citizen_kane), false));

        //recuperer et afficher la question en cours
        question = index > questions.size()-1 ? null : questions.get(index);
        textQuestion.setText(question != null ? question.getText() : String.format(getString(R.string.end),score));
        //afficher le score actuel
        textScore.setText(String.format(getString(R.string.score), score));

        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                CharSequence text = getString(R.string.answerNOK);
                if (question.getAnswer()) {
                    score ++;
                    text = getString(R.string.answerOK);
                }
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                index ++;
                textScore.setText(String.format(getString(R.string.score), score));
                //si index depasse le nombre des questions disponible, ajouter le bouton rejouer, cacher les boutons des reponses
                if(index > questions.size()-1) {
                    btnTrue.setVisibility(View.GONE);
                    btnFalse.setVisibility(View.GONE);
                    btnRestart.setVisibility(View.VISIBLE);
                    textQuestion.setText(String.format(getString(R.string.end),score));
                } else {
                    question = questions.get(index);
                    textQuestion.setText(question.getText());
                }
            }
        });

        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                CharSequence text = getString(R.string.answerNOK);
                if (!question.getAnswer()) {
                    score ++;
                    text = getString(R.string.answerOK);
                }
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                index ++;
                textScore.setText(String.format(getString(R.string.score), score));
                //si index depasse le nombre des questions disponible, ajouter le bouton rejouer, cacher les boutons des reponses
                if(index > questions.size()-1) {
                    btnTrue.setVisibility(View.GONE);
                    btnFalse.setVisibility(View.GONE);
                    btnRestart.setVisibility(View.VISIBLE);
                    textQuestion.setText(String.format(getString(R.string.end),score));
                } else {
                    question = questions.get(index);
                    textQuestion.setText(question.getText());
                }
            }
        });

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = 0;
                score = 0;
                textQuestion.setText(questions.get(index).getText());
                textScore.setText(String.format(getString(R.string.score), score));
                btnTrue.setVisibility(View.VISIBLE);
                btnFalse.setVisibility(View.VISIBLE);
                btnRestart.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //creer le menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //test l'id de l'item selectionne
        if(item.getItemId() == R.id.cheat) {
            //creer un Intent
            Intent intent = new Intent(getApplicationContext(), CheatActivity.class);
            //ajouter le bundle au intent
            intent.putExtra(KEY_QUESTION, questions.get(index));
            //demarer l'activity
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG,"onSaveInstance() called");

        //met dans le bundle indexQuestion et score
        outState.putInt(KEY_INDEX, index);
        outState.putInt(KEY_SCORE, score);
        int test = btnRestart.getVisibility();
        outState.putInt(KEY_BTN_RESTART,(btnRestart.getVisibility()));
    }
}
package com.ita.filmquiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ita.filmquiz.pojos.Question;

public class CheatActivity extends AppCompatActivity {
    private TextView textAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cheat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cheat), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //recuperer le toolbar et l'affecter comme ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //recuperer l'action bar et active le go-back
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //recuperer l'intent qui a appele cette activity
        Intent intent = getIntent();
        //recuperer le bundle
        Bundle bundle = getIntent().getExtras();
        //recuperer la question de bundle
        Question question = (Question)bundle.getSerializable(MainActivity.KEY_QUESTION);
        //recuperer l'element
        textAnswer = findViewById(R.id.textReponse);
        //afficher la reponse
        textAnswer.setText(String.format("%d : %s", question.getText(), question.getAnswer()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        //terminate cette activity
        finish();
        return true;
    }
}
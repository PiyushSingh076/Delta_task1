package com.devdroid.delta_task1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main_activity extends AppCompatActivity {
    String word;
    String clue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText word_editext=(EditText) findViewById(R.id.word_input) ;

        word_editext.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        EditText clue_edittext=(EditText)findViewById(R.id.clue_input) ;
        clue_edittext.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        Button btnStart;
        btnStart=findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int length=word_editext.getText().toString().length();
                if(length<=16){
                    Intent iStart;
                    iStart=new Intent(Main_activity.this,Activity_2.class);
                    word=word_editext.getText().toString().toUpperCase();
                    clue=clue_edittext.getText().toString().toUpperCase();
                    iStart.putExtra("word",word);
                    iStart.putExtra("clue",clue);
                    startActivity(iStart);
                }
                else{
                    Toast.makeText(Main_activity.this, "Limit Exceeded!!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
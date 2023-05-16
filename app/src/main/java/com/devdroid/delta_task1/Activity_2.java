package com.devdroid.delta_task1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Activity_2 extends AppCompatActivity {
    String result="";
    Dialog gameover;

    Button btnInfo,btnDialog,btnCheck,btnReset,btnHome,btnPlay_again;

    AlertDialog.Builder builder;
    AlertDialog.Builder builder2;
    int score=300;
    String word="";
    String clue="";
    String grid="";
    ArrayList<Character> arrayList=new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        EditText editText = (EditText) findViewById(R.id.answer_box);
        Intent fromAct = getIntent();
        word = fromAct.getStringExtra("word");
        clue = fromAct.getStringExtra("clue");
        btnReset=(Button)findViewById(R.id.btnReset);
        EditText answer_box=findViewById(R.id.answer_box);
        answer_box.setText(result);
        for (int i = 0; i < word.length(); i++) {
            arrayList.add(word.charAt(i));
        }
        while (arrayList.size() < 16) {
            int succs = 1;
            char temp = generate_letter();
            for (int j = 0; j < arrayList.size(); j++) {
                if (temp == arrayList.get(j)) {
                    succs = 0;
                    break;
                } else {
                    succs = 1;
                }
            }
            if (succs == 1) {
                arrayList.add(temp);
            }
        }
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grid="";
                answer_box.setText(grid);
            }
        });
        GridView gridView=findViewById(R.id.gridview);
        arrayList=shuffle(arrayList);
        GridView_adapter gridView_adapter=new GridView_adapter(Activity_2.this,arrayList);
        gridView.setAdapter(gridView_adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                grid+=arrayList.get(i);
                answer_box.setText(grid);
            }
        });


        answer_box.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        btnInfo = findViewById(R.id.btnInfo);
        btnCheck = findViewById(R.id.btnCheck);
        btnDialog = findViewById(R.id.btnDialog);
        btnReset = findViewById(R.id.btnReset);
        btnHome=findViewById(R.id.btnHome);
        builder = new AlertDialog.Builder(Activity_2.this);
        builder2= new AlertDialog.Builder(Activity_2.this);
        btnInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view2) {
                AlertDialog.Builder builder4=new AlertDialog.Builder(Activity_2.this);
                View view = getLayoutInflater().inflate(R.layout.alert, null);// to set a new layout
                builder4.setView(view);// to set a new layout
                TextView clue_textview = view.findViewById(R.id.clue_dialog);
                clue_textview.setText(clue);
                // to change the background color of the dialog box
                AlertDialog dialog = builder4.create();// to set the background
                dialog.show();
                view.findViewById(R.id.btnDialog).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });


        btnCheck.setOnClickListener(new View.OnClickListener() {
            int lives = 0;

            @Override
            public void onClick(View view) {
                ImageView heart1 = findViewById(R.id.star_1);
                ImageView heart2 = findViewById(R.id.star_2);
                ImageView heart3 = findViewById(R.id.star_3);
                String ans = editText.getText().toString().toUpperCase();

                if (!ans.equals(word)) {
                    lives += 1;

                    if (lives == 1) {
                        heart3.setImageResource(R.drawable.heart_icon2);
                        builder.setTitle("Wrong Answer").setMessage("Try Again").setCancelable(true).setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();
                        score-=100;
                    } else if (lives == 2) {
                        heart3.setImageResource(R.drawable.heart_icon2);
                        heart2.setImageResource(R.drawable.heart_icon2);
                        builder.setTitle("Wrong Answer").setMessage("Try Again").setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();
                        score-=100;
                    } else if (lives == 3) {
                        score-=100;
                        heart3.setImageResource(R.drawable.heart_icon2);
                        heart2.setImageResource(R.drawable.heart_icon2);
                        heart1.setImageResource(R.drawable.heart_icon2);
                        View view2 = getLayoutInflater().inflate(R.layout.game_over, null);
                        builder2.setView(view2);
                        TextView your_score = view2.findViewById(R.id.your_score);
                        view2.findViewById(R.id.btnHome).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(Activity_2.this,Main_activity.class);
                                startActivity(intent);
                            }
                        });
                        your_score.setText("Your score:"+score);
                        AlertDialog game_over= builder2.create();
                        Button btnPlay_again=view2.findViewById(R.id.btnPlay_again);
                        view2.findViewById(R.id.btnPlay_again).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog edit_round3 = builder2.create();
                                lives=0;
                                score=300;
                                heart1.setImageResource(R.drawable.heart_icon);
                                heart2.setImageResource(R.drawable.heart_icon);
                                heart3.setImageResource(R.drawable.heart_icon);
                                game_over.dismiss();
                            }

                        });
                        your_score.setText("Your score:"+score);
                        game_over.show();

                    }
                }
                else if(ans.equals(word)){
                    View view2 = getLayoutInflater().inflate(R.layout.game_over, null);
                    builder2.setView(view2);//
                    TextView your_score = view2.findViewById(R.id.your_score);
                    Button btnhm=view2.findViewById(R.id.btnHome);
                    btnhm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(Activity_2.this,Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    AlertDialog game_over= builder2.create();// necessary to use dailog.dismiss();
                    // first view view 2 gets the view that we created then we put it in builder 2
                    // which sets the view and then using alert dialog box we can create that builder
                    // that stores the view 2 in it
                    Button btnPlay_again=view2.findViewById(R.id.btnPlay_again);
                    view2.findViewById(R.id.btnPlay_again).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog edit_round3 = builder2.create();
                            lives=0;
                            score=300;
                            heart1.setImageResource(R.drawable.heart_icon);
                            heart2.setImageResource(R.drawable.heart_icon);
                            heart3.setImageResource(R.drawable.heart_icon);
                            game_over.dismiss();
                        }

                    });
                    your_score.setText("Your score:"+score);
                    game_over.show();
                }
                SharedPreferences score_preference = getSharedPreferences("Score", Context.MODE_PRIVATE);
                int homeScore = score_preference.getInt("high_score", 0);
            }
        });

    }

    public ArrayList<Character> shuffle(ArrayList<Character> arrayList){
        ArrayList<Character> arrayList1=new ArrayList<>();
        while(arrayList1.size()!=arrayList.size()){
            Random random=new Random();
            int temp=random.nextInt(16);
            if(arrayList.get(temp)!='0'){
                arrayList1.add(arrayList.get(temp));
                arrayList.set(temp,'0');

            }

        }
        return arrayList1;
    }
    public static char generate_letter() {
        char lttr = ' ';
        Random r = new Random();
        lttr = (char) (r.nextInt(26) + 'A');
        return lttr;
    }
}

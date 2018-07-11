package com.example.zelinbao.cse535assign1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Random;

import com.example.zelinbao.cse535assign1.GraphView;


public class MainActivity extends AppCompatActivity {
        //"100","90","80","70"
    GraphView graph;
    String[] verlabels = new String[]{ "90","80","70","60", "50", "40", "30", "20","10","0"};
    String[] horlabels = new String[]{"0", "1", "2", "3", "4", "5", "6","7","8","9"};
    float[] values = new float[20];
    boolean run = false;

    float[] points = new float[20];

    //FrameLayout graphFrame;
    LinearLayout graphlinear;
    Random random;
    Thread thread;



    @ Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //graph
        graph = new GraphView(this, values, "Running data", horlabels, verlabels, GraphView.LINE);
        //graph linear
        graphlinear = (LinearLayout) findViewById(R.id.linearGraph);
        graphlinear.addView(graph);

        Button startButton = (Button) findViewById(R.id.start);

        boolean male = ((RadioButton) findViewById(R.id.male)).isChecked();


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                random = new Random();
                run = true;

                for (int i = 0; i < points.length; i++) {
                    points[i] = random.nextInt(50);
                }


                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (run) {
                            for (int i = 0; i < points.length; i++) {
                                points[i] = random.nextInt(100);
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    graph.setValues(points);
                                    graphlinear.removeView(graph);
                                    graphlinear.addView(graph);
                                }
                            });

                            try{
                                Thread.sleep(400);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }

                    }
                });
                thread.start();
                Toast.makeText(MainActivity.this,  "run", Toast.LENGTH_SHORT).show();
            }
        });


        Button stopButton = (Button) findViewById(R.id.stop);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                run = false;
                graphlinear.removeView(graph);
                graph.setValues(values);
                graphlinear.addView(graph);

                Toast.makeText(MainActivity.this,  "stop", Toast.LENGTH_SHORT).show();
            }
        });


        RadioGroup sexRadio = (RadioGroup) findViewById(R.id.sexRadioGroup);
        sexRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                RadioButton choise = (RadioButton) findViewById(id);
                String output = choise.getText().toString();
                Toast.makeText(MainActivity.this, "" + output, Toast.LENGTH_SHORT).show();
            }
        });

    }

}

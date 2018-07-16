package com.example.zelinbao.cse535assign1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;



public class MainActivity extends AppCompatActivity {
    private double x = 0;
    private double y =0;
    float[] values = new float[20];
    boolean ifRun = false;
    Random random;
    Thread thread;

    private LineGraphSeries<DataPoint> series;
    GraphView graph;

    @ Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        graph = (GraphView) findViewById(R.id.graph);
        series = new  LineGraphSeries<DataPoint>();

        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(2500);
        viewport.setXAxisBoundsManual(true);
        viewport.setMinX(0);
        viewport.setMaxX(50);
        viewport.setScrollable(true);
        Button startButton = (Button) findViewById(R.id.start);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ifRun == false) {

                    random = new Random();
                    ifRun = true;
                    graph.addSeries(series);

                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (ifRun) {

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {

                                        series.appendData(new DataPoint(x++, random.nextDouble() * 2500d), true, 100);
                                    }
                                });

                                try {
                                    Thread.sleep(400);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                    });
                    thread.start();
                    Toast.makeText(MainActivity.this, "run", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button stopButton = (Button) findViewById(R.id.stop);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ifRun = false;
                graph.removeSeries(series);
                Toast.makeText(MainActivity.this,  "stop", Toast.LENGTH_SHORT).show();
            }
        });


        RadioGroup sexRadio = (RadioGroup) findViewById(R.id.sexRadioGroup);
        sexRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                RadioButton choice = (RadioButton) findViewById(id);
                String output = choice.getText().toString();
                Toast.makeText(MainActivity.this, "" + output, Toast.LENGTH_SHORT).show();
            }
        });


        String name = ((EditText) findViewById(R.id.name)).getText().toString();
        String age = ((EditText) findViewById(R.id.age)).getText().toString();
        String id = ((EditText) findViewById(R.id.id)).getText().toString();

    }

}

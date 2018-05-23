package com.example.upshottechonologies.threadmanagement;

import android.content.DialogInterface;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.*;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView, tv2;
    Button btn, btnAlert;
    Handler handler;
    String [] array = {"Android", "Java", "Kotlin", "Ios", "Swift"};
    boolean[] checkedarray={false, false,false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        tv2 = findViewById(R.id.textview2);
        btn = findViewById(R.id.start);
        handler = new Handler();
        btnAlert = findViewById(R.id.alert);
        final Thread myThread = new Thread(myRunnable);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myThread.start();
                new Thread(new MyNewThread()).start();
            }
        });

        btnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Alert Title");
              //  builder.setMessage("Do you want to cancel?");
                builder.setPositiveButton("My OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String str="Selcted items:\n";
                        for (int i=0 ; i<array.length;i++)
                        {
                            if(checkedarray[i])
                            {
                                str+=array[i]+"\n";
                            }
                        }
                        System.out.println(str);
                        Toast.makeText(MainActivity.this, "You have clicked on Ok", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("My Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "you have clicked in canc el", Toast.LENGTH_LONG).show();
                    }
                });

                builder.setMultiChoiceItems(array, checkedarray, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkedarray[which]=isChecked;
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
    }

    Runnable myRunnable = new Runnable() {
        int count = 0;
        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("Executing on worker thread:"+ (Looper.myLooper()==Looper.getMainLooper()));
                    //System.out.println(Looper.myLooper()==Looper.getMainLooper());
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Executing on Main thread:"+ (Looper.myLooper()==Looper.getMainLooper()));
                        textView.setText("runOnUI:" + count);
                    }
                });
            }
        }
    };

    class  MyNewThread extends Thread{
        int count = 0;
        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("Executing on handler worker thread:"+ (Looper.myLooper()==Looper.getMainLooper()));
                    //System.out.println(Looper.myLooper()==Looper.getMainLooper());
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Executing on Main thread:"+ (Looper.myLooper()==Looper.getMainLooper()));

                        tv2.setText("Handler:" + count);
                    }
                });
            }
        }
    }
}

package com.example.upshottechonologies.threadmanagement;

import android.content.DialogInterface;
import android.os.Looper;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.os.*;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView, tv2;
    Button btn, btnAlert;
    Handler handler;
    String [] array = {"Android", "Java", "Kotlin", "Ios", "Swift"};
    boolean[] checkedarray={false, false,false, false, false};
    ConstraintLayout cl;
PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cl = findViewById(R.id.conll);
        textView = findViewById(R.id.textView);
        tv2 = findViewById(R.id.textview2);
        btn = findViewById(R.id.start);
        handler = new Handler();
        btnAlert = findViewById(R.id.alert);
        final Thread myThread = new Thread(myRunnable);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //myThread.start();
               // new Thread(new MyNewThread()).start();
                new MyAsyncTask().execute("str");
            }
        });

        btnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //alertDial();

                //customDialog();
                openPopup();
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


    private void customDialog()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = (LayoutInflater)MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.custom_aler_dialog_layout, null);
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editText = view.findViewById(R.id.etCustom);
                Toast.makeText(MainActivity.this, "Entered Name: "+editText.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
           Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void alertDial()
    {
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

    class MyAsyncTask extends AsyncTask<String, Integer, Integer>{
        @Override
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this, "Async task started", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            Toast.makeText(MainActivity.this, "Compled", Toast.LENGTH_SHORT).show();
           tv2.setText("Final Count:"+integer.intValue());
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            textView.setText("Counter Value:"+values[0].intValue());
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int count=0;
            while (count<10)
            {
                count++;
                publishProgress(count);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return count;
        }
    }

    private void openPopup()
    {
        LayoutInflater layoutInflater = (LayoutInflater)MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_aler_dialog_layout, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageButton ib = view.findViewById(R.id.ib_cancel);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupWindow.showAtLocation(cl, Gravity.CENTER, 0,0);
    }
}

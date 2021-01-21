package com.example.lab4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    int comp_num = 0;
    int attempts = 5;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Button btnguess = (Button)findViewById(R.id.btn_guess);
        switch (item.getItemId()) {
            case R.id.changeColor:
                btnguess.setBackgroundColor(2);
                break;
            case R.id.changeFontSize:
                btnguess.setTextSize(35);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu); //запуск меню
        return true;//super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.settings:
                //показать диалоговое окно с настройкой уровня игры
                break;
            case R.id.aboutAuther:
                Intent intent = new Intent(MainActivity.this, AboutAutherActivity.class);
                startActivity(intent);

                //показать активити-визитку
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        Log.i (LOG_TAG, "OnStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i (LOG_TAG, "OnStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i (LOG_TAG, "OnDestroy");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.i (LOG_TAG, "OnSaveInstanceState");

        TextView att=(TextView)findViewById(R.id.show_attempts_left);
        outState.putString("attempsView",att.getText().toString());

        EditText num=(EditText)findViewById(R.id.edit_num);
        outState.putString("number",num.getText().toString());

        outState.putInt("comp_num",comp_num);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);

        TextView att=(TextView)findViewById(R.id.show_attempts_left);
        att.setText(savedInstanceState.getString("attempsView"));

        EditText num=(EditText)findViewById(R.id.edit_num);
        num.setText(savedInstanceState.getString("number"));

        comp_num=savedInstanceState.getInt("comp_num");
    }
    @Override
    protected void onResume() {
        Log.i (LOG_TAG, "OnResume");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.i (LOG_TAG, "OnRestart");
        super.onRestart();
    }

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i (LOG_TAG, "Угадай число. onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnguess = (Button)findViewById(R.id.btn_guess);
        btnguess.setOnClickListener(this);
        registerForContextMenu(btnguess);


//        final Button btnguess1 = (Button)findViewById(R.id.btn_guess1);
//        btnguess.setOnClickListener(this);
        if(savedInstanceState == null) {
            showChooseDiapazonDialog();
        }
    }

    @Override
    public void onClick(View v) {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.oneButton:
//                    // do your code
//                    break;
//                case R.id.twoButton:
//                    // do your code
//                    break;
//                case R.id.threeButton:
//                    // do your code
//                    break;
//                default:
//                    break;
//            }
        Button btnguess = (Button)findViewById(R.id.btn_guess);
        if(attempts == 0){
            btnguess.setEnabled(false);
            return;
        }
        EditText editText = (EditText)findViewById(R.id.edit_num);
        String editTextValue = editText.getText().toString();
        if(editTextValue.matches(""))
        {
            CharSequence text = "Введите число!";
            int duration = Toast.LENGTH_SHORT;

            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
            try {
            int x = Integer.parseInt(editText.getText().toString());
            if(x == comp_num)
            {
                btnguess.setText(R.string.congratulation);
                btnguess.setEnabled(false);

                Context context = getApplicationContext();
                CharSequence text = "Поздравляю!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return;
            }
        }
        catch (Exception e){
            CharSequence text = "Слишком большое число!";
            int duration = Toast.LENGTH_SHORT;

            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        editText.setText("");
        attempts--;
        TextView textattempts = (TextView)findViewById(R.id.show_attempts_left);
        textattempts.setText(Integer.toString(attempts));
        if(attempts == 0){
            textattempts.setText(R.string.you_lose);
        }
    }
        public void showAuther(){
        Intent intent = new Intent(MainActivity.this, AboutAutherActivity.class);
        startActivity(intent);
    }
    public void restart(View view) {
        showChooseDiapazonDialog();

        EditText editText = (EditText)findViewById(R.id.edit_num);
        editText.setText("");

        Button btnguess = (Button) findViewById(R.id.btn_guess);
        btnguess.setEnabled(true);
        btnguess.setText(R.string.btn_label_guess);
        attempts = 5;
        TextView textattempts = (TextView)findViewById(R.id.show_attempts_left);
        textattempts.setText(Integer.toString(attempts));
    }

    public static class guessNum {
        static public int rnd_comp_num(int min, int max) {
            int diff = max - min;
            Random random = new Random();
             return random.nextInt(diff + 1) + min;
            //return 10;

        }
    }
    public class MyDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setTitle("Выберете");
            builder.setMessage("алвовд");

            builder.setItems(R.array.diaps_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int min = 0;
                        int max = 0;
                        // The 'which' argument contains the index position of the selected item
                        if(which == 0){
                            min = 10;
                            max = 99;
                        }
                        else if(which == 1){
                            min = 100;
                            max = 999;
                        }
                        else if(which == 2){
                            min = 1000;
                            max = 9999;
                        }
                        comp_num = guessNum.rnd_comp_num(min, max);
                    }
            });
            return builder.create();
        }
    }
    public void showChooseDiapazonDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(R.string.chooseDiapazon);

        builder.setItems(R.array.diaps_array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                int min = 0;
                int max = 0;
                // The 'which' argument contains the index position of the selected item
                if(which == 0){
                    min = 10;
                    max = 99;
                }
                else if(which == 1){
                    min = 100;
                    max = 999;
                }
                else if(which == 2){
                    min = 1000;
                    max = 9999;
                }
                comp_num = guessNum.rnd_comp_num(min, max);
                Log.i (LOG_TAG, "Загаданное число:" +Integer.toString(comp_num));
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();

    }
}

package com.mrinshad.counttracker;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageButton countButton, resetButton, removeButton, undoButton, historyButton;
    TextView countText;
    int sharedCount;
    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String COUNT_KEY = "count_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        sharedCount = sharedPreferences.getInt(COUNT_KEY, 0);

        countButton = (ImageButton) findViewById(R.id.countButton);
        resetButton = (ImageButton) findViewById(R.id.resetButton);
        removeButton = (ImageButton) findViewById(R.id.removeButton);
        undoButton = (ImageButton) findViewById(R.id.undoButton);
        historyButton = (ImageButton) findViewById(R.id.historyButton);
        countText = (TextView) findViewById(R.id.totalCount);
        try {
            countText.setText(String.valueOf(sharedCount));
        } catch (Exception e) {
            Log.e(TAG, "onCreate: " + e.getMessage());
        }
        countButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sharedCount += 1;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(COUNT_KEY, sharedCount);
                    editor.apply();
                    countText.setText(String.valueOf(sharedCount));

                } catch (Exception e) {
                    Log.e(TAG, "error on count button: " + e.getMessage());
                }

            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberInputDialog();
            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sharedCount -= 1;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(COUNT_KEY, sharedCount);
                    editor.apply();
                    countText.setText(String.valueOf(sharedCount));
                    if (sharedCount == 0)
                        return;
                    countText.setText(String.valueOf(sharedCount));

                } catch (Exception e) {
                    Log.e(TAG, "error on undo button: " + e.getMessage());
                }

            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedCount = 0;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(COUNT_KEY, 0);
                editor.apply();
                countText.setText(String.valueOf(0));
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHistory();
            }
        });

    }
    public void showNumberInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter a Number");

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String inputValue = input.getText().toString();
                int number = Integer.parseInt(inputValue);
                // Do something with the number
                sharedCount -= number;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(COUNT_KEY,sharedCount);
                editor.apply();
                countText.setText(String.valueOf(sharedCount));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void showHistory(){
        Toast.makeText(getApplicationContext(),"Coming soon",Toast.LENGTH_SHORT).show();
    }
}
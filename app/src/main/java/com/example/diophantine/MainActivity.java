package com.example.diophantine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.diophantine.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView equationTextView;
    private EditText degreeEditText, kEditText, pEditText, modEditText;
    private SeekBar limitSeekBar;
    private TextView limitValueTextView;
    private ListView resultsListView;
    private Button calculateButton;
    private CheckBox squaresCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        equationTextView = findViewById(R.id.equationTextView);
        degreeEditText = findViewById(R.id.degreeEditText);
        kEditText = findViewById(R.id.kEditText);
        pEditText = findViewById(R.id.pEditText);
        modEditText = findViewById(R.id.modEditText);
        limitSeekBar = findViewById(R.id.limitSeekBar);
        limitValueTextView = findViewById(R.id.limitValueTextView);
        resultsListView = findViewById(R.id.resultsListView);
        calculateButton = findViewById(R.id.calculateButton);
        squaresCheckBox = findViewById(R.id.squaresCheckBox);


        com.example.diophantine.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:contact@ialexpovad.net"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback on Diophantine App");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear Alex Povad,\n\nI have the following feedback/suggestion/bug report:\n\n");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send email"));
                } catch (ActivityNotFoundException ex) {
                    Snackbar.make(view, "No email client installed.", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        equationTextView = findViewById(R.id.equationTextView);
        degreeEditText = findViewById(R.id.degreeEditText);
        kEditText = findViewById(R.id.kEditText);
        pEditText = findViewById(R.id.pEditText);
        modEditText = findViewById(R.id.modEditText);
        limitSeekBar = findViewById(R.id.limitSeekBar);
        limitValueTextView = findViewById(R.id.limitValueTextView);
        resultsListView = findViewById(R.id.resultsListView);
        calculateButton = findViewById(R.id.calculateButton);
        squaresCheckBox = findViewById(R.id.squaresCheckBox);


        // Set default values
        degreeEditText.setText("1");
        limitSeekBar.setProgress(1000);
        limitValueTextView.setText(getString(R.string.limit_text) + " " + 1000);
        squaresCheckBox.setChecked(true);

        // SeekBar listener
        limitSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                limitValueTextView.setText(getString(R.string.limit_text) + " " + progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

            // Load the selected language and apply it

        }
        );

        // Button click listener
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String degreeStr = degreeEditText.getText().toString().trim();
                String kStr = kEditText.getText().toString().trim();
                String pStr = pEditText.getText().toString().trim();
                String modStr = modEditText.getText().toString().trim();

                if (TextUtils.isEmpty(degreeStr) && (TextUtils.isEmpty(kStr) || TextUtils.isEmpty(pStr) || TextUtils.isEmpty(modStr))) {
                    // Show error message if both degreeEditText and kEditText, pEditText, modEditText are empty
                    degreeEditText.setError(getString(R.string.limit_error));
                    return;
                }

                int degree = TextUtils.isEmpty(degreeStr) ? 1 : Integer.parseInt(degreeStr);
                int k = TextUtils.isEmpty(kStr) ? 0 : Integer.parseInt(kStr);
                int p = TextUtils.isEmpty(pStr) ? 0 : Integer.parseInt(pStr);
                int mod = TextUtils.isEmpty(modStr) ? 0 : Integer.parseInt(modStr);
                int limit = limitSeekBar.getProgress();
                boolean squares = squaresCheckBox.isChecked();

                if (degree != 0 && (k != 0 || p != 0 || mod != 0)) {
                    // Ignore k, p, mod if degree is provided
                    k = 0;
                    p = 0;
                    mod = 0;
                }

                List<String> solutions = solveDiophantine(degree, k, p, mod, limit, squares);
                displaySolutions(solutions);
                updateEquationText(degree);
            }
        });
    }


    private List<String> solveDiophantine(int degree, int k, int p, int mod, int limit, boolean squares) {
        List<String> solutions = new ArrayList<>();
        if (!squares) {
            if (k == 0 && p == 0 && mod == 0) {
                for (int n = 1; n <= limit; n++) {
                    int m = (int) Math.pow(2 * n - 1, 1.0 / degree);
                    if (Math.pow(m, degree) == 2 * n - 1) {
                        solutions.add("m = " + m + ", n = " + n);
                    }
                }
            } else {
                for (int n = 1; n <= limit; n++) {
                    int m = (int) Math.pow(2 * n - 1, 1.0 / (p * k));
                    if (Math.pow(m, p * k) % mod == (2 * n - 1) % mod) {
                        solutions.add("m = " + m + ", n = " + n);
                    }
                }
            }
        } else {
            if (k == 0 && p == 0 && mod == 0) {
                for (int b = 1; b <= limit; b++) {
                    int a = (int) Math.pow(2 * b * b - 1, 1.0 / (2 * degree));
                    if (Math.pow(a, 2 * degree) == 2 * b * b - 1) {
                        solutions.add("m = " + (a * a) + ", n = " + (b * b));
                    }
                }
            } else {
                for (int b = 1; b <= limit; b++) {
                    int a = (int) Math.pow(2 * b * b - 1, 1.0 / (2 * p * k));
                    if (Math.pow(a, 2 * p * k) % mod == (2 * b * b - 1) % mod) {
                        solutions.add("m = " + (a * a) + ", n = " + (b * b));
                    }
                }
            }
        }
        return solutions;
    }

    private void displaySolutions(List<String> solutions) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, solutions);
        resultsListView.setAdapter(adapter);
    }

    private void updateEquationText(int degree) {
        equationTextView.setText("m^" + degree + " = 2n - 1");
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            openSettingsActivity();
            return true;
        } else if (id == R.id.action_about) {
            openAboutActivity();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void openAboutActivity() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void openSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
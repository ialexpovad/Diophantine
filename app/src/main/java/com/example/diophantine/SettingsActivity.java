package com.example.diophantine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private static final String PREF_KEY_LANGUAGE = "language";
    private static final String PREF_KEY_THEME = "theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("settings", MODE_PRIVATE);

        // Apply the selected theme
        applyTheme();

        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        setupLanguageOption();
        setupThemeOption();
    }

    private void setupLanguageOption() {
        RadioGroup languageOptions = findViewById(R.id.languageOptions);

        int currentLanguage = preferences.getInt(PREF_KEY_LANGUAGE, R.id.englishLanguage);
        languageOptions.check(currentLanguage);

        languageOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                preferences.edit().putInt(PREF_KEY_LANGUAGE, checkedId).apply();
                setAppLanguage(checkedId);
                // Apply language change to the main activity
                applyLanguageChange();
            }
        });
    }

    private void setupThemeOption() {
        RadioGroup themeOptions = findViewById(R.id.themeOptions);

        // Get the current theme preference
        int currentTheme = preferences.getInt(PREF_KEY_THEME, R.id.lightTheme);
        themeOptions.check(currentTheme);

        themeOptions.setOnCheckedChangeListener((group, checkedId) -> {
            preferences.edit().putInt(PREF_KEY_THEME, checkedId).apply();
            applyTheme();
            // Restart the activity to reflect theme change
            recreate();
        });
    }

    private void applyTheme() {
        int selectedTheme = preferences.getInt(PREF_KEY_THEME, R.id.lightTheme);

        if (selectedTheme == R.id.lightTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (selectedTheme == R.id.darkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if (selectedTheme == R.id.systemTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }

    private void applyLanguageChange() {
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }

    private void setAppLanguage(int languageId) {
        String languageCode;
        if (languageId == R.id.englishLanguage) {
            languageCode = "en";
        } else {
            languageCode = "ru";
        }

        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        recreate();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

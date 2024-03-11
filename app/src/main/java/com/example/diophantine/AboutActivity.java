package com.example.diophantine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // Enable the Up button

        TextView copyrightTextView = findViewById(R.id.copyrightTextView);
        copyrightTextView.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());

        SpannableString spannableString = new SpannableString("Copyright © Alex Povad");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Handle the click action, e.g., open a website
                String url = "https://ialexpovad.net";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        };
        spannableString.setSpan(clickableSpan, 12, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        copyrightTextView.setText(spannableString);

        TextView aboutTextView = findViewById(R.id.about_text_view);
        aboutTextView.setMovementMethod(LinkMovementMethod.getInstance());

        TextView versionTextView = findViewById(R.id.versionTextView);
        versionTextView.setText(getString(R.string.version) + " " + BuildConfig.VERSION_NAME );

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) { // Handle the Up button
            onBackPressed(); // Navigate back
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
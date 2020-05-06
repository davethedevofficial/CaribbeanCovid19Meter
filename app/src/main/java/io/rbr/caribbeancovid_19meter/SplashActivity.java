package io.rbr.caribbeancovid_19meter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "io.rbr.caribbeancovid_19.prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Get Shared Prefs
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);


        // For Testing
//        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
//        preferencesEditor.clear();
//        preferencesEditor.apply();

        redirection();
    }

    @Override
    protected void onResume() {
        super.onResume();
        redirection();
    }

    private void redirection() {
        if (!mPreferences.contains("name")) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        } else {
            if (!mPreferences.contains("island")) {
                Intent i = new Intent(this, IslandSelectionActivity.class);
                startActivity(i);
            } else {
                Intent i = new Intent(this, IslandDetailActivity.class);
                startActivity(i);
            }
        }
    }
}

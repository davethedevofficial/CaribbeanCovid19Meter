package io.rbr.caribbeancovid_19meter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private EditText nameEditView;
    private FloatingActionButton continueButton;
    private String sharedPrefFile = "io.rbr.caribbeancovid_19.prefs";
    private SharedPreferences mPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditView = (EditText) findViewById(R.id.nameEditView);
        continueButton = (FloatingActionButton) findViewById(R.id.continueButton);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        // For Testing
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.clear();
        preferencesEditor.apply();
    }

    public void submit(View view) {
        if (nameEditView.getText().length() > 0) {
            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            preferencesEditor.putString("name", nameEditView.getText().toString());
            preferencesEditor.apply();
        }

        Intent messageIntent = new Intent(this, IslandSelectionActivity.class);
        startActivity(messageIntent);
    }


}

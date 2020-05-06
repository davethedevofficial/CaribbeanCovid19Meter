package io.rbr.caribbeancovid_19meter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private EditText nameEditView;
    private ImageView profileImageView;
    private FloatingActionButton continueButton;
    private String sharedPrefFile = "io.rbr.caribbeancovid_19.prefs";
    private SharedPreferences mPreferences;
    private Switch themeSwitch;
    public static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Shared Prefs
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);


        nameEditView = (EditText) findViewById(R.id.nameEditView);
        profileImageView = (ImageView) findViewById(R.id.profileImageView);
        continueButton = (FloatingActionButton) findViewById(R.id.continueButton);
        themeSwitch = (Switch) findViewById(R.id.themeSwitchView);

        // On switch change
        final MainActivity _this = this;
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                Toast.makeText(_this, (isChecked ? "Turning off the lights \uD83D\uDE0E" : "Switching to light mode \ud83d\ude01"),Toast.LENGTH_LONG).show();
            }
        });
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

    public void pickImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImageUri = data.getData();
            profileImageView.setImageURI(selectedImageUri);

            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            preferencesEditor.putString("imageUri", selectedImageUri.toString());
            preferencesEditor.apply();
        }
    }

}

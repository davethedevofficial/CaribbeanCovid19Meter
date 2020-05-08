package io.rbr.caribbeancovid_19meter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class IslandDetailActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private TextView nameLabel;
    private ImageView profileImageView;
    private TextView dateLabel;
    private TextView country;
    private TextView totalCount;
    private TextView todayCount;
    private TextView totalDeaths;
    private TextView yesterdayTotalCount;
    private TextView yesterdayDeaths;

    private ImageView upTrend;
    private ImageView noTrend;

    private String sharedPrefFile = "io.rbr.caribbeancovid_19.prefs";
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_island_detail);

          int myNum = 0;
          int yesterday = 0;
          int yesterdayTC = 0;
          int todayDeaths = 0;
          int yesterdayDC = 0;
          int yesterdayD = 0;
        //Get Reference to Views
        nameLabel = (TextView) findViewById(R.id.nameLabel);
        profileImageView = (ImageView) findViewById(R.id.profileImageView);
        dateLabel = (TextView) findViewById(R.id.dateLabel);
        country= (TextView) findViewById(R.id.chooseIslandLabel3);
        totalCount = (TextView) findViewById(R.id.totalNo);
        todayCount= (TextView) findViewById(R.id.todayNewCases);
        totalDeaths= (TextView) findViewById(R.id.totalDeaths) ;
        yesterdayTotalCount = (TextView) findViewById(R.id.yesterdayCount);
        yesterdayDeaths = (TextView)findViewById(R.id.yesterdayDeathLabel);
        upTrend= (ImageView) findViewById(R.id.upTrendImage) ;
        noTrend= (ImageView) findViewById(R.id.noTrendImage) ;

        // Set name on the ui
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        nameLabel.setText(mPreferences.getString("name", getString (R.string.default_name)) + '!');

        // Set Date
        dateLabel.setText(android.text.format.DateFormat.format("dd MMMM, yyyy", new java.util.Date()));

        // Set Image
        try {
            if (!mPreferences.getString("imageUri", "").equals("")) {
                Uri uri = Uri.parse(mPreferences.getString("imageUri", ""));
                profileImageView.setImageURI(uri);
            }
        } catch (Exception e) {}

        String s= getIntent().getExtras().getString("Value");
        String c = getIntent().getExtras().getString("Controller");
        String t = getIntent().getExtras().getString("Today");
        String d = getIntent().getExtras().getString("Deaths");

        todayCount.setText("+" + t + " Cases");
        totalDeaths.setText(d + " Total Deaths");
        totalCount.setText(c);
        country.setText(s);

        try {
             yesterday = Integer.parseInt(c);
             myNum = Integer.parseInt(t);

            noTrend.setVisibility(myNum == 0 ? View.VISIBLE : View.GONE);
            upTrend.setVisibility(myNum > 0 ? View.VISIBLE : View.GONE);

            //to calculate yesterday cases
            yesterdayTC = yesterday-myNum;


        }catch(NumberFormatException e){}


        // putting in the textview
        String yesterdayString = Integer.toString(yesterdayTC);
        yesterdayTotalCount.setText(yesterdayString);

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

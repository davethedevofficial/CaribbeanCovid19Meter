package io.rbr.caribbeancovid_19meter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import io.rbr.caribbeancovid_19meter.adapters.IslandListAdapter;
import io.rbr.caribbeancovid_19meter.helpers.DataLoader;
import io.rbr.caribbeancovid_19meter.helpers.IslandManager;

public class IslandSelectionActivity extends AppCompatActivity {
    private TextView nameLabel;
    private ImageView profileImageView;
    private TextView dateLabel;
    private ProgressBar islandsProgressBar;

    private RecyclerView islandsRecyclerView;
    private IslandListAdapter islandListAdapter;

    private String sharedPrefFile = "io.rbr.caribbeancovid_19.prefs";
    private SharedPreferences mPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_island_selection);

        // Get reference to views
        nameLabel = (TextView) findViewById(R.id.nameLabel);
        profileImageView = (ImageView) findViewById(R.id.profileImageView);
        dateLabel = (TextView) findViewById(R.id.dateLabel);
        islandsRecyclerView = (RecyclerView) findViewById(R.id.islandsRecyclerView);
        islandsProgressBar = findViewById(R.id.islandsProgressBar);
        islandsProgressBar.setVisibility(View.GONE);

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

        // Populate List
        islandListAdapter = new IslandListAdapter(this, IslandManager.islands);
        islandsRecyclerView.setAdapter(islandListAdapter);
        islandsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void refresh(View view) {
        islandsProgressBar.setVisibility(View.VISIBLE);
        DataLoader.loadData(new Runnable() {
            public void run() {
                islandsProgressBar.setVisibility(View.GONE);
                islandListAdapter.notifyDataSetChanged();
            }
        });
    }
}

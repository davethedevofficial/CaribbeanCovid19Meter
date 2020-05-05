package io.rbr.caribbeancovid_19meter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedList;

public class IslandSelectionActivity extends AppCompatActivity {
    private TextView nameLabel;
    private TextView dateLabel;

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
        dateLabel = (TextView) findViewById(R.id.dateLabel);
        islandsRecyclerView = (RecyclerView) findViewById(R.id.islandsRecyclerView);

        // Set name on the ui
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        nameLabel.setText(mPreferences.getString("name", getString (R.string.default_name)) + '!');

        // Set Date
        dateLabel.setText(android.text.format.DateFormat.format("dd MMMM, yyyy", new java.util.Date()));

        // TODO::Load data for islands remotely
        LinkedList<Island> islandList = new LinkedList<Island>();
        islandList.add(new Island("Grenada"));
        islandList.add(new Island("Dominica"));
        islandList.add(new Island("Trinidad"));
        islandList.add(new Island("St. Vincent"));
        islandList.add(new Island("St. Lucia"));
        islandList.add(new Island("Jamaica"));
        islandList.add(new Island("Cuba"));

        // Populate List
        islandListAdapter = new IslandListAdapter(this, islandList);
        islandsRecyclerView.setAdapter(islandListAdapter);
        islandsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}

package io.rbr.caribbeancovid_19meter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class IslandSelectionActivity extends AppCompatActivity {
    private TextView nameLabel;
    private ImageView profileImageView;
    private TextView dateLabel;

    private RecyclerView islandsRecyclerView;
    private IslandListAdapter islandListAdapter;

    private String sharedPrefFile = "io.rbr.caribbeancovid_19.prefs";
    private SharedPreferences mPreferences;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_island_selection);
        // Get reference to views
        nameLabel = (TextView) findViewById(R.id.nameLabel);
        profileImageView = (ImageView) findViewById(R.id.profileImageView);
        dateLabel = (TextView) findViewById(R.id.dateLabel);
        islandsRecyclerView = (RecyclerView) findViewById(R.id.islandsRecyclerView);

        // Set name on the ui
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        nameLabel.setText(mPreferences.getString("name", getString (R.string.default_name)) + '!');

        // Set Date
        dateLabel.setText(android.text.format.DateFormat.format("dd MMMM, yyyy", new java.util.Date()));

        // Set Image
        try {
            Uri uri = Uri.parse(mPreferences.getString("imageUri", ""));
            profileImageView.setImageURI(uri);
        } catch (Exception e) {}

        queue = Volley.newRequestQueue(this);

        String url = "https://opendata.ecdc.europa.eu/covid19/casedistribution/json/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TTTTT", response.toString());
                        try {
                            dateLabel.setText("Response: " + response.getJSONArray("records").get(0).toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("TTTTT", error.toString());
                    }
                });

// Access the RequestQueue through your singleton class.
        queue.add(jsonObjectRequest);

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

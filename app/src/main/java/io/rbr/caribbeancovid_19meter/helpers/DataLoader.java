package io.rbr.caribbeancovid_19meter.helpers;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DataLoader {
    private static String url = "https://opendata.ecdc.europa.eu/covid19/casedistribution/json/";
    private static RequestQueue queue;
    private static Context context;

    public static void init(Context context) {
        DataLoader.context = context;
    }

    public static void loadData(final Runnable runnable) {
        queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            IslandManager.addData(response.getJSONArray("records"), runnable);
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

    }
}

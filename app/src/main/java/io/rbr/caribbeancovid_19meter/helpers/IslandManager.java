package io.rbr.caribbeancovid_19meter.helpers;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

import io.rbr.caribbeancovid_19meter.models.Island;

public class IslandManager {
    public static LinkedList<String> approvedIslands = null;
    public static LinkedList<Island> islands;
    public static BulkAddDataTask bulkAddDataTask;
    public static Runnable bulkAddDataTaskCallback;

    public IslandManager() {
        approvedIslands = new LinkedList<String>();
        approvedIslands.add("Anguilla");
        approvedIslands.add("Antigua and Barbuda");
        approvedIslands.add("Aruba");
        approvedIslands.add("The Bahamas");
        approvedIslands.add("Barbados");
        approvedIslands.add("Bonaire");
        approvedIslands.add("British Virgin Islands");
        approvedIslands.add("Cayman Islands");
        approvedIslands.add("Cuba");
        approvedIslands.add("Curaçao");
        approvedIslands.add("Dominica");
        approvedIslands.add("Dominican Republic");
        approvedIslands.add("Federal Dependencies of Venezuela");
        approvedIslands.add("Grenada");
        approvedIslands.add("Guadeloupe");
        approvedIslands.add("Haiti");
        approvedIslands.add("Jamaica");
        approvedIslands.add("Martinique");
        approvedIslands.add("Montserrat");
        approvedIslands.add("Navassa Island");
        approvedIslands.add("Nueva Esparta");
        approvedIslands.add("Puerto Rico");
        approvedIslands.add("Saba");
        approvedIslands.add("San Andrés and Providencia");
        approvedIslands.add("Saint Barthélemy");
        approvedIslands.add("Saint Kitts and Nevis");
        approvedIslands.add("Saint Lucia");
        approvedIslands.add("Saint Martin");
        approvedIslands.add("Saint Vincent and the Grenadines");
        approvedIslands.add("Sint Eustatius");
        approvedIslands.add("Sint Maarten");
        approvedIslands.add("Trinidad and Tobago");
        approvedIslands.add("Turks and Caicos Islands");
        approvedIslands.add("United States Virgin Islands");
    }

    public static void addData(JSONArray records, Runnable runnable) {
        if (bulkAddDataTask != null) {
            bulkAddDataTask.cancel(true);
        }

        bulkAddDataTaskCallback = runnable;
        bulkAddDataTask = new BulkAddDataTask();
        bulkAddDataTask.execute(records);
    }

    private static void addData(JSONObject record) {
        Boolean exist = false;
        String geoId;
        String countryName;
        int population;
        int todayCases;
        int todayDeaths;
        String date;
        String todayDate = (String) android.text.format.DateFormat.format("dd/MM/yyyy", new java.util.Date());

        try {
            geoId = record.getString("geoId");
            countryName = record.getString("countriesAndTerritories").replace("_", " ");;
            todayDeaths = record.getInt("deaths");
            todayCases = record.getInt("cases");
            population = record.getInt("popData2018");
            date = record.getString("dateRep");
        } catch (JSONException e) {
            return;
        }

        if (!approvedIslands.contains(countryName))
            return;

        for (int i = 0; i < islands.size(); i++) {
            if (islands.get(i).name.equals(countryName)) {
                islands.get(i).totalDeaths += todayDeaths;
                islands.get(i).totalCases += todayCases;
                if (date.equals(todayDate)) {
                    islands.get(i).todayCases = todayCases;
                    islands.get(i).todayDeaths = todayDeaths;
                }
                exist = true;
            };
        }

        if (exist)
            return;

        Island i = new Island(countryName, population);
        i.geoId = geoId;
        i.geoId = i.geoId.equals("DO") ? "doo" : i.geoId;
        i.totalDeaths = todayDeaths;
        i.totalCases = todayCases;
        if (date.equals(todayDate)) {
            i.todayCases = todayCases;
            i.todayDeaths = todayDeaths;
        }
        islands.add(i);
    }

    private static class BulkAddDataTask extends AsyncTask<JSONArray, Integer, LinkedList<Island>> {
        protected LinkedList<Island> doInBackground(JSONArray... jsonArrays) {
            JSONArray json = jsonArrays[0];
            islands = new LinkedList<Island>();

            for (int i = 0; i < json.length(); i++) {
                try {
                    JSONObject record = json.getJSONObject(i);
                    IslandManager.addData(record);
                } catch (JSONException e) { }
                publishProgress((int) ((i / (float) json.length()) * 100));

                // Escape early if cancel() is called
                if (isCancelled()) break;
            }
           return IslandManager.islands;
        }

        protected void onProgressUpdate(Integer... progress) {
            Log.d("TTTT", "PROGRESS: " + progress[0]);
        }

        protected void onPostExecute(LinkedList<Island> result) {
            if (bulkAddDataTaskCallback != null)
                bulkAddDataTaskCallback.run();
            Log.d("TTTT", "FINISHED");
//            showDialog("Downloaded " + result + " bytes");
        }
    }
}

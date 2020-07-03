package org.libreapps.rest;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.libreapps.rest.obj.Bill;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ConnectionRest extends AsyncTask {
    private final static String URL = "https://api.munier.me/eb/bills/";
    private JSONObject jsonObj = null;

    protected String doInBackground(Object[] strings) {
        try {
            return get(strings[0].toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String get(String methode) throws IOException, JSONException {
        String url = URL;
        InputStream is = null;
        String parameters = "";
        Log.v("methode", methode);
        if(!methode.equals("POST")&&(jsonObj!=null)){
            url += jsonObj.getInt("id");
        }
        if(jsonObj != null){
            if(methode.equals("PUT")){
                jsonObj.remove("id");
            }
            parameters  = "data="+ URLEncoder.encode(jsonObj.toString(), "utf-8");
            Log.v("URL", url+" "+parameters);
        }
        try {
            final HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod(methode);

            if(methode.equals("POST")||methode.equals("PUT")){
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(parameters);
                out.close();
            }else{
                conn.setDoInput(true);
                conn.connect();
            }

            is = conn.getInputStream();
            return readIt(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private String readIt(InputStream is) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            response.append(line).append('\n');
        }
        return response.toString();
    }

    public ArrayList<Bill> parse(final String json) {
        try {
            final ArrayList bills = new ArrayList<>();
            final JSONArray jBillArray = new JSONArray(json);
            for (int i = 0; i < jBillArray.length(); i++) {
                bills.add(new Bill(jBillArray.optJSONObject(i)));
            }
            return bills;
        } catch (JSONException e) {
            Log.v("TAG","[JSONException] e : " + e.getMessage());
        }
        return null;
    }

    public void setJsonObj(JSONObject jsonObj){
        this.jsonObj = jsonObj;
    }

}

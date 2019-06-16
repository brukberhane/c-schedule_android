package org.squiril.hilcoe.schedule;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import static org.squiril.hilcoe.schedule.Constants.PREF_NAME;

public class NetworkManager {

    private static String TAG = "NetworkManager";
    private Context context;
    private SharedPreferences prefs;

    public NetworkManager(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public byte[] getUrlBytes(String urlSpec)  throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage() + ": with "+ urlSpec);
            }

            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0){
                out.write(buffer, 0, bytesRead);
            }

            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    private String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public int saveScheduleFile(){
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String bID = prefs.getString("bid", "");

        try{
            String url = "https://c-sched-fmpgykdxte.now.sh/schedule/get/" + bID;
            FileInputStream fis;
            try {
                fis = context.openFileInput(bID + ".json");
            } catch (FileNotFoundException e){
                fis = null;
            }
            if (fis == null) {
                String jsonString = getUrlString(url);
                Log.i(TAG, "Received JSON: " + jsonString);

                if (!(new JSONObject(jsonString).getString("message").equals("OK"))){
                    return 2;
                }
                FileOutputStream fos;
                fos = context.openFileOutput(bID + ".json", Context.MODE_PRIVATE);
                fos.write(Objects.requireNonNull(jsonString).getBytes());
                fos.close();
                Log.i(TAG, "File saved (probably)");
                return 0;
            } else {
                return 0;
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return 3;
        }
    }
}

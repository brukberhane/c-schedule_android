package org.squiril.hilcoe.schedule;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.squiril.hilcoe.schedule.models.ClassDay;
import org.squiril.hilcoe.schedule.models.Period;
import org.squiril.hilcoe.schedule.models.Schedule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.squiril.hilcoe.schedule.Constants.PREF_NAME;

public class JSONParser {
    //TODO: For god's sake there has to be a better way to parse the JSON
    private static String TAG = "JSONParser";
    private static String firstClass = "firstClass";
    private static String secondClass = "secondClass";
    private static String thirdClass = "thirdClass";
    private static String fourthClass = "fourthClass";
    private static String title = "Title";
    private static String room = "Room";

    private Context context;
    private SharedPreferences prefs;

    public JSONParser(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public JSONObject getScheduleObject(String bid){
        try{
            JSONObject reader = new JSONObject(readFromFile());

            return reader.getJSONObject("schedule");
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public Schedule getSchedule(JSONObject SCHEDULE) {
        Schedule schedule = new Schedule();

        try {
            schedule.setScheduleType(SCHEDULE.getString("scheduleType"));

            Calendar[] cals = new Calendar[6];
            for (int i=0; i < cals.length; i++){
                cals[i] = new GregorianCalendar();
            }
            ClassDay[] days = new ClassDay[6];
            for (int i=0; i < days.length; i++){
                days[i] = new ClassDay();
            }
            String[] DOW = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};
            String[] PERIODS = {"firstPeriod", "secondPeriod", "thirdPeriod", "fourthPeriod",
                    "fifthPeriod", "sixthPeriod"};

            cals[0].set(Calendar.HOUR_OF_DAY, 8);
            cals[0].set(Calendar.MINUTE, 0);
            cals[1].set(Calendar.HOUR_OF_DAY, 10);
            cals[1].set(Calendar.MINUTE, 0);
            cals[2].set(Calendar.HOUR_OF_DAY, 13);
            cals[2].set(Calendar.MINUTE, 30);
            cals[3].set(Calendar.HOUR_OF_DAY, 15);
            cals[3].set(Calendar.MINUTE, 30);
            cals[4].set(Calendar.HOUR_OF_DAY, 18);
            cals[4].set(Calendar.MINUTE, 30);
            cals[5].set(Calendar.HOUR_OF_DAY, 19);
            cals[5].set(Calendar.MINUTE, 30);


            for (int i = 0 ; i < days.length; i++){

                for (int j = 0; j < cals.length; j++){
                    JSONObject obj = SCHEDULE.getJSONObject(DOW[i]).getJSONObject(PERIODS[j]);
                    switch (j){
                        case 0:
                            days[i].setPeriodOne(new Period(obj.getString(title),
                                    obj.getString(room), cals[j]));
                            break;
                        case 1:
                            days[i].setPeriodTwo(new Period(obj.getString(title),
                                    obj.getString(room), cals[j]));
                            break;
                        case 2:
                            days[i].setPeriodThree(new Period(obj.getString(title),
                                    obj.getString(room), cals[j]));
                            break;
                        case 3:
                            days[i].setPeriodFour(new Period(obj.getString(title),
                                    obj.getString(room), cals[j]));
                            break;
                        case 4:
                            days[i].setPeriodFive(new Period(obj.getString(title),
                                    obj.getString(room), cals[j]));
                            break;
                        case 5:
                            days[i].setPeriodSix(new Period(obj.getString(title),
                                    obj.getString(room), cals[j]));
                            break;
                    }
                }
            }

            schedule.setMonday(days[0]);
            schedule.setTuesday(days[1]);
            schedule.setWednesday(days[2]);
            schedule.setThursday(days[3]);
            schedule.setFriday(days[4]);
            schedule.setSaturday(days[5]);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return schedule;
    }

    private String readFromFile(){
        String ret = "";
        InputStream inputStream = null;
        try{
            inputStream = context.openFileInput(prefs.getString("bid", "") + ".json");

            if (inputStream != null){
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader buffReader = new BufferedReader(reader);
                String receivedString;
                StringBuilder strBuilder = new StringBuilder();

                while ((receivedString = buffReader.readLine()) != null)
                    strBuilder.append(receivedString);

                ret = strBuilder.toString();
                Log.i(TAG + "\nreadFromFile(): ", ret);
            }

        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                assert inputStream != null;
                inputStream.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return ret;
    }

}

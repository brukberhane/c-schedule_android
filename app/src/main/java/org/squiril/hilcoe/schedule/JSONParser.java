package org.squiril.hilcoe.schedule;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.squiril.hilcoe.schedule.models.Schedule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.squiril.hilcoe.schedule.Constants.PREF_NAME;

public class JSONParser {
    private static String TAG = "JSONParser";
    private static String firstClass = "firstClass";
    private static String secondClass = "secondClass";
    private static String thirdClass = "thirdClass";
    private static String fourthClass = "fourthClass";
    private static String title = "title";
    private static String room = "room";

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

    public Schedule getSchedule(JSONObject SCHEDULE){
        Schedule schedule = new Schedule();

        try{

            schedule.setPeriodOneTime("08:00 - 10:00");
            schedule.setPeriodTwoTime("10:00 - 12:00");
            schedule.setPeriodThreeTime("13:30 - 15:30");
            schedule.setPeriodFourTime("15:30 - 17:30");

            schedule.setScheduleType(SCHEDULE.getString("scheduleType"));

            JSONObject monday = SCHEDULE.getJSONObject("monday");

            JSONObject mondayFirst = monday.getJSONObject(firstClass);
            schedule.setMondayFirstTitle(mondayFirst.getString(title));
            schedule.setMondayFirstRoom(mondayFirst.getString(room));
            JSONObject mondaySecond = monday.getJSONObject(secondClass);
            schedule.setMondaySecondTitle(mondaySecond.getString(title));
            schedule.setMondaySecondRoom(mondaySecond.getString(room));
            JSONObject mondayThird = monday.getJSONObject(thirdClass);
            schedule.setMondayThirdTitle(mondayThird.getString(title));
            schedule.setMondayThirdRoom(mondayThird.getString(room));
            JSONObject mondayFourth = monday.getJSONObject(fourthClass);
            schedule.setMondayFourthTitle(mondayFourth.getString(title));
            schedule.setMondayFourthRoom(mondayFourth.getString(room));

            JSONObject tuesday = SCHEDULE.getJSONObject("tuesday");

            JSONObject tuesdayFirst = tuesday.getJSONObject(firstClass);
            schedule.setTuesdayFirstTitle(tuesdayFirst.getString(title));
            schedule.setTuesdayFirstRoom(tuesdayFirst.getString(room));
            JSONObject tuesdaySecond = tuesday.getJSONObject(secondClass);
            schedule.setTuesdaySecondTitle(tuesdaySecond.getString(title));
            schedule.setTuesdaySecondRoom(tuesdaySecond.getString(room));
            JSONObject tuesdayThird = tuesday.getJSONObject(thirdClass);
            schedule.setTuesdayThirdTitle(tuesdayThird.getString(title));
            schedule.setTuesdayThirdRoom(tuesdayThird.getString(room));
            JSONObject tuesdayFourth = tuesday.getJSONObject(fourthClass);
            schedule.setTuesdayFourthTitle(tuesdayFourth.getString(title));
            schedule.setTuesdayFourthRoom(tuesdayFourth.getString(room));

            JSONObject wednesday = SCHEDULE.getJSONObject("wednesday");

            JSONObject wednesdayFirst = wednesday.getJSONObject(firstClass);
            schedule.setWednesdayFirstTitle(wednesdayFirst.getString(title));
            schedule.setWednesdayFirstRoom(wednesdayFirst.getString(room));
            JSONObject wednesdaySecond = wednesday.getJSONObject(secondClass);
            schedule.setWednesdaySecondTitle(wednesdaySecond.getString(title));
            schedule.setWednesdaySecondRoom(wednesdaySecond.getString(room));
            JSONObject wednesdayThird = wednesday.getJSONObject(thirdClass);
            schedule.setWednesdayThirdTitle(wednesdayThird.getString(title));
            schedule.setWednesdayThirdRoom(wednesdayThird.getString(room));
            JSONObject wednesdayFourth = wednesday.getJSONObject(fourthClass);
            schedule.setWednesdayFourthTitle(wednesdayFourth.getString(title));
            schedule.setWednesdayFourthRoom(wednesdayFourth.getString(room));

            JSONObject thursday = SCHEDULE.getJSONObject("thursday");

            JSONObject thursdayFirst = thursday.getJSONObject(firstClass);
            schedule.setThursdayFirstTitle(thursdayFirst.getString(title));
            schedule.setThursdayFirstRoom(thursdayFirst.getString(room));
            JSONObject thursdaySecond = thursday.getJSONObject(secondClass);
            schedule.setThursdaySecondTitle(thursdaySecond.getString(title));
            schedule.setThursdaySecondRoom(thursdaySecond.getString(room));
            JSONObject thursdayThird = thursday.getJSONObject(thirdClass);
            schedule.setThursdayThirdTitle(thursdayThird.getString(title));
            schedule.setThursdayThirdRoom(thursdayThird.getString(room));
            JSONObject thursdayFourth = thursday.getJSONObject(fourthClass);
            schedule.setThursdayFourthTitle(thursdayFourth.getString(title));
            schedule.setThursdayFourthRoom(thursdayFourth.getString(room));

            JSONObject friday = SCHEDULE.getJSONObject("friday");

            JSONObject fridayFirst = friday.getJSONObject(firstClass);
            schedule.setFridayFirstTitle(fridayFirst.getString(title));
            schedule.setFridayFirstRoom(fridayFirst.getString(room));
            JSONObject fridaySecond = friday.getJSONObject(secondClass);
            schedule.setFridaySecondTitle(fridaySecond.getString(title));
            schedule.setFridaySecondRoom(fridaySecond.getString(room));
            JSONObject fridayThird = friday.getJSONObject(thirdClass);
            schedule.setFridayThirdTitle(fridayThird.getString(title));
            schedule.setFridayThirdRoom(fridayThird.getString(room));
            JSONObject fridayFourth = friday.getJSONObject(fourthClass);
            schedule.setFridayFourthTitle(fridayFourth.getString(title));
            schedule.setFridayFourthRoom(fridayFourth.getString(room));

            JSONObject saturday = SCHEDULE.getJSONObject("saturday");

            JSONObject saturdayFirst = saturday.getJSONObject(firstClass);
            schedule.setSaturdayFirstTitle(saturdayFirst.getString(title));
            schedule.setSaturdayFirstRoom(saturdayFirst.getString(room));
            JSONObject saturdaySecond = saturday.getJSONObject(secondClass);
            schedule.setSaturdaySecondTitle(saturdaySecond.getString(title));
            schedule.setSaturdaySecondRoom(saturdaySecond.getString(room));
            JSONObject saturdayThird = saturday.getJSONObject(thirdClass);
            schedule.setSaturdayThirdTitle(saturdayThird.getString(title));
            schedule.setSaturdayThirdRoom(saturdayThird.getString(room));
            JSONObject saturdayFourth = saturday.getJSONObject(fourthClass);
            schedule.setSaturdayFourthTitle(saturdayFourth.getString(title));
            schedule.setSaturdayFourthRoom(saturdayFourth.getString(room));
        } catch (JSONException e){
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

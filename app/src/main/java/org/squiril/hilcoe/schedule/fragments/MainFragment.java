package org.squiril.hilcoe.schedule.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.squiril.hilcoe.schedule.JSONParser;
import org.squiril.hilcoe.schedule.NetworkManager;
import org.squiril.hilcoe.schedule.R;
import org.squiril.hilcoe.schedule.activities.MainActivity;
import org.squiril.hilcoe.schedule.models.Schedule;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static org.squiril.hilcoe.schedule.Constants.PREF_NAME;

public class MainFragment extends Fragment {

    TextView ScheduleType;

    TextView FirstMondayTitle;
    TextView FirstTuesdayTitle;
    TextView FirstWednesdayTitle;
    TextView FirstThursdayTitle;
    TextView FirstFridayTitle;
    TextView FirstSaturdayTitle;
    TextView SecondMondayTitle;
    TextView SecondTuesdayTitle;
    TextView SecondWednesdayTitle;
    TextView SecondThursdayTitle;
    TextView SecondFridayTitle;
    TextView SecondSaturdayTitle;
    TextView ThirdMondayTitle;
    TextView ThirdTuesdayTitle;
    TextView ThirdWednesdayTitle;
    TextView ThirdThursdayTitle;
    TextView ThirdFridayTitle;
    TextView ThirdSaturdayTitle;
    TextView FourthMondayTitle;
    TextView FourthTuesdayTitle;
    TextView FourthWednesdayTitle;
    TextView FourthThursdayTitle;
    TextView FourthFridayTitle;
    TextView FourthSaturdayTitle;

    TextView FirstMondayRoom;
    TextView FirstTuesdayRoom;
    TextView FirstWednesdayRoom;
    TextView FirstThursdayRoom;
    TextView FirstFridayRoom;
    TextView FirstSaturdayRoom;
    TextView SecondMondayRoom;
    TextView SecondTuesdayRoom;
    TextView SecondWednesdayRoom;
    TextView SecondThursdayRoom;
    TextView SecondFridayRoom;
    TextView SecondSaturdayRoom;
    TextView ThirdMondayRoom;
    TextView ThirdTuesdayRoom;
    TextView ThirdWednesdayRoom;
    TextView ThirdThursdayRoom;
    TextView ThirdFridayRoom;
    TextView ThirdSaturdayRoom;
    TextView FourthMondayRoom;
    TextView FourthTuesdayRoom;
    TextView FourthWednesdayRoom;
    TextView FourthThursdayRoom;
    TextView FourthFridayRoom;
    TextView FourthSaturdayRoom;

    NestedScrollView nestedScrollView;
    LinearLayout loadingLayout;

    //TODO: I don't like this here but I do it for the SnackBars
    private View view;

    private JSONParser parser;
    private SharedPreferences prefs;
    private AutofillManager afm; //TODO: This hasn't been working. Find other methods.
    private int currentOrientation; //TODO: I really don't like global vars. Change this.

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        currentOrientation = getResources().getConfiguration().orientation;
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        this.view = view;

        prefs = Objects.requireNonNull(getActivity()).getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        parser = new JSONParser(getActivity());

        initViews(view);
        changeVisibility(0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            afm = getActivity().getSystemService(AutofillManager.class);
        }

        Objects.requireNonNull(((MainActivity) getActivity()).getSupportActionBar())
                .setTitle(prefs.getString("bid", ""));



        if(!checkIfUpdated()){
            new FetchItemsTask().execute();
            changeVisibility(0);
        }else {
            currentOrientation = getResources().getConfiguration().orientation;
            setupMain();
        }

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();

        if (checkIfUpdated())
            setupMain();
    }

    @SuppressLint("StaticFieldLeak")
    private class FetchItemsTask extends AsyncTask<Void, Void, Void> {
        private int sentinel;

        @Override
        protected Void doInBackground(Void... params){

            sentinel = new NetworkManager(Objects.requireNonNull(getActivity())).saveScheduleFile();

            return null;
        }

        @Override
        protected void onPostExecute(Void item){
            switch (sentinel){
                case 0:
                    setupMain();
                    prefs.edit().putBoolean("recentlyUpdated", true).apply();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        afm.commit();
                    }
                    SimpleSnackBar("Schedule Updated");
                    break;
                case 1:
                    SimpleSnackBar("Failed to download");
                    changeVisibility(1);
                    break;
                case 2:
                    if (getActivity() != null)
                        Toast.makeText(
                                getActivity(), "Invalid Batch ID.", Toast.LENGTH_LONG).show();
                    prefs.edit().putString("bid", "").apply();
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new LoginFragment())
                            .commit();
                    changeVisibility(1);
                    break;
                default:
                    if (getActivity() != null)
                        SimpleSnackBar("No Internet Access. Try again later.");
                    changeVisibility(1);
            }
        }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.fragment_main_menu_refresh:

                prefs.edit().putBoolean("recentlyUpdated", false).apply();
                new FetchItemsTask().execute();
                changeVisibility(0);
                if (getActivity() != null)
                    Toast.makeText(getActivity(), "Updating schedule", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.fragment_main_menu_change:

                prefs.edit().putString("bid", "")/*.putBoolean("recentlyUpdated", false)*/.apply();
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initViews(View view){
        ScheduleType = view.findViewById(R.id.fragment_main_schedule_type);

        FirstMondayRoom = view.findViewById(R.id.fragment_summary_first_monday_room);
        FirstMondayTitle = view.findViewById(R.id.fragment_summary_first_monday_title);
        FirstTuesdayTitle = view.findViewById(R.id.fragment_summary_first_tuesday_title);
        FirstTuesdayRoom = view.findViewById(R.id.fragment_summary_first_tuesday_room);
        FirstWednesdayTitle = view.findViewById(R.id.fragment_summary_first_wednesday_title);
        FirstWednesdayRoom = view.findViewById(R.id.fragment_summary_first_wednesday_room);
        FirstThursdayTitle = view.findViewById(R.id.fragment_summary_first_thursday_title);
        FirstThursdayRoom = view.findViewById(R.id.fragment_summary_first_thursday_room);
        FirstFridayTitle = view.findViewById(R.id.fragment_summary_first_friday_title);
        FirstFridayRoom = view.findViewById(R.id.fragment_summary_first_friday_room);
        FirstSaturdayTitle = view.findViewById(R.id.fragment_summary_first_saturday_title);
        FirstSaturdayRoom = view.findViewById(R.id.fragment_summary_first_saturday_room);
        SecondMondayTitle = view.findViewById(R.id.fragment_summary_second_monday_title);
        SecondMondayRoom = view.findViewById(R.id.fragment_summary_second_monday_room);
        SecondTuesdayTitle = view.findViewById(R.id.fragment_summary_second_tuesday_title);
        SecondTuesdayRoom = view.findViewById(R.id.fragment_summary_second_tuesday_room);
        SecondWednesdayTitle = view.findViewById(R.id.fragment_summary_second_wednesday_title);
        SecondWednesdayRoom = view.findViewById(R.id.fragment_summary_second_wednesday_room);
        SecondThursdayTitle = view.findViewById(R.id.fragment_summary_second_thursday_title);
        SecondThursdayRoom = view.findViewById(R.id.fragment_summary_second_thursday_room);
        SecondFridayTitle = view.findViewById(R.id.fragment_summary_second_friday_title);
        SecondFridayRoom = view.findViewById(R.id.fragment_summary_second_friday_room);
        SecondSaturdayTitle = view.findViewById(R.id.fragment_summary_second_saturday_title);
        SecondSaturdayRoom = view.findViewById(R.id.fragment_summary_second_saturday_room);
        ThirdMondayTitle = view.findViewById(R.id.fragment_summary_third_monday_title);
        ThirdMondayRoom = view.findViewById(R.id.fragment_summary_third_monday_room);
        ThirdTuesdayTitle = view.findViewById(R.id.fragment_summary_third_tuesday_title);
        ThirdTuesdayRoom = view.findViewById(R.id.fragment_summary_third_tuesday_room);
        ThirdWednesdayTitle = view.findViewById(R.id.fragment_summary_third_wednesday_title);
        ThirdWednesdayRoom = view.findViewById(R.id.fragment_summary_third_wednesday_room);
        ThirdThursdayTitle = view.findViewById(R.id.fragment_summary_third_thursday_title);
        ThirdThursdayRoom = view.findViewById(R.id.fragment_summary_third_thursday_room);
        ThirdFridayTitle = view.findViewById(R.id.fragment_summary_third_friday_title);
        ThirdFridayRoom = view.findViewById(R.id.fragment_summary_third_friday_room);
        ThirdSaturdayTitle = view.findViewById(R.id.fragment_summary_third_saturday_title);
        ThirdSaturdayRoom = view.findViewById(R.id.fragment_summary_third_saturday_room);
        FourthMondayTitle = view.findViewById(R.id.fragment_summary_fourth_monday_title);
        FourthMondayRoom = view.findViewById(R.id.fragment_summary_fourth_monday_room);
        FourthTuesdayTitle = view.findViewById(R.id.fragment_summary_fourth_tuesday_title);
        FourthTuesdayRoom = view.findViewById(R.id.fragment_summary_fourth_tuesday_room);
        FourthWednesdayTitle = view.findViewById(R.id.fragment_summary_fourth_wednesday_title);
        FourthWednesdayRoom = view.findViewById(R.id.fragment_summary_fourth_wednesday_room);
        FourthThursdayTitle = view.findViewById(R.id.fragment_summary_fourth_thursday_title);
        FourthThursdayRoom = view.findViewById(R.id.fragment_summary_fourth_thursday_room);
        FourthFridayTitle = view.findViewById(R.id.fragment_summary_fourth_friday_title);
        FourthFridayRoom = view.findViewById(R.id.fragment_summary_fourth_friday_room);
        FourthSaturdayTitle = view.findViewById(R.id.fragment_summary_fourth_saturday_title);
        FourthSaturdayRoom = view.findViewById(R.id.fragment_summary_fourth_saturday_room);

        nestedScrollView = view.findViewById(R.id.fragment_main_loaded_view);
        loadingLayout = view.findViewById(R.id.fragment_main_loading_view);
    }
    private void setupMain(){
        //TODO: The data it passes it now is completely useless and is just a preparation for adding multiple schedules.
        Schedule schedule = parser.getSchedule(parser.getScheduleObject(prefs.getString("bid", "") + ".json"));

        //Period Times TODO: This is useless code until the need arises
//        PeriodOneTime.setText(schedule.getPeriodOneTime());
//        PeriodTwoTime.setText(schedule.getPeriodTwoTime());
//        PeriodThreeTIme.setText(schedule.getPeriodThreeTime());
//        PeriodFourTime.setText(schedule.getPeriodFourTime());

        ScheduleType.setText(schedule.getScheduleType());
        ScheduleType.setTextColor(getResources().getColor(android.R.color.holo_red_dark));

        //Monday's schedule


        FirstMondayTitle.setText(checkForOrientation(schedule.getMondayFirstTitle()));
        FirstMondayRoom.setText(schedule.getMondayFirstRoom());

        SecondMondayTitle.setText(checkForOrientation(schedule.getMondaySecondTitle()));
        SecondMondayRoom.setText(schedule.getMondaySecondRoom());


        ThirdMondayTitle.setText(checkForOrientation(schedule.getMondayThirdTitle()));
        ThirdMondayRoom.setText(schedule.getMondayThirdRoom());

        FourthMondayTitle.setText(checkForOrientation(schedule.getMondayFourthTitle()));
        FourthMondayRoom.setText(schedule.getMondayFourthRoom());


        //Tuesday's schedule
        FirstTuesdayTitle.setText(checkForOrientation(schedule.getTuesdayFirstTitle()));
        FirstTuesdayRoom.setText(schedule.getTuesdayFirstRoom());

        SecondTuesdayTitle.setText(checkForOrientation(schedule.getTuesdaySecondTitle()));
        SecondTuesdayRoom.setText(schedule.getTuesdaySecondRoom());

        ThirdTuesdayTitle.setText(checkForOrientation(schedule.getTuesdayThirdTitle()));
        ThirdTuesdayRoom.setText(schedule.getTuesdayThirdRoom());

        FourthTuesdayTitle.setText(checkForOrientation(schedule.getTuesdayFourthTitle()));
        FourthTuesdayRoom.setText(schedule.getTuesdayFourthRoom());


        //Wednesday's schedule

        FirstWednesdayTitle.setText(checkForOrientation(schedule.getWednesdayFirstTitle()));
        FirstWednesdayRoom.setText(schedule.getWednesdayFirstRoom());

        SecondWednesdayTitle.setText(checkForOrientation(schedule.getWednesdaySecondTitle()));
        SecondWednesdayRoom.setText(schedule.getWednesdaySecondRoom());

        ThirdWednesdayTitle.setText(checkForOrientation(schedule.getWednesdayThirdTitle()));
        ThirdWednesdayRoom.setText(schedule.getWednesdayThirdRoom());

        FourthWednesdayTitle.setText(checkForOrientation(schedule.getWednesdayFourthTitle()));
        FourthWednesdayRoom.setText(schedule.getWednesdayFourthRoom());


        //Thursday's schedule

        FirstThursdayTitle.setText(checkForOrientation(schedule.getThursdayFirstTitle()));
        FirstThursdayRoom.setText(schedule.getThursdayFirstRoom());

        SecondThursdayTitle.setText(checkForOrientation(schedule.getThursdaySecondTitle()));
        SecondThursdayRoom.setText(schedule.getThursdaySecondRoom());

        ThirdThursdayTitle.setText(checkForOrientation(schedule.getThursdayThirdTitle()));
        ThirdThursdayRoom.setText(schedule.getThursdayThirdRoom());

        FourthThursdayTitle.setText(checkForOrientation(schedule.getThursdayFourthTitle()));
        FourthThursdayRoom.setText(schedule.getThursdayFourthRoom());


        //Friday's schedule

        FirstFridayTitle.setText(checkForOrientation(schedule.getFridayFirstTitle()));
        FirstFridayRoom.setText(schedule.getFridayFirstRoom());

        SecondFridayTitle.setText(checkForOrientation(schedule.getFridaySecondTitle()));
        SecondFridayRoom.setText(schedule.getFridaySecondRoom());

        ThirdFridayTitle.setText(checkForOrientation(schedule.getFridayThirdTitle()));
        ThirdFridayRoom.setText(schedule.getFridayThirdRoom());

        FourthFridayTitle.setText(checkForOrientation(schedule.getFridayFourthTitle()));
        FourthFridayRoom.setText(schedule.getFridayFourthRoom());


        //Saturday's schedule

        FirstSaturdayTitle.setText(checkForOrientation(schedule.getSaturdayFirstTitle()));
        FirstSaturdayRoom.setText(schedule.getSaturdayFirstRoom());

        SecondSaturdayTitle.setText(checkForOrientation(schedule.getSaturdaySecondTitle()));
        SecondSaturdayRoom.setText(schedule.getSaturdaySecondRoom());

        ThirdSaturdayTitle.setText(checkForOrientation(schedule.getSaturdayThirdTitle()));
        ThirdSaturdayRoom.setText(schedule.getSaturdayThirdRoom());

        FourthSaturdayTitle.setText(checkForOrientation(schedule.getSaturdayFourthTitle()));
        FourthSaturdayRoom.setText(schedule.getSaturdayFourthRoom());

        changeVisibility(1);
    }
    private void SimpleSnackBar(String text){
        CoordinatorLayout cl = view.findViewById(R.id.fragment_main_coordinator_layout);
        final Snackbar snackbar = Snackbar.make(cl, text, Snackbar.LENGTH_LONG);
        snackbar.setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        }).show();
    }
    private boolean checkIfUpdated(){
        FileInputStream fis;
        try {
            fis = Objects.requireNonNull(getActivity()).openFileInput(prefs.getString("bid", ""));
        } catch (FileNotFoundException e){
            fis = null;
        }
        return prefs.getBoolean("recentlyUpdated", false) && fis != null;
    }
    private String checkForOrientation(String text){
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            if (!text.equals("") && text.contains("("))
                return text.substring(0, text.indexOf("("));
            else
                return text;
        } else {
            return text;
        }
    }
    private void changeVisibility(int visibility){
        if (visibility == 1) {
            loadingLayout.setVisibility(LinearLayout.GONE);
            nestedScrollView.setVisibility(NestedScrollView.VISIBLE);
        } else {
            loadingLayout.setVisibility(LinearLayout.VISIBLE);
            nestedScrollView.setVisibility(NestedScrollView.GONE);
        }
    }
}

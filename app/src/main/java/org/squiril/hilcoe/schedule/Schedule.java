package org.squiril.hilcoe.schedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class Schedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        GridView grid = (GridView) findViewById(R.id.grdSched);
        grid.setAdapter(new GridViewAdapter());

    }
    class GridViewAdapter extends BaseAdapter {
        String[][] titles = new String[7][5];
        GridViewAdapter() {
            titles[0][0] = "Date";
            titles[0][1] = "8:00 - 10:00";
            titles[0][2] = "10:00 - 12:00";
            titles[0][3] = "1:30 - 3:30";
            titles[0][4] = "3:30 - 5:30";
            titles[1][0] = "Monday";
            titles[2][0] = "Tuesday";
            titles[3][0] = "Wednesday";
            titles[4][0] = "Thursday";
            titles[5][0] = "Friday";
            titles[6][0] = "Saturday";

            for(int i = 1; i < 7; i++) {
                for(int j = 1; j < 5; j++) if (titles[i][j] == null) titles[i][j] = "Something";
            }
        }
        @Override
        public int getCount() {
            return 35;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        public Object getItem(int i, int j) {
            return titles[i][j];
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.title_item,viewGroup,false);
            TextView text = (TextView) view.findViewById(R.id.txtTitName);
            int j;
            j = (i / 7);
            if(i > 6) i = i % 7;
            Log.d("Iteration", "I = " + i + " J = " + j);
            text.setText(titles[i][j]);
            return view;
        }
    }
}


package org.squiril.hilcoe.schedule.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillManager;
import android.widget.EditText;

import org.squiril.hilcoe.schedule.R;
import org.squiril.hilcoe.schedule.dialogs.ConfirmDialog;

import java.util.Objects;

import static org.squiril.hilcoe.schedule.Constants.PREF_NAME;

public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }

    private CoordinatorLayout coordinatorLayout;
    private EditText bIDEditText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = getLayoutInflater().inflate(R.layout.fragment_login, container, false);
        final SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        coordinatorLayout = view.findViewById(R.id.login_activity_coordinator_layout);
        bIDEditText = view.findViewById(R.id.login_activity_bid_edit_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AutofillManager afm = getActivity().getSystemService(AutofillManager.class);
            if (afm != null && afm.isEnabled() && afm.hasEnabledAutofillServices())
                afm.requestAutofill(bIDEditText);
            else
                Snackbar.make(coordinatorLayout, "No AutoFill", Snackbar.LENGTH_LONG);
        }

        prefs.edit().putBoolean("recentlyUpdated", false).apply();

        FloatingActionButton fab = view.findViewById(R.id.login_activity_enter_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!bIDEditText.getText().toString().equals("")) {
                    ConfirmDialog.Builder builder = new ConfirmDialog.Builder(getActivity())
                            .setTitle("Are you sure about your batch is " + bIDEditText
                                    .getText().toString().trim().toUpperCase()+ "?")
                            .setPositiveButton("Yes", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Snackbar.make(coordinatorLayout, "Noice", Snackbar.LENGTH_LONG).show();

                                    prefs.edit().putString("bid", bIDEditText.getText().toString().trim().toUpperCase()).apply();
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.fragment_container, new MainFragment())
                                            .commit();
                                }
                            })
                            .setNegativeButton("No", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Snackbar.make(coordinatorLayout, "Fine. Be like dat den", Snackbar.LENGTH_LONG).show();
                                }
                            });
                    builder.build().show();
                } else {
                    final Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "First Enter your Batch ID & Section", Snackbar.LENGTH_LONG);
                    snackbar.setAction("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                        }
                    });
                    snackbar.show();
                }
            }
        });

        return view;
    }

}

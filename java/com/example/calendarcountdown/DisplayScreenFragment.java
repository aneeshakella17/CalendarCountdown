package com.example.calendarcountdown;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DisplayScreenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DisplayScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayScreenFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String eventName = "";
    private String date = "";
    private String time = "";
    Activity mContext = null;

    private OnFragmentInteractionListener mListener;
    Runnable updater;

    public DisplayScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DisplayScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayScreenFragment newInstance(String param1, String param2) {
        DisplayScreenFragment fragment = new DisplayScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            this.eventName = getArguments().getString("Event Name");
            this.date = getArguments().getString("Date");
            this.time = getArguments().getString("Time");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v =  inflater.inflate(R.layout.fragment_display_screen, container, false);
        Button deleteButton = v.findViewById(R.id.delete_button);

        TextView eventNameText = (TextView) v.findViewById(R.id.event_name);
        eventNameText.setText(this.eventName);

        TextView dateText = (TextView) v.findViewById(R.id.date);
        dateText.setText(this.date);

        TextView timeText = (TextView) v.findViewById(R.id.time);
        timeText.setText(this.time);


        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EventDbHelper eventDbHelper = new EventDbHelper(getActivity());
                SQLiteDatabase sqLiteDatabase = eventDbHelper.getWritableDatabase();
                eventDbHelper.deleteEvent(eventName, sqLiteDatabase);
                HomeScreen.removeName(eventName);
                Toast.makeText(getActivity(), "Event:" + " " + eventName + " " + "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack();
                HomeScreenFragment hsf = new HomeScreenFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.home, hsf, "AddnamePane");
                fragmentTransaction.commit();
            }
        });;



        final Calendar startCal = Calendar.getInstance();
        System.out.println(this.date);
        String[] dateSplit = this.date.split("/");
        System.out.println(dateSplit);
        String[] timeSplit = this.time.split(":");
        System.out.println(timeSplit);
        startCal.set(Integer.parseInt(dateSplit[2]), Integer.parseInt(dateSplit[0]),
                Integer.parseInt(dateSplit[1]), Integer.parseInt(timeSplit[0]),
                Integer.parseInt(timeSplit[1]));


        Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);

                        if(getActivity() == null)
                            return;

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final Calendar endCalendar = Calendar.getInstance();
                                long end = endCalendar.getTimeInMillis();
                                long start = startCal.getTimeInMillis();
                                String units = Long.toString(TimeUnit.MILLISECONDS.toSeconds(Math.abs(end - start)));
                                int totSeconds = Integer.parseInt(units);
                                int years = (int) Math.floor(totSeconds/(365 * 60 * 60 * 24));
                                int months = (int) Math.floor( (totSeconds - years * (365 * 60 * 60 * 24))/(30 * 60 * 60 * 24));
                                int days = (int) Math.floor((totSeconds - years * (365 * 60 * 60 * 24) - months * (30 * 60 * 60 * 24))/(60 * 60 * 24));
                                int hours = (int) Math.floor((totSeconds - years * (365 * 60 * 60 * 24) - months * (30 * 60 * 60 * 24) - days * 60 * 60 * 24)/(60 * 60));
                                int minutes = (int) Math.floor((totSeconds - years * (365 * 60 * 60 * 24) - months * (30 * 60 * 60 * 24) - days * 60 * 60 * 24 - hours * 60 * 60)/(60));
                                int seconds = (int) Math.floor(totSeconds - years * (365 * 60 * 60 * 24) - months * (30 * 60 * 60 * 24) - days * 60 * 60 * 24 - hours * 60 * 60 - minutes * 60);
                                TextView num_years = v.findViewById(R.id.num_of_years);
                                TextView num_months = v.findViewById(R.id.num_of_months);
                                TextView num_days = v.findViewById(R.id.num_of_days);
                                TextView num_hours = v.findViewById(R.id.num_of_hours);
                                TextView num_minutes = v.findViewById(R.id.num_of_mins);
                                TextView num_seconds = v.findViewById(R.id.num_of_secs);

                                num_years.setText(Integer.toString(years));
                                num_months.setText(Integer.toString(months));
                                num_days.setText(Integer.toString(days));
                                num_hours.setText(Integer.toString(hours));
                                num_minutes.setText(Integer.toString(minutes));
                                num_seconds.setText(Integer.toString(seconds));
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };


        try {
            thread.start();
        }catch (NullPointerException e){
        }

        return v;


    }





    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (Activity) context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

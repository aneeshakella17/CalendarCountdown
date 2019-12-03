package com.example.calendarcountdown;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditScreenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditScreenFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private int year;
    private int day;
    private int month;
    private int hour;
    private int minute;

    private OnFragmentInteractionListener mListener;

    public EditScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditScreenFragment newInstance(String param1, String param2) {
        EditScreenFragment fragment = new EditScreenFragment();
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
            Bundle bundle = this.getArguments();
            this.year = bundle.getInt("year");
            this.month = bundle.getInt("month");
            this.day = bundle.getInt("day");
            this.hour = bundle.getInt("hour");
            this.minute = bundle.getInt("minute");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        String date = Integer.toString(this.month) + "/" + Integer.toString(this.day) + "/" +
                Integer.toString(this.year);
        String time = Integer.toString(this.hour) + ":" + Integer.toString(this.minute);

        View v = inflater.inflate(R.layout.fragment_edit_screen, container, false);
        TextView dateText = (TextView) v.findViewById(R.id.date);
        TextView timeText = (TextView) v.findViewById(R.id.time);
        dateText.setText(date);
        timeText.setText(time);

        Button saveButton = (Button) v.findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText eventNameText = getView().findViewById(R.id.event_name);
                String eventName = eventNameText.getText().toString();
                if(HomeScreen.hasName(eventName)){
                    Toast.makeText(getActivity(), "Name already taken. Please choose a different name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Event e = new Event(eventName, getView().findViewById(R.id.date).toString(), getView().findViewById(R.id.time).toString());
                TextView myDate = getView().findViewById(R.id.date);
                TextView myTime = getView().findViewById(R.id.time);

                EventDbHelper eventDbHelper = new EventDbHelper(getActivity());
                SQLiteDatabase sqLiteDatabase = eventDbHelper.getWritableDatabase();
                eventDbHelper.addEvent(eventName, myDate.getText().toString(), myTime.getText().toString(), sqLiteDatabase);
                ArrayList<String> toPut = new ArrayList<>();
                toPut.add(myDate.getText().toString());
                toPut.add(myTime.getText().toString());

                HomeScreen.putInfo(eventName, toPut);

                Toast.makeText(getActivity(), "Event:" + " " + eventName + " " + "Saved Successfully!", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack();
            }

        });
        return v;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

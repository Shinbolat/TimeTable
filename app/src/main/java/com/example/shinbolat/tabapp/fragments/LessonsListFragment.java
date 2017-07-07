package com.example.shinbolat.tabapp.fragments;

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.shinbolat.tabapp.OperationsActivity;
import com.example.shinbolat.tabapp.R;
import com.example.shinbolat.tabapp.editor.ReadTable;
import com.example.shinbolat.tabapp.findfile.TimeTable;
import com.example.shinbolat.tabapp.utils.Prefs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shinbolat on 2/17/16.
 */

public class LessonsListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    SimpleAdapter simpleAdapter;

    final String NUM="lessons_number";
    final String LESSON="lesson";
    final String WEEKDAY="weekday";
    final String ROOM="room";

    Boolean mDualPane;
    int CurCheckPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i("FRAGMENT", "ON CREATE VIEW");
        return inflater.inflate(R.layout.layout_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i("FRAGMENT", "ON ACTIVITY CREATED");

        View view = getActivity().findViewById(R.id.operations);
        mDualPane = view != null && view.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            CurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if (mDualPane) {
            // In dual-pane mode, the list view highlights the selected item.
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // Make sure our UI is in the correct state.
            showOperationFragment(CurCheckPosition);
        }



    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CurCheckPosition);
    }


    private void showOperationFragment(int curCheckPosition) {

        CurCheckPosition = curCheckPosition;

        if (mDualPane) {
            // We can display everything in-place with fragments, so update
            // the list to highlight the selected item and show the data.
            getListView().setItemChecked(curCheckPosition, true);

            // Check what fragment is currently shown, replace if needed.
            OperationsFragment OperationsAdding = (OperationsFragment)
                    getFragmentManager().findFragmentById(R.id.operations);
            if (OperationsAdding == null ||OperationsAdding.getShownIndex() != curCheckPosition) {
                // Make new fragment to show this selection.
                OperationsAdding = OperationsFragment.newInstance(curCheckPosition);

                // Execute a transaction, replacing any existing fragment
                // with this one inside the frame.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (curCheckPosition == 0) {
                    ft.replace(R.id.operations, OperationsAdding);
                } else {
                    ft.replace(R.id.text1, OperationsAdding);
                }
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else {
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            Intent intent = new Intent();
            intent.setClass(getActivity(), OperationsActivity.class);
            intent.putExtra("index", curCheckPosition);
            startActivity(intent);
        }
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();

        showOperationFragment(position);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("FRAGMENT", "ON RESUME");

        if(!Prefs.getPath().isEmpty()) {
            List<TimeTable> timeTablesList = new ReadTable(getActivity()).getDataFromTable();

            // Set List View
            ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(6);
            Map<String, Object> map;

            for (int i = 0; i < timeTablesList.size(); i++) {
                map = new HashMap<String, Object>();
                map.put(NUM, Integer.valueOf(timeTablesList.get(i).getNumOfSubject()));
                map.put(LESSON, timeTablesList.get(i).getNameOfSubject());
                map.put(WEEKDAY, getWeekday(timeTablesList.get(i).getNameOfWeek()));
                map.put(ROOM, timeTablesList.get(i).getNumOfRoom());
                data.add(map);
            }

            String[] from = {NUM, LESSON, WEEKDAY, ROOM};
            int[] to = {R.id.lesson_num, R.id.lesson, R.id.weekday, R.id.room_num};

            simpleAdapter = new SimpleAdapter(getActivity(), data, R.layout.lessons_list_item, from, to);

            setListAdapter(simpleAdapter);
            getListView().setOnItemClickListener(this);
        }
    }

    private String getWeekday(String temp){

        String [] week = getActivity().getResources().getStringArray(R.array.week);
        String []WeekDay=new String[]{"MON","TUE","WED","THU","FRI","SAT"};

        for(int i = 0; i < week.length; i ++){

            if(WeekDay[i].equals(temp.trim())){
                return week[i];
            }

        }
        return null;
    }
}
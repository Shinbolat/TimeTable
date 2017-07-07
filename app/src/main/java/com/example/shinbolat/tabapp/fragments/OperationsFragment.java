package com.example.shinbolat.tabapp.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shinbolat.tabapp.R;

/**
 * Created by shinbolat on 2/17/16.
 */
public class OperationsFragment extends Fragment {

    public static OperationsFragment newInstance(int index) {
        OperationsFragment f = new OperationsFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(container == null) return  null;

        View v =inflater.inflate(R.layout.operation_dialog, container, false) ;

        return v;
    }
}
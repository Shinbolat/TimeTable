package com.example.shinbolat.tabapp.message;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shinbolat.tabapp.R;

/**
 * Created by shinbolat on 2/14/16.
 */
public class FireMissilesDialogFragment extends DialogFragment {

    String ErrorMessage;
    Context context;

    public FireMissilesDialogFragment(Context context, String ErrorMessage){

        this.ErrorMessage = ErrorMessage;
        this.context = context;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Error")
                .setMessage(ErrorMessage)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        getActivity().finish();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}

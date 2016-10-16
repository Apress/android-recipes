package com.examples.popup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyActivity extends Activity {

    AlertDialog actions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Activity");
        Button button = new Button(this);
        button.setText("Click for Options");
        button.setOnClickListener(buttonListener);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an Option");
        String[] options = {"Delete Item","Copy Item","Edit Item"};
        builder.setItems(options, actionListener);
        builder.setNegativeButton("Cancel", null);
        actions = builder.create();

        setContentView(button);
    }

    //List selection action handled here
    DialogInterface.OnClickListener actionListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch(which) {
            case 0: //Delete
                break;
            case 1: //Copy
                break;
            case 2: //Edit
                break;
            default:
                break;
            }
        }
    };

    //Button action handled here (pop up the dialog)
    View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            actions.show();
        }
    };
}

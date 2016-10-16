package com.examples.popup;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MyActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Register a button for context events
        Button button = new Button(this);
        button.setText("Click for Options");
        button.setOnClickListener(listener);
        registerForContextMenu(button);

        setContentView(button);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        public void onClick(View v) {
            openContextMenu(v);
        }
    };

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu, menu);
        menu.setHeaderTitle("Choose an Option");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //Switch on the item’s ID to find the action the user selected
        switch(item.getItemId()) {
        case R.id.menu_delete:
            //Perform delete actions
            return true;
        case R.id.menu_copy:
            //Perform copy actions
            return true;
        case R.id.menu_edit:
            //Perform edit actions
            return true;
        }
        return super.onContextItemSelected(item);
    }

}

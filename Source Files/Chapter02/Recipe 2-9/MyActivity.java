package com.examples.keys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;

public class MyActivity extends Activity {

    MenuDialog menuDialog;
    private class MenuDialog extends AlertDialog {

        public MenuDialog(Context context) {
            super(context);
            setTitle("Menu");
            View menu = getLayoutInflater().inflate(R.layout.custommenu, null);
            setView(menu);
        }

        @Override
        public boolean onKeyUp(int keyCode, KeyEvent event) {
            if(keyCode == KeyEvent.KEYCODE_MENU) {
                dismiss();
                return true;
            }
            return super.onKeyUp(keyCode, event);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_MENU) {
            if(menuDialog == null) {
                menuDialog = new MenuDialog(this);
            }
            menuDialog.show();
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }

}

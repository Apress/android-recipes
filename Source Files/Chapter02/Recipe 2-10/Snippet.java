@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
    if(keyCode == KeyEvent.KEYCODE_BACK) {
        //Implement a custom back function
        Toast.makeText(this, "You Didn't Say Please!", Toast.LENGTH_SHORT).show();
        //Return true to consume the event
        return true;
    }
    return super.onKeyDown(keyCode, event);
}
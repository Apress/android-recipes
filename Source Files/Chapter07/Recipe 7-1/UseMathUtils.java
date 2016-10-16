// UseMathUtils.java

package com.apress.usemathutils;

import android.app.Activity;

import android.os.Bundle;

import android.widget.TextView;

import com.apress.mathutils.MathUtils;

public class UseMathUtils extends Activity
{
   @Override
   public void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      TextView tv = new TextView(this);
      tv.setText("5! = "+MathUtils.factorial(5));
      setContentView(tv);
   }
}

// UC2.java

package com.apress.uc2;

import android.app.Activity;

import android.content.Context;

import android.database.Cursor;
import android.database.SQLException;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UC2 extends Activity
{
   private int position = 0;
   private String[] conversions;
   private double[] multipliers;
   
   private class DBHelper extends SQLiteOpenHelper
   {
      private final static String DB_PATH = "data/data/com.apress.uc2/databases/";
      private final static String DB_NAME = "conversions.db";
      private final static int CONVERSIONS_COLUMN_ID = 0;
      private final static int MULTIPLIERS_COLUMN_ID = 1;
      
      private SQLiteDatabase db;
      
      public DBHelper(Context context)
      {
         super(context, DB_NAME, null, 1); 
      }

      @Override	  
      public void onCreate(SQLiteDatabase db)
      {
         // Do nothing ... we don't create a new database.
      }

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldver, int newver)
      {
         // Do nothing ... we don't upgrade a database.
      }
      
      public boolean populateArrays()
      {
         try
    	 {
            String path = DB_PATH+DB_NAME;
      	    db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY|
                                             SQLiteDatabase.NO_LOCALIZED_COLLATORS);
      	    Cursor cur = db.query("conversions", null, null, null, null, null, null);
      	    if (cur.getCount() == 0)
      	    {
      	       Toast.makeText(UC2.this, "conversions table is empty", 
                              Toast.LENGTH_LONG).show();
      	       return false;
      	    }
      	    conversions = new String[cur.getCount()];
      	    multipliers = new double[cur.getCount()];
      	    int i = 0;
      	    while (cur.moveToNext())
      	    {	
       	       conversions[i] = cur.getString(CONVERSIONS_COLUMN_ID);
      	       multipliers[i++] = cur.getFloat(MULTIPLIERS_COLUMN_ID);
      	    }
      	    return true;
         }
         catch (SQLException sqle)
         {
            Toast.makeText(UC2.this, sqle.getMessage(), Toast.LENGTH_LONG).show();
         }
         finally
         {
            if (db != null)
               db.close();
         }
         return false;
      }
   }

   @Override
   public void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);

      DBHelper dbh = new DBHelper(this);
      if (!dbh.populateArrays())
          finish();

      final EditText etUnits = (EditText) findViewById(R.id.units);

      final Spinner spnConversions = (Spinner) findViewById(R.id.conversions);
      ArrayAdapter<CharSequence> aa;
      aa = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, 
                                          conversions);
      aa.setDropDownViewResource(android.R.layout.simple_spinner_item);
      spnConversions.setAdapter(aa);
      
      AdapterView.OnItemSelectedListener oisl;
      oisl = new AdapterView.OnItemSelectedListener()
      {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view,
                                    int position, long id)
         {
            UC2.this.position = position;
         }

         @Override
         public void onNothingSelected(AdapterView<?> parent)
         {
            System.out.println("nothing");
         }
      };
      spnConversions.setOnItemSelectedListener(oisl);

      final Button btnClear = (Button) findViewById(R.id.clear);
      AdapterView.OnClickListener ocl;
      ocl = new AdapterView.OnClickListener()
      {
         @Override
         public void onClick(View v)
         {
            etUnits.setText("");
         }
      };
      btnClear.setOnClickListener(ocl);
      btnClear.setEnabled(false);

      final Button btnConvert = (Button) findViewById(R.id.convert);
      ocl = new AdapterView.OnClickListener()
      {
         @Override
         public void onClick(View v)
         {
            String text = etUnits.getText().toString();
            double input = Double.parseDouble(text);
            double result = 0;
            if (position == 3)
               result = input*9.0/5.0+32; // Celsius to Fahrenheit
            else
            if (position == 4)
               result = (input-32)*5.0/9.0; // Fahrenheit to Celsius
            else
               result = input*multipliers[position];
            etUnits.setText(""+result);
         }
      };
      btnConvert.setOnClickListener(ocl);
      btnConvert.setEnabled(false);

      Button btnClose = (Button) findViewById(R.id.close);
      ocl = new AdapterView.OnClickListener()
      {
         @Override
         public void onClick(View v)
         {
            finish();
         }
      };
      btnClose.setOnClickListener(ocl);

      TextWatcher tw;
      tw = new TextWatcher()
      {
         public void afterTextChanged(Editable s)
         {
         }
         public void beforeTextChanged(CharSequence s, int start, int count,
                                       int after)
         {
         }
         public void onTextChanged(CharSequence s, int start, int before,
                                   int count)
         {
            if (etUnits.getText().length() == 0)
            {
               btnClear.setEnabled(false);
               btnConvert.setEnabled(false);
            }
            else
            {
               btnClear.setEnabled(true);
               btnConvert.setEnabled(true);
            }
         }
      };
      etUnits.addTextChangedListener(tw);
   }
}

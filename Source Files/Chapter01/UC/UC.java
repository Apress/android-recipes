// UC.java

package com.apress.uc;

import android.app.Activity;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class UC extends Activity
{
   private int position = 0;

   private double[] multipliers =
   {
      0.0015625,         // Acres to square miles
      101325.0,          // Atmospheres to Pascals
      100000.0,          // Bars to Pascals
      0,                 // Degrees Celsius to Degrees Fahrenheit (placeholder)
      0,                 // Degrees Fahrenheit to Degrees Celsius (placeholder)
      0.00001,           // Dynes to Newtons
      0.3048,            // Feet/Second to Metres/Second
      0.0284130625,      // Fluid Ounces (UK) to Litres
      0.0295735295625,   // Fluid Ounces (US) to Litres
      746.0,             // Horsepower (electric) to Watts
      735.499,           // Horsepower (metric) to Watts
      1/1016.0469088,    // Kilograms to Tons (UK or long)
      1/907.18474,       // Kilograms to Tons (US or short)
      1/0.0284130625,    // Litres to Fluid Ounces (UK)
      1/0.0295735295625, // Litres to Fluid Ounces (US)
      331.5,             // Mach Number to Metres/Second
      1/0.3048,          // Metres/Second to Feet/Second
      1/331.5,           // Metres/Second to Mach Number
      0.833,             // Miles/Gallon (UK) to Miles/Gallon (US)
      1/0.833,           // Miles/Gallon (US) to Miles/Gallon (UK)
      100000.0,          // Newtons to Dynes
      1/101325.0,        // Pascals to Atmospheres
      0.00001,           // Pascals to Bars
      640.0,             // Square Miles to Acres
      1016.0469088,      // Tons (UK or long) to Kilograms
      907.18474,         // Tons (US or short) to Kilograms
      1/746.0,           // Watts to Horsepower (electic)
      1/735.499          // Watts to Horsepower (metric)
   };

   @Override
   public void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);

      final EditText etUnits = (EditText) findViewById(R.id.units);

      final Spinner spnConversions = (Spinner) findViewById(R.id.conversions);
      ArrayAdapter<CharSequence> aa;
      aa = ArrayAdapter.
             createFromResource(this, R.array.conversions,
                                android.R.layout.simple_spinner_item);
      aa.setDropDownViewResource(android.R.layout.simple_spinner_item);
      spnConversions.setAdapter(aa);

      AdapterView.OnItemSelectedListener oisl;
      oisl = new AdapterView.OnItemSelectedListener()
      {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view,
                                    int position, long id)
         {
            UC.this.position = position;
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
         @Override
         public void afterTextChanged(Editable s)
         {
         }

         @Override
         public void beforeTextChanged(CharSequence s, int start, int count,
                                       int after)
         {
         }

         @Override
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

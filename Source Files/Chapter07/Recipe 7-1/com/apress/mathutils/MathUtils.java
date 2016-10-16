// MathUtils.java

package com.apress.mathutils;

public class MathUtils
{
   public static long factorial(long n)
   {
      if (n <= 0)
         return 1;
      else
         return n*factorial(n-1);
   }
}

package com.ssolution.admin.system.util;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberUtil
{
  private static Logger logger = LoggerFactory.getLogger(NumberUtil.class);
  
  public static boolean isNumeric(String text)
  {
    if (text.length() == 0) {
      return false;
    }
    for (int i = 0; i < text.length(); i++) {
      if ((i != 0) || 
        (!text.substring(i, i + 1).equals("-"))) {
        if (((text.substring(i, i + 1).compareTo("0") < 0) || (text.substring(i, i + 1).compareTo("9") > 0)) && 
          (!text.substring(i, i + 1).equals("."))) {
          return false;
        }
      }
    }
    return true;
  }
  
  public static String getDecimal(String strSrc, int len)
  {
    int length = strSrc.length();
    int iDec = strSrc.indexOf('.');
    StringBuffer convert = new StringBuffer();
    convert.append(iDec < 0 ? strSrc : strSrc.substring(0, iDec));
    if (iDec < 0) {
      iDec = length;
    }
    for (int i = 1; i <= len; i++)
    {
      if (i == 1) {
        convert.append('.');
      }
      convert.append(i + iDec < length ? strSrc.charAt(i + iDec) : '0');
    }
    return convert.toString();
  }
  
  public static String getDecimal(double dNumber, int len)
  {
    return getDecimal(Double.toString(dNumber), len);
  }
  
  public static double getRandom(int minValue, int maxValue)
  {
    double value = Math.random() * maxValue + minValue;
    
    return value;
  }
  
  public static int getRandomInt()
  {
    return getRandomInt(0, 100);
  }
  
  public static int getRandomInt(int minValue, int maxValue)
  {
    double value = getRandom(minValue, maxValue);
    String str = Double.toString(value);
    
    int index = str.indexOf(".");
    str = str.substring(0, index);
    
    return Integer.parseInt(str);
  }
  
  public static String maskComma(String src, int pos)
  {
    if ((src == null) || (src.length() == 0)) {
      return "";
    }
    DecimalFormat df = new DecimalFormat();
    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
    dfs.setGroupingSeparator(',');
    df.setGroupingSize(pos);
    


    StringBuffer sb = new StringBuffer();
    for (int i = 1; i <= pos * 7; i++) {
      if ((i % pos == 0) && (i != pos * 7)) {
        sb.append('#').append(',');
      } else {
        sb.append('#');
      }
    }
    sb.append(".######");
    
    df.applyPattern(sb.toString());
    df.setDecimalFormatSymbols(dfs);
    String resultValue = df.format(Double.parseDouble(src)).toString();
    return resultValue;
  }
  
  public static String maskComma(long amt, int pos)
  {
    return maskComma(String.valueOf(amt), pos);
  }
  
  public static String maskComma(double amt, int pos)
  {
    return maskComma(String.valueOf(amt), pos);
  }
  
  public static String maskComma(String src)
  {
    return maskComma(src, 3);
  }
  
  public static String maskComma(long amt)
  {
    return maskComma(String.valueOf(amt), 3);
  }
  
  public static String maskComma(double amt)
  {
    return maskComma(String.valueOf(amt), 3);
  }
  
  public static String removeComma(String src)
  {
    String resultValue = "";
    for (int i = 0; i < src.length(); i++)
    {
      char temp = src.charAt(i);
      if (temp != ',') {
        resultValue = resultValue + temp;
      }
    }
    return resultValue;
  }
  
  public static float round(float f, int len, int round_type)
  {
    float retval = 0.0F;
    try
    {
      retval = new BigDecimal(f).setScale(len, round_type).floatValue();
    }
    catch (NumberFormatException nfe)
    {
      logger.error("{}", nfe);
    }
    return retval;
  }
  
  public static float division(float son, float mother)
  {
    float retval = 0.0F;
    if (mother == 0.0F)
    {
      retval = 0.0F;
    }
    else
    {
      retval = son / mother;
      retval = round(retval, 2, 4);
    }
    return retval;
  }
  
  public static String getDefaultZero(Integer v)
  {
    if (v == null) {
      return "0";
    }
    return String.valueOf(v);
  }
  
  public static void main(String[] args)
  {
    String sSrc = "123456789012.1234567890123";
    long dTest = 123456L;
    double f = 10.12345D;
    

    System.out.println("22>>>" + maskComma(sSrc, 3));
    System.out.println("22>>>" + maskComma(12345.99D, 3));
    System.out.println("22>>>" + round(new Double(10.12345D).floatValue(), 3, 4));
    System.out.println("22>>>" + division(2.0F, 0.0F));
  }
}

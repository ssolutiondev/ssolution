package com.ssolution.admin.system.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256Util
{
  public static String getEncrypt(String encrypt)
  {
    String result = "";
    try
    {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(encrypt.getBytes());
      
      byte[] byteData = md.digest();
      
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < byteData.length; i++) {
        sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16)
          .substring(1));
      }
      result = sb.toString();
    }
    catch (NoSuchAlgorithmException e)
    {
      e.printStackTrace();
    }
    return result;
  }
  
  public static String getEncrypt(String encrypt, String algorithm)
  {
    String result = "";
    try
    {
      MessageDigest md = MessageDigest.getInstance(algorithm);
      md.update(encrypt.getBytes());
      
      byte[] byteData = md.digest();
      
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < byteData.length; i++) {
        sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16)
          .substring(1));
      }
      result = sb.toString();
    }
    catch (NoSuchAlgorithmException e)
    {
      e.printStackTrace();
    }
    return result;
  }
}

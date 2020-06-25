package com.ssolution.admin.system.util;

import java.util.Arrays;
import java.util.StringTokenizer;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil
  extends StringUtils
{
  private static Logger logger = LoggerFactory.getLogger(StringUtil.class);
  
  public static String nvl(String src)
  {
    return nvl(src, "");
  }
  
  public static String nvl(String src, String tgt)
  {
    String res = tgt;
    if (tgt == null) {
      res = "";
    }
    if (src == null) {
      return res;
    }
    if (src.equals("")) {
      return res;
    }
    return src;
  }
  
  public static String nvl(Object obj)
  {
    return nvl(obj, "");
  }
  
  public static String nvl(Object obj, String def)
  {
    if (obj == null) {
      return def;
    }
    return obj.toString();
  }
  
  public static String rpad(String str, int len, char pad)
  {
    String result = str;
    int templen = len - result.getBytes().length;
    for (int i = 0; i < templen; i++) {
      result = result + pad;
    }
    return result;
  }
  
  public static String lpad(String str, int len, char pad)
  {
    String result = str;
    int templen = len - result.getBytes().length;
    for (int i = 0; i < templen; i++) {
      result = pad + result;
    }
    return result;
  }
  
  public static String makePostType(String postno)
  {
    if ((postno == null) || (postno.length() == 0)) {
      return "";
    }
    if (postno.length() != 6) {
      return postno;
    }
    String postno1 = postno.substring(0, 3);
    String postno2 = postno.substring(3, 6);
    return postno1 + "-" + postno2;
  }
  
  public static String makeTelType(String tel)
  {
    if ((tel == null) || (tel.length() == 0)) {
      return "";
    }
    String DELEMETER = "-";
    StringBuffer sb = new StringBuffer();
    String tnum = "";
    if (tel.startsWith("02"))
    {
      sb.append("02").append(DELEMETER);
      tnum = tel.substring(2);
    }
    else
    {
      sb.append(tel.substring(0, 3)).append(DELEMETER);
      tnum = tel.substring(3);
    }
    if (tnum.length() == 7) {
      sb.append(tnum.substring(0, 3)).append(DELEMETER).append(tnum
        .substring(3));
    } else if (tnum.length() == 8) {
      sb.append(tnum.substring(0, 4)).append(DELEMETER).append(tnum
        .substring(4));
    } else {
      return tel;
    }
    return sb.toString();
  }
  
  public static String makeJuminType(String jumin)
  {
    if ((jumin == null) || (jumin.length() == 0)) {
      return "";
    }
    if (jumin.length() != 13) {
      return jumin;
    }
    String postno1 = jumin.substring(0, 6);
    String postno2 = jumin.substring(6);
    return postno1 + "-" + postno2;
  }
  
  public static String getPostCode1(String postcode)
  {
    if ((postcode == null) || (postcode.length() == 0)) {
      return "";
    }
    if (postcode.length() == 6) {
      return postcode.substring(0, 3);
    }
    return postcode;
  }
  
  public static String getPostCode2(String postcode)
  {
    if ((postcode == null) || (postcode.length() == 0)) {
      return "";
    }
    if (postcode.length() == 6) {
      return postcode.substring(3);
    }
    return postcode;
  }
  
  public static String getTelNum1(String tel)
  {
    if ((tel == null) || (tel.length() == 0)) {
      return "";
    }
    if (tel.startsWith("02")) {
      return "02";
    }
    return tel.substring(0, 3);
  }
  
  public static String getTelNum2(String tel)
  {
    if ((tel == null) || (tel.length() == 0)) {
      return "";
    }
    if (tel.startsWith("02"))
    {
      if (tel.length() - 2 == 7) {
        return tel.substring(2, 5);
      }
      if (tel.length() - 2 == 8) {
        return tel.substring(2, 6);
      }
      return tel;
    }
    if (tel.length() - 3 == 7) {
      return tel.substring(3, 6);
    }
    if (tel.length() - 3 == 8) {
      return tel.substring(3, 7);
    }
    return tel;
  }
  
  public static String getTelNum3(String tel)
  {
    if ((tel == null) || (tel.length() == 0)) {
      return "";
    }
    if (tel.startsWith("02"))
    {
      if (tel.length() - 2 == 7) {
        return tel.substring(5);
      }
      if (tel.length() - 2 == 8) {
        return tel.substring(6);
      }
      return tel;
    }
    if (tel.length() - 3 == 7) {
      return tel.substring(6);
    }
    if (tel.length() - 3 == 8) {
      return tel.substring(7);
    }
    return tel;
  }
  
  public static String getGender(String gender)
  {
    if ((gender == null) || (gender.length() == 0)) {
      return "";
    }
    String gen = gender.toUpperCase();
    if ((gen.equals("F")) || (gen.equals("FEMAIL")) || (gen.equals("W")) || 
      (gender.equals("WOMAN"))) {
      return "여자";
    }
    if ((gen.equals("M")) || (gen.equals("MAIL")) || 
      (gender.equals("MAN"))) {
      return "남자";
    }
    return gender;
  }
  
  public String encodeSpace(String str)
  {
    StringTokenizer st = new StringTokenizer(str);
    String spString = "";
    while (st.hasMoreTokens()) {
      spString = spString + st.nextToken() + "_";
    }
    return spString;
  }
  
  public String decodeSpace(String str)
  {
    StringTokenizer st = new StringTokenizer(str, "_");
    String returnStr = "";
    while (st.hasMoreTokens()) {
      returnStr = returnStr + st.nextToken() + " ";
    }
    return returnStr;
  }
  
  public static String remove(String str, String tok)
  {
    return replaceAll(str, tok, "");
  }
  
  public static String replace(String str1, String str2, String replace)
  {
    return replace(str1, 0, str2, replace);
  }
  
  public static String replace(String str, int off, int len, String replace)
  {
    StringBuffer buff = new StringBuffer(str);
    buff.replace(off, off + len, replace);
    return buff.toString();
  }
  
  public static String replace(String str1, int off, String str2, String replace)
  {
    off = str1.indexOf(str2, off);
    if (off == -1) {
      return str1;
    }
    StringBuffer buff = new StringBuffer(str1);
    buff.replace(off, off + str2.length(), replace);
    return buff.toString();
  }
  
  public static String replaceAll(String str1, int off, String str2, String replace)
  {
    if ((str1 == null) || (str2 == null) || (replace == null)) {
      return str1;
    }
    off = str1.indexOf(str2, off);
    StringBuffer buff = new StringBuffer(str1);
    while (off != -1)
    {
      buff.replace(off, off + str2.length(), replace);
      str1 = buff.toString();
      if (off + str2.length() < str1.length()) {
        off = str1.indexOf(str2, off + replace.length());
      } else {
        off = -1;
      }
    }
    return str1;
  }
  
  public static String replaceAll(String str1, String str2, String replace)
  {
    return replaceAll(str1, 0, str2, replace);
  }
  
  public static String getShortString(String p_str, int p_len)
  {
    boolean chkFlag = false;
    String strName = p_str.trim();
    byte[] arName = strName.getBytes();
    if (arName.length > p_len)
    {
      for (int idx = 0; idx < p_len; idx++) {
        if (arName[idx] < 0) {
          chkFlag = !chkFlag;
        } else {
          chkFlag = false;
        }
      }
      if (chkFlag) {
        strName = new String(arName, 0, p_len + 1);
      } else {
        strName = new String(arName, 0, p_len);
      }
    }
    else
    {
      strName = new String(arName, 0, arName.length);
    }
    return strName;
  }
  
  public static String getShortStatement(String str, int maxNum)
  {
    return str.length() > maxNum ? getShortString(str, maxNum) + "..." : str;
  }
  
  public static String getRawDigit(String str)
  {
    char[] c = str.toCharArray();
    StringBuffer buff = new StringBuffer();
    try
    {
      for (int i = 0; i < c.length; i++) {
        if (Character.isDigit(c[i])) {
          buff.append(c[i]);
        }
      }
    }
    catch (Exception ex)
    {
      logger.error("{}", ex);
    }
    return buff.toString();
  }
  
  public static String changeEmptyStringToNULL(String inStr)
  {
    if (inStr == null) {
      return null;
    }
    if (inStr.equals("")) {
      return null;
    }
    return inStr;
  }
  
  public static String changeEntityReference(String text)
  {
    String result = text;
    
    result = replaceAll(result, "&", "&amp;");
    result = replaceAll(result, "'", "&apos;");
    result = replaceAll(result, "\"", "&quot;");
    result = replaceAll(result, "<", "&lt;");
    result = replaceAll(result, ">", "&gt;");
    
    return result;
  }
  
  public static String changeNormalText(String text)
  {
    String result = text;
    
    result = replaceAll(result, "&gt;", ">");
    result = replaceAll(result, "&lt;", "<");
    result = replaceAll(result, "&quot;", "\"");
    result = replaceAll(result, "&apos;", "'");
    result = replaceAll(result, "&amp;", "&");
    
    return result;
  }
  
  public static String getRandomString(int minValue, int maxValue, int fixLength)
  {
    double value = NumberUtil.getRandom(minValue, maxValue);
    String str = Double.toString(value);
    
    int index = str.indexOf(".");
    str = str.substring(0, index);
    if (fixLength == -1) {
      return str;
    }
    index = str.length();
    while (index < fixLength)
    {
      str = "0" + str;
      
      index++;
    }
    return str;
  }
  
  public static void main(String[] args)
  {
    String sText1 = "123_456_789_10";
    String sText2 = "_";
    String sText3 = "=";
    
    logger.debug("--" + rpad("123", 5, '0'));
    
    logger.debug("1>>" + remove(sText1, sText2));
    logger.debug("1>>" + replace(sText1, sText2, sText3));
    logger.debug("2>>" + replace("123&&&456", 3, 3, ""));
    logger.debug("3>>" + replace(sText1, 4, sText2, sText3));
    logger.debug("4>>" + replaceAll(sText1, sText2, sText3));
    logger.debug("5>>" + replaceAll(sText1, 4, sText2, sText3));
    
    logger.debug("6>>" + getRawDigit("ab3c4"));
    logger.debug("8>>" + getShortString("abcde한fg", 6));
    
    logger.debug("9>>" + getTelNum2("0101230123"));
    logger.debug("9>>" + getTelNum2("01012340123"));
    
    logger.debug("10>>" + Arrays.asList(split("010-111-2222", "-")));
  }
  
  public static boolean checkEnumString(String[] list, String val)
  {
    if (val == null) {
      return false;
    }
    for (String s : list) {
      if (s != null) {
        if (s.equals(val)) {
          return true;
        }
      }
    }
    return false;
  }
  
  public static String encode(String src)
  {
    if (nvl(src).equals("")) {
      return "";
    }
    return new String(Base64.encodeBase64(Base64.encodeBase64(Base64.encodeBase64(src.getBytes()))));
  }
  
  public static String decode(String src)
  {
    if (nvl(src).equals("")) {
      return "";
    }
    return new String(Base64.decodeBase64(Base64.decodeBase64(Base64.decodeBase64(src))));
  }
}

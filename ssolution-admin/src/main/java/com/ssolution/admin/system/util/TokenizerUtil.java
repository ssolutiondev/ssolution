package com.ssolution.admin.system.util;

public class TokenizerUtil
{
  private String m_str;
  private String m_delimiter;
  
  public TokenizerUtil(String str, String delimiter)
  {
    if ((str != null) && (str.length() == 0)) {
      this.m_str = null;
    } else {
      this.m_str = str;
    }
    this.m_delimiter = delimiter;
  }
  
  public boolean hasMoreTokens()
  {
    return this.m_str != null;
  }
  
  public String nextToken()
  {
    String ret = null;
    if (this.m_str == null) {
      return ret;
    }
    int index = this.m_str.indexOf(this.m_delimiter);
    if (index == -1)
    {
      ret = this.m_str;
      this.m_str = null;
    }
    else
    {
      ret = this.m_str.substring(0, index);
      if (this.m_str.length() > this.m_delimiter.length()) {
        this.m_str = this.m_str.substring(index + this.m_delimiter.length());
      } else {
        this.m_str = null;
      }
    }
    return ret;
  }
  
  public int countTokens()
  {
    int count = 1;
    for (int i = 0; i < this.m_str.length(); i++) {
      if (this.m_str.startsWith(this.m_delimiter, i)) {
        count++;
      }
    }
    return count;
  }
}

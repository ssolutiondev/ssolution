package com.ssolution.admin.system.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CurrencyConverter
  implements Converter
{
  private final Log log = LogFactory.getLog(CurrencyConverter.class);
  private DecimalFormat formatter = new DecimalFormat("###,###.00");
  
  public void setDecimalFormatter(DecimalFormat df)
  {
    this.formatter = df;
  }
  
  public final Object convert(Class type, Object value)
  {
    if (value == null) {
      return null;
    }
    if ((value instanceof String))
    {
      if (this.log.isDebugEnabled()) {
        this.log.debug("value (" + value + ") instance of String");
      }
      try
      {
        if (StringUtils.isBlank(String.valueOf(value))) {
          return null;
        }
        if (this.log.isDebugEnabled()) {
          this.log.debug("converting '" + value + "' to a decimal");
        }
        Number num = this.formatter.parse(String.valueOf(value));
        
        return Double.valueOf(num.doubleValue());
      }
      catch (ParseException pe)
      {
        pe.printStackTrace();
      }
    }
    else if ((value instanceof Double))
    {
      if (this.log.isDebugEnabled())
      {
        this.log.debug("value (" + value + ") instance of Double");
        this.log.debug("returning double: " + this.formatter.format(value));
      }
      return this.formatter.format(value);
    }
    throw new ConversionException("Could not convert " + value + " to " + type.getName() + "!");
  }
}

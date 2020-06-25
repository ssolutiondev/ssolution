package com.ssolution.admin.system.util;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeansUtil
{
  private static final Logger logger = LoggerFactory.getLogger(BeansUtil.class);
  
  public static final void copyProperties(Object dest, Object orig)
    throws Exception
  {
    PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
    propertyUtilsBean.copyProperties(dest, orig);
  }
  
  public static final String toStringBean(Object bean)
  {
    if (bean == null) {
      return "";
    }
    return ReflectionToStringBuilder.toString(bean);
  }
}

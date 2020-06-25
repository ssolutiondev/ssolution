package com.ssolution.admin.system.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationUtil
{
  static Logger log = LoggerFactory.getLogger(AnnotationUtil.class);
  
  public static String getClassAnnotationValue(Class classType, Class annotationType, String attributeName)
  {
    String value = "";
    
    Annotation annotation = classType.getAnnotation(annotationType);
    if (annotation != null) {
      try
      {
        value = (String)annotation.annotationType().getMethod(attributeName, new Class[0]).invoke(annotation, new Object[0]);
        if (value == null) {
          value = "";
        }
      }
      catch (Exception ex)
      {
        log.error("{}", ex);
      }
    }
    return value;
  }
  
  public static boolean getClassAnnotation(Class classType, Class annotationType)
  {
    Annotation annotation = classType.getAnnotation(annotationType);
    if (annotation != null) {
      return true;
    }
    return false;
  }
}

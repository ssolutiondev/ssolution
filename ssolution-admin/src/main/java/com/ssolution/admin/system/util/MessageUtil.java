package com.ssolution.admin.system.util;

import com.ssolution.admin.system.util.StringUtil;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class MessageUtil
{
  private static MessageSourceAccessor msAcc = null;
  
  public void setMessageSourceAccessor(MessageSourceAccessor pMsAcc)
  {
    if (msAcc == null) {
      msAcc = pMsAcc;
    }
  }
  
  public static String getMessage(String key)
  {
    HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    
    String sessionCountry = (String)request.getSession().getAttribute("sessionCountry");
    String sessionLanguage = (String)request.getSession().getAttribute("sessionLanguage");
    Locale sessionLocale;
    
    if ((!StringUtil.isEmpty(sessionCountry)) && (!StringUtil.isEmpty(sessionLanguage))) {
      sessionLocale = new Locale(sessionLanguage, sessionCountry);
    } else {
      sessionLocale = new Locale(request.getLocale().getLanguage(), request.getLocale().getCountry());
    }
    return msAcc.getMessage(key, sessionLocale);
  }
  
  public static String getMessage(String key, Locale locale)
  {
    return msAcc.getMessage(key, locale);
  }
  
  public static String getMessage(String key, Object[] objs)
  {
    HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    
    String sessionCountry = (String)request.getSession().getAttribute("sessionCountry");
    String sessionLanguage = (String)request.getSession().getAttribute("sessionLanguage");
    Locale sessionLocale;
    if ((!StringUtil.isEmpty(sessionCountry)) && (!StringUtil.isEmpty(sessionLanguage))) {
      sessionLocale = new Locale(sessionLanguage, sessionCountry);
    } else {
      sessionLocale = new Locale(request.getLocale().getLanguage(), request.getLocale().getCountry());
    }
    return msAcc.getMessage(key, objs, sessionLocale);
  }
  
  public static String getMessage(String key, Object[] objs, Locale locale)
  {
    return msAcc.getMessage(key, objs, locale);
  }
}

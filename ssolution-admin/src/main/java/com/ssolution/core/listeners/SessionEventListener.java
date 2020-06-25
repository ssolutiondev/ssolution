package com.ssolution.core.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.context.support.SecurityWebApplicationContextUtils;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionEventListener implements HttpSessionListener {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    ApplicationContext getContext(ServletContext servletContext) {
        return SecurityWebApplicationContextUtils.findRequiredWebApplicationContext(servletContext);
    }

    public void sessionCreated(HttpSessionEvent event) {
        int sesstionTimeOut = 86400;
        event.getSession().setMaxInactiveInterval(sesstionTimeOut);
        HttpSessionCreatedEvent e = new HttpSessionCreatedEvent(event.getSession());
        if (logger.isDebugEnabled()) {
            logger.debug("Session Created event: " + e.getSession().getId());
        }

        this.getContext(event.getSession().getServletContext()).publishEvent(e);
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSessionDestroyedEvent e = new HttpSessionDestroyedEvent(event.getSession());
        if (logger.isDebugEnabled()) {
            logger.debug("Session destroyed event: " + e.getId());
        }

        this.getContext(event.getSession().getServletContext()).publishEvent(e);
    }
}

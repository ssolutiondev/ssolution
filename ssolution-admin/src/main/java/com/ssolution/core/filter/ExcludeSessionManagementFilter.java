package com.ssolution.core.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <PRE>
 * 1. FileName	: ExcludeSessionManagementFilter.java
 * 2. Package	: com.ssolution.core.filter
 * 3. Comment	: 세션 사용하지 않을 요청 Filter
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:57:45
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class ExcludeSessionManagementFilter extends GenericFilterBean {
    protected final Log logger = LogFactory.getLog(this.getClass());

    private final String[] EXCLUED_PATHS = {
            "/"
            , "/system/login/login"
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if (servletRequest instanceof HttpServletRequest == false)
            filterChain.doFilter(servletRequest, servletResponse);


        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        boolean isExclued = false;
        for (String path : EXCLUED_PATHS) {
            if (path.equals(httpServletRequest.getServletPath())) {
                isExclued = true;
                break;
            }
        }

        if (isExclued) {
            httpServletRequest.setAttribute("__spring_security_session_mgmt_filter_applied", Boolean.TRUE);
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }
}

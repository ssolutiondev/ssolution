package com.ssolution.core.tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.ssolution.admin.system.domain.login.SessionUserVO;
import com.ssolution.admin.system.service.common.menu.MenuTagService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * <PRE>
 * 1. FileName	: TopMenuTag.java
 * 2. Package	: com.ssolution.core.tag
 * 3. Comment	: 최상단 메뉴 출력 태그 라이브러리
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:59:46
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class TopMenuTag extends TagSupport {

    /** Add generated version serial ID. */
    private static final long serialVersionUID = -3180920422727061465L;

    /** the logger. */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public int doStartTag() throws JspException {
        logger.debug("Top Menu Tag Start");

        // Top Menu Html
        String topMenu = null;

        HttpSession session = pageContext.getSession();
        if (session.getAttribute("session_user") != null) {
            SessionUserVO sessionUser = (SessionUserVO) session.getAttribute("session_user");
            String sessionLanguage = (String) session.getAttribute("sessionLanguage");
            topMenu = makeMenu(sessionUser.getUserId(), sessionUser.getSessionId(), sessionLanguage);
        }
        try {
            pageContext.getOut().print(topMenu);
        } catch (IOException e) {
            logger.error("error", e);
        }
        return SKIP_BODY;
    }

    private String makeMenu(String userId, String sessionId, String sessionLanguage) {
        WebApplicationContext applicationContext = RequestContextUtils.findWebApplicationContext((HttpServletRequest) pageContext.getRequest(), pageContext.getServletContext());
        MenuTagService menuTagService = (MenuTagService) applicationContext.getBean("menuTagServiceImpl");
        return menuTagService.getTopMenu(userId, sessionId, sessionLanguage);

    }


}

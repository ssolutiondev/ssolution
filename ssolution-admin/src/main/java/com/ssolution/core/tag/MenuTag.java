package com.ssolution.core.tag;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.ssolution.admin.system.domain.login.SessionUserVO;
import com.ssolution.admin.system.service.common.menu.MenuTagService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * <PRE>
 * 1. FileName	: MenuTag.java
 * 2. Package	: com.ssolution.core.tag
 * 3. Comment	: 메뉴 태그 라이브러리
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:59:23
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class MenuTag extends TagSupport {

    /** Add generated version serial ID. */
    private static final long serialVersionUID = -4737401661375830268L;

    /** the logger. */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String AUTH_MENU_CACHE = "authMenuCache";

    /** 2레벨 메뉴 Id */
    private String menuId;
    /** 선택 메뉴ID . */
    private String selectMenuId;
    /** 선택 메뉴명 */
    private String selectMenuNm;
    /** 최상위 메뉴ID */
    private String topMenuId;
    /** 최상위 메뉴명 */
    private String topMenuNm;

    @SuppressWarnings("rawtypes")
    @Override
    public int doStartTag() throws JspException {

        String result = "";

        HttpSession session = pageContext.getSession();


        String sessionLanguage = (String) session.getAttribute("sessionLanguage");


        if (session.getAttribute("session_user") != null) {
            SessionUserVO sessionUser = (SessionUserVO) session.getAttribute("session_user");
            String sessionId = sessionUser.getSessionId();
            result = makeMenu(sessionUser.getUserId(), sessionUser.getSessionId(), sessionLanguage);

            WebApplicationContext applicationContext = RequestContextUtils.findWebApplicationContext((HttpServletRequest) pageContext.getRequest(), pageContext.getServletContext());

            if (!StringUtils.isEmpty(selectMenuId)) {
                String sessionKey = sessionId + selectMenuId + sessionLanguage;
                CacheManager cacheManager = (CacheManager) applicationContext.getBean("ehCacheCacheManager");
                Ehcache cacheAuth = cacheManager.getEhcache(AUTH_MENU_CACHE);
                Element menuAuthElement = cacheAuth.get(sessionKey);
                if (menuAuthElement != null) {
                    Map<String, Object> munuAuth = (HashMap) menuAuthElement.getObjectValue();

                    if (munuAuth.get(selectMenuId) != null) {
                        Map<String, Object> auth = (HashMap) munuAuth.get(selectMenuId);
                        session.setAttribute("menuAuthC", auth.get("AUTH_REG"));
                        session.setAttribute("menuAuthD", auth.get("AUTH_DEL"));
                        session.setAttribute("menuAuthP", auth.get("AUTH_PRT"));
                        session.setAttribute("menuAuthR", auth.get("AUTH_INQ"));
                        session.setAttribute("menuAuthU", auth.get("AUTH_CHG"));
                    } else {
                        session.setAttribute("menuAuthC", "");
                        session.setAttribute("menuAuthD", "");
                        session.setAttribute("menuAuthP", "");
                        session.setAttribute("menuAuthR", "");
                        session.setAttribute("menuAuthU", "");
                    }
                } else {
                    session.setAttribute("menuAuthC", "");
                    session.setAttribute("menuAuthD", "");
                    session.setAttribute("menuAuthP", "");
                    session.setAttribute("menuAuthR", "");
                    session.setAttribute("menuAuthU", "");
                }
            }

            if (logger.isDebugEnabled()) {
                /*
                 * Session Attribute Log 출력
                 */
                if (session != null) {
                    Enumeration attribuuteNames = session.getAttributeNames();
                    while (attribuuteNames != null && attribuuteNames.hasMoreElements()) {
                        String attrinuteName = (String) attribuuteNames.nextElement();
                        StringBuilder sb = new StringBuilder();
                        sb.append("[");
                        sb.append(session.getAttribute(attrinuteName) == null ? "" : session.getAttribute(attrinuteName)
                                .toString());
                        sb.append("]");
                        logger.debug("Session Parameter\t{}\t : {} ", attrinuteName, sb.toString());
                    }
                }
            }
        }

        try {
            Enumeration<String> keyEnum = pageContext.getRequest().getParameterNames();
            while (keyEnum.hasMoreElements()) {
                String key = keyEnum.nextElement();
                if ("menuId".equals(key) || "selectMenuId".equals(key) ||
                        "selectMenuNm".equals(key) ||
                        "topMenuId".equals(key) ||
                        "topMenuNm".equals(key)) {
                    continue;
                }

                logger.debug("Page Open Parameter : " + key + " / " + pageContext.getRequest().getParameter(key));
                pageContext.setAttribute(key, pageContext.getRequest().getParameter(key), PageContext.REQUEST_SCOPE);
            }
            pageContext.getOut().print(result);
        } catch (IOException e) {

            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    /**
     * 메뉴 생성.
     *
     * @param sessionId 세션ID
     * @return String
     */
    private String makeMenu(String userId, String sessionId, String sessionLanguage) {
        WebApplicationContext applicationContext = RequestContextUtils.findWebApplicationContext((HttpServletRequest) pageContext.getRequest(), pageContext.getServletContext());
        MenuTagService menuTagService = (MenuTagService) applicationContext.getBean("menuTagServiceImpl");
        return menuTagService.getLeftMenu(userId, sessionId, topMenuId, menuId, selectMenuId, sessionLanguage);
    }


    /**
     * @return the menuId
     */
    public String getMenuId() {
        return menuId;
    }

    /**
     * @param menuId the menuId to set
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    /**
     * @return the selectMenuId
     */
    public String getSelectMenuId() {
        return selectMenuId;
    }

    /**
     * @param selectMenuId the selectMenuId to set
     */
    public void setSelectMenuId(String selectMenuId) {
        this.selectMenuId = selectMenuId;
    }

    /**
     * @return the selectMenuNm
     */
    public String getSelectMenuNm() {
        return selectMenuNm;
    }

    /**
     * @param selectMenuNm the selectMenuNm to set
     */
    public void setSelectMenuNm(String selectMenuNm) {
        this.selectMenuNm = selectMenuNm;
    }

    /**
     * @return the topMenuId
     */
    public String getTopMenuId() {
        return topMenuId;
    }

    /**
     * @param topMenuId the topMenuId to set
     */
    public void setTopMenuId(String topMenuId) {
        this.topMenuId = topMenuId;
    }

    /**
     * @return the topMenuNm
     */
    public String getTopMenuNm() {
        return topMenuNm;
    }

    /**
     * @param topMenuNm the topMenuNm to set
     */
    public void setTopMenuNm(String topMenuNm) {
        this.topMenuNm = topMenuNm;
    }


}

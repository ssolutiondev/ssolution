package com.ssolution.core.interceptor;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ssolution.admin.system.domain.common.menu.MenuVO;
import com.ssolution.admin.system.domain.common.menu.SelectedMenuVO;
import com.ssolution.admin.system.service.common.menu.MenuNavigationService;
import com.ssolution.admin.system.util.CommonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <PRE>
 * 1. FileName	: MenuNavigationInterceptor.java
 * 2. Package	: com.ssolution.core.interceptor
 * 3. Comment	: 메뉴 전환 Interceptor
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:58:44
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class MenuNavigationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private MenuNavigationService menuNavigationService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String menuId = StringUtils.defaultString(request.getParameter("menuId"), "-1");
        String selectMenuId = StringUtils.defaultString(request.getParameter("selectMenuId"), "-1");
        String selectMenuNm = StringUtils.defaultString(request.getParameter("selectMenuNm"), "");
        String topMenuId = StringUtils.defaultString(request.getParameter("topMenuId"), "-1");
        String topMenuNm = StringUtils.defaultString(request.getParameter("topMenuNm"), "");
        if (!"-1".equals(menuId) && !"-1".equals(selectMenuId)) {

            SelectedMenuVO selectedMenu = CommonUtil.getSessionSelectMenuInfo();
            if (selectedMenu == null) {
                selectedMenu = new SelectedMenuVO();
                CommonUtil.setSessionSelectMenuInfo(selectedMenu);
            }
            selectedMenu.setMenuId(menuId);
            selectedMenu.setSelectMenuId(selectMenuId);
            selectedMenu.setSelectMenuNm(selectMenuNm);
            selectedMenu.setTopMenuId(topMenuId);
            selectedMenu.setTopMenuNm(topMenuNm);

            CommonUtil.setSessionSelectMenuInfo(selectedMenu);
        }

        /* 요청 정보 로그 출력 */
        if (logger.isDebugEnabled()) {
            if (request.getPathInfo() != null)
                logger.info("REQUEST PATH \t: {}", request.getPathInfo());
            if (request.getQueryString() != null)
                logger.info("REQUEST QueryString \t: {}", request.getQueryString());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) {

        String isChangeMenu = StringUtils.defaultString(request.getParameter("isChangeMenu"), "");

        if ("Y".equals(isChangeMenu)) {
            SelectedMenuVO selectedMenuVO = CommonUtil.getSessionSelectMenuInfo();
            if (selectedMenuVO == null) {
                selectedMenuVO = new SelectedMenuVO();
                CommonUtil.setSessionSelectMenuInfo(selectedMenuVO);
            }

            if (!"".equals(selectedMenuVO.getSelectMenuId()) && !"-1".equals(selectedMenuVO.getSelectMenuId())) {
                injectMenuPaths(request, response, modelAndView, selectedMenuVO.getSelectMenuId());
            }

        } else {
            String selectMenuId = StringUtils.defaultString(request.getParameter("selectMenuId"), "-1");
            if (!"-1".equals(selectMenuId)) {
                injectMenuPaths(request, response, modelAndView, selectMenuId);
            }
        }

        if (logger.isDebugEnabled()) {
            if (modelAndView != null && modelAndView.getViewName() != null)
                logger.debug("RESPONSE VIEW \t : {}", modelAndView.getViewName());
        }
    }

    /**
     * <PRE>
     * 1. MethodName: injectMenuPaths
     * 2. ClassName : MenuNavigationInterceptor
     * 3. Comment   : 메뉴 경로를 삽입
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오후 2:26:57
     * </PRE>
     * 
     * @return void
     * @param request      {@link HttpServletRequest}
     * @param response     {@link HttpServletResponse}
     * @param modelAndView {@link ModelAndView}
     * @param selectMenuId 선택 메뉴ID
     */
    private void injectMenuPaths(HttpServletRequest request,
                                 HttpServletResponse response,
                                 ModelAndView modelAndView,
                                 String selectMenuId) {

        if (isNeedMenuPaths(request, response)) {
            if (modelAndView != null) {
                HttpSession session = request.getSession();
                String sessionLanguage = (String) session.getAttribute("sessionLanguage");

                Map<String, Object> info = getMenuPaths(selectMenuId, sessionLanguage);

                session.setAttribute("naviMenuList", info.get("NAVI_LIST"));
                session.setAttribute("menuNm", info.get("MENU_NM"));
                session.setAttribute("lastPagePath", info.get("VIEW_PATH"));
            }
        }
    }

    /**
     * <PRE>
     * 1. MethodName: isNeedMenuPaths
     * 2. ClassName : MenuNavigationInterceptor
     * 3. Comment   : 메뉴 경로가 필요한 화면인지 판단
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오후 2:26:23
     * </PRE>
     * 
     * @return boolean 판단결과
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @return
     */
    private boolean isNeedMenuPaths(HttpServletRequest request, HttpServletResponse response) {
        return !request.getRequestURI().toLowerCase().endsWith(".json");
    }

    /**
     * <PRE>
     * 1. MethodName: getMenuPaths
     * 2. ClassName : MenuNavigationInterceptor
     * 3. Comment   : 메뉴 ID의 전체 경로 찾기
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오후 2:25:50
     * </PRE>
     * 
     * @return Map<String,Object> 전체 메뉴 경로
     * @param selectMenuId    선택 메뉴ID
     * @param sessionLanguage 언어코드
     */
    public Map<String, Object> getMenuPaths(String selectMenuId, String sessionLanguage) {

        Map<String, Object> result = new HashMap<String, Object>();

        List<MenuVO> menuList;

        try {
            menuList = menuNavigationService.processMenuList(selectMenuId, sessionLanguage);

            if (menuList.size() == 4 && !StringUtils.isEmpty(menuList.get(3).getMenuNm())) {
                result.put("MENU_NM", menuList.get(3).getMenuNm());
                result.put("VIEW_PATH", menuList.get(3).getViewPath());
            } else {
                result.put("MENU_NM", "");
                result.put("VIEW_PATH", "");
            }
            result.put("NAVI_LIST", menuList);
        } catch (Exception e) {
            menuList = Collections.emptyList();
            result.put("NAVI_LIST", menuList);
            result.put("MENU_NM", "");
            result.put("VIEW_PATH", "");
        }

        return result;
    }

}

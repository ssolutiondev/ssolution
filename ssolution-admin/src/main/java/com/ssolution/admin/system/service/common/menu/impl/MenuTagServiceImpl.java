package com.ssolution.admin.system.service.common.menu.impl;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssolution.admin.system.domain.common.menu.*;
import com.ssolution.admin.system.mapper.common.menu.MenuTagMapper;
import com.ssolution.admin.system.service.common.menu.MenuTagService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <PRE>
 * 1. FileName	: MenuTagServiceImpl.java
 * 2. Package	: com.ssolution.admin.system.service.common.menu.impl
 * 3. Comment	: 메뉴 Tag 서비스
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:42:44
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
@Service
public class MenuTagServiceImpl implements MenuTagService {
    /** the logger. */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String TOP_MENU_CACHE = "topMenuCache";
    private static final String LEFT_MENU_CACHE = "leftMenuCache";
    private static final String AUTH_MENU_CACHE = "authMenuCache";

    @Autowired
    private MenuTagMapper menuTagMapper;

    @Autowired
    private CacheManager cacheManager;


    @Override
    public String getTopMenu(String userId, String sessionId, String sessionLanguage) {
        List<TopMenuVO> topMenuList = null;
        List<FastMenuVO> fastMenuList = null;

        String cacheKey = sessionId + sessionLanguage;

        /* cache에서 로드 */
        Ehcache topMenuCache = cacheManager.getEhcache(TOP_MENU_CACHE);
        Element topMenuElement = topMenuCache.get(cacheKey);
        logger.debug("Top Menu Cache Size : " + topMenuCache.getSize());

        if (topMenuElement != null && topMenuElement.getObjectValue() != null) {
            topMenuList = (List<TopMenuVO>) topMenuElement.getObjectValue();
            logger.debug("get topMenu from cache.");
        } else {
            // 메뉴 1레벨 조회
            topMenuList = menuTagMapper.getTopMenu(userId, sessionLanguage);

            fastMenuList = new ArrayList<FastMenuVO>();

            for (TopMenuVO topMenu : topMenuList) {
                // 메뉴 2레벨 조회
                List<TopSubMenuVO> topSubMenuList = menuTagMapper.getTopSubMenu(userId, sessionLanguage, topMenu.getMenuId());

                for (TopSubMenuVO topSubMenu : topSubMenuList) {
                    // 메뉴 3레벨 조회
                    List<LeftTopMenuVO> leftTopMenuList = menuTagMapper.getLeftTopMenu(userId,
                            topSubMenu.getMenuId(),
                            sessionLanguage);

                    // 하위 메뉴가 없는 경우 Continue
                    if (leftTopMenuList.size() == 0)
                        continue;

                    int leftTopCnt = 0;

                    for (LeftTopMenuVO leftTopMenu : leftTopMenuList) {
                        leftTopCnt++;

                        // 메뉴 4레벨 조회
                        List<LeftSubMenuVO> leftSubMenuList = menuTagMapper.getLeftSubMenu(userId,
                                leftTopMenu.getMenuId(),
                                sessionLanguage);

                        // 하위 메뉴가 없는 경우 Continue
                        if (leftSubMenuList.size() == 0)
                            continue;

                        int leftSubCnt = 0;

                        for (LeftSubMenuVO leftSubMenu : leftSubMenuList) {
                            leftSubCnt++;

                            if (leftTopCnt == 1 && leftSubCnt == 1) { //2레벨의 첫번째 열릴 화면 지정
                                topSubMenu.setSelectMenuId(leftSubMenu.getMenuId());
                                topSubMenu.setSelectMenuNm(leftSubMenu.getMenuNm());
                                topSubMenu.setViewPath(leftSubMenu.getViewPath());
                            }

                            // 빠른 검색 메뉴 추가
                            FastMenuVO fastMenu = new FastMenuVO();
                            fastMenu.setName(leftSubMenu.getMenuNm());
                            fastMenu.setMenuId(leftSubMenu.getMenuId());
                            fastMenu.setViewPath(leftSubMenu.getViewPath());
                            fastMenu.setTopMenuId(leftSubMenu.getMenuId());
                            fastMenu.setTopMenuNm(fastMenu.getName());
                            fastMenu.setSubMenuId(topSubMenu.getMenuId());
                            fastMenu.setSubMenuNm(topSubMenu.getMenuNm());
                            fastMenuList.add(fastMenu);
                        }
                        leftTopMenu.setLeftSubMenu(leftSubMenuList);
                    }

                    //Left 메뉴 Cache 저장
                    String leftCacheKey = sessionId + topSubMenu.getMenuId() + sessionLanguage;
                    Ehcache leftCache = cacheManager.getCache(LEFT_MENU_CACHE);
                    Element leftMenuHtmlElement = new Element(leftCacheKey, leftTopMenuList);
                    leftCache.put(leftMenuHtmlElement);
                    logger.debug("put leftMenu to cache.");
                }
                topMenu.setSubMenu(topSubMenuList);
            }

            /* cache에 저장 */
            topMenuElement = new Element(cacheKey, topMenuList);
            topMenuCache.put(topMenuElement);
            logger.debug("put topMenu to cache.");
        }


            StringBuilder result = new StringBuilder();
            for (TopMenuVO topMenu : topMenuList) {
                result.append("<li class=\"dropdown\">");
                result.append("<a aria-expanded=\"false\" role=\"button\" href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">");
                if (topMenu.getMenuId().startsWith("10")) {
                    result.append(" <i class=\"savanaicon-man\"></i>");
                } else if (topMenu.getMenuId().startsWith("20")) {
                    result.append(" <i class=\"savanaicon-security\"></i>");
                } else if (topMenu.getMenuId().startsWith("30")) {
                    result.append(" <i class=\"savanaicon-billing\"></i>");
                } else if (topMenu.getMenuId().startsWith("40")) {
                    result.append(" <i class=\"savanaicon-tool\"></i>");
                } else if (topMenu.getMenuId().startsWith("50")) {
                    result.append(" <i class=\"savanaicon-helmet\"></i>");
                } else if (topMenu.getMenuId().startsWith("60")) {
                    result.append(" <i class=\"savanaicon-wallet\"></i>");
                } else if (topMenu.getMenuId().startsWith("70")) {
                    result.append(" <i class=\"savanaicon-channel\"></i>");
                } else if (topMenu.getMenuId().startsWith("80")) {
                    result.append(" <i class=\"savanaicon-car\"></i>");
                } else if (topMenu.getMenuId().startsWith("90")) {
                    result.append(" <i class=\"savanaicon-setting\"></i>");
                } else if (topMenu.getMenuId().startsWith("91")) {
                    result.append(" <i class=\"savanaicon-emonitor\"></i>");
                }
                result.append(topMenu.getMenuNm());
                result.append(" ");
                result.append("<span class=\"caret\"></span></a>");
                if (topMenu.getSubMenu().size() > 0) {
                    result.append("<ul role=\"menu\" class=\"dropdown-menu dropdown-menu-right\">");
                    for (int i = 0; i < topMenu.getSubMenu().size(); i++) {
                        TopSubMenuVO topSubMenu = topMenu.getSubMenu().get(i);
                        result.append("<li>");
                        result.append("<span onClick='goTopMenu(\"");
                        makeTopMenuHtmlTag(result, topSubMenu);
                        result.append(topSubMenu.getMenuNm());
                        result.append("</span>");
                        result.append("<span class=\"m-right\" onClick='goTopMenuNewTab(\"");
                        makeTopMenuHtmlTag(result, topSubMenu);
                        result.append("<i class=\"savanaicon-windownew\"></i>");
                        result.append("</span>");
                        result.append("</li>");
                    }
                    result.append("</ul>");

                }
                result.append("</li>");
            }
        return result.toString();
    }

    @Override
    public String getLeftMenu(String userId, String sessionId, String topMenuId, String menuId, String selectMenuId, String sessionLanguage) {
        String leftMenuTag = "";

        List<LeftTopMenuVO> leftTopMenuList = null;
        /* cache에서 로드 */
        String leftCacheKey = sessionId + menuId + sessionLanguage;
        String authCacheKey = sessionId + selectMenuId + sessionLanguage;
        Ehcache cache = cacheManager.getCache(LEFT_MENU_CACHE);
        Element leftMenuHtmlElement = cache.get(leftCacheKey);

        logger.debug("Left Menu Cache Size : " + cache.getSize());
        if (leftMenuHtmlElement != null && leftMenuHtmlElement.getObjectValue() != null) {
            leftTopMenuList = (List<LeftTopMenuVO>) leftMenuHtmlElement.getObjectValue();

            logger.debug("get leftMenu from cache.");
        } else {
            leftTopMenuList = menuTagMapper.getLeftTopMenu(userId, menuId, sessionLanguage);
            for (LeftTopMenuVO leftTopMenu : leftTopMenuList) {
                List<LeftSubMenuVO> leftSubMenuList = menuTagMapper.getLeftSubMenu(userId, leftTopMenu.getMenuId(), sessionLanguage);
                leftTopMenu.setLeftSubMenu(leftSubMenuList);
            }
        }

        StringBuilder leftMenuStr = new StringBuilder();
        Map<String, Object> menuAuth = new HashMap<String, Object>();

        for (LeftTopMenuVO leftTopMenu : leftTopMenuList) {
            List<LeftSubMenuVO> leftSubMenu = leftTopMenu.getLeftSubMenu();
            leftTopMenu.setLeftSubMenu(leftSubMenu);

            // Left 메뉴의 하위 메뉴가 존재하지 않을 경우
            if (leftSubMenu.size() == 0) {
                leftMenuStr.append("<li>");
                leftMenuStr.append("<a href=\"#\">");
                leftMenuStr.append(leftTopMenu.getMenuNm());
                leftMenuStr.append("</a>");
            } else {
                //선택 메뉴 찾기
                boolean isSelected = false;
                for (LeftSubMenuVO subMenu : leftTopMenu.getLeftSubMenu()) {
                    if (!StringUtils.isEmpty(selectMenuId) && selectMenuId.equals(subMenu.getMenuId())) {
                        isSelected = true;
                        break;
                    }
                }

                if (isSelected) { //선택된 하위 메뉴가 존재 할 경우
                    leftMenuStr.append("<li class=\"active\">");
                    menuLeftTopHtmlTag(leftMenuStr, leftTopMenu);
                } else { //선택된 하위 메뉴가 존재 하지 않을 경우
                    leftMenuStr.append("<li>");
                    menuLeftTopHtmlTag(leftMenuStr, leftTopMenu);
                }
                leftMenuStr.append("<ul class=\"nav nav-second-level collapse\">");

                for (LeftSubMenuVO subMenu : leftTopMenu.getLeftSubMenu()) {
                    if (!StringUtils.isEmpty(selectMenuId) && selectMenuId.equals(subMenu.getMenuId())) { //선택된 메뉴의 경우
                        leftMenuStr.append("<li class=\"active\">");
                    } else { //선택 되지 않은 메뉴의 경우
                        leftMenuStr.append("<li>");
                    }
                    leftMenuStr.append("<span onClick=\"goLeftMenuPage('");
                    makeLeftSubHtmlTag(topMenuId, menuId, leftMenuStr, leftTopMenu, subMenu);
                    leftMenuStr.append(subMenu.getMenuNm());
                    leftMenuStr.append("</span>");
                    leftMenuStr.append("<span class=\"m-right\" onClick=\"goLeftMenuPageNewTab('");
                    makeLeftSubHtmlTag(topMenuId, menuId, leftMenuStr, leftTopMenu, subMenu);
                    leftMenuStr.append("<i class=\"savanaicon-windownew\"></i>");
                    leftMenuStr.append("</span>");
                    leftMenuStr.append("</li>");

                    Map<String, Object> auth = new HashMap<String, Object>();
                    auth.put("AUTH_REG", subMenu.getAuthReg());
                    auth.put("AUTH_DEL", subMenu.getAuthDel());
                    auth.put("AUTH_PRT", subMenu.getAuthPrt());
                    auth.put("AUTH_INQ", subMenu.getAuthInq());
                    auth.put("AUTH_CHG", subMenu.getAuthChg());
                    menuAuth.put(subMenu.getMenuId(), (HashMap<String, Object>) auth);
                }
                leftMenuStr.append("</ul>");
                leftMenuStr.append("</li>");
            }
        }
        leftMenuTag = leftMenuStr.toString();

        /* cache에 저장 */
        leftMenuHtmlElement = new Element(leftCacheKey, leftTopMenuList);
        cache.put(leftMenuHtmlElement);
        logger.debug("put leftMenuHtml to cache.");

        Ehcache authCache = cacheManager.getCache(AUTH_MENU_CACHE);
        Element authMenuElement = new Element(authCacheKey, menuAuth);
        authCache.put(authMenuElement);
        return leftMenuTag;
    }

    private void makeTopMenuHtmlTag(StringBuilder topMenu, TopSubMenuVO topSubMenu) {
        topMenu.append(topSubMenu.getMenuId());
        topMenu.append("\",\"");
        topMenu.append(topSubMenu.getViewPath());
        topMenu.append("\",\"");
        topMenu.append(topSubMenu.getSelectMenuId());
        topMenu.append("\",\"");
        topMenu.append(topSubMenu.getSelectMenuNm());
        topMenu.append("\",\"");
        topMenu.append(topSubMenu.getTopMenuId());
        topMenu.append("\",\"");
        topMenu.append(topSubMenu.getTopMenuNm());
        topMenu.append("\");'>");
    }

    private void makeLeftSubHtmlTag(String topMenuId, String menuId, StringBuilder leftMenuStr, LeftTopMenuVO leftTopMenu, LeftSubMenuVO subMenu) {
        leftMenuStr.append(menuId);
        leftMenuStr.append("','");
        leftMenuStr.append(subMenu.getViewPath());
        leftMenuStr.append("','");
        leftMenuStr.append(subMenu.getMenuId());
        leftMenuStr.append("','");
        leftMenuStr.append(subMenu.getMenuNm());
        leftMenuStr.append("','");
        leftMenuStr.append(topMenuId);
        leftMenuStr.append("','");
        leftMenuStr.append(leftTopMenu.getTopMenuNm());
        leftMenuStr.append("');\">");
    }

    private void menuLeftTopHtmlTag(StringBuilder leftMenuStr, LeftTopMenuVO leftTopMenu) {
        leftMenuStr.append("<a href=\"#\">");
        leftMenuStr.append("<span class=\"nav-label\">");
        leftMenuStr.append(leftTopMenu.getMenuNm());
        leftMenuStr.append("</span>");
        leftMenuStr.append("<span class=\"savanaicon-arrow_line_left\"></span>");
        leftMenuStr.append("</a>");
    }

    @Override
    public List<FastMenuVO> getFastMenu(String userId, String sessionId, String sessionLanguage) {

        List<TopMenuVO> topMenuList = null;
        List<FastMenuVO> fastMenuList = null;

        String cacheKey = sessionId + sessionLanguage;
        /* cache에서 로드 */
        Ehcache topMenuCache = cacheManager.getEhcache(TOP_MENU_CACHE);
        Element topMenuElement = topMenuCache.get(cacheKey);
        logger.debug("Top Menu Cache Size : " + topMenuCache.getSize());

        if (topMenuElement != null && topMenuElement.getObjectValue() != null) {
            topMenuList = (List<TopMenuVO>) topMenuElement.getObjectValue();
            logger.debug("get cache topMenu");
        } else {
            topMenuList = menuTagMapper.getTopMenu(userId, sessionLanguage);
            fastMenuList = new ArrayList<FastMenuVO>();

            for (TopMenuVO topMenu : topMenuList) {
                List<TopSubMenuVO> subTopMenu = menuTagMapper.getTopSubMenu(userId, sessionLanguage, topMenu.getMenuId());

                for (TopSubMenuVO topSubMenu : subTopMenu) {
                    List<LeftTopMenuVO> leftTopMenuList = menuTagMapper.getLeftTopMenu(userId,
                            topSubMenu.getMenuId(),
                            sessionLanguage);
                    if (leftTopMenuList == null || leftTopMenuList.size() == 0)
                        continue;
                    int leftTopCnt = 0;

                    for (LeftTopMenuVO leftTopMenu : leftTopMenuList) {
                        leftTopCnt++;

                        List<LeftSubMenuVO> leftSubMenuList = menuTagMapper.getLeftSubMenu(userId,
                                leftTopMenu.getMenuId(),
                                sessionLanguage);
                        if (leftSubMenuList == null || leftSubMenuList.size() == 0)
                            continue;

                        int leftSubCnt = 0;
                        for (LeftSubMenuVO leftSubMenu : leftSubMenuList) {
                            leftSubCnt++;

                            if (leftTopCnt == 1 && leftSubCnt == 1) { //2레벨의 첫번째 열릴 화면 지정
                                topSubMenu.setSelectMenuId(leftSubMenu.getMenuId());
                                topSubMenu.setSelectMenuNm(leftSubMenu.getMenuNm());
                                topSubMenu.setViewPath(leftSubMenu.getViewPath());
                            }

                            FastMenuVO fastMenu = new FastMenuVO();

                            fastMenu.setName(leftSubMenu.getMenuNm());
                            fastMenu.setMenuId(leftSubMenu.getMenuId());
                            fastMenu.setViewPath(leftSubMenu.getViewPath());
                            fastMenu.setTopMenuId(topMenu.getMenuId());
                            fastMenu.setTopMenuNm(fastMenu.getName());
                            fastMenu.setSubMenuId(topSubMenu.getMenuId());
                            fastMenu.setSubMenuNm(topSubMenu.getMenuNm());

                            fastMenuList.add(fastMenu);
                        }
                        leftTopMenu.setLeftSubMenu(leftSubMenuList);
                    }

                    //Left 메뉴 Cache 저장
                    String leftCacheKey = sessionId + topSubMenu.getMenuId() + sessionLanguage;
                    Ehcache leftCache = cacheManager.getCache(LEFT_MENU_CACHE);
                    Element leftMenuHtmlElement = new Element(leftCacheKey, leftTopMenuList);
                    leftCache.put(leftMenuHtmlElement);
                    logger.debug("put leftMenu to cache.");


                }
                topMenu.setSubMenu(subTopMenu);
            }
            /* cache에 저장 */
            topMenuElement = new Element(cacheKey, topMenuList);
            topMenuCache.put(topMenuElement);
            logger.debug("put cache topMenu");
        }

        return fastMenuList;
    }
}
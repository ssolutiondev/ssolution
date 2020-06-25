package com.ssolution.admin.system.service.common.menu.impl;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssolution.admin.system.domain.common.logging.MenuConnLoggingVO;
import com.ssolution.admin.system.domain.common.menu.MenuVO;
import com.ssolution.admin.system.domain.login.SessionUserVO;
import com.ssolution.admin.system.mapper.common.menu.MenuNavigationMapper;
import com.ssolution.admin.system.service.common.menu.MenuNavigationService;
import com.ssolution.admin.system.util.CommonUtil;
import com.ssolution.admin.system.util.DateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <PRE>
 * 1. FileName	: MenuNavigationServiceImpl.java
 * 2. Package	: com.ssolution.admin.system.service.common.menu.impl
 * 3. Comment	: 메뉴 이동 인터셉터
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:42:34
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
@Service
public class MenuNavigationServiceImpl implements MenuNavigationService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /** the mapper. */
    @Autowired
    private MenuNavigationMapper menuNavigationMapper;

    @Autowired
    private CacheManager cacheManager;

    private static final String NAVI_MENU_CACHE = "naviMenuCache";


    @SuppressWarnings("unchecked")
    @Override
    public List<MenuVO> processMenuList(String selectMenuId, String sessionLanguage) {
        List<MenuVO> menuNaviList = null;

        String menuId = selectMenuId;

        /* 메뉴 로그 */
        SessionUserVO sessionUser = CommonUtil.getSessionManager();
        MenuConnLoggingVO menuConnLoggingVO = new MenuConnLoggingVO();
        String dateStr = DateUtil.getDateStringYYYYMMDDHH24MISS(0);
        /* 메뉴 로그 */

        String cacheKey = selectMenuId + sessionLanguage;

        /* cache에서 로드 */
        Ehcache naviMenuCache = cacheManager.getEhcache(NAVI_MENU_CACHE);
        Element naviMenuElement = naviMenuCache.get(cacheKey);

        if (naviMenuElement != null && naviMenuElement.getObjectValue() != null) {
            menuNaviList = (List<MenuVO>) naviMenuElement.getObjectValue();
        } else {
            menuNaviList = new ArrayList<MenuVO>();

            for (int i = 4; i > 0; i--) {
                MenuVO menu = menuNavigationMapper.getMenuList(menuId, i, sessionLanguage);
                menuNaviList.add(menu);
                menuId = menu.getUppMenuId();
            }

            Collections.reverse(menuNaviList);
        }
        naviMenuElement = new Element(cacheKey, menuNaviList);
        naviMenuCache.put(naviMenuElement);

        //메뉴접속 로그 insert
        if (menuNaviList.size() == 4 && !StringUtils.isEmpty(menuNaviList.get(3).getMenuNm())) {
            try {

                menuConnLoggingVO.setUserId(sessionUser.getUserId());
                menuConnLoggingVO.setAcsDt(dateStr.substring(0, 8));
                menuConnLoggingVO.setAcsTm(dateStr.substring(8, 14));
                menuConnLoggingVO.setSessionId(sessionUser.getSessionId());
                menuConnLoggingVO.setLvl1MenuId(menuNaviList.get(0).getMenuId());
                menuConnLoggingVO.setLvl2MenuId(menuNaviList.get(1).getMenuId());
                menuConnLoggingVO.setLvl3MenuId(menuNaviList.get(2).getMenuId());
                menuConnLoggingVO.setLvl4MenuId(menuNaviList.get(3).getMenuId());
                menuConnLoggingVO.setMenuAcsUrl(menuNaviList.get(3).getViewPath());
                menuNavigationMapper.insertMenuAcsHist(menuConnLoggingVO);

            } catch (Exception e) {
                log.warn("Fail to insert menu access log : " + e);
            }
        }

        return menuNaviList;
    }

}

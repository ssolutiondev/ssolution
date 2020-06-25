package com.ssolution.admin.system.service.common.menu;

import java.util.List;

import com.ssolution.admin.system.domain.common.menu.FastMenuVO;

/**
 * <PRE>
 * 1. FileName	: MenuTagService.java
 * 2. Package	: com.ssolution.admin.system.service.common.menu
 * 3. Comment	: 메뉴태그 서비스
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:43:05
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public interface MenuTagService {

    /**
     * <PRE>
     * 1. MethodName: getTopMenu
     * 2. ClassName : MenuTagService
     * 3. Comment   : 최상단 메뉴 추출
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오후 4:03:52
     * </PRE>
     * 
     * @return String 메뉴 HTML
     * @param userId          사용자ID
     * @param sessionId       세션ID
     * @param sessionLanguage 언어코드
     */
    public String getTopMenu(String userId, String sessionId, String sessionLanguage);

    /**
     * <PRE>
     * 1. MethodName: getLeftMenu
     * 2. ClassName : MenuTagService
     * 3. Comment   :
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오후 4:04:46
     * </PRE>
     * 
     * @return String 메뉴HTML
     * @param userId          사용자ID
     * @param sessionId       세션ID
     * @param topMenuId       최상단메뉴ID
     * @param menuId          상단서브메뉴ID
     * @param selectMenuId    선택메뉴ID
     * @param sessionLanguage 언어
     */
    public String getLeftMenu(String userId,
                              String sessionId,
                              String topMenuId,
                              String menuId,
                              String selectMenuId,
                              String sessionLanguage);

    /**
     * <PRE>
     * 1. MethodName: getFastMenu
     * 2. ClassName : MenuTagService
     * 3. Comment   : 메뉴검색 정보 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 8. 8. 오후 4:46:39
     * </PRE>
     * 
     * @return List<FastMenu> 메뉴검색정보
     * @param userId          사용자ID
     * @param sessionId       세션ID
     * @param sessionLanguage 언어
     */
    public List<FastMenuVO> getFastMenu(String userId, String sessionId, String sessionLanguage);

}

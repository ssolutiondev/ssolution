package com.ssolution.admin.system.service.common.menu;

import java.util.List;

import com.ssolution.admin.system.domain.common.menu.MenuVO;

/**
 * <PRE>
 * 1. FileName	: MenuNavigationService.java
 * 2. Package	: com.ssolution.admin.system.service.common.menu
 * 3. Comment	: 메뉴 인터셉터 서비스
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:42:54
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public interface MenuNavigationService {

    /**
     * <PRE>
     * 1. MethodName: processMenuList
     * 2. ClassName : MenuNavigationInterceptorService
     * 3. Comment   : 메뉴 네이게이션 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 8. 8. 오후 4:59:48
     * </PRE>
     * 
     * @return List<Menu> 네이게이션정보
     * @param selectMenuNo    선택메뉴
     * @param sessionLanguage 선택언어코드
     * @return
     */
    public List<MenuVO> processMenuList(String selectMenuNo, String sessionLanguage);

}

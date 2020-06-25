package com.ssolution.admin.system.mapper.common.menu;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ssolution.admin.system.domain.common.logging.MenuConnLoggingVO;
import com.ssolution.admin.system.domain.common.menu.MenuVO;

/**
 * <PRE>
 * 1. FileName	: MenuNavigationMapper.java
 * 2. Package	: com.ssolution.admin.system.mapper.common.menu
 * 3. Comment	: 메뉴 mappe
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 11:01:46
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
@Component
public interface MenuNavigationMapper {

    /**
     * <PRE>
     * 1. MethodName: getMenuList
     * 2. ClassName : MenuNavigationInterceptorMapper
     * 3. Comment   : 메뉴정보조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오후 4:07:12
     * </PRE>
     * 
     * @return MenuVO 메뉴정보
     * @param selectMenuId    선택메뉴ID
     * @param lvl             레벨
     * @param sessionLanguage 언어코드
     */
    MenuVO getMenuList(@Param("selectMenuId") String selectMenuId,
                       @Param("lvl") int lvl,
                       @Param("sessionLanguage") String sessionLanguage);

    /**
     * 
     * <PRE>
     * 1. MethodName: insertMenuAcsHist
     * 2. ClassName : MenuNavigationInterceptorMapper
     * 3. Comment   : 메뉴접속로그 인서트
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2018. 1. 30. 오후 6:35:51
     * </PRE>
     * 
     * @return int
     * @param menuConnLoggingVO
     * @return
     */
    int insertMenuAcsHist(MenuConnLoggingVO menuConnLoggingVO);

}
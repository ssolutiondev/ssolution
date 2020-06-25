package com.ssolution.admin.system.service.menu;

import java.util.List;
import java.util.Map;

import com.ssolution.admin.system.domain.manage.MenuMngVO;
import com.ssolution.admin.system.domain.manage.ProgListPopupVO;

/**
 * <PRE>
 * 1. FileName	: MenuMngService.java
 * 2. Package	: com.ssolution.admin.system.service.menu
 * 3. Comment	: 메뉴관리 서비스
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:55:00
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public interface MenuMngService {

    /**
     * <PRE>
     * 1. MethodName: getMenuList
     * 2. ClassName : MenuMngService
     * 3. Comment   : 메뉴 Tree뷰 작성을 위한 메뉴 리스트 조회
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 2:52:36
     * </PRE>
     * 
     * @return Map<String,Object>
     * @param lng 언어
     * @return
     */
    Map<String, Object> getMenuList(String lng);

    /**
     * <PRE>
     * 1. MethodName: getDownMenuList
     * 2. ClassName : MenuMngService
     * 3. Comment   : 하위 메뉴 리스트 조회
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 2:53:09
     * </PRE>
     * 
     * @return List<MenuMngVO> 메뉴 리스트
     * @param menuMngVO 메뉴관리VO
     */
    List<MenuMngVO> getDownMenuList(MenuMngVO menuMngVO);

    /**
     * <PRE>
     * 1. MethodName: insertMenuAction
     * 2. ClassName : MenuMngService
     * 3. Comment   : 메뉴 등록
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 2:53:55
     * </PRE>
     * 
     * @return void
     * @param menu 메뉴관리VO
     */
    void insertMenuAction(MenuMngVO menu);

    /**
     * <PRE>
     * 1. MethodName: updateMenuAction
     * 2. ClassName : MenuMngService
     * 3. Comment   : 메뉴 수정
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 2:54:21
     * </PRE>
     * 
     * @return void
     * @param menu 메뉴관리VO
     */
    void updateMenuAction(MenuMngVO menu);

    /**
     * <PRE>
     * 1. MethodName: deleteAction
     * 2. ClassName : MenuMngService
     * 3. Comment   : 메뉴 삭제
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 2:54:44
     * </PRE>
     * 
     * @return void
     * @param menu 메뉴관리VO
     * @param lng  언어
     * @param lvl  메뉴레벨
     */
    void deleteAction(MenuMngVO menu, String lng, String lvl);

    /**
     * <PRE>
     * 1. MethodName: getProgListPopup
     * 2. ClassName : MenuMngService
     * 3. Comment   : 프로그램 리스트 조회(팝업)
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 4:30:28
     * </PRE>
     * 
     * @return List<ProgListPopupVO>
     * @param progListPopup
     * @return
     */
    List<ProgListPopupVO> getProgListPopup(ProgListPopupVO progListPopup);

}
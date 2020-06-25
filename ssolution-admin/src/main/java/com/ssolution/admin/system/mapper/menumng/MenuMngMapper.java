package com.ssolution.admin.system.mapper.menumng;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ssolution.admin.system.domain.manage.MenuMngVO;
import com.ssolution.admin.system.domain.manage.ProgListPopupVO;

import java.util.List;

/**
 * <PRE>
 * 1. FileName	: MenuMngMapper.java
 * 2. Package	: com.ssolution.admin.system.mapper.menumng
 * 3. Comment	: 메뉴관리 Mapper
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 11:03:59
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
@Component
public interface MenuMngMapper {

    /**
     * <PRE>
     * 1. MethodName: getAuthList
     * 2. ClassName : MenuMngMapper
     * 3. Comment   : 메뉴 Tree뷰 작성을 위한 Object
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 3:09:05
     * </PRE>
     * 
     * @return List<MenuMngVO>
     * @param lvl       레벨
     * @param uppMenuId 상위메뉴ID
     * @param lng       언어
     * @return
     */
    List<MenuMngVO> getAuthList(@Param(value = "lvl") String lvl,
                                @Param(value = "uppMenuId") String uppMenuId,
                                @Param(value = "lng") String lng);

    /**
     * <PRE>
     * 1. MethodName: getDownMenuList
     * 2. ClassName : MenuMngMapper
     * 3. Comment   :
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 3:09:39
     * </PRE>
     * 
     * @return List<MenuMngVO> 하위메뉴리스트
     * @param menuMngVO 메뉴관리VO
     * @return
     */
    List<MenuMngVO> getDownMenuList(@Param(value = "menuMngVO") MenuMngVO menuMngVO);

    /**
     * <PRE>
     * 1. MethodName: insertMenuAction
     * 2. ClassName : MenuMngMapper
     * 3. Comment   : 메뉴등록
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 3:10:52
     * </PRE>
     * 
     * @return void
     * @param menu 메뉴관리VO
     */
    void insertMenuAction(@Param(value = "menu") MenuMngVO menu);

    /**
     * <PRE>
     * 1. MethodName: insertLngMenu
     * 2. ClassName : MenuMngMapper
     * 3. Comment   : 메뉴에 해당하는 언어 등록
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 3:11:08
     * </PRE>
     * 
     * @return void
     * @param menu 메뉴관리VO
     */
    void insertLngMenu(@Param(value = "menu") MenuMngVO menu);

    /**
     * <PRE>
     * 1. MethodName: updateMenuAction
     * 2. ClassName : MenuMngMapper
     * 3. Comment   : 메뉴수정
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 3:11:54
     * </PRE>
     * 
     * @return void
     * @param menu 메뉴관리VO
     */
    void updateMenuAction(@Param(value = "menu") MenuMngVO menu);

    /**
     * <PRE>
     * 1. MethodName: deleteMenuAction
     * 2. ClassName : MenuMngMapper
     * 3. Comment   : 해당메뉴의 언어 삭제
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 3:12:09
     * </PRE>
     * 
     * @return void
     * @param menuId 메뉴ID
     */
    void deleteMenuAction(@Param(value = "menuId") String menuId);

    /**
     * <PRE>
     * 1. MethodName: getMenuNoList
     * 2. ClassName : MenuMngMapper
     * 3. Comment   : 해당 menu_no가 up_menu_no인 리스트 조회
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 3:12:38
     * </PRE>
     * 
     * @return List<MenuMngVO>
     * @param menuId 메뉴ID
     */
    List<MenuMngVO> getMenuNoList(@Param(value = "menuId") String menuId);

    /**
     * <PRE>
     * 1. MethodName: deleteAction
     * 2. ClassName : MenuMngMapper
     * 3. Comment   : 메뉴 삭제
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 3:13:27
     * </PRE>
     * 
     * @return void
     * @param menuId 메뉴ID
     */
    void deleteAction(@Param(value = "menuId") String menuId);

    /**
     * <PRE>
     * 1. MethodName: deleteUpAction
     * 2. ClassName : MenuMngMapper
     * 3. Comment   :
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 3:13:55
     * </PRE>
     * 
     * @return void
     * @param menuId 메뉴ID
     */
    void deleteUpAction(@Param(value = "menuId") String menuId);

    /**
     * <PRE>
     * 1. MethodName: deleteMenuAuth
     * 2. ClassName : MenuMngMapper
     * 3. Comment   :
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 3:14:20
     * </PRE>
     * 
     * @return void
     * @param menuId 메뉴ID
     */
    void deleteMenuAuth(@Param(value = "menuId") String menuId);

    /**
     * <PRE>
     * 1. MethodName: deleteLastMenuAuth
     * 2. ClassName : MenuMngMapper
     * 3. Comment   :
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 3:14:47
     * </PRE>
     * 
     * @return void
     * @param menuId 메뉴ID
     */
    void deleteLastMenuAuth(@Param(value = "menuId") String menuId);

    /**
     * <PRE>
     * 1. MethodName: deleteLastLng
     * 2. ClassName : MenuMngMapper
     * 3. Comment   :
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 3:15:04
     * </PRE>
     * 
     * @return void
     * @param menuId 메뉴ID
     */
    void deleteLastLng(String menuId);

    /**
     * <PRE>
     * 1. MethodName: checkMenu
     * 2. ClassName : MenuMngMapper
     * 3. Comment   : 중복체크
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 3:15:20
     * </PRE>
     * 
     * @return int
     * @param menu 메뉴관리VO
     * @return
     */
    int checkMenu(@Param(value = "menu") MenuMngVO menu);

    /**
     * <PRE>
     * 1. MethodName: getProgListPopup
     * 2. ClassName : MenuMngMapper
     * 3. Comment   : 프로그램 리스트 조회
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 4:33:03
     * </PRE>
     * 
     * @return List<ProgListPopupVO>
     * @param progListPopup
     * @return
     */
    List<ProgListPopupVO> getProgListPopup(ProgListPopupVO progListPopup);

}
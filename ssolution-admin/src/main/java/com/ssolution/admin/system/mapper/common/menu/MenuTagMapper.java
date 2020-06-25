package com.ssolution.admin.system.mapper.common.menu;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssolution.admin.system.domain.common.menu.LeftSubMenuVO;
import com.ssolution.admin.system.domain.common.menu.LeftTopMenuVO;
import com.ssolution.admin.system.domain.common.menu.TopMenuVO;
import com.ssolution.admin.system.domain.common.menu.TopSubMenuVO;

import java.util.List;

/**
 * <PRE>
 * 1. FileName	: MenuTagMapper.java
 * 2. Package	: com.ssolution.admin.system.mapper.common.menu
 * 3. Comment	: 사용자 그룹 권한 mapper
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 11:01:23
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
@Mapper
public interface MenuTagMapper {

    /**
     * <PRE>
     * 1. MethodName: getTopMenu
     * 2. ClassName : MenuTagMapper
     * 3. Comment   : 최상단 메뉴 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오후 3:52:40
     * </PRE>
     * 
     * @return List<TopMenuVO> 최상단메뉴리스트
     * @param userId          사용자ID
     * @param sessionLanguage 언어코드
     */
    List<TopMenuVO> getTopMenu(@Param(value = "userId") String userId,
                               @Param(value = "sessionLanguage") String sessionLanguage);

    /**
     * <PRE>
     * 1. MethodName: getTopSubMenu
     * 2. ClassName : MenuTagMapper
     * 3. Comment   : 서브 상단 메뉴 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오후 3:53:00
     * </PRE>
     * 
     * @return List<TopSubMenuVO> 서브 상단 메뉴 리스트
     * @param userId          사용자ID
     * @param sessionLanguage 언어코드
     * @param topMenuId       최상단메뉴ID
     * @return
     */
    List<TopSubMenuVO> getTopSubMenu(@Param(value = "userId") String userId,
                                     @Param(value = "sessionLanguage") String sessionLanguage,
                                     @Param(value = "topMenuId") String topMenuId);

    /**
     * <PRE>
     * 1. MethodName: getLeftTopMenu
     * 2. ClassName : MenuTagMapper
     * 3. Comment   : 좌측 상단 메뉴 리스트
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오후 3:53:47
     * </PRE>
     * 
     * @return List<LeftTopMenuVO> 좌측 상단 메뉴 리스트
     * @param userId          사용자ID
     * @param uppMenuId       상위메뉴
     * @param sessionLanguage 언어코드
     * @return
     */
    List<LeftTopMenuVO> getLeftTopMenu(@Param(value = "userId") String userId,
                                       @Param(value = "uppMenuId") String uppMenuId,
                                       @Param(value = "sessionLanguage") String sessionLanguage);

    /**
     * <PRE>
     * 1. MethodName: getLeftSubMenu
     * 2. ClassName : MenuTagMapper
     * 3. Comment   : 좌측 하위 메뉴 리스트
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오후 3:54:42
     * </PRE>
     * 
     * @return List<LeftSubMenuVO> 좌측 하위 메뉴 리스트
     * @param userId          사용자ID
     * @param leftTopMenuId   좌측사위메뉴ID
     * @param sessionLanguage 언어코드
     * @return
     */
    List<LeftSubMenuVO> getLeftSubMenu(@Param(value = "userId") String userId,
                                       @Param(value = "leftTopMenuId") String leftTopMenuId,
                                       @Param(value = "sessionLanguage") String sessionLanguage);
}

package com.ssolution.admin.system.mapper.manage;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ssolution.admin.system.domain.manage.AuthMngVO;
import com.ssolution.admin.system.domain.manage.GrpMngVO;

import java.util.List;


/**
 * <PRE>
 * 1. FileName	: AuthMngMapper.java
 * 2. Package	: com.ssolution.admin.system.mapper.manage
 * 3. Comment	: 그룹별권한관리 MAPPER
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 11:02:52
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
@Component
public interface AuthMngMapper {

    /**
     * <PRE>
     * 1. MethodName: getAuthGroupList
     * 2. ClassName : AuthMngMapper
     * 3. Comment   : 권한 그룹 조회
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 24. 오전 9:34:00
     * </PRE>
     * 
     * @return List<GrpMngVO> 권한 그룹 리스트
     * @param grpMng 권한그룹VO
     */
    List<GrpMngVO> getAuthGroupList(@Param(value = "auth") GrpMngVO grpMng);

    /**
     * <PRE>
     * 1. MethodName: getAuthList
     * 2. ClassName : AuthMngMapper
     * 3. Comment   : 권한정보 조회
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 24. 오전 9:34:00
     * </PRE>
     * 
     * @return List<Map<String,Object>> 사용자그룹아이디에 해당하는 권한정보
     * @param authGrpId  권한그룹ID
     * @param condAsgnYn 할당메뉴만 조회
     * @param lvl        순서번호
     * @param uppMenuId  상위메뉴ID
     * @param lng        언어코드
     */
    List<AuthMngVO> getAuthList(@Param(value = "authGrpId") String authGrpId,
                                @Param(value = "condAsgnYn") String condAsgnYn,
                                @Param(value = "lvl") int lvl,
                                @Param(value = "uppMenuId") String uppMenuId,
                                @Param(value = "lng") String lng);

    /**
     * <PRE>
     * 1. MethodName: updateAuth
     * 2. ClassName : AuthMngMapper
     * 3. Comment   : 권한 수정
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 24. 오전 10:00:34
     * </PRE>
     * 
     * @return int
     * @param authGrpId
     * @param menuId
     * @param authInq
     * @param authreg
     * @param authChg
     * @param authDel
     * @param authPrt
     */
    int updateAuth(@Param(value = "authMng") AuthMngVO authMng);

    /**
     * <PRE>
     * 1. MethodName: deleteAuth
     * 2. ClassName : AuthMngMapper
     * 3. Comment   : 해당메뉴와 사용자그룹아이디에 해당하는 사용자그룹권한 삭제
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 24. 오전 9:34:00
     * </PRE>
     * 
     * @return int 삭제여부
     * @param authGrpId 권한그룹ID
     * @param menuId    메뉴ID
     */
    int deleteAuth(@Param(value = "authGrpId") String authGrpId, @Param(value = "menuId") String menuId);

    /**
     * <PRE>
     * 1. MethodName: getMenuList
     * 2. ClassName : AuthMngMapper
     * 3. Comment   : 해당메뉴번호가 상위번호이면서 순서가 같은 MENU_ID 조회
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 24. 오전 9:34:00
     * </PRE>
     * 
     * @return List<String> 해당메뉴번호가 상위번호이면서 순서가 같은 MENU_ID 조회
     * @param menuId 메뉴ID
     * @param lvl    순서번호
     */
    List<String> getMenuList(@Param(value = "menuId") String menuId, @Param(value = "lvl") int lvl);

    /**
     * <PRE>
     * 1. MethodName: getUpMenuNo
     * 2. ClassName : AuthMngMapper
     * 3. Comment   : 해당메뉴의 상위메뉴번호 조회
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 24. 오전 9:34:00
     * </PRE>
     * 
     * @return String 상위메뉴ID
     * @param menuId 메뉴ID
     */
    String getUpMenuNo(@Param(value = "menuId") String menuId);

    /**
     * <PRE>
     * 1. MethodName: insertAuth
     * 2. ClassName : AuthMngMapper
     * 3. Comment   : 권한정보 등록
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 24. 오전 10:24:43
     * </PRE>
     * 
     * @return int 등록여부
     * @param authMng 권한그룹VO
     */
    int insertAuth(@Param(value = "authMng") AuthMngVO authMng);

    /**
     * <PRE>
     * 1. MethodName: getMenuAuthCnt
     * 2. ClassName : AuthMngMapper
     * 3. Comment   : 메뉴갯수 조회
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 24. 오전 10:24:43
     * </PRE>
     * 
     * @return int 메뉴개수
     * @param authGrpId 권한그룹ID
     * @param menuId    상위메뉴ID
     */
    int getMenuAuthCnt(@Param(value = "authGrpId") String authGrpId, @Param(value = "menuId") String menuId);

}
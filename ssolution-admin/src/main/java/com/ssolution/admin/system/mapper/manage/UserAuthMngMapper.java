package com.ssolution.admin.system.mapper.manage;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ssolution.admin.system.domain.manage.ManagerAuthMngVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <PRE>
 * 1. FileName	: UserAuthMngMapper.java
 * 2. Package	: com.ssolution.admin.system.mapper.manage
 * 3. Comment	: 그룹별 사용자관리
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 11:03:31
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
@Component
public interface UserAuthMngMapper {

    /**
     * <PRE>
     * 1. MethodName: getUserList
     * 2. ClassName : UserAuthMngMapper
     * 3. Comment   : 사용자 리스트 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 19. 오후 3:21:42
     * </PRE>
     * 
     * @return List<UserAuthMngVO> 사용자권한관리VO
     * @param userAuth   사용자권한관리 조건
     * @param soAuthList 사업권한리스트
     * @param today      조회일자
     */
    List<ManagerAuthMngVO> getUserList(@Param(value = "userAuth") ManagerAuthMngVO userAuth,
                                    @Param(value = "soAuthList") List<Map<String, Object>> soAuthList,
                                    @Param(value = "today") String today);

    /**
     * <PRE>
     * 1. MethodName: getUserAuthList
     * 2. ClassName : UserAuthMngMapper
     * 3. Comment   : 사용자에게 할당된 권한 그룹 리스트 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 19. 오후 3:25:19
     * </PRE>
     * 
     * @return List<UserAuthMngVO> 권한그룹리스트
     * @param userAuth 조회조건
     */
    List<ManagerAuthMngVO> getUserAuthList(@Param("userAuth") ManagerAuthMngVO userAuth);

    /**
     * <PRE>
     * 1. MethodName: deleteAllAuth
     * 2. ClassName : UserAuthMngMapper
     * 3. Comment   : 사용자에게 할당된 모든 권한 그룹 삭제
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 8. 8. 오전 10:34:37
     * </PRE>
     * 
     * @param userAuth 사용자별 그룹관리 VO
     */
    void deleteAllAuth(@Param("userAuth") ManagerAuthMngVO userAuth);

    /**
     * <PRE>
     * 1. MethodName: insertUserAuth
     * 2. ClassName : UserAuthMngMapper
     * 3. Comment   : 사용자에 권한 그룹 추가
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 8. 8. 오전 10:35:02
     * </PRE>
     * 
     * @param userId      사용자ID
     * @param date
     * @param string2
     * @param string
     * @param userGroupId 사용자권한그룹ID
     */
    void insertUserAuth(@Param("userId") String userId,
                        @Param("authGrpId") String authGrpId,
                        @Param("inptMenuId") String inptMenuId,
                        @Param("regrId") String regrId,
                        @Param("regDate") Date regDate);

}
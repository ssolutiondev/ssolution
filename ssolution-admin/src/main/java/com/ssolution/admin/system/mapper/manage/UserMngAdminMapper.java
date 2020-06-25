package com.ssolution.admin.system.mapper.manage;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ssolution.admin.system.domain.manage.ManagerMngAdminVO;

import java.util.List;
import java.util.Map;

/**
 * <PRE>
 * 1. FileName	: UserMngAdminMapper.java
 * 2. Package	: com.ssolution.admin.system.mapper.manage
 * 3. Comment	: 사용자관리 관리자용 Mapper
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 11:03:50
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
@Component
public interface UserMngAdminMapper {

    /**
     * <PRE>
     * 1. MethodName: getUserInfoList
     * 2. ClassName : UserMngAdminMapper
     * 3. Comment   : 사용자정보 리스트 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 1:42:51
     * </PRE>
     * 
     * @return List<UserMngAdminVO> 사용자 정보 리스트
     * @param user       사용자정보
     * @param soAuthList 권한 사업자 리스트
     */
    List<ManagerMngAdminVO> getUserInfoList(@Param(value = "user") ManagerMngAdminVO user,
                                         @Param(value = "soAuthList") List<Map<String, Object>> soAuthList);

    /**
     * <PRE>
     * 1. MethodName: insertUserInfo
     * 2. ClassName : UserMngAdminMapper
     * 3. Comment   : 사용자 정보 등록
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 1:43:24
     * </PRE>
     * 
     * @return int 등록수
     * @param user 사용자 정보
     */
    int insertUserInfo(ManagerMngAdminVO user);

    /**
     * <PRE>
     * 1. MethodName: updateUserInfo
     * 2. ClassName : UserMngAdminMapper
     * 3. Comment   : 사용자 정보 수정
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 1:43:57
     * </PRE>
     * 
     * @return int 수정수
     * @param user 사용자 정보
     */
    int updateUserInfo(ManagerMngAdminVO user);

    /**
     * <PRE>
     * 1. MethodName: checkUserId
     * 2. ClassName : UserMngAdminMapper
     * 3. Comment   : 사용자ID 중복 체크
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 1:44:19
     * </PRE>
     * 
     * @return int 사용자 정보 수
     * @param user 사용자정보
     */
    int checkUserId(ManagerMngAdminVO user);

    /**
     * <PRE>
     * 1. MethodName: insertUserAuth
     * 2. ClassName : UserMngAdminMapper
     * 3. Comment   : 사용자 ID의 기본 권한 그룹 등록
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 1:45:09
     * </PRE>
     * 
     * @return void void
     * @param user 사용자 정보
     */
    void insertUserAuth(ManagerMngAdminVO user);

    /**
     * <PRE>
     * 1. MethodName: insertSoAuth
     * 2. ClassName : UserMngAdminMapper
     * 3. Comment   : 해당 user_id에 해당하는 기본SO 등록
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 1:45:09
     * </PRE>
     * 
     * @return void
     * @param user 사용자VO
     */
    void insertSoAuth(ManagerMngAdminVO user);

    /**
     * <PRE>
     * 1. MethodName: getUserSoId
     * 2. ClassName : UserMngAdminMapper
     * 3. Comment   : 사용자 ID의 SO ID조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 1:46:39
     * </PRE>
     * 
     * @return String 사업ID
     * @param user  사용자 정보
     * @param today 조회일자
     */
    String getUserSoId(@Param(value = "user") ManagerMngAdminVO user, @Param(value = "today") String today);

    /**
     * <PRE>
     * 1. MethodName: userAuthCount
     * 2. ClassName : UserMngAdminMapper
     * 3. Comment   : 사용자의 기본 권한 그룹 존재 유무 확인
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 1:47:40
     * </PRE>
     * 
     * @return int 권한그룹조회수
     * @param user 사용자정보
     */
    int userAuthCount(ManagerMngAdminVO user);

    /**
     * <PRE>
     * 1. MethodName: getUserInfo
     * 2. ClassName : UserMngAdminMapper
     * 3. Comment   : 사용자 정보 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 1:48:13
     * </PRE>
     * 
     * @return UserMngAdminVO 사용자정보
     * @param userId 사용자ID
     * @return
     */
    ManagerMngAdminVO getUserInfo(@Param(value = "userId") String userId);

    int updatePassword(ManagerMngAdminVO user);
}
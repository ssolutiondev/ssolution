package com.ssolution.admin.system.service.manager;

import java.util.List;

import com.ssolution.admin.system.domain.manage.PasswordChgVO;
import com.ssolution.admin.system.domain.manage.ManagerMngAdminVO;

/**
 * <PRE>
 * 1. FileName	: ManagerMngAdminService.java
 * 2. Package	: com.ssolution.admin.system.service.manager
 * 3. Comment	: 사용자관리 관리자용 서비스
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:54:41
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public interface ManagerMngAdminService {

    /**
     * <PRE>
     * 1. MethodName: getUserInfoList
     * 2. ClassName : UserMngAdminService
     * 3. Comment   : 사용자관리 관리자용 서비스
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 1:36:34
     * </PRE>
     * 
     * @return List<UserMngAdminVO> 사용자 정보 리스트
     * @param user 사용자정보VO
     */
    List<ManagerMngAdminVO> getUserInfoList(ManagerMngAdminVO user);

    /**
     * <PRE>
     * 1. MethodName: getUserInfo
     * 2. ClassName : UserMngAdminService
     * 3. Comment   : 사용자 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2018. 2. 25. 오후 9:53:46
     * </PRE>
     * 
     * @return UserMngAdminVO 사용자정보
     * @param userId 사용자ID
     */
    ManagerMngAdminVO getUserInfo(String userId);

    /**
     * <PRE>
     * 1. MethodName: insertUserInfo
     * 2. ClassName : UserMngAdminService
     * 3. Comment   : 사용자 등록 서비스
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 1:37:33
     * </PRE>
     * 
     * @return int 등록수
     * @param user 사용자정보
     */
    int insertUserInfo(ManagerMngAdminVO user);

    /**
     * <PRE>
     * 1. MethodName: updateUserInfo
     * 2. ClassName : UserMngAdminService
     * 3. Comment   : 사용자 정보 수정 서비스
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 1:38:23
     * </PRE>
     * 
     * @return int 수정수
     * @param user 사용자정보
     */
    int updateUserInfo(ManagerMngAdminVO user);

    /**
     * <PRE>
     * 1. MethodName: checkUserId
     * 2. ClassName : UserMngAdminService
     * 3. Comment   : 사용자ID 중복 체크
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 1:38:52
     * </PRE>
     * 
     * @return int 사용자 정보 조회 수
     * @param user 사용자정보
     */
    int checkUserId(ManagerMngAdminVO user);

    /**
     * 패스워드 변경
     * @param userId 변경대상 사용자ID
     * @param passwordChgVO 변경정보
     * @return 변경수
     */
    int updatePassword(String userId, PasswordChgVO passwordChgVO);
}
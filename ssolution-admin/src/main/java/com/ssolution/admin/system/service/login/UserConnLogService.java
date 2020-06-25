package com.ssolution.admin.system.service.login;

import com.ssolution.admin.system.domain.login.SessionUserVO;
import com.ssolution.admin.system.domain.login.LoginHistoryVO;
import com.ssolution.admin.system.domain.login.UserConnLogVO;

import java.util.List;
import java.util.Map;

/**
 * <PRE>
 * 1. FileName	: UserConnLogService.java
 * 2. Package	: com.ssolution.admin.system.service.login
 * 3. Comment	: 사용자 접속 로그 조회
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:46:02
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public interface UserConnLogService {

    /**
     *
     * <PRE>
     * 1. MethodName: getUserConnLogListCount
     * 2. ClassName : UserConnLogService
     * 3. Comment   : 사용자 접속 로그 리스트 카운트
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 25. 오후 3:03:10
     * </PRE>
     * 
     * @return int
     * @param userConnLogVO
     * @return
     */
    int getUserConnLogListCount(UserConnLogVO userConnLogVO);

    /**
     *
     * <PRE>
     * 1. MethodName: getUserConnLogList
     * 2. ClassName : UserConnLogService
     * 3. Comment   : 사용자 접속 로그 리스트 조회
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 25. 오후 3:03:23
     * </PRE>
     * 
     * @return List<UserConnLogVO>
     * @param userConnLogVO
     * @return
     */
    List<UserConnLogVO> getUserConnLogList(UserConnLogVO userConnLogVO);

    /**
     *
     * <PRE>
     * 1. MethodName: getUserConnLogExcelList
     * 2. ClassName : UserConnLogService
     * 3. Comment   : 사용자 접속 로그 리스트 엑셀
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 25. 오후 3:03:42
     * </PRE>
     * 
     * @return List<Map<String,Object>>
     * @param userConnLogVO
     * @return
     */
    List<Map<String, Object>> getUserConnLogExcelList(UserConnLogVO userConnLogVO);

    int insertLoginHistory(LoginHistoryVO loginHistory);

    int updateLogoutHistory(SessionUserVO sessionUser, String logoutDt, String logoutTm, String status);
}

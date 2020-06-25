package com.ssolution.admin.system.mapper.login;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ssolution.admin.system.domain.login.SessionUserVO;
import com.ssolution.admin.system.domain.login.UserConnLogVO;
import com.ssolution.admin.system.domain.login.LoginHistoryVO;

import java.util.List;
import java.util.Map;

/**
 * <PRE>
 * 1. FileName	: UserConnLogMapper.java
 * 2. Package	: com.ssolution.admin.system.mapper.login
 * 3. Comment	: 사용자 접속 로그 조회
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 11:02:34
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
@Component
public interface UserConnLogMapper {

    /**
     *
     * <PRE>
     * 1. MethodName: getUserConnLogListCount
     * 2. ClassName : UserConnLogMapper
     * 3. Comment   : 사용자 접속 로그 리스트 카운트
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 25. 오후 3:01:57
     * </PRE>
     * 
     * @return int
     * @param userConnLogVO
     * @return
     */
    int getUserConnLogListCount(@Param(value = "userConnLogVO") UserConnLogVO userConnLogVO);

    /**
     *
     * <PRE>
     * 1. MethodName: getUserConnLogList
     * 2. ClassName : UserConnLogMapper
     * 3. Comment   : 사용자 접속 로그 리스트 조회
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 25. 오후 3:02:10
     * </PRE>
     * 
     * @return List<UserConnLogVO>
     * @param start
     * @param end
     * @param userConnLogVO
     * @return
     */
    List<UserConnLogVO> getUserConnLogList(@Param(value = "start") String start,
                                           @Param(value = "end") String end,
                                           @Param(value = "userConnLogVO") UserConnLogVO userConnLogVO);

    /**
     *
     * <PRE>
     * 1. MethodName: getUserConnLogExcelList
     * 2. ClassName : UserConnLogMapper
     * 3. Comment   : 사용자 접속 로그 리스트 엑셀
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 25. 오후 3:02:19
     * </PRE>
     * 
     * @return List<Map<String,Object>>
     * @param userConnLogVO
     * @return
     */
    List<Map<String, Object>> getUserConnLogExcelList(@Param(value = "userConnLogVO") UserConnLogVO userConnLogVO);

    /**
     * <PRE>
     * 1. MethodName: insertLoginHistory
     * 2. ClassName : LoginMapper
     * 3. Comment   : 로그인 이력 생성
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오전 10:54:08
     * </PRE>
     *
     * @return int 생성 Cnt
     * @param loginHistory 로그인 이력 정보
     */
    int insertLoginHistory(@Param("loginHistory") LoginHistoryVO loginHistory);

    /**
     * <PRE>
     * 1. MethodName: updateLogoutHistory
     * 2. ClassName : LoginMapper
     * 3. Comment   : 로그아웃 이력 기록
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오전 11:07:51
     * </PRE>
     *
     * @return int 수정 Count
     * @param sessionUser  세션유저 정보
     * @param logoutDt     로그아웃일자
     * @param logoutTm     로그아웃시간
     * @param logoutStatus 상태(N:정상, T:Session Timeout)
     */
    int updateLogoutHistory(@Param("sessionUser") SessionUserVO sessionUser,
                            @Param("logoutDt") String logoutDt,
                            @Param("logoutTm") String logoutTm,
                            @Param("logoutStatus") String status);
}

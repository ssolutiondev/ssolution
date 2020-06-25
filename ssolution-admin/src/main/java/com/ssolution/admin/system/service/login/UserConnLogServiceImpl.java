package com.ssolution.admin.system.service.login;

import com.ssolution.admin.system.domain.login.SessionUserVO;
import com.ssolution.admin.system.mapper.login.UserConnLogMapper;
import com.ssolution.admin.system.domain.login.LoginHistoryVO;
import com.ssolution.admin.system.domain.login.UserConnLogVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * <PRE>
 * 1. FileName	: UserConnLogServiceImpl.java
 * 2. Package	: com.ssolution.admin.system.service.login
 * 3. Comment	: 사용자 접속 로그 조회
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:46:10
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
@Service
public class UserConnLogServiceImpl implements UserConnLogService {

    @Autowired
    UserConnLogMapper userConnLogMapper;

    /**
     * 사용자 접속 로그 리스트 카운트
     */
    @Override
    public int getUserConnLogListCount(UserConnLogVO userConnLogVO) {
        return userConnLogMapper.getUserConnLogListCount(userConnLogVO);
    }

    /**
     * 사용자 접속 로그 리스트 조회
     */
    @Override
    public List<UserConnLogVO> getUserConnLogList(UserConnLogVO userConnLogVO) {
        return userConnLogMapper.getUserConnLogList(userConnLogVO.getStart(), userConnLogVO.getEnd(), userConnLogVO);
    }

    /**
     * 사용자 접속 로그 리스트 엑셀
     */
    @Override
    public List<Map<String, Object>> getUserConnLogExcelList(UserConnLogVO userConnLogVO) {
        return userConnLogMapper.getUserConnLogExcelList(userConnLogVO);
    }

    @Override
    public int insertLoginHistory(LoginHistoryVO loginHistory) {
        return userConnLogMapper.insertLoginHistory(loginHistory);
    }

    @Override
    public int updateLogoutHistory(SessionUserVO sessionUser, String logoutDt, String logoutTm, String status) {
        return userConnLogMapper.updateLogoutHistory(sessionUser, logoutDt, logoutTm, status);
    }

}

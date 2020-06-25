package com.ssolution.admin.system.service.login;

import java.util.Map;

import com.ssolution.admin.system.domain.login.SessionUserVO;


/**
 * <PRE>
 * 1. FileName	: LoginService.java
 * 2. Package	: com.ssolution.admin.system.service.login
 * 3. Comment	: 로그인 처리 서비스
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:45:52
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public interface LoginService {

    /**
     * 로그인 처리
     * @param userId
     * @param pswd
     * @param remoteAddress
     * @return
     */
    Map<String, Object> login(String userId, String pswd, String remoteAddress, String sessionId);

    void logout(SessionUserVO sessionUser, String status);
}

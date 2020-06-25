package com.ssolution.admin.system.service.login.impl;


import com.ssolution.admin.system.util.MessageUtil;
import com.ssolution.admin.system.domain.login.LoginVO;
import com.ssolution.admin.system.domain.login.SessionUserVO;
import com.ssolution.admin.system.domain.login.LoginHistoryVO;
import com.ssolution.admin.system.domain.manage.ManagerMngAdminVO;
import com.ssolution.admin.system.mapper.login.LoginMapper;
import com.ssolution.admin.system.service.login.LoginService;
import com.ssolution.admin.system.service.login.UserConnLogService;
import com.ssolution.admin.system.service.manager.ManagerMngAdminService;
import com.ssolution.admin.system.util.CommonUtil;
import com.ssolution.admin.system.util.DateUtil;
import com.ssolution.core.config.ApplicationConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <PRE>
 * 1. FileName	: LoginServiceImpl.java
 * 2. Package	: com.ssolution.admin.system.service.login.impl
 * 3. Comment	: 로그인 처리 서비스
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 12. 오전 10:07:38
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 12.	|	신규 작성
 * </PRE>
 */
@Service
public class LoginServiceImpl implements LoginService {

    /** 로그 출력. */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplicationConfiguration applicationConfiguration;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private UserConnLogService userConnLogService;

    @Autowired
    private ManagerMngAdminService userMngAdminService;

    /**
     * fdsafsd
     * @param userId
     * @param pswd
     * @param remoteAddress
     * @return
     */
    @Override
    public Map<String, Object> login(String userId, String pswd, String remoteAddress, String sessionId) {

        Map<String, Object> loginResult = new HashMap<>();

        // 사용자 ID 및 패스워드 필수 체크
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(pswd)) {
            logger.debug("Login Fail : user id or password is empty : {}", userId);
            loginResult.put("RESULT", "ERROR_INPUT_NULL");
            loginResult.put("DASHBOARD_URL", "");
            return loginResult;
        }

        // 사용자 존재 여부 체크
        LoginVO userInfo = loginMapper.getLoginUser(userId);
        if( userInfo == null){
            logger.debug("Login Fail : user id not exists. : {}", userId);
            loginResult.put("RESULT", "LOGIN_FAIL");
            loginResult.put("DASHBOARD_URL", "");
            return loginResult;
        }

        //Account Lock 체크
        if("Y".equals(userInfo.getLockYn())){
            logger.debug("Login Fail : user account has been locked : {}", userId);
            loginResult.put("RESULT", "LOCK_ACCOUNT");
            loginResult.put("DASHBOARD_URL", "");
            return loginResult;
        }

        // 로그인 실패 횟수 초과로 계정이 잠겨있는지 검사
        if (userInfo.getLoginFailCnt() > applicationConfiguration.getLoginFailCnt()) {
            logger.debug("Login Fail : fail count exceeded. : {}", userId);
            loginResult.put("RESULT", "OVER_LOGIN_FAIL_COUNT");
            loginResult.put("DASHBOARD_URL", "");
            return loginResult;
        }

        // 패스워드 일치 체크
        boolean isMatchPswd = passwordEncoder.matches(pswd, userInfo.getPswd());
        if(isMatchPswd == false){
            logger.debug("Login Fail : password is not match. : {}", userId);
            loginResult.put("RESULT", "LOGIN_FAIL");
            loginResult.put("DASHBOARD_URL", "");
            updateLoginFailCount(userId);
            return loginResult;
        }

        // IP Address 체크
        if(isAllowIp(userInfo.getPermIpBand(), remoteAddress) == false){
            logger.debug("Login Fail : ip address is not allowed. : {}", userId);
            loginResult.put("RESULT", "FAIL_PASS_IP_BANDWIDTH");
            loginResult.put("DASHBOARD_URL", "");
            updateLoginFailCount(userId);
            return loginResult;
        }

        // 사용자 정보 조회
        try {
            SessionUserVO sessionUser = getSessionUser(userId, remoteAddress);
            // 로그인 사용자의 패스워드 만료 일자 체크 한다.
            ManagerMngAdminVO user = userMngAdminService.getUserInfo(sessionUser.getUserId());
            boolean passwordExpiredNotify = false;
            boolean passwordExpired = false;
            long diffDate = DateUtil.diffOfDate(DateUtil.getDateStringYYYYMMDD(0), user.getPswdChgPlnDt());
            if(diffDate < 7 && diffDate >= 0){
                passwordExpiredNotify = true;
            }else if(diffDate < 0){
                passwordExpired = true;
            }

            sessionUser.setSessionId(sessionId);
            loginResult.put("SESSION_USER", sessionUser);
            loginResult.put("MASK_SWITCH", false);
            if(passwordExpiredNotify){
                Object[] messageObj = {DateUtil.getLngDateFormat(user.getPswdChgPlnDt(), CommonUtil.getSessionLng())};
                loginResult.put("MESSAGE", MessageUtil.getMessage("MSG.M13.MSG00077", messageObj));
            }else if(passwordExpired){
                loginResult.put("MESSAGE", MessageUtil.getMessage("MSG.M13.MSG00078"));
            }

            loginResult.put("MASK_SWITCH", false);
            loginResult.put("RESULT", passwordExpiredNotify ? "GO_MAIN_PASSWORD_NOTIFY" : (passwordExpired ? "PASSWORD_EXPIRED" : "GO_MAIN"));
            loginResult.put("DASHBOARD_URL", sessionUser.getDashboardUrl());

            // 로그인 성공 처리 한다.
            loginMapper.updateLastLoginDateTime(userId);

            insertLoginHistory(sessionUser);
        } catch (Exception e) {
            logger.debug("Login Fail : session user get fail. : {}", userId);
            loginResult.put("RESULT", StringUtils.isEmpty(e.getMessage()) ? e.getMessage() : "LOGIN_ERROR");
            loginResult.put("DASHBOARD_URL", "");
            return loginResult;
        }

        return loginResult;
    }

    @Override
    public void logout(SessionUserVO sessionUser, String status) {
        String date = DateUtil.getDateStringYYYYMMDDHH24MISS(0);

        String logoutDt = date.substring(0, 8);
        String logoutTm = date.substring(8);

        userConnLogService.updateLogoutHistory(sessionUser, logoutDt, logoutTm, status);
    }

    private SessionUserVO getSessionUser(String userId, String remoteAddress) throws Exception {

        // 사용자 정보를 조회 한다.
        SessionUserVO sessionUser = loginMapper.getSessionUser(userId);

        // 사용자의 권한 그룹을 조회 한다.
        sessionUser.setAuthGrpIdList(loginMapper.getAuthGrpList(userId));

        // 사용자 조직 정보 조회 한다.
        Map<String, Object> orgInfo = loginMapper.getOrgInfo(userId, DateUtil.getDateStringYYYYMMDD(0));
        if(orgInfo == null || orgInfo.isEmpty()){
            throw new Exception("CHECK_ACCOUNT_INFO");
        }
        sessionUser.setSoId((String) orgInfo.get("so_id"));
        sessionUser.setSoNm((String) orgInfo.get("so_nm"));
        sessionUser.setOrgId((String) orgInfo.get("org_id"));
        sessionUser.setOrgNm((String) orgInfo.get("org_nm"));
        sessionUser.setAuthOrgId((String) orgInfo.get("auth_org_id"));
        sessionUser.setAuthOrgNm((String) orgInfo.get("auth_org_nm"));

        // 사용자 SO 권한 조회 한다.
        sessionUser.setSoAuthList(loginMapper.getSoAuthList(userId));

        // 접속 IP 세팅
        sessionUser.setLoginGatewayIp(remoteAddress);

        return sessionUser;
    }

    /**
     * 로그인 Fail Count 증가
     * @param userId 사용자ID
     */
    private void updateLoginFailCount(String userId) {

        // 사용자 로그인 Fail Count
        loginMapper.updateFailCount(userId);

        LoginVO userInfo = loginMapper.getLoginUser(userId);

        // 로그인 Fail Count 초과시 계정 Lock
        if (userInfo.getLoginFailCnt() > applicationConfiguration.getLoginFailCnt()) {
            loginMapper.updateAccountLock(userId);
        }
    }

    /**
     * 허용 IP 대역 확인
     * @param allowIpBand 허용 IP 대역
     * @param remoteAddress 접속 IP
     * @return true : 허용, false : 불가
     */
    private boolean isAllowIp(String allowIpBand, String remoteAddress) {
        String[] ip_bandwidth = allowIpBand.split("\\.");

        // localhost 접속이면 건너뜀
        if (remoteAddress.equals("localhost") || remoteAddress.equals("0:0:0:0:0:0:0:1")) {
            return true;
        } else if (!remoteAddress.contains(".")) { // ip에 '.'이 있는지 확인
            return false;
        }

        String ip[] = remoteAddress.split("\\.");
        logger.debug("ip : {}", ip.length);
        // '.'이 세개인지 확인
        if (ip.length > 4) {
            return false;
        }

        boolean result = true;
        for (int i = 0; i < ip_bandwidth.length; i++) {
            logger.debug("ip_bandwidth[{}] : {}", i, ip_bandwidth[i]);

            if (!"*".equals(ip_bandwidth[i])) {
                logger.debug("ip_bandwidth[i] : !*", i);

                if (!ip[i].equals(ip_bandwidth[i])) {
                    logger.debug("ip not equal ip_bandwidth");

                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    private int insertLoginHistory(SessionUserVO sessionUser) {

        LoginHistoryVO loginHistory = new LoginHistoryVO();

        loginHistory.setUserId(sessionUser.getUserId());
        loginHistory.setSessionId(sessionUser.getSessionId());
        loginHistory.setLoginIp(sessionUser.getLoginGatewayIp() == null ? "" : sessionUser.getLoginGatewayIp()
                .length() > 200 ? sessionUser.getLoginGatewayIp()
                .substring(0,
                        200) : sessionUser.getLoginGatewayIp());
        /*
         * 로그인 시간 조회
         */
        Map<String, String> loginDate = loginMapper.getLoginDate(sessionUser.getUserId());
        if (loginDate == null) {
            loginHistory.setLoginDt("");
            loginHistory.setLoginTm("");
        } else {
            loginHistory.setLoginDt(loginDate.get("login_dt") == null ? "" : loginDate.get("login_dt"));
            loginHistory.setLoginTm(loginDate.get("login_tm") == null ? "" : loginDate.get("login_tm"));

        }

        int cnt = userConnLogService.insertLoginHistory(loginHistory);
        return cnt;
    }
}

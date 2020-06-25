package com.ssolution.admin.system.domain.login;

/**
 * <PRE>
 * 1. FileName	: LoginHistoryVO.java
 * 2. Package	: com.ssolution.admin.system.domain.login
 * 3. Comment	: 로그인 이력 작성용 VO
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:30:21
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class LoginHistoryVO {

    /**
     * 로그인ID
     */
    private String userId;

    /**
     * 로그인일자(YYYYMMDD)
     */
    private String loginDt;

    /**
     * 로그인시간(HH24MISS)
     */
    private String loginTm;

    /**
     * 세션ID
     */
    private String sessionId;

    /**
     * 접속IP
     */
    private String loginIp;

    /**
     * 로그아웃일자(YYYYMMDD)
     */
    private String logoutDt;
    /**
     * 로그아웃시간(HH24MISS)
     */
    private String logoutTm;

    /**
     * 로그아웃상태(N:정상로그아웃, T:Session Timeout)
     */
    private String logoutStat;

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the loginDt
     */
    public String getLoginDt() {
        return loginDt;
    }

    /**
     * @param loginDt the loginDt to set
     */
    public void setLoginDt(String loginDt) {
        this.loginDt = loginDt;
    }

    /**
     * @return the loginTm
     */
    public String getLoginTm() {
        return loginTm;
    }

    /**
     * @param loginTm the loginTm to set
     */
    public void setLoginTm(String loginTm) {
        this.loginTm = loginTm;
    }

    /**
     * @return the sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return the loginIp
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * @param loginIp the loginIp to set
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    /**
     * @return the logoutDt
     */
    public String getLogoutDt() {
        return logoutDt;
    }

    /**
     * @param logoutDt the logoutDt to set
     */
    public void setLogoutDt(String logoutDt) {
        this.logoutDt = logoutDt;
    }

    /**
     * @return the logoutTm
     */
    public String getLogoutTm() {
        return logoutTm;
    }

    /**
     * @param logoutTm the logoutTm to set
     */
    public void setLogoutTm(String logoutTm) {
        this.logoutTm = logoutTm;
    }

    /**
     * @return the logoutStat
     */
    public String getLogoutStat() {
        return logoutStat;
    }

    /**
     * @param logoutStat the logoutStat to set
     */
    public void setLogoutStat(String logoutStat) {
        this.logoutStat = logoutStat;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LoginHistory [userId=");
        builder.append(userId);
        builder.append(", loginDt=");
        builder.append(loginDt);
        builder.append(", loginTm=");
        builder.append(loginTm);
        builder.append(", sessionId=");
        builder.append(sessionId);
        builder.append(", loginIp=");
        builder.append(loginIp);
        builder.append(", logoutDt=");
        builder.append(logoutDt);
        builder.append(", logoutTm=");
        builder.append(logoutTm);
        builder.append(", logoutStat=");
        builder.append(logoutStat);
        builder.append("]");
        return builder.toString();
    }

}

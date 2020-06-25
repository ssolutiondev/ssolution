package com.ssolution.admin.system.domain.login;


import com.ssolution.admin.system.domain.common.paging.PagingValueVO;

/**
 * <PRE>
 * 1. FileName	: UserConnLogVO.java
 * 2. Package	: com.ssolution.admin.system.domain.login
 * 3. Comment	: 사용자 접속 로그 조회
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:31:23
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class UserConnLogVO extends PagingValueVO {

    private String userId;
    private String loginDt;
    private String loginTm;
    private String sessionId;
    private String loginIp;
    private String logoutDt;
    private String logoutTm;
    private String logoutStat;
    private String userNm;
    private String orgId;
    private String orgNm;
    private String startDt;
    private String endDt;
    private String logoutStatNm;

    public String getLogoutStatNm() {
        return logoutStatNm;
    }

    public void setLogoutStatNm(String logoutStatNm) {
        this.logoutStatNm = logoutStatNm;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginDt() {
        return loginDt;
    }

    public void setLoginDt(String loginDt) {
        this.loginDt = loginDt;
    }

    public String getLoginTm() {
        return loginTm;
    }

    public void setLoginTm(String loginTm) {
        this.loginTm = loginTm;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLogoutDt() {
        return logoutDt;
    }

    public void setLogoutDt(String logoutDt) {
        this.logoutDt = logoutDt;
    }

    public String getLogoutTm() {
        return logoutTm;
    }

    public void setLogoutTm(String logoutTm) {
        this.logoutTm = logoutTm;
    }

    public String getLogoutStat() {
        return logoutStat;
    }

    public void setLogoutStat(String logoutStat) {
        this.logoutStat = logoutStat;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgNm() {
        return orgNm;
    }

    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

}

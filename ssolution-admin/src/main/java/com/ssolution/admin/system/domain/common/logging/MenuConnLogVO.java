package com.ssolution.admin.system.domain.common.logging;

import com.ssolution.admin.system.domain.common.paging.PagingValueVO;

/**
 * <PRE>
 * 1. FileName	: MenuConnLogVO.java
 * 2. Package	: com.ssolution.admin.system.domain.common.logging
 * 3. Comment	: 메뉴 접속 로그 조회
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 12. 오전 9:37:20
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 12.	|	신규 작성
 * </PRE>
 */
public class MenuConnLogVO extends PagingValueVO {

    private String userId;
    private String acsDt;
    private String acsTm;
    private String sessionId;
    private String lvl1MenuId;
    private String lvl2MenuId;
    private String lvl3MenuId;
    private String lvl4MenuId;
    private String menuAcsUrl;
    private String userNm;
    private String orgId;
    private String orgNm;
    private String startDt;
    private String endDt;
    private String lvl1MenuNm;
    private String lvl2MenuNm;
    private String lvl3MenuNm;
    private String lvl4MenuNm;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAcsDt() {
        return acsDt;
    }

    public void setAcsDt(String acsDt) {
        this.acsDt = acsDt;
    }

    public String getAcsTm() {
        return acsTm;
    }

    public void setAcsTm(String acsTm) {
        this.acsTm = acsTm;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLvl1MenuId() {
        return lvl1MenuId;
    }

    public void setLvl1MenuId(String lvl1MenuId) {
        this.lvl1MenuId = lvl1MenuId;
    }

    public String getLvl2MenuId() {
        return lvl2MenuId;
    }

    public void setLvl2MenuId(String lvl2MenuId) {
        this.lvl2MenuId = lvl2MenuId;
    }

    public String getLvl3MenuId() {
        return lvl3MenuId;
    }

    public void setLvl3MenuId(String lvl3MenuId) {
        this.lvl3MenuId = lvl3MenuId;
    }

    public String getLvl4MenuId() {
        return lvl4MenuId;
    }

    public void setLvl4MenuId(String lvl4MenuId) {
        this.lvl4MenuId = lvl4MenuId;
    }

    public String getMenuAcsUrl() {
        return menuAcsUrl;
    }

    public void setMenuAcsUrl(String menuAcsUrl) {
        this.menuAcsUrl = menuAcsUrl;
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

    public String getLvl1MenuNm() {
        return lvl1MenuNm;
    }

    public void setLvl1MenuNm(String lvl1MenuNm) {
        this.lvl1MenuNm = lvl1MenuNm;
    }

    public String getLvl2MenuNm() {
        return lvl2MenuNm;
    }

    public void setLvl2MenuNm(String lvl2MenuNm) {
        this.lvl2MenuNm = lvl2MenuNm;
    }

    public String getLvl3MenuNm() {
        return lvl3MenuNm;
    }

    public void setLvl3MenuNm(String lvl3MenuNm) {
        this.lvl3MenuNm = lvl3MenuNm;
    }

    public String getLvl4MenuNm() {
        return lvl4MenuNm;
    }

    public void setLvl4MenuNm(String lvl4MenuNm) {
        this.lvl4MenuNm = lvl4MenuNm;
    }

}

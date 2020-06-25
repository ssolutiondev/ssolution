package com.ssolution.admin.system.domain.manage;

import java.util.Date;

import com.ssolution.admin.system.domain.common.paging.PagingValueVO;

public class AuthMngVO extends PagingValueVO {

    /**
     * 권한그룹ID
     */
    private String authGrpId;

    /**
     * 권한그룹명
     */
    private String authGrpNm;

    /**
     * 메뉴ID
     */
    private String menuId;

    /**
     * 메뉴명
     */
    private String menuNm;

    /**
     * 메뉴레벨
     */
    private String lvl;

    /**
     * 권한All
     */
    private String authAll;

    /**
     * 권한조회
     */
    private String authInq;

    /**
     * 권한등록
     */
    private String authReg;

    /**
     * 권한수정
     */
    private String authChg;

    /**
     * 권한삭제
     */
    private String authDel;

    /**
     * 권한출력
     */
    private String authPrt;

    /**
     * 입력메뉴ID
     */
    private String inptMenuId;

    /**
     * 등록자ID
     */
    private String regrId;

    /**
     * 등록자명
     */
    private String regrNm;

    /**
     * 등록일시
     */
    private Date regDate;

    /**
     * 수정자ID
     */
    private String chgrId;

    /**
     * 수정자명
     */
    private String chgrNm;

    /**
     * 수정일시
     */
    private Date chgDate;

    public String getAuthGrpId() {
        return authGrpId;
    }

    public void setAuthGrpId(String authGrpId) {
        this.authGrpId = authGrpId;
    }

    public String getAuthGrpNm() {
        return authGrpNm;
    }

    public void setAuthGrpNm(String authGrpNm) {
        this.authGrpNm = authGrpNm;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuNm() {
        return menuNm;
    }

    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm;
    }

    public String getLvl() {
        return lvl;
    }

    public void setLvl(String lvl) {
        this.lvl = lvl;
    }

    public String getAuthAll() {
        return authAll;
    }

    public void setAuthAll(String authAll) {
        this.authAll = authAll;
    }

    public String getAuthInq() {
        return authInq;
    }

    public void setAuthInq(String authInq) {
        this.authInq = authInq;
    }

    public String getAuthReg() {
        return authReg;
    }

    public void setAuthReg(String authReg) {
        this.authReg = authReg;
    }

    public String getAuthChg() {
        return authChg;
    }

    public void setAuthChg(String authChg) {
        this.authChg = authChg;
    }

    public String getAuthDel() {
        return authDel;
    }

    public void setAuthDel(String authDel) {
        this.authDel = authDel;
    }

    public String getAuthPrt() {
        return authPrt;
    }

    public void setAuthPrt(String authPrt) {
        this.authPrt = authPrt;
    }

    public String getInptMenuId() {
        return inptMenuId;
    }

    public void setInptMenuId(String inptMenuId) {
        this.inptMenuId = inptMenuId;
    }

    public String getRegrId() {
        return regrId;
    }

    public void setRegrId(String regrId) {
        this.regrId = regrId;
    }

    public String getRegrNm() {
        return regrNm;
    }

    public void setRegrNm(String regrNm) {
        this.regrNm = regrNm;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getChgrId() {
        return chgrId;
    }

    public void setChgrId(String chgrId) {
        this.chgrId = chgrId;
    }

    public String getChgrNm() {
        return chgrNm;
    }

    public void setChgrNm(String chgrNm) {
        this.chgrNm = chgrNm;
    }

    public Date getChgDate() {
        return chgDate;
    }

    public void setChgDate(Date chgDate) {
        this.chgDate = chgDate;
    }

}
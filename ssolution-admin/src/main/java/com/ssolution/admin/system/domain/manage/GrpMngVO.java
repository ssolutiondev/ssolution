package com.ssolution.admin.system.domain.manage;

import java.util.Date;

import com.ssolution.admin.system.domain.common.paging.PagingValueVO;


/**
 * <PRE>
 * 1. FileName	: GrpMngVO.java
 * 2. Package	: com.ssolution.admin.system.domain.manage
 * 3. Comment	: 권한 그룹 관리 VO
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:37:09
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class GrpMngVO extends PagingValueVO {
    private String authGrpId; // 권한 그룹 ID
    private String authGrpNm; // 권한 그룹 명
    private String dashboardTp; // Dashboard 유형
    private String dashboardTpNm; // Dashboard 유형명
    private String mainView; // MainView
    private int ord; // 순서
    private String authGrpDesc; // 권한 그룹 설명
    private String inptMenuId; // 입력 메뉴ID
    private String regrId; // 등록자ID
    private String regrNm; // 등록자명
    private Date regDate; // 등록일시
    private String chgrId; // 수정자ID
    private String chgrNm; // 수정자명
    private Date chgDate; // 수정일시

    /**
     * @return the authGrpId
     */
    public String getAuthGrpId() {
        return authGrpId;
    }

    /**
     * @param authGrpId the authGrpId to set
     */
    public void setAuthGrpId(String authGrpId) {
        this.authGrpId = authGrpId;
    }

    /**
     * @return the authGrpNm
     */
    public String getAuthGrpNm() {
        return authGrpNm;
    }

    /**
     * @param authGrpNm the authGrpNm to set
     */
    public void setAuthGrpNm(String authGrpNm) {
        this.authGrpNm = authGrpNm;
    }

    /**
     * @return the dashboardTp
     */
    public String getDashboardTp() {
        return dashboardTp;
    }

    /**
     * @param dashboardTp the dashboardTp to set
     */
    public void setDashboardTp(String dashboardTp) {
        this.dashboardTp = dashboardTp;
    }

    /**
     * @return the dashboardTpNm
     */
    public String getDashboardTpNm() {
        return dashboardTpNm;
    }

    /**
     * @param dashboardTpNm the dashboardTpNm to set
     */
    public void setDashboardTpNm(String dashboardTpNm) {
        this.dashboardTpNm = dashboardTpNm;
    }

    /**
     * @return the mainView
     */
    public String getMainView() {
        return mainView;
    }

    /**
     * @param mainView the mainView to set
     */
    public void setMainView(String mainView) {
        this.mainView = mainView;
    }

    /**
     * @return the ord
     */
    public int getOrd() {
        return ord;
    }

    /**
     * @param ord the ord to set
     */
    public void setOrd(int ord) {
        this.ord = ord;
    }

    /**
     * @return the authGrpDesc
     */
    public String getAuthGrpDesc() {
        return authGrpDesc;
    }

    /**
     * @param authGrpDesc the authGrpDesc to set
     */
    public void setAuthGrpDesc(String authGrpDesc) {
        this.authGrpDesc = authGrpDesc;
    }

    /**
     * @return the inptMenuId
     */
    public String getInptMenuId() {
        return inptMenuId;
    }

    /**
     * @param inptMenuId the inptMenuId to set
     */
    public void setInptMenuId(String inptMenuId) {
        this.inptMenuId = inptMenuId;
    }

    /**
     * @return the regrId
     */
    public String getRegrId() {
        return regrId;
    }

    /**
     * @param regrId the regrId to set
     */
    public void setRegrId(String regrId) {
        this.regrId = regrId;
    }

    /**
     * @return the regrNm
     */
    public String getRegrNm() {
        return regrNm;
    }

    /**
     * @param regrNm the regrNm to set
     */
    public void setRegrNm(String regrNm) {
        this.regrNm = regrNm;
    }

    /**
     * @return the regDate
     */
    public Date getRegDate() {
        return regDate;
    }

    /**
     * @param regDate the regDate to set
     */
    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    /**
     * @return the chgrId
     */
    public String getChgrId() {
        return chgrId;
    }

    /**
     * @param chgrId the chgrId to set
     */
    public void setChgrId(String chgrId) {
        this.chgrId = chgrId;
    }

    /**
     * @return the chgrNm
     */
    public String getChgrNm() {
        return chgrNm;
    }

    /**
     * @param chgrNm the chgrNm to set
     */
    public void setChgrNm(String chgrNm) {
        this.chgrNm = chgrNm;
    }

    /**
     * @return the chgDate
     */
    public Date getChgDate() {
        return chgDate;
    }

    /**
     * @param chgDate the chgDate to set
     */
    public void setChgDate(Date chgDate) {
        this.chgDate = chgDate;
    }

}
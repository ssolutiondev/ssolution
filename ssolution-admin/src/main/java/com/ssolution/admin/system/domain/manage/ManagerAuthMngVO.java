package com.ssolution.admin.system.domain.manage;

import java.util.Date;
import java.util.List;

import com.ssolution.admin.system.domain.common.paging.PagingValueVO;


/**
 * <PRE>
 * 1. FileName	: ManagerAuthMngVO.java
 * 2. Package	: com.ssolution.admin.system.domain.manage
 * 3. Comment	: 그룹별 사용자 관리 VO
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:37:33
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class ManagerAuthMngVO extends PagingValueVO {

    private String checkYn; // 체크여부(Y/N)
    private String authGrpId; // 권한그룹ID
    private String authGrpNm; // 권한그룹명
    private String userId; // 사용자ID
    private String userNm; // 사용자명
    private String inptMenuId; // 입력 메뉴ID
    private String regrId; // 등록자ID
    private String regrNm; // 등록자명
    private Date regDate; // 등록일시
    private String chgrId; // 수정자ID
    private String chgrNm; // 수정자명
    private Date chgDate; // 수정일시

    private String orgId; // 조직ID
    private String orgNm; // 조직명

    private List<String> updateAuthIdList; //수정 권한 그룹ID 리스트

    /**
     * @return the checkYn
     */
    public String getCheckYn() {
        return checkYn;
    }

    /**
     * @param checkYn the checkYn to set
     */
    public void setCheckYn(String checkYn) {
        this.checkYn = checkYn;
    }

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
     * @return the userNm
     */
    public String getUserNm() {
        return userNm;
    }

    /**
     * @param userNm the userNm to set
     */
    public void setUserNm(String userNm) {
        this.userNm = userNm;
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

    /**
     * @return the orgId
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the orgNm
     */
    public String getOrgNm() {
        return orgNm;
    }

    /**
     * @param orgNm the orgNm to set
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    /**
     * @return the updateAuthIdList
     */
    public List<String> getUpdateAuthIdList() {
        return updateAuthIdList;
    }

    /**
     * @param updateAuthIdList the updateAuthIdList to set
     */
    public void setUpdateAuthIdList(List<String> updateAuthIdList) {
        this.updateAuthIdList = updateAuthIdList;
    }

}
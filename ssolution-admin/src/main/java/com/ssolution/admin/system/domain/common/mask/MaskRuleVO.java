package com.ssolution.admin.system.domain.common.mask;

/**
 * <PRE>
 * 1. FileName	: MaskRuleVO.java
 * 2. Package	: com.ssolution.admin.system.domain.common.mask
 * 3. Comment	: 마스크 처리 기준 정보 VO
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:07:01
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class MaskRuleVO {

    private String soId; /* 사업ID */
    private String fldNm; /* 필드명(CamelCase) */
    private Integer strtIndx; /* 마스크처리 시작 INDEX */
    private Integer endIndx; /* 마스크처리 종료 INDEX(9999 세팅시 문자열 끝까지 적용) */
    private Integer addStrtIndx; /* 2단 마스크처리 시작 INDEX  */
    private Integer addEndIndx; /* 2단 마스크처리 시작 (9999 세팅시 문자열 끝까지 적용) */
    private Integer authStrtIndx; /* 마스크 해제 마스크처리 시작 INDEX */
    private Integer authEndIndx; /* 마스크처리 종료 INDEX(9999 세팅시 문자열 끝까지 적용) */
    private Integer authAddStrtIndx; /* 2단 마스크처리 시작 INDEX  */
    private Integer authAddEndIndx; /* 2단 마스크처리 시작 (9999 세팅시 문자열 끝까지 적용) */
    private String chgChar; /* 처리문자 */

    /**
     * @return the soId
     */
    public String getSoId() {
        return soId;
    }

    /**
     * @param soId the soId to set
     */
    public void setSoId(String soId) {
        this.soId = soId;
    }

    /**
     * @return the fldNm
     */
    public String getFldNm() {
        return fldNm;
    }

    /**
     * @param fldNm the fldNm to set
     */
    public void setFldNm(String fldNm) {
        this.fldNm = fldNm;
    }

    /**
     * @return the strtIndx
     */
    public Integer getStrtIndx() {
        return strtIndx;
    }

    /**
     * @param strtIndx the strtIndx to set
     */
    public void setStrtIndx(Integer strtIndx) {
        this.strtIndx = strtIndx;
    }

    /**
     * @return the endIndx
     */
    public Integer getEndIndx() {
        return endIndx;
    }

    /**
     * @param endIndx the endIndx to set
     */
    public void setEndIndx(Integer endIndx) {
        this.endIndx = endIndx;
    }

    /**
     * @return the addStrtIndx
     */
    public Integer getAddStrtIndx() {
        return addStrtIndx;
    }

    /**
     * @param addStrtIndx the addStrtIndx to set
     */
    public void setAddStrtIndx(Integer addStrtIndx) {
        this.addStrtIndx = addStrtIndx;
    }

    /**
     * @return the addEndIndx
     */
    public Integer getAddEndIndx() {
        return addEndIndx;
    }

    /**
     * @param addEndIndx the addEndIndx to set
     */
    public void setAddEndIndx(Integer addEndIndx) {
        this.addEndIndx = addEndIndx;
    }

    /**
     * @return the authStrtIndx
     */
    public Integer getAuthStrtIndx() {
        return authStrtIndx;
    }

    /**
     * @param authStrtIndx the authStrtIndx to set
     */
    public void setAuthStrtIndx(Integer authStrtIndx) {
        this.authStrtIndx = authStrtIndx;
    }

    /**
     * @return the authEndIndx
     */
    public Integer getAuthEndIndx() {
        return authEndIndx;
    }

    /**
     * @param authEndIndx the authEndIndx to set
     */
    public void setAuthEndIndx(Integer authEndIndx) {
        this.authEndIndx = authEndIndx;
    }

    /**
     * @return the authAddStrtIndx
     */
    public Integer getAuthAddStrtIndx() {
        return authAddStrtIndx;
    }

    /**
     * @param authAddStrtIndx the authAddStrtIndx to set
     */
    public void setAuthAddStrtIndx(Integer authAddStrtIndx) {
        this.authAddStrtIndx = authAddStrtIndx;
    }

    /**
     * @return the authAddEndIndx
     */
    public Integer getAuthAddEndIndx() {
        return authAddEndIndx;
    }

    /**
     * @param authAddEndIndx the authAddEndIndx to set
     */
    public void setAuthAddEndIndx(Integer authAddEndIndx) {
        this.authAddEndIndx = authAddEndIndx;
    }

    /**
     * @return the chgChar
     */
    public String getChgChar() {
        return chgChar;
    }

    /**
     * @param chgChar the chgChar to set
     */
    public void setChgChar(String chgChar) {
        this.chgChar = chgChar;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MaskRuleVO [soId=");
        builder.append(soId);
        builder.append(", fldNm=");
        builder.append(fldNm);
        builder.append(", strtIndx=");
        builder.append(strtIndx);
        builder.append(", endIndx=");
        builder.append(endIndx);
        builder.append(", addStrtIndx=");
        builder.append(addStrtIndx);
        builder.append(", addEndIndx=");
        builder.append(addEndIndx);
        builder.append(", authStrtIndx=");
        builder.append(authStrtIndx);
        builder.append(", authEndIndx=");
        builder.append(authEndIndx);
        builder.append(", authAddStrtIndx=");
        builder.append(authAddStrtIndx);
        builder.append(", authAddEndIndx=");
        builder.append(authAddEndIndx);
        builder.append(", chgChar=");
        builder.append(chgChar);
        builder.append("]");
        return builder.toString();
    }

}

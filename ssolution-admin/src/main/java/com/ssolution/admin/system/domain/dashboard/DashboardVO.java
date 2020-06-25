package com.ssolution.admin.system.domain.dashboard;


import com.ssolution.admin.system.domain.common.paging.PagingValueVO;

/**
 * <PRE>
 * 1. FileName	: DashboardVO.java
 * 2. Package	: com.ssolution.admin.system.domain.dashboard
 * 3. Comment	: Dashboard View 리스트 조회
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:29:59
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class DashboardVO extends PagingValueVO {
    /**
     * 페이지명
     */
    private String commonCdNm;
    /**
     * 페이지 코드
     */
    private String commonCd;
    /**
     * 페이지URL
     */
    private String refCode;

    private String returnId1;
    private String returnId2;

    /**
     * @return the commonCdNm
     */
    public String getCommonCdNm() {
        return commonCdNm;
    }

    /**
     * @param commonCdNm the commonCdNm to set
     */
    public void setCommonCdNm(String commonCdNm) {
        this.commonCdNm = commonCdNm;
    }

    /**
     * @return the commonCd
     */
    public String getCommonCd() {
        return commonCd;
    }

    /**
     * @param commonCd the commonCd to set
     */
    public void setCommonCd(String commonCd) {
        this.commonCd = commonCd;
    }

    /**
     * @return the refCode
     */
    public String getRefCode() {
        return refCode;
    }

    /**
     * @param refCode the refCode to set
     */
    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    /**
     * @return the returnId1
     */
    public String getReturnId1() {
        return returnId1;
    }

    /**
     * @param returnId1 the returnId1 to set
     */
    public void setReturnId1(String returnId1) {
        this.returnId1 = returnId1;
    }

    /**
     * @return the returnId2
     */
    public String getReturnId2() {
        return returnId2;
    }

    /**
     * @param returnId2 the returnId2 to set
     */
    public void setReturnId2(String returnId2) {
        this.returnId2 = returnId2;
    }

}
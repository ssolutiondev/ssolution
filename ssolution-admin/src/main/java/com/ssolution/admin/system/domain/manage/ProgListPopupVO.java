package com.ssolution.admin.system.domain.manage;


import com.ssolution.admin.system.domain.common.paging.PagingValueVO;

/**
 * <PRE>
 * 1. FileName	: ProgListPopupVO.java
 * 2. Package	: com.ssolution.admin.system.domain.manage
 * 3. Comment	: 프로그램 목록 팝업 VO
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:38:16
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class ProgListPopupVO extends PagingValueVO {

    /**
     * 시스템구분명
     */
    private String sysTpNm;

    /**
     * 시스템구분
     */
    private String sysTpCd;

    /**
     * viewPath URL
     */
    private String viewPathUrl;

    /**
     * 프로그램ID
     */
    private String progId;

    /**
     * 프로그램명
     */
    private String progNm;

    /**
     * 언어유형
     */
    private String langTp;

    /**
     * 리턴항목ID(URL코드)
     */
    private String returnIdFroUrlCd;

    /**
     * 리턴항목ID(URL명)
     */
    private String returnIdFroUrlNm;

    /**
     * 리턴항목ID(URL PATH)
     */
    private String returnIdFroUrlPath;

    public String getSysTpNm() {
        return sysTpNm;
    }

    public void setSysTpNm(String sysTpNm) {
        this.sysTpNm = sysTpNm;
    }

    public String getSysTpCd() {
        return sysTpCd;
    }

    public void setSysTpCd(String sysTpCd) {
        this.sysTpCd = sysTpCd;
    }

    public String getViewPathUrl() {
        return viewPathUrl;
    }

    public void setViewPathUrl(String viewPathUrl) {
        this.viewPathUrl = viewPathUrl;
    }

    public String getProgId() {
        return progId;
    }

    public void setProgId(String progId) {
        this.progId = progId;
    }

    public String getProgNm() {
        return progNm;
    }

    public void setProgNm(String progNm) {
        this.progNm = progNm;
    }

    public String getLangTp() {
        return langTp;
    }

    public void setLangTp(String langTp) {
        this.langTp = langTp;
    }

    public String getReturnIdFroUrlCd() {
        return returnIdFroUrlCd;
    }

    public void setReturnIdFroUrlCd(String returnIdFroUrlCd) {
        this.returnIdFroUrlCd = returnIdFroUrlCd;
    }

    public String getReturnIdFroUrlNm() {
        return returnIdFroUrlNm;
    }

    public void setReturnIdFroUrlNm(String returnIdFroUrlNm) {
        this.returnIdFroUrlNm = returnIdFroUrlNm;
    }

    public String getReturnIdFroUrlPath() {
        return returnIdFroUrlPath;
    }

    public void setReturnIdFroUrlPath(String returnIdFroUrlPath) {
        this.returnIdFroUrlPath = returnIdFroUrlPath;
    }

}
package com.ssolution.admin.system.domain.manage;

import java.util.Date;

import com.ssolution.admin.system.domain.common.paging.PagingValueVO;

/**
 * <PRE>
 * 1. FileName	: GrpReqMngVO.java
 * 2. Package	: com.ssolution.admin.system.domain.manage
 * 3. Comment	: 사용자 그룹 등록관리 VO
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:37:21
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class GrpReqMngVO extends PagingValueVO {

    private String reqId; // 요청 ID
    private String reqDt; // 요청일자
    private String reqUserId; // 요청 사용자ID
    private String reqUserNm; // 요청 사용자 이름
    private Date reqDate; // 요청 일시
    private String reqCt; // 요청 내용
    private String reqStat; // 요청 상태
    private String reqStatNm; // 요청 상태 이름
    private String procDt; // 처리 일자
    private String procUserId; // 처리 사용자ID
    private String procUserNm; // 처리 사용자 이름
    private Date procDate; // 처리 일시
    private String procCt; // 처리 내용
    private String inptMenuId; // 입력 메뉴ID
    private String regrId; // 등록자ID
    private Date regDate; // 등록일시
    private String chgrId; // 수정자ID
    private Date chgDate; // 수정일시

    private String condReqStat; // 검색어 요청상태
    private String condReqUserId; // 검색어 요청자 ID

    public String getReqStatNm() {
        return reqStatNm;
    }

    public void setReqStatNm(String reqStatNm) {
        this.reqStatNm = reqStatNm;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getReqDt() {
        return reqDt;
    }

    public void setReqDt(String reqDt) {
        this.reqDt = reqDt;
    }

    public String getReqUserId() {
        return reqUserId;
    }

    public void setReqUserId(String reqUserId) {
        this.reqUserId = reqUserId;
    }

    public Date getReqDate() {
        return reqDate;
    }

    public void setReqDate(Date reqDate) {
        this.reqDate = reqDate;
    }

    public String getReqCt() {
        return reqCt;
    }

    public void setReqCt(String reqCt) {
        this.reqCt = reqCt;
    }

    public String getReqStat() {
        return reqStat;
    }

    public void setReqStat(String reqStat) {
        this.reqStat = reqStat;
    }

    public String getProcDt() {
        return procDt;
    }

    public void setProcDt(String procDt) {
        this.procDt = procDt;
    }

    public String getProcUserId() {
        return procUserId;
    }

    public void setProcUserId(String procUserId) {
        this.procUserId = procUserId;
    }

    public Date getProcDate() {
        return procDate;
    }

    public void setProcDate(Date procDate) {
        this.procDate = procDate;
    }

    public String getProcCt() {
        return procCt;
    }

    public void setProcCt(String procCt) {
        this.procCt = procCt;
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

    public Date getChgDate() {
        return chgDate;
    }

    public void setChgDate(Date chgDate) {
        this.chgDate = chgDate;
    }

    public String getCondReqStat() {
        return condReqStat;
    }

    public void setCondReqStat(String condReqStat) {
        this.condReqStat = condReqStat;
    }

    public String getCondReqUserId() {
        return condReqUserId;
    }

    public void setCondReqUserId(String condReqUserId) {
        this.condReqUserId = condReqUserId;
    }

    public String getReqUserNm() {
        return reqUserNm;
    }

    public void setReqUserNm(String reqUserNm) {
        this.reqUserNm = reqUserNm;
    }

    public String getProcUserNm() {
        return procUserNm;
    }

    public void setProcUserNm(String procUserNm) {
        this.procUserNm = procUserNm;
    }

    @Override
    public String toString() {
        return "GrpReqMngVO [reqId=" + reqId +
               ", reqDt=" +
               reqDt +
               ", reqUserId=" +
               reqUserId +
               ", reqUserNm=" +
               reqUserNm +
               ", reqDate=" +
               reqDate +
               ", reqCt=" +
               reqCt +
               ", reqStat=" +
               reqStat +
               ", reqStatNm=" +
               reqStatNm +
               ", procDt=" +
               procDt +
               ", procUserId=" +
               procUserId +
               ", procUserNm=" +
               procUserNm +
               ", procDate=" +
               procDate +
               ", procCt=" +
               procCt +
               ", inptMenuId=" +
               inptMenuId +
               ", regrId=" +
               regrId +
               ", regDate=" +
               regDate +
               ", chgrId=" +
               chgrId +
               ", chgDate=" +
               chgDate +
               ", condReqStat=" +
               condReqStat +
               ", condReqUserId=" +
               condReqUserId +
               "]";
    }

}

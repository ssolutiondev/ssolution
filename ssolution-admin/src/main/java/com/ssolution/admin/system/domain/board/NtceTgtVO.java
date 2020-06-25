package com.ssolution.admin.system.domain.board;

import java.util.Date;
import java.util.List;

/**
 * <PRE>
 * 1. FileName	: NtceTgtVO.java
 * 2. Package	: com.ssolution.admin.system.domain.board
 * 3. Comment	: 게시글 타겟 VO
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 9:57:55
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class NtceTgtVO {
    private int ntceId; // 공지게시판 SEQ
    private String tgtTp; // 대상 타입
    private String tgtId; // 대상 ID
    private String tgtIdAa; // 대상 그룹 ID
    private String tgtIdBb; // 대상 직책 ID
    private String inptMenuId; // 게시판 메뉴 ID
    private String regrId; // 등록자
    private Date regDate; // 등록일
    private String chgrId; // 수정자
    private Date chgId; // 수정일

    private List<NtceTgtVO> tgtList; //대상 타입 리스트

    public int getNtceId() {
        return ntceId;
    }

    public void setNtceId(int ntceId) {
        this.ntceId = ntceId;
    }

    public String getTgtTp() {
        return tgtTp;
    }

    public void setTgtTp(String tgtTp) {
        this.tgtTp = tgtTp;
    }

    public String getTgtId() {
        return tgtId;
    }

    public void setTgtId(String tgtId) {
        this.tgtId = tgtId;
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

    public Date getChgId() {
        return chgId;
    }

    public void setChgId(Date chgId) {
        this.chgId = chgId;
    }

    public List<NtceTgtVO> getTgtList() {
        return tgtList;
    }

    public void setTgtList(List<NtceTgtVO> tgtList) {
        this.tgtList = tgtList;
    }

    public String getTgtIdAa() {
        return tgtIdAa;
    }

    public void setTgtIdAa(String tgtIdAa) {
        this.tgtIdAa = tgtIdAa;
    }

    public String getTgtIdBb() {
        return tgtIdBb;
    }

    public void setTgtIdBb(String tgtIdBb) {
        this.tgtIdBb = tgtIdBb;
    }

    @Override
    public String toString() {
        return "NtceTgtVO [ntceId=" + ntceId +
               ", tgtTp=" +
               tgtTp +
               ", tgtId=" +
               tgtId +
               ", tgtIdAa=" +
               tgtIdAa +
               ", tgtIdBb=" +
               tgtIdBb +
               ", inptMenuId=" +
               inptMenuId +
               ", regrId=" +
               regrId +
               ", regDate=" +
               regDate +
               ", chgrId=" +
               chgrId +
               ", chgId=" +
               chgId +
               ", tgtList=" +
               tgtList +
               "]";
    }

}

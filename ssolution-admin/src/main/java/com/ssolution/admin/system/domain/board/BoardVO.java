package com.ssolution.admin.system.domain.board;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ssolution.admin.system.domain.common.paging.PagingValueVO;

/**
 * <PRE>
 * 1. FileName	: BoardVO.java
 * 2. Package	: com.ssolution.admin.system.domain.board
 * 3. Comment	: 게시판 VO
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 10. 오후 6:22:40
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 10.	|	신규 작성
 * </PRE>
 */
public class BoardVO extends PagingValueVO {

    private int seq; //sequence
    private int ntceId; // 게시 ID
    private String ntceTp; // 게시 유형
    private String ntceTpNm; // 게시 유형 이륾
    private String title; // 제목
    private String ntceCt; // 게시 내용
    private String ntceStrtDt; // 게시 시작일
    private String ntceEndDt; // 게시 종료일
    private int readCnt; // 조회수
    private String popupYn; // 팝업여부
    private String inptMenuId; // 입력 메뉴ID
    private String regrId; // 등록자ID
    private Date regDate; // 등록일시
    private String chgrId; // 수정자ID
    private Date chgDate; // 수정일시
    private String chgrNm; // 수정자 이름
    private String regrNm; // 등록자 이름

    private String tgtId; // 대상 ID
    private String tgtIdAa; // 대상 그룹 ID
    private String tgtIdBb; // 대상 회원 유형 ID
    private String tgtTp; // 대상 타입

    private String condTitle; // 검색어 제목

    private String ntceOrd; // 파일 순서

    private NtceTgtVO ntceTgtVO; //공지 대상 VO
    private FileVO fileVO = new FileVO(); // 파일 VO
    private List<FileVO> fileList = new ArrayList<FileVO>(); // 파일 리스트

    public String getNtceTpNm() {
        return ntceTpNm;
    }

    public void setNtceTpNm(String ntceTpNm) {
        this.ntceTpNm = ntceTpNm;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getReadCnt() {
        return readCnt;
    }

    public void setReadCnt(int readCnt) {
        this.readCnt = readCnt;
    }

    public int getNtceId() {
        return ntceId;
    }

    public void setNtceId(int ntceId) {
        this.ntceId = ntceId;
    }

    public String getNtceTp() {
        return ntceTp;
    }

    public void setNtceTp(String ntceTp) {
        this.ntceTp = ntceTp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNtceCt() {
        return ntceCt;
    }

    public void setNtceCt(String ntceCt) {
        this.ntceCt = ntceCt;
    }

    public String getNtceStrtDt() {
        return ntceStrtDt;
    }

    public void setNtceStrtDt(String ntceStrtDt) {
        this.ntceStrtDt = ntceStrtDt;
    }

    public String getNtceEndDt() {
        return ntceEndDt;
    }

    public void setNtceEndDt(String ntceEndDt) {
        this.ntceEndDt = ntceEndDt;
    }

    public String getPopupYn() {
        return popupYn;
    }

    public void setPopupYn(String popupYn) {
        this.popupYn = popupYn;
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

    public String getChgrNm() {
        return chgrNm;
    }

    public void setChgrNm(String chgrNm) {
        this.chgrNm = chgrNm;
    }

    public String getRegrNm() {
        return regrNm;
    }

    public void setRegrNm(String regrNm) {
        this.regrNm = regrNm;
    }

    public String getCondTitle() {
        return condTitle;
    }

    public void setCondTitle(String condTitle) {
        this.condTitle = condTitle;
    }

    public String getTgtId() {
        return tgtId;
    }

    public void setTgtId(String tgtId) {
        this.tgtId = tgtId;
    }

    public String getTgtTp() {
        return tgtTp;
    }

    public void setTgtTp(String tgtTp) {
        this.tgtTp = tgtTp;
    }

    public FileVO getFileVO() {
        return fileVO;
    }

    public void setFileVO(FileVO fileVO) {
        this.fileVO = fileVO;
    }

    public NtceTgtVO getNtceTgtVO() {
        return ntceTgtVO;
    }

    public void setNtceTgtVO(NtceTgtVO ntceTgtVO) {
        this.ntceTgtVO = ntceTgtVO;
    }

    public String getTgtIdBb() {
        return tgtIdBb;
    }

    public void setTgtIdBb(String tgtIdBb) {
        this.tgtIdBb = tgtIdBb;
    }

    public String getTgtIdAa() {
        return tgtIdAa;
    }

    public void setTgtIdAa(String tgtIdAa) {
        this.tgtIdAa = tgtIdAa;
    }

    public List<FileVO> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileVO> fileList) {
        this.fileList = fileList;
    }

    public String getNtceOrd() {
        return ntceOrd;
    }

    public void setNtceOrd(String ntceOrd) {
        this.ntceOrd = ntceOrd;
    }

    @Override
    public String toString() {
        return "NoticeVO [seq=" + seq +
               ", ntceId=" +
               ntceId +
               ", ntceTp=" +
               ntceTp +
               ", ntceTpNm=" +
               ntceTpNm +
               ", title=" +
               title +
               ", ntceCt=" +
               ntceCt +
               ", ntceStrtDt=" +
               ntceStrtDt +
               ", ntceEndDt=" +
               ntceEndDt +
               ", readCnt=" +
               readCnt +
               ", popupYn=" +
               popupYn +
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
               ", chgrNm=" +
               chgrNm +
               ", regrNm=" +
               regrNm +
               ", tgtId=" +
               tgtId +
               ", tgtIdAa=" +
               tgtIdAa +
               ", tgtIdBb=" +
               tgtIdBb +
               ", tgtTp=" +
               tgtTp +
               ", condTitle=" +
               condTitle +
               ", ntceOrd=" +
               ntceOrd +
               ", ntceTgtVO=" +
               ntceTgtVO +
               ", fileVO=" +
               fileVO +
               ", fileList=" +
               fileList +
               "]";
    }

}

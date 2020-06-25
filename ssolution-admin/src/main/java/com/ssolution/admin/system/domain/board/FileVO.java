package com.ssolution.admin.system.domain.board;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <PRE>
 * 1. FileName	: FileVO.java
 * 2. Package	: com.ssolution.admin.system.domain.board
 * 3. Comment	: 파일 VO
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 10. 오후 6:22:58
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 10.	|	신규 작성
 * </PRE>
 */
public class FileVO {
    private int ntceId;
    private int ntceOrd;
    private String filePath;
    private String fileNm;
    private String uuid;
    private String inptMenuId;
    private String regrId;
    private Date regDate;
    private String chgrId;
    private Date chgrDate;

    private int totalFileCount;

    private List<FileVO> fileList = new ArrayList<FileVO>();

    public FileVO() {
        super();
    }

    public FileVO(int ntceOrd, String filePath, String fileNm, String uuid) {
        super();
        this.ntceOrd = ntceOrd;
        this.filePath = filePath;
        this.fileNm = fileNm;
        this.uuid = uuid;
    }

    public FileVO(int ntceId,
                  int ntceOrd,
                  String filePath,
                  String fileNm,
                  String uuid,
                  String inptMenuId,
                  String regrId,
                  Date regDate,
                  String chgrId,
                  Date chgrDate) {
        super();
        this.ntceId = ntceId;
        this.ntceOrd = ntceOrd;
        this.filePath = filePath;
        this.fileNm = fileNm;
        this.uuid = uuid;
        this.inptMenuId = inptMenuId;
        this.regrId = regrId;
        this.regDate = regDate;
        this.chgrId = chgrId;
        this.chgrDate = chgrDate;
    }

    public int getNtceId() {
        return ntceId;
    }

    public void setNtceId(int ntceId) {
        this.ntceId = ntceId;
    }

    public int getNtceOrd() {
        return ntceOrd;
    }

    public void setNtceOrd(int ntceOrd) {
        this.ntceOrd = ntceOrd;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileNm() {
        return fileNm;
    }

    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public Date getChgrDate() {
        return chgrDate;
    }

    public void setChgrDate(Date chgrDate) {
        this.chgrDate = chgrDate;
    }

    public List<FileVO> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileVO> fileList) {
        this.fileList = fileList;
    }

    public int getTotalFileCount() {
        return totalFileCount;
    }

    public void setTotalFileCount(int totalFileCount) {
        this.totalFileCount = totalFileCount;
    }

    @Override
    public String toString() {
        return "FileVO [ntceId=" + ntceId +
               ", ntceOrd=" +
               ntceOrd +
               ", filePath=" +
               filePath +
               ", fileNm=" +
               fileNm +
               ", uuid=" +
               uuid +
               ", inptMenuId=" +
               inptMenuId +
               ", regrId=" +
               regrId +
               ", regDate=" +
               regDate +
               ", chgrId=" +
               chgrId +
               ", chgrDate=" +
               chgrDate +
               ", totalFileCount=" +
               totalFileCount +
               ", fileList=" +
               fileList +
               "]";
    }

}

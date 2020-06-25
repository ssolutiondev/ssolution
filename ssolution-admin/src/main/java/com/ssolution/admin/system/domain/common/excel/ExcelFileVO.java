package com.ssolution.admin.system.domain.common.excel;

import java.util.List;

/**
 * <PRE>
 * 1. FileName	: ExcelFileVO.java
 * 2. Package	: com.ssolution.admin.system.domain.common.excel
 * 3. Comment	: 엑셀 출력용 File VO
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:02:01
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class ExcelFileVO {

    /**
     * File 명
     */
    private String fileName;

    /**
     * Sheet Count
     */
    private int sheetCount;

    /**
     * Sheet Lists
     */
    private List<ExcelSheetVO> sheetList;

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the sheetCount
     */
    public int getSheetCount() {
        return sheetCount;
    }

    /**
     * @param sheetCount the sheetCount to set
     */
    public void setSheetCount(int sheetCount) {
        this.sheetCount = sheetCount;
    }

    /**
     * @return the sheetList
     */
    public List<ExcelSheetVO> getSheetList() {
        return sheetList;
    }

    /**
     * @param sheetList the sheetList to set
     */
    public void setSheetList(List<ExcelSheetVO> sheetList) {
        this.sheetList = sheetList;
    }

}
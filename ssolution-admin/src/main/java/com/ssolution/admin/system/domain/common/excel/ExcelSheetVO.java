package com.ssolution.admin.system.domain.common.excel;

import java.util.List;

/**
 * <PRE>
 * 1. FileName	: ExcelSheetVO.java
 * 2. Package	: com.ssolution.admin.system.domain.common.excel
 * 3. Comment	: 엑셀 출력용 Sheet VO
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:02:24
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class ExcelSheetVO {
    /**
     * Sheet Name
     */
    private String sheetName;

    /**
     * Header Title Name List
     */
    private List<ExcelColumnVO> titleList;

    /**
     * Data List
     */
    private List<ExcelRowVO> dataList;

    /**
     * @return the sheetName
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * @param sheetName the sheetName to set
     */
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    /**
     * @return the titleList
     */
    public List<ExcelColumnVO> getTitleList() {
        return titleList;
    }

    /**
     * @param titleList the titleList to set
     */
    public void setTitleList(List<ExcelColumnVO> titleList) {
        this.titleList = titleList;
    }

    /**
     * @return the dataList
     */
    public List<ExcelRowVO> getDataList() {
        return dataList;
    }

    /**
     * @param dataList the dataList to set
     */
    public void setDataList(List<ExcelRowVO> dataList) {
        this.dataList = dataList;
    }

}

package com.ssolution.admin.system.domain.common.excel;

import java.util.Map;

/**
 * <PRE>
 * 1. FileName	: ExcelRowVO.java
 * 2. Package	: com.ssolution.admin.system.domain.common.excel
 * 3. Comment	: 엑셀 출력용 Row VO
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:02:15
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class ExcelRowVO {
    /**
     * 한 Row Map data
     */
    private Map<String, ExcelCellVO> rowData;

    /**
     * 생성자
     *
     * @category constructor
     */
    public ExcelRowVO() {

    }

    /**
     * 생성자
     *
     * @category constructor
     * @param rowData row를 구성하는 컬럼목록
     */
    public ExcelRowVO(Map<String, ExcelCellVO> rowData) {
        this.rowData = rowData;
    }

    /**
     * @category accessor
     * @return the rowData
     */
    public Map<String, ExcelCellVO> getRowData() {
        return rowData;
    }

    /**
     * @category accessor
     * @param rowData the rowData to set
     */
    public void setRowData(Map<String, ExcelCellVO> rowData) {
        this.rowData = rowData;
    }

    public ExcelCellVO getCell(String key) {
        return this.rowData.get(key);
    }

}

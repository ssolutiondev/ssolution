package com.ssolution.admin.system.domain.common.excel;

import java.util.Date;

/**
 * <PRE>
 * 1. FileName	: ExcelCellVO.java
 * 2. Package	: com.ssolution.admin.system.domain.common.excel
 * 3. Comment	: 엑셀 출력용 Cell VO
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:01:42
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class ExcelCellVO {

    /**
     * 출력값(String or Date)
     */
    private Object value;

    /**
     * 생성자
     * 
     * @category constructor
     */
    public ExcelCellVO() {

    }

    /**
     * 생성자
     *
     * @category constructor
     * @param value 셀에 설정할 값
     */
    public ExcelCellVO(Object value) {
        this.value = value;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        if (value instanceof String) {
            return "null".equals(value) ? "" : value;
        } else if (value instanceof Date) {
            return value;
        } else {
            return "null".equals(value) ? "" : value;
        }
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        if (value instanceof String) {
            this.value = String.valueOf(value);
        } else if (value instanceof Date) {
            this.value = value;
        } else {
            this.value = String.valueOf(value);
        }
    }
}

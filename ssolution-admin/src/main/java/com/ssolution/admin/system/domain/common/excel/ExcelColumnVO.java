package com.ssolution.admin.system.domain.common.excel;

import org.apache.poi.ss.usermodel.CellStyle;

import com.ssolution.admin.system.consts.ExcelConstant;

/**
 * <PRE>
 * 1. FileName	: ExcelColumnVO.java
 * 2. Package	: com.ssolution.admin.system.domain.common.excel
 * 3. Comment	: 엑셀 출력용 컬럼 타이블 VO
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:01:54
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class ExcelColumnVO {
    /**
     * Size
     */
    private int columnSize;
    /**
     * 타이틀
     */
    private String title;

    /**
     * Key
     */
    private String key;

    /**
     * Cell Style
     */
    private CellStyle style;

    /**
     * 출력 타입
     */
    private ExcelConstant.ExcelFormatType type;

    public ExcelColumnVO(int columnSize, String title, String key, ExcelConstant.ExcelFormatType type) {
        super();
        this.columnSize = columnSize;
        this.title = title;
        this.key = key;
        this.type = type;

    }

    /**
     * @return the columnSize
     */
    public int getColumnSize() {
        return columnSize;
    }

    /**
     * @param columnSize the columnSize to set
     */
    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the style
     */
    public CellStyle getStyle() {
        return style;
    }

    /**
     * @param style the style to set
     */
    public void setStyle(CellStyle style) {
        this.style = style;
    }

    /**
     * @return the type
     */
    public ExcelConstant.ExcelFormatType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(ExcelConstant.ExcelFormatType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ExcelColumnVO [columnSize=");
        builder.append(columnSize);
        builder.append(", title=");
        builder.append(title);
        builder.append(", key=");
        builder.append(key);
        builder.append(", style=");
        builder.append(style);
        builder.append(", type=");
        builder.append(type);
        builder.append("]");
        return builder.toString();
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + columnSize;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((style == null) ? 0 : style.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ExcelColumnVO other = (ExcelColumnVO) obj;
        if (columnSize != other.columnSize)
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (style == null) {
            if (other.style != null)
                return false;
        } else if (!style.equals(other.style))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

}

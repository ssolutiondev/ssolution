package com.ssolution.admin.system.consts;

public abstract class ExcelConstant {

	public static final String KEY = "excelFile";

	public static final String XLS = "xls";
	public static final String XLSX = "xlsx";
	public static final String XLSX_STREAM = "xlsx-stream";


	/** The content type for an Excel response */
	private static final String CONTENT_TYPE = "application/vnd.ms-excel";

	public static enum ExcelFormatType {
		STRING,
		NUMBER_WITH_COMMA_POINT,
		NUMBER_WITH_COMMA,
		NUMBER,
		DATE,
		STRING_LEFT,
		STRING_DATE_YYYYMMDD,
		STRING_DATE_YYYYMM,
		STRING_DATE_YYYYMMDDHH24MISS,
		STRING_TEL_NO,
		STRING_BIZ_NO
	}
}

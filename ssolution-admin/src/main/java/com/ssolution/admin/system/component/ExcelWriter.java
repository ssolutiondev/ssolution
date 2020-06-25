package com.ssolution.admin.system.component;

import com.ssolution.admin.system.util.StringUtil;
import com.ssolution.admin.system.consts.ExcelConstant;
import com.ssolution.admin.system.domain.common.excel.ExcelCellVO;
import com.ssolution.admin.system.domain.common.excel.ExcelColumnVO;
import com.ssolution.admin.system.domain.common.excel.ExcelFileVO;
import com.ssolution.admin.system.domain.common.excel.ExcelRowVO;
import com.ssolution.admin.system.domain.common.excel.ExcelSheetVO;
import com.ssolution.admin.system.util.DateUtil;
import com.ssolution.admin.system.util.DownloadUtil;
import com.ssolution.admin.system.util.FormatterUtil;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.DateFormatConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ExcelWriter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private Workbook workbook;
	private Map<String, Object> model;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public ExcelWriter(Workbook workbook, Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		this.workbook = workbook;
		this.model = model;
		this.request = request;
		this.response = response;
	}

	public void create() throws Exception {
		ExcelFileVO excelDataFile = (ExcelFileVO) model.get(ExcelConstant.KEY);
		// 파일명을 설정 한다.
		String fileName = excelDataFile.getFileName() + "_" + DateUtil.getDateStringYYYYMMDDHH24MISS(0) + "." + ExcelConstant.XLSX;
		String downloadFileName = DownloadUtil.getDisposition(fileName, DownloadUtil.getBrowser(request));
		response.setHeader("Content-Disposition", "attachment; filename=\"" + downloadFileName+ "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");


		// 로케일 세팅
		/**
		 * Local Set
		 */
		String sessionCountry = (String) request.getSession().getAttribute("sessionCountry");
		String sessionLanguage = (String) request.getSession().getAttribute("sessionLanguage");
		Locale locale = new Locale(sessionLanguage, sessionCountry);
		String excelFormatPattern = DateFormatConverter.convert(locale, "yyyy-MM-dd hh:mm:ss");

		/**
		 * Sheet수 만큼 페이지 작성
		 */
		List<ExcelSheetVO> sheetList = excelDataFile.getSheetList();
		for (int i = 0; i < sheetList.size(); i++) {
			ExcelSheetVO sheetData = sheetList.get(i);
			Sheet sheet = createSheet(sheetData.getSheetName(), i);
			/**
			 * Sheet의 컬럼 타이틀 작성
			 */
			createColumnLabel(sheet, workbook, sheetData.getTitleList());
			/**
			 * Row Data 작성
			 */

//			String pathInfo = request.getPathInfo();
//			boolean isExcluded = false;
//			for (String url : maskService.getExcludedList()) {
//				if (pathInfo.startsWith(url)) {
//					isExcluded = true;
//					break;
//				}
//			}
//
//			if (!isExcluded) {
//				maskService.processExcelMask(su.getSoId(), isUnMask, sheetData.getDataList());
//			}

			createPageRow(sheet, sheetData.getDataList(), sheetData.getTitleList(), workbook, excelFormatPattern);
		}
	}

	private Sheet createSheet(String sheetName, int sheetIndex) {
		Sheet sheet = workbook.createSheet();
		workbook.setSheetName(sheetIndex, sheetName);
		return sheet;
	}

	private void createColumnLabel(Sheet sheet, Workbook workbook, List<ExcelColumnVO> columnList) {

		// 헤더의 컬럼 스타일을 지정 한다.
		CellStyle cellStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("ARIAL");
		font.setBold(true);
		font.setFontHeightInPoints((short) 8);
		font.setColor(IndexedColors.BLACK.getIndex());
		cellStyle.setFont(font);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		getCellStyle(cellStyle);

		Row firstRow = sheet.createRow(0);
		short width = 265;

		// 선언된 헤더 컬럼 만큽 내용을 세팅 한다.
		for (int i = 0; i < columnList.size(); i++) {
			ExcelColumnVO column = columnList.get(i);
			Cell cell = firstRow.createCell(i);
			if (column.getColumnSize() > 0) {
				sheet.setColumnWidth(i, (column.getColumnSize() * width));
			}
			cell.setCellValue(column.getTitle());
			cell.setCellStyle(cellStyle);
		}
	}

	private void getCellStyle(CellStyle cellStyle) {
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setTopBorderColor(IndexedColors.AUTOMATIC.getIndex());
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setLeftBorderColor(IndexedColors.AUTOMATIC.getIndex());
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setRightBorderColor(IndexedColors.AUTOMATIC.getIndex());
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBottomBorderColor(IndexedColors.AUTOMATIC.getIndex());
	}

	@SuppressWarnings("Duplicates")
	private void createPageRow(Sheet sheet,
							   List<ExcelRowVO> dataList,
							   List<ExcelColumnVO> columnList,
							   Workbook workbook,
							   String excelFormatPattern) {
		Font font = workbook.createFont();
		font.setFontName("ARIAL");
		font.setFontHeightInPoints((short) 8);

		DataFormat dataformat = workbook.createDataFormat();

		if (dataList != null && dataList.size() > 0) {
			for (int i = 0; i <= dataList.size() - 1; i++) {
				Row row = sheet.createRow(i + 1);
				ExcelRowVO rowdata = dataList.get(i);

				for (int j = 0; j < columnList.size(); j++) {
					ExcelColumnVO column = columnList.get(j);
					ExcelCellVO cellData = rowdata.getCell(column.getKey());
					Cell cell = row.createCell(j);

					if (i == 0) { //첫번째 Row일때만 스타일 생성
						CellStyle cellStyle =  workbook.createCellStyle();
						cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
						getCellStyle(cellStyle);
						cellStyle.setFont(font);


						Object value = cellData.getValue();
						if (column.getType() == ExcelConstant.ExcelFormatType.STRING) {
							cellStyle.setAlignment(HorizontalAlignment.CENTER);
							cell.setCellValue(value == null ? "" : (String) value);
						} else if (column.getType() == ExcelConstant.ExcelFormatType.STRING_LEFT) {
							cellStyle.setAlignment(HorizontalAlignment.LEFT);
							cell.setCellValue(value == null ? "" : (String) value);
						} else if (column.getType() == ExcelConstant.ExcelFormatType.NUMBER) {
							if (value == null || StringUtil.isEmpty((String) value)) {
								value = "0";
							}
							cellStyle.setAlignment(HorizontalAlignment.RIGHT);
							cellStyle.setDataFormat(dataformat.getFormat("0"));
							double d = Double.parseDouble((String) value);
							cell.setCellValue(d);
						} else if (column.getType() == ExcelConstant.ExcelFormatType.NUMBER_WITH_COMMA) {
							if (value == null || StringUtil.isEmpty((String) value)) {
								value = "0";
							}
							cellStyle.setAlignment(HorizontalAlignment.RIGHT);
							cellStyle.setDataFormat(dataformat.getFormat("#,##0"));
							double d = Double.parseDouble((String) value);
							cell.setCellValue(d);
						} else if (column.getType() == ExcelConstant.ExcelFormatType.NUMBER_WITH_COMMA_POINT) {
							if (value == null || StringUtil.isEmpty((String) value)) {
								value = "0";
							}

							cellStyle.setAlignment(HorizontalAlignment.RIGHT);
							cellStyle.setDataFormat(dataformat.getFormat("#,##0.00"));
							double d = Double.parseDouble((String) value);
							cell.setCellValue(d);
						} else if (column.getType() == ExcelConstant.ExcelFormatType.DATE) {
							cellStyle.setAlignment(HorizontalAlignment.CENTER);
							cellStyle.setDataFormat(dataformat.getFormat(excelFormatPattern));
							if (value == null) {
								cell.setCellValue("");
							} else {

								if (value instanceof Date)
									cell.setCellValue((Date) value);
								else
									cell.setCellValue((String) value);
							}

						} else if (column.getType() == ExcelConstant.ExcelFormatType.STRING_DATE_YYYYMMDD) {
							SimpleDateFormat oldFormat = new SimpleDateFormat("yyyyMMdd");
							SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
							cellStyle.setAlignment(HorizontalAlignment.CENTER);
							if (value == null || StringUtil.isEmpty((String) value)) {
								cell.setCellValue("");
							} else {
								try {
									cell.setCellValue(newFormat.format(oldFormat.parse((String) value)));
								} catch (ParseException e) {
									cell.setCellValue((String) value);
								}
							}
						} else if (column.getType() == ExcelConstant.ExcelFormatType.STRING_DATE_YYYYMM) {
							SimpleDateFormat oldFormat = new SimpleDateFormat("yyyyMM");
							SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM");
							if (value == null || StringUtil.isEmpty((String) value)) {
								cell.setCellValue("");
							} else {
								try {
									cell.setCellValue(newFormat.format(oldFormat.parse((String) value)));
								} catch (ParseException e) {
									cell.setCellValue((String) value);
								}
							}
						} else if (column.getType() == ExcelConstant.ExcelFormatType.STRING_DATE_YYYYMMDDHH24MISS) {
							SimpleDateFormat oldFormat = new SimpleDateFormat("yyyyMMddHHmmss");
							SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							cellStyle.setAlignment(HorizontalAlignment.CENTER);
							if (value == null || StringUtil.isEmpty((String) value)) {
								cell.setCellValue("");
							} else {
								try {
									cell.setCellValue(newFormat.format(oldFormat.parse((String) value)));
								} catch (ParseException e) {
									cell.setCellValue((String) value);
								}
							}
						} else if (column.getType() == ExcelConstant.ExcelFormatType.STRING_TEL_NO) {
							cellStyle.setAlignment(HorizontalAlignment.CENTER);
							if (value == null || StringUtil.isEmpty((String) value)) {
								cell.setCellValue("");
							} else {
								// 전화번호
								cell.setCellValue(FormatterUtil.formattTelNo(cellData.getValue().toString()));
							}
						} else if (column.getType() == ExcelConstant.ExcelFormatType.STRING_BIZ_NO) {
							if (value == null || StringUtil.isEmpty((String) value)) {
								cell.setCellValue("");
							} else {
								// 사업자 번호
								cellStyle.setAlignment(HorizontalAlignment.CENTER);
								cell.setCellValue(FormatterUtil.formattBizNo(cellData.getValue().toString()));
							}

						} else {
							cellStyle.setAlignment(HorizontalAlignment.CENTER);
							cell.setCellValue((String) value);
						}
						column.setStyle(cellStyle);
					} else {
						Object value = cellData.getValue();

						if (column.getType() == ExcelConstant.ExcelFormatType.STRING) {
							cell.setCellValue(value == null ? "" : (String) value);
						} else if (column.getType() == ExcelConstant.ExcelFormatType.STRING_LEFT) {
							cell.setCellValue(value == null ? "" : (String) value);
						} else if (column.getType() == ExcelConstant.ExcelFormatType.NUMBER) {
							if (value == null || StringUtil.isEmpty((String) value)) {
								value = "0";
							}
							double d = Double.parseDouble((String) value);
							cell.setCellValue(d);
						} else if (column.getType() == ExcelConstant.ExcelFormatType.NUMBER_WITH_COMMA) {
							if (value == null || StringUtil.isEmpty((String) value)) {
								value = "0";
							}
							double d = Double.parseDouble((String) value);
							cell.setCellValue(d);
						} else if (column.getType() == ExcelConstant.ExcelFormatType.NUMBER_WITH_COMMA_POINT) {
							if (value == null || StringUtil.isEmpty((String) value)) {
								value = "0";
							}
							double d = Double.parseDouble((String) value);
							cell.setCellValue(d);
						} else if (column.getType() == ExcelConstant.ExcelFormatType.DATE) {
							if (value == null) {
								cell.setCellValue("");
							} else {

								if (value instanceof Date)
									cell.setCellValue((Date) value);
								else
									cell.setCellValue((String) value);
							}
						} else if (column.getType() == ExcelConstant.ExcelFormatType.STRING_DATE_YYYYMMDD) {
							SimpleDateFormat oldFormat = new SimpleDateFormat("yyyyMMdd");
							SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
							if (value == null || StringUtil.isEmpty((String) value)) {
								cell.setCellValue("");
							} else {
								try {
									cell.setCellValue(newFormat.format(oldFormat.parse((String) value)));
								} catch (ParseException e) {
									cell.setCellValue((String) value);
								}
							}
						} else if (column.getType() == ExcelConstant.ExcelFormatType.STRING_DATE_YYYYMM) {
							SimpleDateFormat oldFormat = new SimpleDateFormat("yyyyMM");
							SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM");
							if (value == null || StringUtil.isEmpty((String) value)) {
								cell.setCellValue("");
							} else {
								try {
									cell.setCellValue(newFormat.format(oldFormat.parse((String) value)));
								} catch (ParseException e) {
									cell.setCellValue((String) value);
								}
							}
						} else if (column.getType() == ExcelConstant.ExcelFormatType.STRING_DATE_YYYYMMDDHH24MISS) {
							SimpleDateFormat oldFormat = new SimpleDateFormat("yyyyMMddHHmmss");
							SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							if (value == null || StringUtil.isEmpty((String) value)) {
								cell.setCellValue("");
							} else {
								try {
									cell.setCellValue(newFormat.format(oldFormat.parse((String) value)));
								} catch (ParseException e) {
									cell.setCellValue((String) value);
								}
							}
						} else if (column.getType() == ExcelConstant.ExcelFormatType.STRING_TEL_NO) {
							if (value == null || StringUtil.isEmpty((String) value)) {
								cell.setCellValue("");
							} else {
								// 전화번호
								cell.setCellValue(FormatterUtil.formattTelNo(cellData.getValue().toString()));
							}
						} else if (column.getType() == ExcelConstant.ExcelFormatType.STRING_BIZ_NO) {
							if (value == null || StringUtil.isEmpty((String) value)) {
								cell.setCellValue("");
							} else {
								// 사업자 번호
								cell.setCellValue(FormatterUtil.formattBizNo(cellData.getValue().toString()));
							}
						} else {
							cell.setCellValue((String) value);
						}
					}
					cell.setCellStyle(column.getStyle());
				}
			}
		}
	}
}
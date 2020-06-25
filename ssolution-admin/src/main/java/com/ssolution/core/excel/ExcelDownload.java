package com.ssolution.core.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssolution.admin.system.consts.ExcelConstant;
import com.ssolution.admin.system.domain.common.excel.ExcelCellVO;
import com.ssolution.admin.system.domain.common.excel.ExcelColumnVO;
import com.ssolution.admin.system.domain.common.excel.ExcelFileVO;
import com.ssolution.admin.system.domain.common.excel.ExcelRowVO;
import com.ssolution.admin.system.domain.common.excel.ExcelSheetVO;
import com.ssolution.admin.system.domain.common.excel.ExcelVO;

public class ExcelDownload {
	
	public static ExcelFileVO getExcelData(ExcelVO vo) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> headers = mapper.readValue(vo.getHead(), new TypeReference<List<Map<String, Object>>>(){});
		List<Map<String, Object>> list = mapper.readValue(vo.getData(), new TypeReference<List<Map<String, Object>>>(){});
		String fileName = (String) vo.getFileName();
		List<ExcelColumnVO> columnList = new ArrayList<ExcelColumnVO>();
		
		/*
		 * 헤더 세팅
		 */
		for(Map<String, Object> header : headers) {
			String txt = (String)header.get("text");
			String val = (String)header.get("value");
			
			columnList.add(new ExcelColumnVO(30, txt, val, ExcelConstant.ExcelFormatType.STRING));
		}
		
        /*
         * 데이터 세팅
         */
        List<ExcelRowVO> rowList = new ArrayList<ExcelRowVO>();
        for (Map<String, Object> row : list) {
            ExcelRowVO rowVo = new ExcelRowVO();
            //Row
            Map<String, ExcelCellVO> rowMap = new HashMap<String, ExcelCellVO>();
            for (ExcelColumnVO col : columnList) {
                //Col 세팅
                ExcelCellVO cell = new ExcelCellVO();
                Object obj = row.get(col.getKey());
                cell.setValue(obj);
                rowMap.put(col.getKey(), cell);
            }
            rowVo.setRowData(rowMap);
            rowList.add(rowVo);
        }

        /*
         * Sheet 작성
         */
        List<ExcelSheetVO> shList = new ArrayList<ExcelSheetVO>();
        ExcelSheetVO sh = new ExcelSheetVO();
        sh.setSheetName(fileName);
        sh.setDataList(rowList);
        sh.setTitleList(columnList);
        shList.add(sh);

        /*
         * 파일작성
         */
        ExcelFileVO file = new ExcelFileVO();
        file.setFileName(fileName);
        file.setSheetCount(1);
        file.setSheetList(shList);
        
        return file;
	}
}

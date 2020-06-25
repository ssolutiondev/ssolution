package com.ssolution.admin.system.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssolution.admin.system.consts.ExcelConstant;
import com.ssolution.admin.system.domain.common.excel.ExcelFileVO;
import com.ssolution.admin.system.domain.common.excel.ExcelVO;
import com.ssolution.core.excel.ExcelDownload;

/**
 * <PRE>
 * 1. FileName	: ExcelDownController.java
 * 2. Package	: com.ssolution.admin.system.controller.common
 * 3. Comment	: 엑셀 관련 컨트롤러
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 10. 오후 4:36:30
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 10.	|	신규 작성
 * </PRE>
 */
@Controller
public class ExcelDownController {

	@PostMapping(value = "/usedPhone/excelDown")
	public String excelDown(Model model, ExcelVO excelVO, HttpServletRequest request) {
		ExcelFileVO vo = null;
		
		try {
			vo = ExcelDownload.getExcelData(excelVO);
			model.addAttribute(ExcelConstant.KEY, vo);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return "excelXlsxView";
	}
	
	/*
	 * EXCEL UPLOAD SAMPLE
	@ResponseBody
	@RequestMapping(value = "/usedPhone/deal/btoc/excelUpload", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	public String excelUpload(@RequestParam("excel") MultipartFile multipartFile)throws IOException, JSONException {

		JSONObject data = new JSONObject();
		
		try {
			List<Deposit> excelDspts = reader.readFileToList(multipartFile, Deposit::new);
			excelDspts.remove(0); 
			String ex = new ObjectMapper().writeValueAsString(excelDspts);
			data.put("code", "200");
			data.put("excelDspts", ex);
		} catch (InvalidFormatException e) {
			data.put("code", "999"); 
			data.put("msg", "엑셀변환 오류. 형식을 확인 또는 관리자에게 문의주세요"); 
		} 
		return data.toString();
	}
	*/
}

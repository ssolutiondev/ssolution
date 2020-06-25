package com.ssolution.admin.system.controller.sample;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <PRE>
 * 1. FileName	: SampleController.java
 * 2. Package	: com.ssolution.admin.system.controller.sample
 * 3. Comment	: 샘플 컨트롤러
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 10. 오후 6:22:19
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 10.	|	신규 작성
 * </PRE>
 */
@Controller
public class SampleController {

	/**
	 * <PRE>
	 * 1. ClassName		: SampleController
	 * 2. MethodName	: test
	 * 3. Comment		: 테스트 페이지
	 * 4. 작성자			: DEV.YKLEE
	 * 5. 작성일			: 2020. 6. 16. 오후 4:27:49
	 *	- 
	 * </PRE>
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sample/test/test", method = RequestMethod.GET)
	public String test(Model model, HttpServletRequest request) {
		return "sample/test/test";
	}
	
	/**
	 * <PRE>
	 * 1. ClassName		: SampleController
	 * 2. MethodName	: eformViewer
	 * 3. Comment		: OZ e-Form 뷰어
	 * 4. 작성자			: DEV.YKLEE
	 * 5. 작성일			: 2020. 6. 16. 오후 4:28:23
	 *	- 
	 * </PRE>
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sample/eform/viewer", method = RequestMethod.GET)
	public String eformViewer(Model model, HttpServletRequest request) {
		return "sample/eform/viewer";
	}
}

package com.ssolution.admin.system.controller.manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ssolution.admin.system.domain.manage.GrpReqMngVO;
import com.ssolution.admin.system.service.common.code.CommonDataService;
import com.ssolution.admin.system.service.manager.GrpReqMngService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * <PRE>
 * 1. FileName	: GrpReqMngController.java
 * 2. Package	: com.ssolution.admin.system.controller.manage
 * 3. Comment	: 관리자 그룹 등록 컨트롤러
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 10. 오후 4:49:05
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 10.	|	신규 작성
 * </PRE>
 */
@Controller
@RequestMapping(value = "/system/authMng/grpReqMng/grpReqMng")
public class GrpReqMngController {

    private static String URL = "system/authMng/grpReqMng/grpReqMng";

    /**
     * Logger
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 사용자 권한 그룹 요청 서비스
     */
    @Autowired
    GrpReqMngService grpReqMngService;

    /**
     * 공통코드 서비스
     */
    @Autowired
    private CommonDataService commonDataService;

    
    /**
     * <PRE>
     * 1. ClassName		: GrpReqMngController
     * 2. MethodName	: grpReqMng
     * 3. Comment		: 사용자 그룹 등록 관리 메인 페이지
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 9. 오후 6:06:08
     *	- 
     * </PRE>
     * @param model
     * @param grpReqMngVO
     * @param request
     * @return
     */
    @RequestMapping(value = "grpReqMng", method = RequestMethod.POST)
    public String grpReqMng(Model model, GrpReqMngVO grpReqMngVO, HttpServletRequest request) {
        String lng = (String) request.getSession().getAttribute("sessionLanguage");
        model.addAttribute("reqStatList", commonDataService.getCommonCodeListOptionalSearch("SY00010", lng));
        model.addAttribute("reqStatListInput", commonDataService.getCommonCodeListInput("SY00010", lng));
        return URL + "/grpReqMng";
    }

    /**
     * <PRE>
     * 1. ClassName		: GrpReqMngController
     * 2. MethodName	: mainListAction
     * 3. Comment		: 사용자 그룹 등록 관리 조회
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 9. 오후 6:06:30
     *	- 
     * </PRE>
     * @param model
     * @param grpReqMngVO
     * @param request
     * @return
     */
    @RequestMapping(value = "mainListAction", method = RequestMethod.POST)
    public String mainListAction(Model model, GrpReqMngVO grpReqMngVO, HttpServletRequest request) {

        if (grpReqMngVO.getIsTablesDrawed() == false) {
            model.addAttribute("data", new ArrayList<Object>());
            return URL + "/ajax/grpReqMng";
        }

        List<GrpReqMngVO> data = grpReqMngService.getGrpReqList(grpReqMngVO);
        model.addAttribute("data", data);

        return URL + "/ajax/grpReqMng";

    }

    /**
     * <PRE>
     * 1. ClassName		: GrpReqMngController
     * 2. MethodName	: insertGrpReqInfoAction
     * 3. Comment		: 사용자 그룹 요청 등록
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 9. 오후 6:06:40
     *	- 
     * </PRE>
     * @param model
     * @param grpReqMngVO
     * @param request
     * @return
     */
    @RequestMapping(value = "insertGrpReqInfoAction", method = RequestMethod.POST)
    public String insertGrpReqInfoAction(Model model, GrpReqMngVO grpReqMngVO, HttpServletRequest request) {

        int count = grpReqMngService.insertGrpReqInfo(grpReqMngVO);
        model.addAttribute("data", count);

        return URL + "/ajax/grpReqMng";

    }

    /**
     * <PRE>
     * 1. ClassName		: GrpReqMngController
     * 2. MethodName	: updateGrpReqInfoAction
     * 3. Comment		: 사용자 그룹 요청 수정
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 9. 오후 6:06:51
     *	- 
     * </PRE>
     * @param model
     * @param grpReqMngVO
     * @param request
     * @return
     */
    @RequestMapping(value = "updateGrpReqInfoAction", method = RequestMethod.POST)
    public String updateGrpReqInfoAction(Model model, GrpReqMngVO grpReqMngVO, HttpServletRequest request) {

        int count = grpReqMngService.updateGrpReqInfo(grpReqMngVO);
        model.addAttribute("data", count);

        return URL + "/ajax/grpReqMng";

    }

}

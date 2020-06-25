package com.ssolution.admin.system.controller.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ssolution.admin.system.domain.manage.GrpMngVO;
import com.ssolution.admin.system.service.manager.GrpMngService;
import com.ssolution.core.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * <PRE>
 * 1. FileName	: GrpMngController.java
 * 2. Package	: com.ssolution.admin.system.controller.manage
 * 3. Comment	: 관리자 그룹 컨트롤러
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 10. 오후 4:38:31
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 10.	|	신규 작성
 * </PRE>
 */
@Controller
@RequestMapping(value = "/system/authMng/authMng/grpMng")
public class GrpMngController {
    /**
     * 그룹관리 메인 URL
     */
    private static String URL = "system/authMng/authMng/grpMng";

    /**
     * 그룹관리 서비스
     */
    @Autowired
    private GrpMngService grpMngService;

    
    /**
     * <PRE>
     * 1. ClassName		: GrpMngController
     * 2. MethodName	: grpMng
     * 3. Comment		: 그룹 관리 메인
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 9. 오후 4:57:28
     *	- 
     * </PRE>
     * @param model
     * @param request
     * @param grpMng
     * @return
     */
    @RequestMapping(value = "grpMng", method = RequestMethod.POST)
    public String grpMng(Model model, HttpServletRequest request, GrpMngVO grpMng) {
        model.addAttribute("grpMng", grpMng);
        return URL + "/grpMng";
    }

    /**
     * <PRE>
     * 1. ClassName		: GrpMngController
     * 2. MethodName	: getGrpListAction
     * 3. Comment		: 그룹 리스트 조회
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 9. 오후 5:40:56
     *	- 
     * </PRE>
     * @param model
     * @param request
     * @param grpMng
     * @return
     */
    @RequestMapping(value = "getGrpListAction", method = RequestMethod.POST)
    public String getGrpListAction(Model model, HttpServletRequest request, GrpMngVO grpMng) {

        if (grpMng.getIsTablesDrawed() == false) {
            model.addAttribute("grpList", new ArrayList<Object>());
            return URL + "/ajax/grpMng";
        }

        List<GrpMngVO> data = grpMngService.getGrpList(grpMng);
        model.addAttribute("grpList", data);

        return URL + "/ajax/grpMng";
    }

    /**
     * <PRE>
     * 1. ClassName		: GrpMngController
     * 2. MethodName	: getCheckGrpIdAction
     * 3. Comment		: 그룹ID 중복 체크
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 9. 오후 5:41:05
     *	- 
     * </PRE>
     * @param model
     * @param request
     * @param grpMng
     * @return
     */
    @RequestMapping(value = "getCheckGrpIdAction", method = RequestMethod.POST)
    public String getCheckGrpIdAction(Model model, HttpServletRequest request, GrpMngVO grpMng) {
        int check = grpMngService.checkGrpId(grpMng);
        model.addAttribute("check", check);
        return URL + "/ajax/grpMng";
    }

    /**
     * <PRE>
     * 1. ClassName		: GrpMngController
     * 2. MethodName	: insertGrpAction
     * 3. Comment		: 그룹 등록
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 9. 오후 5:41:28
     *	- 
     * </PRE>
     * @param model
     * @param request
     * @param grpMng
     * @return
     */
    @RequestMapping(value = "insertGrpAction", method = RequestMethod.POST)
    public String insertGrpAction(Model model, HttpServletRequest request, GrpMngVO grpMng) {

        try {
            int result = grpMngService.insertGrp(grpMng);
            model.addAttribute("result", result);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("MSG.M10.MSG00005");
        }

        return URL + "/ajax/grpMng";
    }

    /**
     * <PRE>
     * 1. ClassName		: GrpMngController
     * 2. MethodName	: updateGrpAction
     * 3. Comment		: 그룹 수정
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 9. 오후 5:42:04
     *	- 
     * </PRE>
     * @param model
     * @param request
     * @param grpMng
     * @return
     */
    @RequestMapping(value = "updateGrpAction", method = RequestMethod.POST)
    public String updateGrpAction(Model model, HttpServletRequest request, GrpMngVO grpMng) {
        int result = grpMngService.updateGrp(grpMng);
        model.addAttribute("result", result);
        return URL + "/ajax/grpMng";
    }

    /**
     * <PRE>
     * 1. ClassName		: GrpMngController
     * 2. MethodName	: deleteGrpAction
     * 3. Comment		: 그룹 삭제
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 9. 오후 5:42:35
     *	- 
     * </PRE>
     * @param model
     * @param request
     * @param grpMng
     * @return
     */
    @RequestMapping(value = "deleteGrpAction", method = RequestMethod.POST)
    public String deleteGrpAction(Model model, HttpServletRequest request, GrpMngVO grpMng) {
        int result = grpMngService.deleteGrp(grpMng);
        model.addAttribute("result", result);
        return URL + "/ajax/grpMng";
    }
}
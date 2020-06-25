package com.ssolution.admin.system.controller.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ssolution.admin.system.domain.manage.ManagerAuthMngVO;
import com.ssolution.admin.system.service.manager.ManagerAuthMngService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * <PRE>
 * 1. FileName	: ManagerMngController.java
 * 2. Package	: com.ssolution.admin.system.controller.manage
 * 3. Comment	: 관리자 관리 컨트롤러
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 10. 오후 5:25:42
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 10.	|	신규 작성
 * </PRE>
 */
@Controller
@RequestMapping(value = "/system/authMng/authMng/managerAuthMng")
public class ManagerMngController {
    /**
     * 사용자별 그룹관리 메인 URL
     */
    private static String URL = "system/authMng/authMng/managerAuthMng";

    /**
     * 사용자별 그룹관리 서비스
     */
    @Autowired
    private ManagerAuthMngService managerAuthMngService;

    
    /**
     * <PRE>
     * 1. ClassName		: ManagerAuthMngController
     * 2. MethodName	: managerAuthMng
     * 3. Comment		: 사용자별 그룹관리 메인
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 9. 오후 6:04:24
     *	- 
     * </PRE>
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "managerAuthMng", method = RequestMethod.POST)
    public String managerAuthMng(Model model, HttpServletRequest request) {
        return URL + "/managerAuthMng";
    }

    /**
     * <PRE>
     * 1. ClassName		: ManagerAuthMngController
     * 2. MethodName	: getManagerList
     * 3. Comment		: 사용자리스트 조회
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 9. 오후 6:04:40
     *	- 
     * </PRE>
     * @param model
     * @param request
     * @param managerAuth
     * @return
     */
    @RequestMapping(value = "getManagerListAction", method = RequestMethod.POST)
    public String getManagerList(Model model, HttpServletRequest request, ManagerAuthMngVO managerAuth) {
        if (managerAuth.getIsTablesDrawed() == false) {
            model.addAttribute("managerList", new ArrayList<Object>());
            return URL + "/ajax/managerAuthMng";
        }
        model.addAttribute("managerList", managerAuthMngService.getManagerList(managerAuth));
        return URL + "/ajax/managerAuthMng";
    }

    /**
     * <PRE>
     * 1. ClassName		: ManagerAuthMngController
     * 2. MethodName	: getManagerAuthList
     * 3. Comment		: 그룹별 사용자 리스트 조회
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 9. 오후 6:04:55
     *	- 
     * </PRE>
     * @param model
     * @param request
     * @param managerAuth
     * @return
     */
    @RequestMapping(value = "getManagerAuthListAction", method = RequestMethod.POST)
    public String getManagerAuthList(Model model, HttpServletRequest request, ManagerAuthMngVO managerAuth) {
        if (managerAuth.getIsTablesDrawed() == false) {
            model.addAttribute("managerAuthList", new ArrayList<Object>());
            return URL + "/ajax/managerAuthMng";
        }

        model.addAttribute("managerAuthList", managerAuthMngService.getManagerAuthList(managerAuth));
        return URL + "/ajax/managerAuthMng";
    }

    /**
     * <PRE>
     * 1. ClassName		: ManagerAuthMngController
     * 2. MethodName	: updateManagerAuth
     * 3. Comment		: 사용자별 권한그룹 수정
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 9. 오후 6:05:12
     *	- 
     * </PRE>
     * @param model
     * @param request
     * @param managerAuth
     * @return
     */
    @RequestMapping(value = "updateManagerAuthAction", method = RequestMethod.POST)
    public String updateManagerAuth(Model model, HttpServletRequest request, @RequestBody ManagerAuthMngVO managerAuth) {

        managerAuthMngService.updateManagerAuth(managerAuth);
        return URL + "/ajax/managerAuthMng";
    }
}
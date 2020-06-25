package com.ssolution.admin.system.controller.manage;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssolution.admin.system.domain.login.SessionUserVO;
import com.ssolution.admin.system.domain.manage.AuthMngVO;
import com.ssolution.admin.system.domain.manage.GrpMngVO;
import com.ssolution.admin.system.service.manager.AuthMngService;
import com.ssolution.admin.system.util.CommonUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * <PRE>
 * 1. FileName	: AuthMngController.java
 * 2. Package	: com.ssolution.admin.system.controller.manage
 * 3. Comment	: 권한 컨트롤러
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 10. 오후 4:38:04
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 10.	|	신규 작성
 * </PRE>
 */
@Controller
@RequestMapping(value = "/system/authMng/authMng/authMng")
public class AuthMngController {

    /**
     * 그룹별 권한관리 메인 URL
     */
    private static String URL = "system/authMng/authMng/authMng";

    /**
     * 그룹별 권한관리서비스
     */
    @Autowired
    AuthMngService authMngService;

    /**
     * <PRE>
     * 1. ClassName		: AuthMngController
     * 2. MethodName	: authMng
     * 3. Comment		: 그룹별 권한관리 MAIN
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 9. 오후 4:53:11
     *	- 
     * </PRE>
     * @param model
     * @param auth
     * @param request
     * @return
     */
    @RequestMapping(value = "authMng", method = RequestMethod.POST)
    public String authMng(Model model, AuthMngVO auth, HttpServletRequest request) {
        return URL + "/authMng";
    }


    /**
     * <PRE>
     * 1. ClassName		: AuthMngController
     * 2. MethodName	: getAuthGroupListAction
     * 3. Comment		: 권한 그룹 리스트 조회
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 9. 오후 4:54:56
     *	- 
     * </PRE>
     * @param model
     * @param grpMng
     * @param request
     * @return
     */
    @RequestMapping(value = "getAuthGroupListAction", method = RequestMethod.POST)
    public String getAuthGroupListAction(Model model, GrpMngVO grpMng, HttpServletRequest request) {

        if (grpMng.getIsTablesDrawed() == false) {
            model.addAttribute("data", new ArrayList<Object>());
            return URL + "/ajax/authMng";
        }

        // 권한 그룹 리스트 조회
        List<GrpMngVO> groupList = authMngService.getAuthGroupList(grpMng);
        model.addAttribute("data", groupList);

        return URL + "/ajax/authMng";
    }

    /**
     * <PRE>
     * 1. ClassName		: AuthMngController
     * 2. MethodName	: getAuthListAction
     * 3. Comment		: 권한 리스트 조회(트리)
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 9. 오후 4:55:18
     *	- 
     * </PRE>
     * @param model
     * @param request
     * @param authGrpId
     * @param condAsgnYn
     * @return
     */
    @RequestMapping(value = "getAuthListAction", method = RequestMethod.POST)
    public @ResponseBody Object getAuthListAction(Model model,
                                                  HttpServletRequest request,
                                                  String authGrpId,
                                                  String condAsgnYn) {

        String lng = (String) request.getSession().getAttribute("sessionLanguage");

        ArrayList<Object> root = new ArrayList<Object>(); // json data

        Map<String, Object> rootNode = new HashMap<String, Object>();
        rootNode.put("id", "0");
        rootNode.put("text", "/");
        rootNode.put("icon", "fa fa-folder");

        if (!StringUtils.isEmpty(authGrpId)) {

            Map<String, Object> authList = authMngService.getAuthList(authGrpId, condAsgnYn, lng);

            rootNode.put("children", authList.get("authList"));
            rootNode.put("menuSearchList", authList.get("menuSearchList"));

        }

        root.add(rootNode);

        return root;
    }

    /**
     * <PRE>
     * 1. ClassName		: AuthMngController
     * 2. MethodName	: updateAuthAction
     * 3. Comment		: 그룹 권한 수정
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 9. 오후 4:55:49
     *	- 
     * </PRE>
     * @param model
     * @param request
     * @param authMng
     * @return
     */
    @RequestMapping(value = "updateAuthAction", method = RequestMethod.POST)
    public String updateAuthAction(Model model, HttpServletRequest request, AuthMngVO authMng) {

        // 등록자 세션 유저 설정
        SessionUserVO sessionUser = CommonUtil.getSessionManager();
        authMng.setRegrId(sessionUser.getUserId());

        // 그룹별 권한 수정
        authMngService.updateAuth(authMng);

        return URL + "/ajax/authMng";
    }

}
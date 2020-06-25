package com.ssolution.admin.system.controller.common.popup;

import com.ssolution.admin.system.util.StringUtil;
import com.ssolution.admin.system.domain.common.popup.OrgSearchPopupVO;
import com.ssolution.admin.system.domain.login.SessionUserVO;
import com.ssolution.admin.system.service.common.code.CommonDataService;
import com.ssolution.admin.system.service.common.popup.OrgSearchPopupService;
import com.ssolution.admin.system.util.CommonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <PRE>
 * 1. FileName	: OrgSearchPopupController.java
 * 2. Package	: com.ssolution.admin.system.controller.common.popup
 * 3. Comment	: 조직 검색 팝업
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 10. 오전 10:58:27
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 10.	|	신규 작성
 * </PRE>
 */
@Controller
@RequestMapping(value = "/system/common/popup/orgSearchPopup")
public class OrgSearchPopupController {

    private static String URL = "system/common/popup/orgSearchPopup";

    /** the logger. */
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommonDataService commonDataService;

    @Autowired
    private OrgSearchPopupService orgSearchPopupService;

    /**
     * <PRE>
     * 1. ClassName		: OrgSearchPopupController
     * 2. MethodName	: orgSearchPopup
     * 3. Comment		: 조직 검색 화면 팝업(하위조직 검색)
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 10. 오전 10:59:00
     *	- 
     * </PRE>
     * @param model
     * @param request
     * @param orgSearch
     * @return
     */
    @RequestMapping(value = "orgSearchPopup", method = RequestMethod.POST)
    public String orgSearchPopup(Model model, HttpServletRequest request, OrgSearchPopupVO orgSearch) {

        // 등록자 세션 유저 설정
        SessionUserVO sessionUser = CommonUtil.getSessionManager();
        if (StringUtil.isEmpty(orgSearch.getSoId())) {
            // 사업ID 존재하지 않는 경우
            // 사업ID 설정
            orgSearch.setSoId(sessionUser.getSoId()); //사업ID
        }

        if (StringUtil.isEmpty(orgSearch.getAuthOrgId())) {
            // 권한조직ID 존재하지 않는 경우
            // 조직ID 설정
            orgSearch.setUserOrgId(sessionUser.getOrgId()); //조직ID
        }

        // 권한 조직 조회
        OrgSearchPopupVO orgInfo = orgSearchPopupService.getAuthOrgInfo(orgSearch);
        // 권한 조직명 설정
        orgSearch.setAuthOrgId(orgInfo.getOrgId());
        orgSearch.setAuthOrgNm(orgInfo.getOrgNm());

        String lng = (String) request.getSession().getAttribute("sessionLanguage");
        // 공통코드 조회(조직유형)
        model.addAttribute("orgTpCodeList", commonDataService.getCommonCodeListOptionalSearch("AW00001", lng));
        // 공통코드 조회(조직유형상세)
        model.addAttribute("orgTpDtlCodeList", commonDataService.getCommonCodeListOptionalSearch("AW00002", lng));

        model.addAttribute("paramOrgInfo", orgSearch);
        return URL + "/popup/orgSearchPopup";
    }

    /**
     * <PRE>
     * 1. ClassName		: OrgSearchPopupController
     * 2. MethodName	: getOrgListPopupByAuth
     * 3. Comment		: 조직 목록 조회(권한조직)
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 10. 오후 1:15:00
     *	- 
     * </PRE>
     * @param model
     * @param request
     * @param orgSearch
     * @return
     */
    @RequestMapping(value = "getOrgListPopupByAuth", method = RequestMethod.POST)
    public String getOrgListPopupByAuth(Model model, HttpServletRequest request, OrgSearchPopupVO orgSearch) {

        // 조직 목록 조회
        List<OrgSearchPopupVO> orgList = orgSearchPopupService.getOrgListByAuth(orgSearch);
        model.addAttribute("orgList", orgList);

        return URL + "/ajax/orgSearchPopup";
    }

    /**
     * <PRE>
     * 1. ClassName		: OrgSearchPopupController
     * 2. MethodName	: getOrgListPopupForTree
     * 3. Comment		: 조직 목록 조회 For Tree
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 10. 오후 1:15:16
     *	- 
     * </PRE>
     * @param model
     * @param request
     * @param orgSearch
     * @return
     */
    @RequestMapping(value = "getOrgListPopupForTree", method = RequestMethod.POST)
    public @ResponseBody Object getOrgListPopupForTree(Model model, HttpServletRequest request, OrgSearchPopupVO orgSearch) {

        // 조직 목록 조회
        List<Map<String, Object>> orgList = orgSearchPopupService.getOrgListByAuthForTree(orgSearch);
        return orgList;
    }

}
